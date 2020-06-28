package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsYhyc {
    /**
     * 用户用词代码
     */
    private Integer yhycDm;

    /**
     * 用户用词类型 1、拒绝类型
     */
    private Integer yhycType;

    /**
     * 用户用词名称
     */
    private String yhycmc;

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