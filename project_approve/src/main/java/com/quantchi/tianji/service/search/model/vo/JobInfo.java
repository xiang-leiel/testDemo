package com.quantchi.tianji.service.search.model.vo;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/23 5:54 PM
 */
@Data
public class JobInfo {

    /** 职位 **/
    private Integer job;

    /** 驻点地址 **/
    private String location;

    /** 组长姓名 **/
    private String leaderName;

    /** 组长id **/
    private String leaderId;

    /** 手机号 **/
    private String mobile;

}
