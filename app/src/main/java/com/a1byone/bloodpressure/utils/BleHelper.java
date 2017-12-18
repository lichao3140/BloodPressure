package com.a1byone.bloodpressure.utils;

import android.bluetooth.BluetoothGattCharacteristic;
import android.text.TextUtils;
import android.util.Log;

import com.a1byone.bloodpressure.bean.Records;
import com.a1byone.bloodpressure.service.BluetoothLeService;
import com.holtek.libHTBodyfat.HTBodyfatGeneral;
import com.holtek.libHTBodyfat.HTDataType;

import java.util.Date;

/**
 * BLE工具类
 * Created by Administrator on 2017-12-18.
 */

public class BleHelper {
    private static final String TAG = "BleHelper";
    private BleHelper(){}
    private static BleHelper bleHelper = null;

    public static synchronized BleHelper getInstance() {
        if (bleHelper == null) {
            bleHelper = new BleHelper();
        }
        return bleHelper;
    }

    /**
     * 获取下发人体参数
     * @param group  用户组号  必须0-9
     * @param gender  性别  0-女 1-男
     * @param level   级别  0-普通人 1-业余  2-专业运动员
     * @param height  身高 cm厘米 单位
     * @param age    年龄
     * @param danwei  单位  参考 BluetoolUtil中单位常量
     * @return
     */
    public static String getUserInfo(int group,int gender,int level,int height ,int age,String danwei) {
        StringBuffer sb = new StringBuffer();
        sb.append("0").append(group);
        sb.append("0").append(gender);
        sb.append("0").append(level);
        if (height > 15) {
            sb.append(Integer.toHexString(height));
        } else {
            sb.append("0" + Integer.toHexString(height));
        }

        if (age > 15) {
            sb.append(Integer.toHexString(age));
        } else {
            sb.append("0" + Integer.toHexString(age));
        }

        if (danwei.equals(BlueToolUtil.UNIT_KG)) {
            sb.append("01");
        } else if (danwei.equals(BlueToolUtil.UNIT_LB)) {
            sb.append("02");
        } else if (danwei.equals(BlueToolUtil.UNIT_ST)) {
            sb.append("04");
        } else if (danwei.equals(BlueToolUtil.UNIT_FATLB)) {
            sb.append("02");
        } else {
            sb.append("01");
        }

        String bcc = StringUtils.getBCC(StringUtils.hexStringToByteArray(sb.toString()));
        String out = "FE" + sb.toString() + bcc;
        return out;
    }

    /**
     * 开启监听阿里通道
     * @param mBluetoothLeService
     */
    public  void listenAliScale(BluetoothLeService mBluetoothLeService){
        if(null!=mBluetoothLeService){
            // 监听 阿里秤 读通道
            final BluetoothGattCharacteristic characteristic = mBluetoothLeService.getCharacteristicNew(mBluetoothLeService.getSupportedGattServices(), "fff4");
            mBluetoothLeService.setCharacteristicIndaicate(characteristic, true); // 开始监听通道
        }
    }

    /**
     * 构建阿里秤 发送数据
     * @param unit  单位  00, 01, 02
     * @param group 用户组 00,01,02,03....
     */
    public String assemblyAliData(String unit,String group){
        // 获取 校验位
        String xor = Integer.toHexString(StringUtils.hexToTen("fd") ^ StringUtils.hexToTen("37")^ StringUtils.hexToTen(unit) ^ StringUtils.hexToTen(group));
        return "fd37"+unit + group + "000000000000" + xor;
    }

    /**
     * 解析得力称端数据
     * @param readMessage 称端发送上来数据
     * @param height  身高  cm厘米 单位
     * @param sex      性别  0-女 1-男
     * @param age      年龄
     * @param level    级别   0-普通人 1-业余  2-专业运动员
     * @return  CF 88 13 00 14 00 00 00 00 00 40
     */
    public Records parseDLScaleMeaage(String readMessage, float height, int sex, int age, int level) {
        Log.e("test", "解析数据：" + readMessage);
        Records recod = null;
        if(TextUtils.isEmpty(readMessage) && readMessage.length()<22){
            return null;
        }
        double weight = 0;
        int impedance = 0;
        recod = new Records();
        String scaleType = readMessage.substring(0,2);
        impedance = (int)(StringUtils.hexToTen(readMessage.substring(4,6)+readMessage.substring(2,4))*0.1f);
        impedance = StringUtils.hexToTen(readMessage.substring(14, 16)+readMessage.substring(12, 14) + readMessage.substring(10, 12));
        //String unit = readMessage.substring(16, 18);
        weight = StringUtils.hexToTen(readMessage.substring(8, 10)+readMessage.substring(6, 8))*0.01d;
        String unit = readMessage.substring(16, 18);
        recod.setScaleType(scaleType);
        recod.setSex(sex+"");
        recod.setRweight((float)weight);
        recod.setRecordTime(UtilTooth.dateTimeChange(new Date()));

        if (unit.equals("00")) {//kg
            recod.setUnitType(0);
        } else if (unit.equals("01")) {//lb
            recod.setUnitType(1);
        } else if (unit.equals("02")) {//st
            recod.setUnitType(2);
        } else if (unit.equals("03")) {//斤
            recod.setUnitType(3);
        } else if (unit.equals("04")) {//g
            recod.setUnitType(4);
        }else if (unit.equals("05")) {//lb:oz
            recod.setUnitType(5);
        }else if (unit.equals("06")) {//oz
            recod.setUnitType(6);
        }else if (unit.equals("07")) {//ml(water)
            recod.setUnitType(7);
        }else if (unit.equals("08")) {//ml(milk)
            recod.setUnitType(8);
        }else {
            recod.setUnitType(0);
        }

        try {
            HTBodyfatGeneral bodyfat = new HTBodyfatGeneral(weight,height,sex, age, level, impedance);

            Log.e(TAG, "输入参数==>体重："+weight+"  身高:"+height+"  性别:"+sex+"  年龄:"+age+"  类型:"+level+"  阻抗:"+impedance);

            Log.e(TAG, "计算结果==>" + bodyfat.getBodyfatParameters()+"====阻抗系数++>"+impedance);

            if(bodyfat.getBodyfatParameters() == HTDataType.ErrorNone){
                recod.setRweight((float)weight);
                //正常计算
                recod.setRbmi(UtilTooth.keep1Point3(bodyfat.BMI));
                recod.setRbmr((int)bodyfat.BMR);
                recod.setRbodyfat(UtilTooth.keep1Point3(bodyfat.bodyfatPercentage));
                recod.setRbodywater(UtilTooth.keep1Point3(bodyfat.waterPercentage));
                recod.setRbone(UtilTooth.keep1Point3(bodyfat.boneKg));
                recod.setRmuscle(UtilTooth.keep1Point3(bodyfat.muscleKg));
                recod.setRvisceralfat((int) bodyfat.VFAL);

                Log.e(TAG, "阻抗:" + bodyfat.ZTwoLegs +
                        "Ω  BMI:" + String.format("%.1f",bodyfat.BMI) +
                        "  BMR:" + (int) bodyfat.BMR +
                        "  内脏脂肪:" + (int) bodyfat.VFAL +
                        "  骨量:" + String.format("%.1fkg",bodyfat.boneKg) +
                        "  脂肪率:" +String.format("%.1f%%",bodyfat.bodyfatPercentage) +
                        "  水分:" + String.format("%.1f%%",bodyfat.waterPercentage) +
                        "  肌肉:" + String.format("%.1fkg",bodyfat.muscleKg) + "\r\n");
            }else {
                recod.setRbmi(UtilTooth.myround(UtilTooth.countBMI2(recod.getRweight(), height / 100)));
                Log.e(TAG, "输入数据有误==>" + bodyfat.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "解析数据异常==>" + e.getMessage());
        }
        return recod;
    }


    /**
     * 解析称端数据
     * @param readMessage 称端发送上来数据
     * @param height  身高  cm厘米 单位
     * @param sex      性别  0-女 1-男
     * @param age      年龄
     * @param level    级别   0-普通人 1-业余  2-专业运动员
     * @return
     */
    public Records parseScaleData(String readMessage,double height,int sex,int age,int level){
        if(TextUtils.isEmpty(readMessage) || readMessage.length()<35){return null;}
        Records recod = new Records();
        double weight = StringUtils.hexToTen(readMessage.substring(24, 26)+readMessage.substring(22, 24))*0.01d;
        int impedance = StringUtils.hexToTen(readMessage.substring(34, 36)+readMessage.substring(32, 34) + readMessage.substring(30, 32));
        HTBodyfatGeneral bodyfat = new HTBodyfatGeneral(weight,height,sex, age, level, impedance);
        if(bodyfat.getBodyfatParameters() == HTDataType.ErrorNone){
            //正常计算
            recod.setRweight((float)weight);
            recod.setRbmi(UtilTooth.keep1Point3(bodyfat.BMI*0.1f));
            recod.setRbmr((int)bodyfat.BMR);
            recod.setRbodyfat(UtilTooth.keep1Point3(bodyfat.bodyfatPercentage));
            recod.setRbodywater(UtilTooth.keep1Point3(bodyfat.waterPercentage));
            recod.setRbone(UtilTooth.keep1Point3(bodyfat.boneKg));
            recod.setRmuscle(UtilTooth.keep1Point3(bodyfat.muscleKg));
            recod.setRvisceralfat((int) bodyfat.VFAL);
            float bmi = UtilTooth.countBMI2(recod.getRweight(), (float) (height / 100));
            recod.setBodyAge(UtilTooth.getPhysicAge(bmi,age));
            recod.setEffective(true);

            Log.i("记录", "阻抗:" + bodyfat.ZTwoLegs +
                    "Ω  BMI:" + String.format("%.1f",bodyfat.BMI) +
                    "  BMR:" + (int) bodyfat.BMR +
                    "  内脏脂肪:" + (int) bodyfat.VFAL +
                    "  骨量:" + String.format("%.1fkg",bodyfat.boneKg) +
                    "  脂肪率:" +String.format("%.1f%%",bodyfat.bodyfatPercentage) +
                    "  水分:" + String.format("%.1f%%",bodyfat.waterPercentage) +
                    "  肌肉:" + String.format("%.1fkg",bodyfat.muscleKg) + "\r\n");
        }else {
            recod.setEffective(false);
            recod.setRbmi(UtilTooth.countBMI2(recod.getRweight(), (float) (height / 100)));
        }
        return recod;
    }


    /**
     * 发送 数据给称
     * @param mBluetoothLeService
     * @param data
     */
    public void sendDateToScale(BluetoothLeService mBluetoothLeService,String data) {
        if(TextUtils.isEmpty(data) || null==mBluetoothLeService) return;
        // 通知秤
        final BluetoothGattCharacteristic characteristic2 = mBluetoothLeService.getCharacteristic(mBluetoothLeService.getSupportedGattServices(), "fff4");
        if (characteristic2 != null) {
            mBluetoothLeService.setCharacteristicNotification(characteristic2, true);
        }
        try {
            Thread.sleep(400);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取秤写通道
        final BluetoothGattCharacteristic characteristic = mBluetoothLeService.getCharacteristic(mBluetoothLeService.getSupportedGattServices(), "fff1");
        if (characteristic != null) {
            final byte[] dataArray = StringUtils.hexStringToByteArray(data);
            characteristic.setValue(dataArray);
            mBluetoothLeService.wirteCharacteristic(characteristic);// 发送数据
            characteristic.getProperties();
        }
        try {
            Thread.sleep(500);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取秤写通道
        //final BluetoothGattCharacteristic characteristic = mBluetoothLeService.getCharacteristic(mBluetoothLeService.getSupportedGattServices(), "fff1");
        if (characteristic != null) {
            final byte[] dataArray = StringUtils.hexStringToByteArray(data);
            characteristic.setValue(dataArray);
            mBluetoothLeService.wirteCharacteristic(characteristic);// 发送数据
            characteristic.getProperties();
        }
    }
}
