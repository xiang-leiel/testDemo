package com.quantchi.tianji.service.search.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-11 17:36
 **/
@Data
public class BusinessCardDO {

    private Integer id;

    private String name;

    private String company;

    private String department;

    private String title;

    private String telCell;

    private String telWork;

    private String addr;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String staffId;

    private String city;

    private String category;

    private int isLeader;

    private String imgUrl;

    private String companyId;

}
