package com.quantchi.tianji.service.search.model;

import lombok.Data;

import java.util.List;

/**
 * 用于封装分页结果的类
 * @param <E>
 */
@Data
public class PageBean<E> {

    /**
     * 分页数据列表
     */
    private List<E> list;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;
}