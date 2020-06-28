package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: DeQing-InvestmentWeb
 * @description: 渠道
 * @author: mf
 * @create: 2019-07-19 20:47
 **/
@Data
public class Channel implements Serializable {

    private String keyword;

    private String city;

    private String type;

    private String industry;

    private String domain;

    private Integer page;

    private Integer pageSize;

    private String sort;

    private String startTime;

    private String endTime;

}
