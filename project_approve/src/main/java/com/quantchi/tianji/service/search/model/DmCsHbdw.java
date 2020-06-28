package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsHbdw {
    /**
     * 货币单位代码
     */
    private Integer hbdwDm;

    /**
     * 货币单位名称
     */
    private String hbdwMc;

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