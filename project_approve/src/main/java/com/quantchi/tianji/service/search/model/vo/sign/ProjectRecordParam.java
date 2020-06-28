package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/19 11:39 AM
 */
@Data
public class ProjectRecordParam {

    /**
     * 关联用户行程id
     */
    private String id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 当前状态 待拜访、待记录、待研判
     */
    private Integer status;

    /**
     * 备注
     */
    private String statusRemark;

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
     * 预计投资规模单位
     */
    private Integer investmentUnit;

    /**
     * 拜访名称
     */
    private String visitName;
}
