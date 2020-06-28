package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/17 9:09 AM
 */
public enum ProjectStatusEnum {

    WAIT_VISIT(0,"待拜访"),
    WAIT_REPORT(1,"待上报"),
    WAIT_JUDGE(2,"待研判"),
    WAIT_CONTRACT(3,"待签约"),
    WAIT_STATION(4,"待入驻"),
    STATIONED(5,"已入驻"),
    REFUSE(6,"未通过/叫停"),
    INVALID(10,"无效同行数据");

    private int code;

    private String desc;

    private ProjectStatusEnum(int code, String desc) {
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
        for (ProjectStatusEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }

}
