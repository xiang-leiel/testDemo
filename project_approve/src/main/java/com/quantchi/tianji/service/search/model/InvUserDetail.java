package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class InvUserDetail {
    private String userId;

    /**
    * 驻点纬度
    */
    private Double latitude;

    /**
    * 驻点经度
    */
    private Double longitude;

    /**
    * 性别
    */
    private String sex;

    /**
    * 头像
    */
    private String imgUrl;

    /**
    * 组别
    */
    private String group;

    /**
    * 生日
    */
    private String birthday;

    /**
    * 籍贯
    */
    private String nativePlace;

    /**
    * 总对接企业数
    */
    private Integer dockingEnterpriseTotal;

    /**
    * 招商累计市值
    */
    private Integer merchantsValue;

    /**
    * 招商成功数
    */
    private Integer successBusinessNum;

    private Date messageTime;

    private Date channelTime;

    /**
    * 职位 0:组员 1:组长
    */
    private Integer job;

    /**
    * 驻点地址
    */
    private String location;

    /**
     * 驻点地区
     */
    private String region;
}