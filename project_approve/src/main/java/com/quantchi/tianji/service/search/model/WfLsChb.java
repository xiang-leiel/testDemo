package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class WfLsChb {
    /**
     * 流程主表id
     */
    private String wfzbId;

    /**
     * 项目基本信息id
     */
    private String xmjbxxId;

    /**
     * 用户用词代码
     */
    private Integer yhycDm;

    /**
     * 审批意见
     */
    private String spyj;

    /**
     * 操作时间
     */
    private Date czrq;

    /**
     * 操作员代码
     */
    private Integer userDm;

    /**
     * 审批状态: 1:审批通过  2:暂缓  3:审批不通过
     */
    private Integer spzt;
}