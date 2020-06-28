package com.quantchi.tianji.service.search.model;

import java.util.Date;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class ProjectRecord {
    /**
    * 关联用户行程id
    */
    private Long id;

    /**
    * 项目名称
    */
    private String name;

    /**
    * 企业意向 0:有意向 1:意向模糊 2:无意向
    */
    private String intentionStatus;

    /**
    * 产业类型
    */
    private String type;

    /**
    * 考察次数
    */
    private Integer times;

    /**
    * 引进大学生数
    */
    private Integer students;

    /**
    * 项目标签
    */
    private String projectLabel;

    /**
    * 行业领域
    */
    private String field;

    /**
    * 投资方简介
    */
    private String fieldInfo;

    /**
    * 项目简介
    */
    private String projectInfo;

    /**
    * 接待照片
    */
    private String receivePhoto;

    /**
    * 接待记录
    */
    private String receiveRecord;

    /**
    * 意向迁出时间
    */
    private String intentionMoveTime;

    /**
    * 需求土地
    */
    private String needLand;

    /**
    * 需求办公面积
    */
    private String needOffice;

    /**
    * 其他需求
    */
    private String needRemark;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 预计投资规模
    */
    private String investmentScale;

    /**
     * 预计投资规模
     */
    private Integer investmentUnit;

    /**
    * 拜访名称
    */
    private String visitName;

    /**
    * 特殊标签 包含1:是否为科研机构 2:是否为外资项目 3:高层次人才项目 4:是否已县级考察  数据存入如：1,2,4
    */
    private String specialLabel;
}