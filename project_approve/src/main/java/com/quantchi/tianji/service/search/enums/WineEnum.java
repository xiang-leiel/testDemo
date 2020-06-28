package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/20 1:49 PM
 */

public enum WineEnum {

    WHITE_WINE(0,"白酒"),
    BEER(1,"啤酒"),
    GRAPE_WINE(2,"葡萄酒"),
    YELLOW_WINE(3,"黄酒"),
    RICE_WINE(4,"米酒"),
    MEDICINAL_WINE(5,"药酒");

    private int code;

    private String desc;

    private WineEnum(int code, String desc) {
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
        for (WineEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
