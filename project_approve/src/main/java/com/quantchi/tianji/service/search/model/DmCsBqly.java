package com.quantchi.tianji.service.search.model;

import java.util.Date;
import lombok.Data;

@Data
public class DmCsBqly {
    /**
     * 项目标签或行业领域代码
     */
    private Integer bqlyDm;

    /**
     * 上级项目标签或行业领域代码
     */
    private Integer sjbqlyDm;

    /**
     * 项目标签或行业领域名称
     */
    private String bqlyMc;

    /**
     * 1项目标签 2行业领域  3人才标签
     */
    private Integer bqlybj;

    /**
     * 层级标记
     */
    private Integer cjbj;

    /**
     * 数值标记，若为1则表示该字段需要输入数值
     */
    private Integer szbj;

    /**
     * 单复选框标记(0:单选;1:复选)
     */
    private Integer dfxbj;

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
}