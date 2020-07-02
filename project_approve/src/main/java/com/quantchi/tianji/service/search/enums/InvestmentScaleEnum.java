package com.quantchi.tianji.service.search.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leiel
 * @Description 产业项目金额枚举
 * @Date 2019/12/26 4:33 PM
 */
public enum InvestmentScaleEnum {

    LESS_ONE(1007,"1亿以下"),
    ONE_FIVE(1001,"1-5亿"),
    FIVE_TEN(1002,"5-10亿"),
    TEN_TWENTY(1003,"10-20亿"),
    TWENTY_FIFTY(1004,"20-50亿"),
    FIFTY_HUNDRED(1005,"50-100亿"),
    BEYOND_HUNDRED(1006,">100亿"),;

    private int code;

    private String desc;

    private InvestmentScaleEnum(int code, String desc) {
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
        for (InvestmentScaleEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }

    public static List<Integer> getList() {
        List<Integer> codes = new ArrayList<>();

        for (InvestmentScaleEnum result : values()) {
            codes.add(result.getCode());
        }
        return codes;

    }
}
