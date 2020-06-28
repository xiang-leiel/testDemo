package com.quantchi.tianji.service.search.enums;
/**
 * @Description 1、存量  2、新征  3、待定  4、租赁厂房
 * @author leiel
 * @Date 2020/4/21 3:40 PM
 */

public enum NeedLandEnums {

    STOCK(1,"存量"),
    NEW_SIGN(2,"新征"),
    DETERMIN(3,"待定"),
    RENTAL(4,"租赁厂房");

    private int code;

    private String desc;

    private NeedLandEnums(int code, String desc) {
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
        for (NeedLandEnums result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
