package com.a1byone.bloodpressure.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017-12-18.
 */

@SuppressLint("SimpleDateFormat")
public class UtilTooth {
    public static final int BAR_WEIGHT_SUM = 170;
    public static final int BAR_WEIGHT_THIN = 27;
    public static final int BAR_WEIGHT_HEALTHY = 73;
    public static final int BAR_WEIGHT_FAT = 40;
    public static final int BAR_WEIGHT_OVERWEIGHT = 30;


    /**
     * 计算身体年龄
     * @param bmi
     * @param age
     * @return
     */
    public static float getPhysicAge(float bmi,int age){
        float physicAge = 0;

        if(bmi<22){
            float temp = (age - 5) + ((22-bmi)*(5/(22-18.5f)));
            if(Math.abs(temp-age)>=5){
                physicAge=age+5;
            }else{
                physicAge = temp;
            }
        }else if(bmi==22){
            physicAge=age-5;
        }else if(bmi>22){
            float temp = (age - 5) + ((bmi-22)*(5/(24.9f-22)));
            if(Math.abs(temp-age)>=8){
                physicAge=age+8;
            }else{
                physicAge = temp;
            }
        }

        return physicAge;
    }

    public static float keep1Point3(double kg) {
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }


    public static int kgToML(float tempKg) {
        BigDecimal b0 = new BigDecimal(String.valueOf(tempKg));
        BigDecimal b1 = new BigDecimal("63701");
        BigDecimal b4 = new BigDecimal("65536");
        BigDecimal b3 = new BigDecimal(String.valueOf(b0.multiply(b1).doubleValue())).divide(b4, 0, BigDecimal.ROUND_HALF_UP);

        return b3.intValue();

    }

    public static float kgToLB_ForFatScale3(float tempKg){
        BigDecimal b0 = new BigDecimal(String.valueOf(tempKg));
        BigDecimal b1 = new BigDecimal("1155845");
        BigDecimal b2 = new BigDecimal("16");
        BigDecimal b4 = new BigDecimal("65536");
        BigDecimal b5 = new BigDecimal("2");
        BigDecimal b3 = new BigDecimal(String.valueOf(b0.multiply(b1).doubleValue())).divide(b2,5,BigDecimal.ROUND_HALF_EVEN).divide(b4, 1, BigDecimal.ROUND_HALF_UP);
        float templb = b3.multiply(b5).floatValue();
        return templb;
    }

    public static double kgToLB_ForFatScale2(double tempKg){
        BigDecimal b0 = new BigDecimal(String.valueOf(tempKg));
        BigDecimal b1 = new BigDecimal("1155845");
        BigDecimal b2 = new BigDecimal("16");
        BigDecimal b4 = new BigDecimal("65536");
        BigDecimal b5 = new BigDecimal("2");
        BigDecimal b3 = new BigDecimal(String.valueOf(b0.multiply(b1).doubleValue())).divide(b2,5,BigDecimal.ROUND_HALF_EVEN).divide(b4, 1, BigDecimal.ROUND_HALF_UP);
        double templb = b3.multiply(b5).doubleValue();
        return templb;
    }

    public static float kgToLB_target(float tempKg) {
        float templb = 2.2046226f*tempKg;
        BigDecimal b = new BigDecimal(templb);
        float f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static float lbToKg_target(float lb) {
        float kg = lb * 0.4535924f;
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static String onePoint(float lb) {
        BigDecimal b = new BigDecimal(lb);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    /**成人*/
    public static float changeBMI(float bmi, int width) {
        float unit = ((float) width / BAR_WEIGHT_SUM);
        float unitThin = ((float) unit * BAR_WEIGHT_THIN / 18.5f); // 0-18.5
        float unitHealthy = ((float) unit * BAR_WEIGHT_HEALTHY / 5.5f); // 18.5-24
        float unitFat = ((float) unit * BAR_WEIGHT_FAT / 4); // 24-28
        float unitOverWeight = ((float) unit * BAR_WEIGHT_OVERWEIGHT / 11); // 28-39
        if (bmi < 18.5) {
            // 0-18.5
            bmi = bmi * unitThin;
        } else if (bmi < 24) {
            // 18.5-24
            bmi = (unit * BAR_WEIGHT_THIN) + ((bmi - 18.5f) * unitHealthy);
        } else if (bmi < 28) {
            // 24-28
            bmi = (unit * (BAR_WEIGHT_THIN + BAR_WEIGHT_HEALTHY)) + ((bmi - 24) * unitFat);
        } else if (bmi < 39) {
            // 28-39
            bmi = (unit * (BAR_WEIGHT_THIN + BAR_WEIGHT_HEALTHY + BAR_WEIGHT_FAT)) + ((bmi - 28) * unitOverWeight);
        } else {
            bmi = width - 8;
        }

        return bmi - 5;
    }

    /**婴儿*/
    public static float changeBMIBaby(float bmi, int width) {
        float unit = ((float) width / BAR_WEIGHT_SUM);
        float unitThin = ((float) unit * BAR_WEIGHT_THIN / 15); // 0-15
        float unitHealthy = ((float) unit * BAR_WEIGHT_HEALTHY / 3); // 15-18
        float unitFat = ((float) unit * BAR_WEIGHT_FAT / 4); // 18-22
        float unitOverWeight = ((float) unit * BAR_WEIGHT_OVERWEIGHT / 8); // 22-30
        if (bmi < 15) {
            // 0-15
            bmi = bmi * unitThin;
        } else if (bmi < 18) {
            // 15-18
            bmi = (unit * BAR_WEIGHT_THIN) + ((bmi - 15) * unitHealthy);
        } else if (bmi < 22) {
            // 18-22
            bmi = (unit * (BAR_WEIGHT_THIN + BAR_WEIGHT_HEALTHY)) + ((bmi - 18) * unitFat);
        } else if (bmi < 30) {
            // 22-30
            bmi = (unit * (BAR_WEIGHT_THIN + BAR_WEIGHT_HEALTHY + BAR_WEIGHT_FAT)) + ((bmi - 22) * unitOverWeight);
        } else {
            bmi = width - 8;
        }

        return bmi - 5;
    }

    public static float myround(float f) {
        float templ = Math.round(f * 10);
        float retd = (float) (templ / 10.0);
        return retd;
    }

    public static String myroundString3(String f) {
        if (f == null) {
            return "";
        }

        int pIndex = f.indexOf(".");
        int eIndex = pIndex + 3;
        if (eIndex >= f.length()) {
            return f;
        } else {
            return f.substring(0, eIndex);
        }
    }

    public static String myroundString(String f) {
        if (f == null) {
            return "";
        }
        int pIndex = f.indexOf(".");
        int eIndex = pIndex + 2;
        if (eIndex >= f.length()) {
            return f;
        } else {
            return f.substring(0, eIndex);
        }
    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static float myround2(float f) {
        float templ = Math.round(f * 100);
        float retd = (float) (templ / 100.0);
        return retd;
    }

    public static String dateTimeChange(Date source) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String changeTime = format.format(source);
        return changeTime;
    }

    public static Date stringToTime(String time) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = null;
        try {
            date2 = df2.parse(time);
        } catch (ParseException e) {
        }
        return date2;
    }

    public static String kgToLB(float kg) {
        return kgToLB_new(kg);
    }

    public static String kgToLbForScaleBaby(float kg) {
        float lb = (float) (kg * 2.2046);
        int lbint = (int) (lb / 1);
        float lbloat = (lb % 1) * 16;
        lbloat = myround(lbloat);
        return lbint + ":" + lbloat;
    }

    public static String kgToLbForScaleBaby_china(float kg) {
        float lb = (float) (kg * 0.1574803);
        return myround2(lb) + "";
    }

    public static String keep0Point(float kg) {
        BigDecimal b = new BigDecimal(kg);
        int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return String.valueOf(f1);
    }

    public static String keep2Point(float kg) {
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }
    public static float keep1Point2(float kg) {
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static float kgToLB_F(float kg) {
        float templb = ((kg * 10 * 10 * 0x2B0F / 0xC350 + 1) / 2) * 2 / 10;
        BigDecimal b = new BigDecimal(templb);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static String lbToLBOZ(float lb) {
        int lbint = (int) (lb / 1);
        float lbloat = (lb % 1) * 16;
        lbloat = myround(lbloat);
        return lbint + ":" + lbloat;
    }
    public static double lbToLBOZ_F(float kg) {
        float lb = (float) (kg * 2.2046);
        int lbint = (int) (lb / 1);
        float lbloat = (lb % 1) * 16;
        BigDecimal b = new BigDecimal(lbloat);
        lbloat = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String strLB = ((myroundString(lbloat + "")).replace(".", "47"));
        strLB += "0556";

        BigDecimal b1 = new BigDecimal(Float.toString(lbint));
        BigDecimal b2 = new BigDecimal("0." + strLB);

        double re = b1.add(b2).doubleValue();
        return re;
    }

    public static double lbToLBOZ_F(double kg) {
        float lb = (float) (kg * 2.2046);
        int lbint = (int) (lb / 1);
        float lbloat = (lb % 1) * 16;
        BigDecimal b = new BigDecimal(lbloat);
        lbloat = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String strLB = ((myroundString(lbloat + "")).replace(".", "47"));
        strLB += "0556";

        BigDecimal b1 = new BigDecimal(Float.toString(lbint));
        BigDecimal b2 = new BigDecimal("0." + strLB);

        double re = b1.add(b2).doubleValue();

        return re;
    }



    public static String gToLb(float lb) {
        float kg = (float) (lb * 0.0022046);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    public static String gToOz(float oz) {
        float kg = (float) (oz * 0.035274);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    public static String gTogS(float g) {
        float kg = (float) (g * 1);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    public static float lbTog(float lb) {
        float kg = (float) (lb * 453.59237);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static float gTog(float lb) {
        float kg = (float) (lb * 1);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static float ozTog(float lb) {
        float kg = (float) (lb * 28.3495231);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static String lbTog_int(float lb) {
        float kg = (float) (lb * 453.59237);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    public static String lbTog_new(float lb) {
        float kg = (float) (lb * 453.59237);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    public static String kgToLB_new(float tempKg) {
        float templb = (((144479*(int)(tempKg*10)+32768)/65536 +1)&0xFFFE)/10f;
        return String.valueOf(templb);
    }

    public static String lbToKg_new(float lb) {
        float kg = (float) (lb * 0.45359);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(f1);
    }

    public static String lbToKg2_new(float lb) {
        float kg = (float) (lb * 0.45359);
        BigDecimal b = new BigDecimal(kg);
        float f1 = b.setScale(5, BigDecimal.ROUND_FLOOR).floatValue();
        return String.valueOf(f1);
    }

    public static String toOnePonit(float lb) {
        BigDecimal b = new BigDecimal(lb);
        float f1 = b.setScale(1, BigDecimal.ROUND_FLOOR).floatValue();
        return String.valueOf(f1);
    }

    public static String kgToFloz(float tempKg){
        float wei = tempKg*10;
        float temp = (2311*wei+32768);
        float temp2 = (temp/65536)*0.1f;

        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(1);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(temp2);
        //return String.valueOf(b3.floatValue());
    }

    public static String kgToLBoz(float tempKg){
        //float wei = tempKg*10;
        float temp = (23117*tempKg+32768)/65536;

        int lb = (int)(temp*0.1f)/16;
        float oz = (temp*0.1f)%16;

        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(1);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);

        return lb+":"+formater.format(oz);
    }

    public static String kgToLB_ForBodyScale(float tempKg){
        float wei = tempKg*10;
        float temp = (144479*wei+32768)/65536+1;
        int tempInt = (int) Math.floor(temp);

        if(tempInt%2!=0){
            tempInt = tempInt - 1;
        }
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(1);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(tempInt*0.1);
    }

    public static String kgToLB_ForFatScale(float tempKg){
        BigDecimal b0 = new BigDecimal(String.valueOf(tempKg));
        BigDecimal b1 = new BigDecimal("1155845");
        BigDecimal b2 = new BigDecimal("16");
        BigDecimal b4 = new BigDecimal("65536");
        BigDecimal b5 = new BigDecimal("2");
        BigDecimal b3 = new BigDecimal(String.valueOf(b0.multiply(b1).doubleValue())).divide(b2,5,BigDecimal.ROUND_HALF_EVEN).divide(b4, 1, BigDecimal.ROUND_HALF_UP);
        float templb = b3.multiply(b5).floatValue();
        return String.valueOf(templb);
    }

    public static String kgToStLb(float kg) {
        float st = (float) (kg * 0.1575);
        int stint = (int) (st / 1);
        int lb = (int) ((st - stint) * 15 / 1);
        return stint + ":" + lb;
    }

    public static String kgToStLb_china(float kg) {
        float st = (float) (kg * 0.1574803);
        return myround2(st) + "";
    }

    public static String kgToStLbForScaleFat(float kg) {
        float st = (float) (kg * 0.1575);
        int stint = (int) (st / 1);
        int lbint = (int) ((st - stint) * 15 / 1);
        float lbfloat = ((st - stint) * 15 % 1);

        String fen = "";
        if (lbfloat >= 0 && lbfloat < 0.125) {

        } else if (lbfloat >= 0.125 && lbfloat < 0.375) {
            fen = "1/4";
        } else if (lbfloat >= 0.375 && lbfloat < 0.625) {
            fen = "1/2";
        } else if (lbfloat >= 0.625 && lbfloat < 0.875) {
            fen = "3/4";
        } else if (lbfloat >= 0.875 && lbfloat < 1) {
            fen = "";
        }
        if (fen.equals("")) {
            return stint + ":" + lbint;
        } else {
            return stint + ":" + lbint + "(" + fen + ")";
        }
    }

    public static double kgToStLb_B(float kg) {
        float st = (float) (kg * 0.1575);
        int stint = (int) (st / 1);
        int lbint = (int) ((st - stint) * 15 / 1);
        float lbfloat = ((st - stint) * 15 % 1);

        String fen = "0";
        if (lbfloat >= 0 && lbfloat < 0.125) {

        } else if (lbfloat >= 0.125 && lbfloat < 0.375) {
            fen = "14"; // 1/4
        } else if (lbfloat >= 0.375 && lbfloat < 0.625) {
            fen = "12"; // 1/2
        } else if (lbfloat >= 0.625 && lbfloat < 0.875) {
            fen = "34"; // 3/4
        } else if (lbfloat >= 0.875 && lbfloat < 1) {
            fen = "0";
        }
        if (lbint < 10) {
            return Double.parseDouble(stint + ".0" + lbint + "474" + fen + "8787");
        } else {
            return Double.parseDouble(stint + "." + lbint + "474" + fen + "8787");
        }
    }

    public static double kgToStLb_F(float kg) {
        float st = (float) (kg * 0.1575);
        int stint = (int) (st / 1);
        int lbint = (int) ((st - stint) * 15 / 1);
        float lbfloat = ((st - stint) * 15 % 1);

        String fen = "0";
        if (lbfloat >= 0 && lbfloat < 0.125) {

        } else if (lbfloat >= 0.125 && lbfloat < 0.375) {
            fen = "14"; // 1/4
        } else if (lbfloat >= 0.375 && lbfloat < 0.625) {
            fen = "12"; // 1/2
        } else if (lbfloat >= 0.625 && lbfloat < 0.875) {
            fen = "34"; // 3/4
        } else if (lbfloat >= 0.875 && lbfloat < 1) {
            fen = "0";
        }
        if (lbint < 10) {
            return Double.parseDouble(stint + ".0" + lbint + "474" + fen + "8787");
        } else {
            return Double.parseDouble(stint + "." + lbint + "474" + fen + "8787");
        }
    }

    public static String[] kgToStLbForScaleFat2(float kg) {
        float st = (float) (kg * 0.1575);
        int stint = (int) (st / 1);
        int lbint = (int) ((st - stint) * 15 / 1);
        float lbfloat = ((st - stint) * 15 % 1);

        String fen = "";
        if (lbfloat >= 0 && lbfloat < 0.125) {

        } else if (lbfloat >= 0.125 && lbfloat < 0.375) {
            fen = "1/4";
        } else if (lbfloat >= 0.375 && lbfloat < 0.625) {
            fen = "1/2";
        } else if (lbfloat >= 0.625 && lbfloat < 0.875) {
            fen = "3/4";
        } else if (lbfloat >= 0.875 && lbfloat < 1) {
            fen = "";
        }
        return new String[]{stint + ":" + lbint, fen};
    }

    public static String lbToST(float lb) {
        float st = (float) (lb * 15);
        DecimalFormat myformat = new DecimalFormat("######.00");
        return myformat.format(st);
    }

    public static String lbToKg(float lb) {
        float kg = (float) (lb * 0.45359);
        DecimalFormat myformat = new DecimalFormat("######.00");
        Log.v("tag", "string2:" + myformat.format(kg));
        return myformat.format(kg);
    }

    public static String countBMI(float weight, float height) {
        float bmi = weight / (height * height);
        DecimalFormat myformat = new DecimalFormat("######.0");
        return myformat.format(bmi);
    }
    public static float countBMI2(float weight, float height) {
        float bmi = weight / (height * height);
        return bmi;
    }

    public static float cm2foot(float cm) {
        return cm / 30.48f;
    }

    public static float foot2cm(float foot) {
        return foot * 30.48f;
    }

    public static String cm2FT_IN(float cm) {
        int ft = (int) (cm / 30.48f);
        int in = (int) (Math.round((cm / 30.48f - ft) * 12));
        return ft + "'" + in + "\"";
    }

    public static int ft_in2CM(String ftin) {
        float cm = 0;
        int ftIndex = ftin.indexOf("'");
        int inIndex = ftin.indexOf("\"");
        if (ftIndex > 0) {
            int ft = Integer.parseInt(ftin.substring(0, ftIndex));
            cm = ft * 30.48f;
        }
        if (inIndex >= 0) {
            int in = Integer.parseInt(ftin.substring(ftIndex + 1, inIndex));
            cm += in / 12f * 30.48f;
        }
        return (int) cm;
    }

    public static int ft_in2CMArray(String[] ftin) {
        float cm = 0;
        if (ftin.length > 0) {
            int ft = Integer.parseInt(ftin[0]);
            cm = ft * 30.48f;
        }
        if (ftin.length >= 0) {
            int in = Integer.parseInt(ftin[1]);
            cm += in / 12f * 30.48f;
        }
        return (int) cm;
    }

    public static String[] cm2FT_INArray(float cm) {
        int ft = (int) (cm / 30.48f);
        int in = (int) (Math.round((cm / 30.48f - ft) * 12));
        if (in >= 12) {
            int mo = in / 12;
            int yu = in % 12;

            ft = ft + mo;
            in = yu;
        }
        return new String[]{ft + "", in + ""};
    }
}
