package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.Date;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-12 17:51
 **/
@Data
public class User {

    private String id;

    private String name;

    private String departmentId;

    private String mobile;

    private String password;

    private Date indexNoticeTime;

    private Date messageTime;

    private Date channelTime;

    private String departName;

    private String roleName;

    private Integer followCompanyCardNum;
    private Integer applyCardNum;
    private Integer uploadCardNum;
    private Integer passFlag;

    private String imgUrl;

    public User() {

    }

    public User(String id, String name, String departmentId, String mobile, String password) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.mobile = mobile;
        this.password = password;
    }
}
