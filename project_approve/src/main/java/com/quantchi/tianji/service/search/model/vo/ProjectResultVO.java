package com.quantchi.tianji.service.search.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/17 10:47 AM
 */
@Data
public class ProjectResultVO {

    /** 全部数据量 */
    private Integer allCount;

    /** 全部数据 */
    private List<ProjectInfo> allData;

    /** 待拜访数据量 */
    private Integer visitCount;

    /** 待拜访数据 */
    private List<ProjectInfo> visitData;

    /** 待研判数据量 */
    private Integer judgeCount;

    /** 待研判数据 */
    private List<ProjectInfo> judgeData;

    /** 待落地数据量 */
    private Integer landCount;

    /** 待落地数据 */
    private List<ProjectInfo> landData;

    /** 未通过/叫停数据量 */
    private Integer refuseCount;

    /** 未通过/叫停数据 */
    private List<ProjectInfo> refuseData;

    /** 最新项目情况 */
    private ProjectInfo latestProject;

}
