package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class XmjbxxKzb {
    /**
    * 项目扩展表id
    */
    private Integer xmkzbId;

    /**
    * 项目基本信息id
    */
    private Integer xmjbxxId;

    /**
    * 是否为产业项目,即投资规模 1 是 0否
    */
    private Integer cyxmBj;

    /**
    * 是否为高层次人才项目 1 是 0否
    */
    private Integer gccrcBj;

    /**
    * 是否人才引进项目 1 是 0否
    */
    private Integer rcyjBj;

    /**
    * 是否为人才项目 1 是 0否
    */
    private Integer rcxmBj;

    /**
    * 是否科研或院校项目 1 是 0否
    */
    private Integer kyjgBj;

    /**
    * 是否为沪资5亿项目 1 是 0否
    */
    private Integer hzwyBj;

    /**
    * 是否为外资 1 是 0否
    */
    private Integer wzBj;

    /**
    * 是否为500强 1 是 0否
    */
    private Integer wbqBj;

    /**
    * 是否为国企央企 1 是 0否
    */
    private Integer gqyqBj;

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
}