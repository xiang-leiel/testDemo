package com.quantchi.tianji.service.search.model.vo;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/27 2:27 PM
 */
@Data
public class ProjectQueryParam {

    /** 招商员id */
    private String staffId;

    /** 类型 */
    private Integer type;

    /** 页 */
    private Integer page;

    /** 一页多少数 */
    private Integer pageSize;

    public ProjectQueryParam(String staffId, Integer type, Integer page, Integer pageSize) {
        this.staffId = staffId;
        this.type = type;
        this.page = page;
        this.pageSize = pageSize;
    }

}
