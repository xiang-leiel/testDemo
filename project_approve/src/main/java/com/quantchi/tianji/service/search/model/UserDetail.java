package com.quantchi.tianji.service.search.model;

import com.quantchi.tianji.service.search.model.vo.JobInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-09-09 17:55
 **/
@Data
public class UserDetail {

    private String userId;

    private String roleName;

    private Double latitude;

    private Double longitude;

    private String sex;

    private Date indexNoticeTime;

    private Date messageTime;

    private Date channelTime;

    private String imgUrl;

    private String group;

    private String birthday;

    private String nativePlace;

    private Integer dockingEnterpriseTotal;

    private Integer merchantsValue;

    private String name;

    private String successBusinessNum;

    private Integer job;

    private String location;

    private List<UserEducation> userEducations;

    private List<UserWork> userWorks;

    private List<UserCity> userCities;

    private List<UserBusiness> userBusinesses;

    /** 组长信息 */
    private JobInfo jobInfo;

}
