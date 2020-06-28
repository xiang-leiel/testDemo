package com.quantchi.tianji.service.search.enums;

import io.swagger.models.auth.In;

/**
 * @author leiel
 * @Description
 * @Date 2020/2/11 3:17 PM
 */

public enum IndustyEnum {

    YICHAN(1,"一产"),
    ERCHAN(2,"二产"),
    SANCHAN(3,"三产");

    private int code;

    private String desc;

    private IndustyEnum(int code, String desc) {
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
        for (IndustyEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }

    public static Integer getCodeByDesc(String desc) {
        for (IndustyEnum result : values()) {
            if (result.getDesc().equals(desc)) {
                return result.getCode();
            }
        }

        return null;
    }

}
