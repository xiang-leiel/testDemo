package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: DeQing-InvestmentWeb
 * @description:
 * @author: mf
 * @create: 2019-07-20 18:02
 **/
@Data
public class Event implements Serializable {

    private String domain;

    private String subject;

    private String area;

    private Integer page;

    private String companyId;

    private Integer pageSize;

    private String keyword;

    private String sort;

    private String startTime;

    private String endTime;

    private String park;

    private String industry;
}
