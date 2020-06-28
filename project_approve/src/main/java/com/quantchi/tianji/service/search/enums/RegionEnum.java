package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2020/1/3 3:12 PM
 */

public enum RegionEnum {

    JING_AN(0,"长三角工作部","上海静安区"),
    JIA_DING(1,"上海一组","上海嘉定区"),
    MIN_HANG(2,"上海二组","上海闵行区"),
    QING_PU(3,"上海三组","上海青浦区"),
    SONG_JIANG(4,"上海四组","上海松江区"),
    PU_DONG(5,"上海五组","上海浦东新区"),
    JIN_SHAN(6,"上海六组","上海金山区"),
    BAO_SHAN(7,"上海七组","上海宝山区"),
    XU_HUI(8,"外资产业组","上海徐汇区"),
    SU_ZHOU(9,"高端装备组","江苏苏州"),
    HANG_ZHOU(10,"生物医药组","浙江杭州"),
    HU_BEI(11,"地理信息组","湖北武汉"),
    CHANG_NING(12,"通航产业组","上海长宁区"),
    BEI_JING(13,"北京工作部","北京市"),
    SHEN_ZHEN(14,"深圳工作部","深证/广州");

    private int code;

    private String group;

    private String region;

    private RegionEnum(int code, String group, String region) {
        this.code = code;
        this.group = group;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public static String getRegionByGroup(String group) {
        for (RegionEnum result : values()) {
            if (result.getGroup().equals(group)) {
                return result.getRegion();
            }
        }
        return null;
    }
}
