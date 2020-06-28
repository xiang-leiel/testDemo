package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2020/2/5 3:08 PM
 */
public enum GroupRelationEnum {

    JING_AN(0,"长三角工作部","长三角工作部"),
    JIA_DING(1,"上海一组","高新区二局"),
    MIN_HANG(2,"上海二组","高新区三局"),
    QING_PU(3,"上海三组","高新区四局"),
    SONG_JIANG(4,"上海四组","经开区"),
    PU_DONG(5,"上海五组","莫干山旅游度假区"),
    JIN_SHAN(6,"上海六组","高新区一局"),
    BAO_SHAN(7,"上海七组","高新区二局"),
    XU_HUI(8,"外资产业组","高新区二局"),
    SU_ZHOU(9,"高端装备组","商务局机动组"),
    HANG_ZHOU(10,"生物医药组","高新区二局"),
    HU_BEI(11,"地理信息组","高新区二局"),
    CHANG_NING(12,"通航产业组","高新区一局"),
    BEI_JING(13,"北京工作部","北京工作部"),
    SHEN_ZHEN(14,"深圳工作部","深圳工作部");

    private int code;

    private String group;

    private String department;

    private GroupRelationEnum(int code, String group, String department) {
        this.code = code;
        this.group = group;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public static String getdepartmentByGroup(String group) {
        for (GroupRelationEnum result : values()) {
            if (result.getGroup().equals(group)) {
                return result.getDepartment();
            }
        }
        return null;
    }
}
