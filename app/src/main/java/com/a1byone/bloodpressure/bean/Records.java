package com.a1byone.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-18.
 */

public class Records implements Serializable {
    private static final long serialVersionUID = 1781102348190850847L;
    private int id;//id
    private int useId;//用户Id
    private String scaleType;//CF=脂肪秤CE=人体秤CB=婴儿秤CA=营养秤
    private String ugroup;//用户组
    private String recordTime;//接收时间
    private String compareRecord; //与上次数据对比
    private float rweight;//重量kg
    private float rbmi; //BMI
    private float rbone; // bone kg
    private float rbodyfat; // body fat 脂肪率%
    private float rmuscle; // Muscale Mass %
    private float rbodywater; // Body Water 水分率%
    private float rvisceralfat; // Visceral Fat
    private float rbmr; //BMR Kcal
    private String level;//绛夌骇
    private String sex;//鎬у埆
    private float bodyAge;//韬綋骞撮緞
    private int unitType;// 00-g,01=ml,02=lb,04-ml
    private boolean isEffective;//娴嬮噺缁撴灉鏄惁鏈夋晥

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public String getScaleType() {
        return scaleType;
    }

    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

    public String getUgroup() {
        return ugroup;
    }

    public void setUgroup(String ugroup) {
        this.ugroup = ugroup;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getCompareRecord() {
        return compareRecord;
    }

    public void setCompareRecord(String compareRecord) {
        this.compareRecord = compareRecord;
    }

    public float getRweight() {
        return rweight;
    }

    public void setRweight(float rweight) {
        this.rweight = rweight;
    }

    public float getRbmi() {
        return rbmi;
    }

    public void setRbmi(float rbmi) {
        this.rbmi = rbmi;
    }

    public float getRbone() {
        return rbone;
    }

    public void setRbone(float rbone) {
        this.rbone = rbone;
    }

    public float getRbodyfat() {
        return rbodyfat;
    }

    public void setRbodyfat(float rbodyfat) {
        this.rbodyfat = rbodyfat;
    }

    public float getRmuscle() {
        return rmuscle;
    }

    public void setRmuscle(float rmuscle) {
        this.rmuscle = rmuscle;
    }

    public float getRbodywater() {
        return rbodywater;
    }

    public void setRbodywater(float rbodywater) {
        this.rbodywater = rbodywater;
    }

    public float getRvisceralfat() {
        return rvisceralfat;
    }

    public void setRvisceralfat(float rvisceralfat) {
        this.rvisceralfat = rvisceralfat;
    }

    public float getRbmr() {
        return rbmr;
    }

    public void setRbmr(float rbmr) {
        this.rbmr = rbmr;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getBodyAge() {
        return bodyAge;
    }

    public void setBodyAge(float bodyAge) {
        this.bodyAge = bodyAge;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "Records{" +
                "体重=" + rweight +
                ", BMI=" + rbmi +
                ", 骨骼=" + rbone +
                ", 脂肪=" + rbodyfat +
                ", 肌肉=" + rmuscle +
                ", 水分=" + rbodywater +
                ", 内脏脂肪=" + rvisceralfat +
                ", BMR=" + rbmr +
                '}';
    }
}
