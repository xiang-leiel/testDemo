package com.quantchi.tianji.service.search.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class XmjbxxZb {
    /**
     * 项目id
     */
    private String xmjbxxId;

    /**
     * 项目名称
     */
    private String xmmc;

    /**
     * 项目状态代码
     */
    private Integer xmztDm;

    /**
     * 产业类型 1:一产 2:二产  3:三产
     */
    private Integer cylxBj;

    /**
     * 国家代码
     */
    private Integer gjDm;

    /**
     * 行政区划代码
     */
    private Integer xzqhDm;

    /**
     * 项目内容
     */
    private String xmNr;

    /**
     * 行业领域（存储是hyly_dm字段，这里是复选，不同hyly_dm字段用'|'间隔）
     */
    private String xmHyly;

    /**
     * 项目所需土地面积
     */
    private BigDecimal xmXyd;

    /**
     * 项目所需土地类型标记（1、存量  2、新征  3、待定  4、租赁厂房）
     */
    private Integer xmYdlxbj;

    /**
     * 需用地单位 1亩 2平方米
     */
    private Integer xydUnit;

    /**
     * 项目所需办公面积（单位：平方米）
     */
    private BigDecimal xmBgmj;

    /**
     * 项目总投资
     */
    private BigDecimal xmZtz;

    /**
     * 固定资产投资
     */
    private BigDecimal xmGdtzzc;

    /**
     * 货币单位代码
     */
    private Integer hbdwDm;

    /**
     * 投资规模代码
     */
    private Integer tzgmDm;

    /**
     * 项目落地平台（拟）代码
     */
    private Integer ldptDeptDm;

    /**
     * 项目所属招商组部门代码
     */
    private Integer zszDeptDm;

    /**
     * 项目投资方所在行政区划代码
     */
    private Integer tzfXzqhDm;

    /**
     * 项目其他要求条件
     */
    private String xmQtyq;

    /**
     * 投资意向代码
     */
    private Integer tzyxDm;

    /**
     * 投资意向日期
     */
    private Date tzyxrq;

    /**
     * 项目上报人代码（用户代码），若多个，可用"|"分割
     */
    private String sbrUserDm;

    /**
     * 备注
     */
    private String bz;

    /**
     * 操作时间
     */
    private Date czrq;

    /**
     * 修改时间
     */
    private Date xgrq;

    /**
     * 上报时间
     */
    private Date sbrq;

    /**
     * 操作员代码
     */
    private Integer userDm;

    /**
     * 项目是否隐藏标记  0不隐藏 1隐藏
     */
    private Integer xmYcbj;

    /**
     * 项目首报人代码
     */
    private Integer masterUserDm;

    /**
     * 启用标记 1:启用 0:失效
     */
    private Integer qybj;
}