package com.quantchi.tianji.service.search.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-11 17:18
 **/
@Data
public class BusinessCard {

    private Integer id;

    private String name;

    private List<String> company;

    private List<String> department;

    private List<String> title;

    private List<String> tel_cell;

    private List<String> tel_work;

    private List<String> addr;

    private List<String> email;

    private Date createTime;

    private String staffId;

    private int isLeader;

    private String imgUrl;

    private String companyId;

}
