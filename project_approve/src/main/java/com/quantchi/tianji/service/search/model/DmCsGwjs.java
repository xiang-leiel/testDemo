package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsGwjs {
    /**
    * 岗位角色代码
    */
    private Integer gwjsDm;

    /**
    * 岗位角色名称
    */
    private String gwjsmc;

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