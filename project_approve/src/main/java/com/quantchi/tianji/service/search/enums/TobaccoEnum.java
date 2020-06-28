package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/20 1:41 PM
 */

public enum TobaccoEnum {

    BAKE(0,"烤烟型"),
    MIX(1,"混合型"),
    CIGAR(2,"雪茄型"),
    OUT_FRAGRANT(3,"外香型");

    private int code;

    private String desc;

    private TobaccoEnum(int code, String desc) {
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
        for (TobaccoEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
