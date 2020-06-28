package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class LoginLog {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 操作员代码
     */
    private Integer userDm;
}