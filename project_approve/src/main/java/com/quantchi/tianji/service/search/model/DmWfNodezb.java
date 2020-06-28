package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmWfNodezb {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 工作流设置代码
     */
    private Integer wfnodezbDm;

    /**
     * 前序工作流设置代码
     */
    private Integer preWfnodezbDm;

    /**
     * 流程名称
     */
    private String wfmc;

    /**
     * 流程描述
     */
    private String wfDesc;

    /**
     * 人员岗位对照关系代码
     */
    private Integer rygwdzDm;

    /**
     * 流程状态代码
     */
    private Integer lcztDm;

    /**
     * 部门代码
     */
    private Integer deptDm;

    /**
     * 流程阶段标记，从1开始
     */
    private Integer lcjdbj;

    /**
     * 终审标记，1:终审,0:待办
     */
    private Integer zsbj;

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
     * 后置流程节点
     */
    private Integer nextWfnodezbDm;
}