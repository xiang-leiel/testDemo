package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2020/2/7 1:19 PM
 */

public enum FlowTypeEnum {

    REPORT(1,"上报"),
    FIRST(2,"初审"),
    AGAIN(2,"复审"),
    JUDGE(3,"研判");

    private int code;

    private String desc;

    private FlowTypeEnum(int code, String desc) {
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
        for (FlowTypeEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
