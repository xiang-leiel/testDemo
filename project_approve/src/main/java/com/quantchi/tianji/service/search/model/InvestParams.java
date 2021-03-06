package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/29 10:31 AM
 */
@Data
public class InvestParams {

    /**
     *投资方id
     */
    private String investId;

    /**
     *投资方
     */
    private String investName;

    /**
     * 投资方简介
     */
    private String investInfo;


    private String country;

    private String region;

    /**
     * 投资方联系人姓名+职位+联系方式
     */
    private List<String> investor;

}

