package com.quantchi.tianji.service.search.model;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/9 10:34 AM
 */
@Data
public class UserInfoNew {

    /**
     * 审批人id
     */
    private String userId;

    /**
     * 审批人手机号
     */
    private String mobile;

    /**
     * 审批人姓名
     */
    private String userName;

    /**
     * 部门id
     */
    private Integer departmentId;

}
