package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class TQueryCatalog {
    /**
     * 分类id
     */
    private Integer catalogId;

    /**
     * 分类名称
     */
    private String catalogName;

    /**
     * 上一级目录id
     */
    private Integer parentId;

    /**
     * 对应查询页面的id
     */
    private Integer pageId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 1:区间 2:选择 3:日期 4:模糊查询
     */
    private Integer type;

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

    /**
     * 层级
     */
    private Integer level;

    /**
     * 页面排序
     */
    private Integer sort;
}