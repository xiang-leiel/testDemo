package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description 项目标签
 * @Date 2019/12/20 3:01 PM
 */
public enum ProjectSpecialTagsEnum {

    RESEARCH_INSTITUTION(1,"科研机构"),
    FOREIGN_PROJECT(2,"外资项目"),
    TALENT_PROJECT(3,"高层次人才项目"),
    EXPEDITIONED(4,"已县级考察"),
    GEOGRAPHIC_INFORMATION(5,"地理信息行业"),
    INDUSTRIAL_PROJECT(6,"产业项目"),
    BETTER_PROJECT(7,"优质项目标签"),
    DOCTOR_PROJECT(8,"博士及博士后领衔团队项目"),
    ACADEMICIAN_PROJECT(9,"院士领衔团队项目"),
    CITY_ECONIMIC(10,"城市经济项目"),
    TRADE_ECONIMIC(11,"外贸项目"),
    SHANGHAI_PROJECT(12,"亿元以上沪资项目"),;

    private int code;

    private String desc;

    private ProjectSpecialTagsEnum(int code, String desc) {
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
        for (ProjectSpecialTagsEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }

}
