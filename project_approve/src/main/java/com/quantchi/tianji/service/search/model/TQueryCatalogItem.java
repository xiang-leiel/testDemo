package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class TQueryCatalogItem {
    /**
    * 选线id
    */
    private Integer itemId;

    /**
    * 选项名称
    */
    private String itemName;

    /**
    * 选项值
    */
    private String itemValue;

    /**
    * 分类id
    */
    private Integer catalogId;

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