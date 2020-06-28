package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class VisitRecord {
    /**
     * 主键id
     */
    private String id;

    /**
     * 招商人员id
     */
    private String staffId;

    /**
     * 招商人员拜访同行人
     */
    private String visitPartner;

    /**
     * 招商人员拜访会议或客商名称
     */
    private String visitName;

    /**
     * 类型 0:客商 1:会议 2:其他
     */
    private Integer visitType;

    /**
     * 公司或会议的图片Url
     */
    private String imgUrl;

    /**
     * 招商人员拜访地址
     */
    private String visitLocation;

    /**
     * 拜访地址维度
     */
    private Double visitLatitude;

    /**
     * 拜访地址经度
     */
    private Double visitLongitude;

    /**
     * 是否已拜访 0：未拜访，1：已拜访
     */
    private Integer visitStatus;

    /**
     * 是否为指派数据 0：非指派，1：指派数据
     */
    private Integer recordType;

    /**
     * 逻辑删除, 0为有效, 1:失效
     */
    private Integer isdelete;

    /**
     * 拜访时间
     */
    private Date visitTime;

    /**
     * 拜访时间
     */
    private String visitTimeDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 当前行程的流程节点  0,"待拜访" 1,"待上报" 2,"待研判" 3,"待签约" 4,"待入驻" 5,"已入驻" 6,"未通过/叫停"
     */
    private Integer status;

    /**
     * 状态备注
     */
    private String statusRemark;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 是否为同行数据  0:不是  1:是
     */
    private Integer together;

    /**
     * 若为同行人关联的主行程id
     */
    private String masterId;

    /**
     * 发起人姓名
     */
    private String staffIdName;
}