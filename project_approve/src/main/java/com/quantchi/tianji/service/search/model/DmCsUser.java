package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsUser {
    /**
    * 用户代码
    */
    private Integer userDm;

    /**
    * 姓名
    */
    private String xm;

    /**
    * 职务
    */
    private String zw;

    /**
    * 手机号
    */
    private String mobile;

    /**
    * 所在部门代码
    */
    private Integer deptDm;

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
    * 登录密码
    */
    private String dlMm;
}