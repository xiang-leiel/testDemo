package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class WfLsZb {
    /**
     * 流程主表id
     */
    private String wfzbId;

    /**
     * 前序流程id
     */
    private String preWflsId;

    /**
     * 流程终审标记，1:终审,0:待办
     */
    private Integer lczsbj;

    /**
     * 工作流设置代码
     */
    private Integer wfnodeszbDm;

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