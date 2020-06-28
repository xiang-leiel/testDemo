package com.quantchi.tianji.service.search.model;

import lombok.Data;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 用于封装排序标准的类
 * @author: mf
 * @create: 2019-07-11 09:51
 **/
@Data
public class Order {

    /**
     * 排序字段,按部门或者按用户排序
     */
    private String sortBy;

    /**
     * 排序依据,升降序或者是某个具体的用户id或者部门id
     */
    private String sortOrder;

    private Integer page;

    private Integer pageSize;

    private Integer size;



    public Order(String sortBy, String sortOrder, Integer page, Integer pageSize) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.pageSize = pageSize;
    }
}
