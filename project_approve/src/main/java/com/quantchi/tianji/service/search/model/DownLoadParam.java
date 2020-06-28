package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/16 3:19 PM
 */
@Data
public class DownLoadParam {

    /**
     * 项目id
     */
    private List<Integer> projectIds;

    /**
     * 登录人员id
     */
    private Integer userId;

    /**
     * 部门id
     */
    private Integer deptId;

}
