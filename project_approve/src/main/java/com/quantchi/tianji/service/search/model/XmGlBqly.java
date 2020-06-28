package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmGlBqly {
    /**
     * 主键id
     */
    private String xmbqlyId;

    /**
     * 项目基本信息表id
     */
    private String xmjbxxId;

    /**
     * 项目标签或领域代码
     */
    private Integer bqlyDm;

    /**
     * 标签类型里输入的数值
     */
    private Integer bqlxSz;

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