package com.a1byone.bloodpressure.utils;

/**
 * 单位枚举
 * Created by Administrator on 2017-12-18.
 */

public enum Units {
    UNIT_KG("00","kg"),
    UNIT_LB("01","lb"),
    UNIT_ST("02","st");

    private String  code;
    private String  desc;


    Units(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
