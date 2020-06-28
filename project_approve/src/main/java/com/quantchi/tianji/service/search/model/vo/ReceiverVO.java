package com.quantchi.tianji.service.search.model.vo;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/18 5:01 PM
 */
@Data
public class ReceiverVO {

    /**
     * 接待人id
     */
    private Long id;

    /**
     * 接待人姓名
     */
    private String name;

    /**
     * 职位
     */
    private String job;

    /**
     * 头像Url
     */
    private String photoUrl;

    /**
     * 是否已评价 0:未评价 1:已评价
     */
    private Integer isEvaluate;
}
