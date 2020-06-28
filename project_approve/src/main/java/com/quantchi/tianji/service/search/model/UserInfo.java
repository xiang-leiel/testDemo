package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class UserInfo {
    /**
    * 招商人员id
    */
    private String staffId;

    /**
     * 招商人员头像
     */
    private String staffImgUrl;

    /**
     * 招商人员姓名
     */
    private String staffName;

    /**
     * 招商人员所属部门
     */
    private String departId;

    /**
     * 招商人员角色
     */
    private String staffRole;

    /**
    * 招商人员手机号
    */
    private String mobile;

    /**
    * 招商人员岗位 0:组员 1:组长
    */
    private Integer staffJob;

    /**
    * 当前招商人员所属组别
    */
    private String staffGroup;

    /**
    * 当前招商人员驻点地址
    */
    private String stationLocation;

    /**
    * 驻点地址维度
    */
    private Double stationLatitude;

    /**
    * 驻点地址经度
    */
    private Double stationLongitude;

    /**
     * 组所在区
     */
    private String region;

}