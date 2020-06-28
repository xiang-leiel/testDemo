package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmGlTzgm {
    /**
     * 主键id
     */
    private String tzgmId;

    /**
     * 项目基本信息id
     */
    private String xmjbxxId;

    /**
     * 投资规模代码
     */
    private Integer tzgmDm;

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