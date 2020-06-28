package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmTzf {
    /**
     * 投资方id
     */
    private String tzfId;

    /**
     * 投资方名称
     */
    private String tzfmc;

    /**
     * 投资方统一社会信用代码
     */
    private String shxydm;

    /**
     * 投资方简介
     */
    private String tzfjj;

    /**
     * 投资方联系人姓名，多值名用“|”分隔
     */
    private String tzflxrxm;

    /**
     * 投资方联系人电话，多值名用“|”分隔
     */
    private String tzflxrdh;

    /**
     * 投资方联系人职位，多值名用“|”分隔
     */
    private String tzflxrzw;

    /**
     * 国家代码
     */
    private Integer gjDm;

    /**
     * 行政区划代码
     */
    private Integer xzqhDm;

    /**
     * 启用标记 1:启用 0:失效
     */
    private Integer qybj;

    /**
     * 操作时间
     */
    private Date czrq;

    /**
     * 修改时间
     */
    private Date xgrq;

    /**
     * 操作员代码
     */
    private Integer userDm;
}