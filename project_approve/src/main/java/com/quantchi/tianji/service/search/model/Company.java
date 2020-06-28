package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: DeQing-InvestmentWeb
 * @description: 企业搜索入参字段
 * @author: mf
 * @create: 2019-07-19 13:39
 **/
@Data
public class Company implements Serializable {

    private String city;

    private String keyword;

    private String industry;

    private String domain;

    private String type;

    private String financeRound;

    private String sort;

    private Integer page;

    private Integer pageSize;

}
