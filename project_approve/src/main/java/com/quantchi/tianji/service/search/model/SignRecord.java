package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class SignRecord {
    /**
    * 主键id
    */
    private String id;

    /**
    * 招商人员id
    */
    private String staffId;

    /**
    * 当前招商人员所属组别
    */
    private String staffGroup;

    /**
    * 当前拜访地址
    */
    private String visitLocation;

    /**
    * 当前拜访地址纬度
    */
    private Double visitLatitude;

    /**
    * 当前拜访地址经度
    */
    private Double visitLongitude;

    /**
    * 当前招商人员行程距离
    */
    private Double distanceTotal;

    /**
    * 今日招商人员签到次数
    */
    private Integer signTimes;

    /**
    * 是否为拜访数据 1为拜访数据
    */
    private Integer visitFlag;

    /**
    * 逻辑删除, 0为有效, 1:失效
    */
    private Byte isdelete;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 拜访地址id
    */
    private Long visitId;
}