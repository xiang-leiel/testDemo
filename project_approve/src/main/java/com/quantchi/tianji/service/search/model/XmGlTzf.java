package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmGlTzf {
    /**
     * 主键id
     */
    private String xmtzfId;

    /**
     * 项目基本信息表id
     */
    private String xmjbxxId;

    /**
     * 投资方id
     */
    private String tzfId;

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