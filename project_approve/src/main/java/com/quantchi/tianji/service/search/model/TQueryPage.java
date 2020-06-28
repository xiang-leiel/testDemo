package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class TQueryPage {
    /**
     * 查询页面id
     */
    private Integer pageId;

    /**
     * 表头
     */
    private String pageTitle;

    /**
     * 查询语句
     */
    private String sql;

    /**
     * 报表列表显示字段(多个用逗号隔开)
     */
    private String resultTitles;

    /**
     * 排序字段
     */
    private String fieldName;

    /**
     * 1:Asc 0:Desc(排序方式)
     */
    private Integer method;

    /**
     * 启用标记 1:启用 0:失效
     */
    private Integer flag;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作员代码
     */
    private Integer userDm;
}