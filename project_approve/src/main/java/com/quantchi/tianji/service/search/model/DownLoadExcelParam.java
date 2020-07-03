package com.quantchi.tianji.service.search.model;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/16 3:54 PM
 */
@Data
public class DownLoadExcelParam {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 投资方名称
     */
    private String investName;

    /**
     * 总投资
     */
    private String totalInvenst;

    /**
     * 固定投资资产
     */
    private String assetInvenst;

    /**
     * 投资方简介
     */
    private String investInfo;

    /**
     * 项目内容
     */
    private String projectInfo;

    /**
     * 需用地
     */
    private String needLand;

    /**
     * 企业联系人
     */
    private String linkName;

    /**
     * 企业联系人联系方式
     */
    private String linkMobile;

    /**
     * 招商组片区
     */
    private String group;

    /**
     * 跟进人员
     */
    private String staffName;

    /**
     * 首报人
     */
    private String masterName;

    /**
     * 产业类型
     */
    private String industryType;

    /**
     * 拟落地平台
     */
    private String suggestLand;
}
