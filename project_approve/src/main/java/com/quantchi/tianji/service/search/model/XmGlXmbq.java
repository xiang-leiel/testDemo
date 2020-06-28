package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmGlXmbq {
    /**
     * 主键id
     */
    private Integer xmglbqId;

    /**
     * 项目基本信息表id
     */
    private Integer xmjbxxId;

    /**
     * 项目标签代码
     */
    private Integer xmbqDm;

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