package com.quantchi.tianji.service.search.model.database;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/10 11:03 PM
 */
@Data
public class projectInfoNew {

    /**
     * 项目id
     */
    private Integer xmjbxxId;

    /**
     * 项目状态代码
     */
    private Integer xmztDm;

    /**
     * 产业类型
     */
    private Integer cylxBj;

    /**
     * 投资方简介
     */
    private String xmTzfjj;

    /**
     * 项目内容
     */
    private String xmNr;

    /**
     * 行业领域
     */
    private String xmHyly;

    /**
     * 需用地
     */
    private String xmXyd;

    /**
     * 需用地单位 1亩 2平方米
     */
    private Integer xydUnit;

    /**
     * 固定资产投资
     */
    private BigDecimal xmGdtzzc;

    /**
     * 货币单位代码
     */
    private Integer hbdwDm;

    /**
     * 项目落地平台代码
     */
    private Integer ldptDeptDm;

    /**
     * 项目所属招商组部门代码
     */
    private Integer zszDeptDm;

    /**
     * 项目所在省
     */
    private String xmProvince;

    /**
     * 是否为产业项目,即投资规模 1 是 0否
     */
    private Integer cyxmBj;

    /**
     * 是否为高层次人才项目 1 是 0否
     */
    private Integer gccrcBj;

    /**
     * 是否人才引进项目 1 是 0否
     */
    private Integer rcyjBj;

    /**
     * 是否为人才项目 1 是 0否
     */
    private Integer rcxmBj;

    /**
     * 是否科研或院校项目 1 是 0否
     */
    private Integer kyjgBj;

    /**
     * 是否为沪资5亿项目 1 是 0否
     */
    private Integer hzwyBj;

    /**
     * 是否为外资 1 是 0否
     */
    private Integer wzBj;

    /**
     * 是否为500强 1 是 0否
     */
    private Integer wbqBj;

    /**
     * 是否为国企央企 1 是 0否
     */
    private Integer gqyqBj;

    /**
     * 项目投资规模
     */
    private Integer xmtzgm;

}
