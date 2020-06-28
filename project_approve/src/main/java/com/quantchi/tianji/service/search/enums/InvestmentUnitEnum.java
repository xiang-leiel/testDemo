package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description 产业项目金额枚举
 * @Date 2019/12/26 4:33 PM
 */
public enum InvestmentUnitEnum {

    LESS_ONE(0,"<1亿"),
    ONE_FIVE(1,"1-5亿"),
    FIVE_TEN(2,"5-10亿"),
    TEN_TWENTY(3,"10-20亿"),
    TWENTY_FIFTY(4,"20-50亿"),
    FIFTY_HUNDRED(5,"50-100亿"),
    BEYOND_HUNDRED(6,">100亿"),
    TOP_500(7,"500强"),
    STATE_ENTERPRISE(8,"国企央企");

    private int code;

    private String desc;

    private InvestmentUnitEnum(int code, String desc) {
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
        for (InvestmentUnitEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }
}
