package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsDept {
    /**
     * 部门代码
     */
    private Integer deptDm;

    /**
     * 部门类型: 1:组  2片区  3:平台  4.落地平台  5县级部门
     */
    private Integer deptType;

    /**
     * 上级部门代码
     */
    private Integer deptSjDm;

    /**
     * 部门名称
     */
    private String bmmc;

    /**
     * 部门所在片区 1:北京  2:上海  3:深圳  4:杭州  5:合肥  6:南京  7:武汉
     */
    private Integer deptDydm;

    /**
     * 启用标记 1:启用 0:失效
     */
    private Integer qybj;

    /**
     * 操作时间
     */
    private Date czrq;

    /**
     * 修改时间
     */
    private Date xgrq;

    /**
     * 操作员代码
     */
    private Integer userDm;

    /**
     * 部门排序
     */
    private Integer deptSort;
}