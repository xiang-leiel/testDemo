package com.quantchi.tianji.service.search.model;

import lombok.Data;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description: 封装数据库中钉钉部门信息,字段一一对应
 * @author: mf
 * @create: 2019-07-12 16:58
 **/
@Data
public class Department {

    private String id;

    private String name;

    private String parentId;

}
