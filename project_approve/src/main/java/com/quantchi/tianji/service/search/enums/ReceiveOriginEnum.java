package com.quantchi.tianji.service.search.enums;
/**
 * @Description 接待人来源枚举类
 * @author leiel
 * @Date 2019/12/16 5:15 PM
 */
public enum ReceiveOriginEnum {

    CONFERENCE(0,"会议（展会、峰会等）"),
    ALUMNA_CONFERENCE(1,"校友会"),
    INVEST_COMPANY(2,"投资公司"),
    FUND_COMPANY(3,"基金公司"),
    CHAMBER_COMMERCE(4,"商会"),
    UNION(5,"协会"),
    GOVERNMENT(6,"政府机构"),
    UNIVERSITY_OFFICE(7,"大学办事处"),
    AGENT(8,"中介");

    private int code;

    private String desc;

    private ReceiveOriginEnum(int code, String desc) {
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
        for (ReceiveOriginEnum result : values()) {
            if (result.getCode() == code) {
                return result.getDesc();
            }
        }

        return null;
    }

}
