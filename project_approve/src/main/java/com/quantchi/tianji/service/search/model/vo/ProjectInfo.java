package com.quantchi.tianji.service.search.model.vo;

import com.quantchi.tianji.service.search.model.UserInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/17 10:52 AM
 */
@Data
public class ProjectInfo {

    private String staffId;

    private String partners;

    /** id */
    private String projectId;

    /** 名称 */
    private String projectName;

    /** 图标 */
    private String projectImgUrl;

    /** 项目流程 0:客商 1:会议 */
    private Integer projectType;

    /** 状态 */
    private Integer status;

    /** 接待人个数 */
    private Integer recevier;

    /** 接待人已评价个数 */
    private Integer evaluateCount;

    /** 显示更新时间 */
    private Date showTime;

    /** 更新时间 */
    private Date updateTime;

    /** 时间描述 */
    private String timeDesc;

    /**  */
    private String remark;

    /** 签到状态 null或0:未 1:已*/
    private Integer signStatus;

    /** 个人签到状态显示 null或0:未 1:已 2:补签*/
    private Integer personSignStatus;

    /** 评价状态 null或0:未 1:已*/
    private Integer evaluationStatus;

    /** 记录状态 null或0:未 1:已*/
    private Integer recordStatus;

    /** 用户基本信息 */
    private UserInfo userInfo;

    /** 项目类型 0:是发起数据 1:分派数据*/
    private Integer visitType;

    /** 公司id*/
    private String companyId;

    /**Masterid*/
    private Long masterId;
}
