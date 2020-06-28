package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsXzqh {
    /**
     * 行政区划代码
     */
    private Integer xzqhDm;

    private String xzqhmc;

    /**
     * 行政区等级标记（1:省级行政区;2:市级行政区;3:区级行政区）
     */
    private Integer xzqdjbj;

    private Integer sjxzqhDm;

    /**
     * 国家代码
     */
    private Integer gjDm;

    /**
     * 启用标记(0:不启用;1:启用)
     */
    private Integer qybj;

    /**
     * 操作日期
     */
    private Date czrq;

    /**
     * 修改日期
     */
    private Date xgrq;

    /**
     * 操作员代码
     */
    private Integer userDm;
}