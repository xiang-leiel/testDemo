package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.io.Serializable;

/**
 * company_follower
 * @author 
 */
@Data
public class CompanyFollower implements Serializable {

    private Long id;

    /**
     * 招商人员id
     */
    private String staffId;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 是否关注 0:未关注 1:关注
     */
    private Byte status;

    private String from;

    private String origin;

}