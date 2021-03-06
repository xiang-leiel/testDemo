package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/29 10:40 AM
 */
@Data
public class SubmitLeaderParam {

    /**
     * 项目标签
     */
    private List<Integer> projectLabels;

    /**
     * 投资规模
     */
    private List<Integer> investScale;

}
