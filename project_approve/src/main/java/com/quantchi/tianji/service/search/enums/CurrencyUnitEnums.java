package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2020/7/1 5:12 PM
 */

public enum CurrencyUnitEnums {

    CNY(1,"人民币元"),
    HKD(2,"香港元"),
    JPY(3,"日元"),
    GBP(4,"英镑"),
    USD(5,"美元"),
    TWD(6,"新台湾元"),
    EUR(7,"欧元");

    private int code;

    private String desc;

    private CurrencyUnitEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (CurrencyUnitEnums result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
