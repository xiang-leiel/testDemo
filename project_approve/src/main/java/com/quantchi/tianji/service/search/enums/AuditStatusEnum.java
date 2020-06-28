package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2020/2/7 3:34 PM
 */

public enum AuditStatusEnum {

    JING_AN(0,"高新区一局","2,3,4"),
    JIA_DING(1,"高新区二局","2,3,4"),
    MIN_HANG(2,"高新区三局","2,3,4"),
    QING_PU(3,"高新区四局","2,3,4"),
    SONG_JIANG(4,"经开区","2,3,4"),
    PU_DONG(5,"莫干山旅游度假区","2,3,4"),
    SU_ZHOU(6,"商务局机动组","2,3,4"),
    CHANG_NING(7,"长三角工作部","2,3,4"),
    BEI_JING(8,"北京工作部","2,3,4"),
    SHEN_ZHEN(9,"深圳工作部","2,3,4"),
    SHANGWU(10,"商务局","5,6,7,8,9,10,11,12,13,14");

    private int code;

    private String group;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    private AuditStatusEnum(int code, String group, String status) {
        this.code = code;
        this.group = group;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    public static String getdepartmentByGroup(String group) {
        for (AuditStatusEnum result : values()) {
            if (result.getGroup().equals(group)) {
                return result.getStatus();
            }
        }
        return null;
    }
}
