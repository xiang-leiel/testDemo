package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsLczt {
    /**
     * 流程状态代码
     */
    private Integer lcztDm;

    /**
     * 流程状态名称
     */
    private String lcztmc;

    /**
     * 流程层级标记，从1开始，越小越低
     */
    private Integer lccjbj;

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

    /**
     * 状态代码
     */
    private Integer xmztDm;
}