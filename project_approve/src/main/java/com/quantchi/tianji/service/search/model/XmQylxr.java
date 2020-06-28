package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmQylxr {
    /**
     * 企业联系人主键id
     */
    private String qylxrId;

    /**
     * 项目基本信息id
     */
    private String xmjbxxId;

    /**
     * 企业联系人姓名
     */
    private String qylxrXm;

    /**
     * 企业联系人职位
     */
    private String qylxrZw;

    /**
     * 企业联系人电话
     */
    private String qylxrDh;

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