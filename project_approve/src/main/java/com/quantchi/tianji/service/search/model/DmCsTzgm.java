package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsTzgm {
    /**
    * 投资规模代码
    */
    private Integer tzgmDm;

    /**
    * 投资规模名称
    */
    private String tzgmMc;

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