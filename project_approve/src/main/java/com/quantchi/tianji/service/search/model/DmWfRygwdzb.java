package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmWfRygwdzb {
    /**
    * 人员岗位对照关系代码
    */
    private Integer rygwdzDm;

    /**
    * 用户代码
    */
    private Integer userDm;

    /**
    * 岗位角色代码
    */
    private Integer gwjsDm;

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
}