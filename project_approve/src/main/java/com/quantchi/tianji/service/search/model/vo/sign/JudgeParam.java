package com.quantchi.tianji.service.search.model.vo.sign;

import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/19 12:47 PM
 */
@Data
public class JudgeParam {

    /**
     * 项目id
     */
    private String visitId;

    /**
     * 是否通过 0:未通过 1:通过
     */
    private Integer resulut;

    /**
     * 判定结果描述
     */
    private String mark;
}
