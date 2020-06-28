package com.quantchi.tianji.service.search.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/23 10:30 AM
 */
@Data
public class PreferenceParam {

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
     * 区域
     */
    private String rangeUnit;

    /**
     * 地址纬度
     */
    private Double locationAte;

    /**
     * 地址经度
     */
    private Double locationLte;

    /**
     * 范围优先---距离
     */
    private String rangeDistance;
}
