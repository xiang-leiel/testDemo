package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsGj {
    /**
    * 国家代码
    */
    private Integer gjDm;

    /**
    * ISO英文名称
    */
    private String gjywmc;

    /**
    * 国家中文名称
    */
    private String gjmc;

    /**
    * 二位字母代码
    */
    private String gjewzmDm;

    /**
    * 三位字母代码
    */
    private String gjswzmDm;

    /**
    * 启用标记(0:不启用;1:启用)
    */
    private Integer qybj;

    /**
    * 操作日期
    */
    private Date czrq;

    /**
    * 修改日期
    */
    private Date xgrq;

    /**
    * 操作员代码
    */
    private Integer userDm;
}