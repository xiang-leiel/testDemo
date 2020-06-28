package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/12 9:27 AM
 */
public enum JobEnum {

    MEMBER(0,"组员"),
    LEADER(1,"组长");

    private int code;

    private String desc;

    private JobEnum(int code, String desc) {
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
        for (JobEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
