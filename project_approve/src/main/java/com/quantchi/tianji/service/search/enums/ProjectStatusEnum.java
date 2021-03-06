package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/17 9:09 AM
 */
public enum ProjectStatusEnum {

    WAIT_VISIT(0,"待拜访"),
    WAIT_REPORT(1,"待上报"),
    WAIT_FIRST_AUDIT(2,"待初审"),
    WAIT_AGAIN_AUDIT(3,"待复审"),
    WAIT_JUDGE(4,"待研判"),
    WAIT_EVALUATION(5,"待预评价"),
    WAIT_SIGN(6,"待签约"),
    SIGNED(7,"签约完成"),
    END(1001,"终止"),
    PAUSE(1002,"暂缓");

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
