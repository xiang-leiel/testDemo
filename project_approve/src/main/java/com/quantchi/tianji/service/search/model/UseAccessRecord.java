package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class UseAccessRecord {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * ip
     */
    private String accessIp;

    /**
     * 用户访问接口
     */
    private String accessUrl;

    /**
     * 用户访问接口参数
     */
    private String accessParam;

    /**
     * 访问时间
     */
    private Date accessTime;

    /**
     * 操作员代码
     */
    private Integer userDm;

    /**
     * 来源  1钉钉  2商务局web
     */
    private Integer source;
}