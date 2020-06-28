package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/20 1:37 PM
 */

public enum TeaEnum {

    GREEN_TEA(0,"绿茶"),
    RED_TEA(1,"红茶"),
    OOLONG_TEA(2,"乌龙茶"),
    WHITE_TEA(3,"白茶"),
    YELLOW_TEA(4,"黄茶"),
    BLACK_TEA(5,"黑茶");

    private int code;

    private String desc;

    private TeaEnum(int code, String desc) {
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
        for (TeaEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }

}
