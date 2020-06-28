package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class PreferenceInfo {
    /**
    * 主键id
    */
    private Integer id;

    /**
    * 招商人员id
    */
    private String staffId;

    /**
    * 预计投资规模
    */
    private String investmentScale;

    /**
    * 项目标签
    */
    private String label;

    /**
    * 行业领域
    */
    private String field;

    /**
    * 区域
    */
    private String area;

    /**
    * 范围优先---地址
    */
    private String rangeAddress;

    /**
    * 范围优先---距离
    */
    private String rangeDistance;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
     * 区域
     */
    private String rangeUnit;

    /**
     * 范围优先---地址
     */
    private Double locationAte;

    /**
     * 范围优先---距离
     */
    private Double locationLte;
}