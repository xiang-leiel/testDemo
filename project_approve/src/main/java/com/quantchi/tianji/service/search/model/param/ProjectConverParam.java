package com.quantchi.tianji.service.search.model.param;

import com.quantchi.tianji.service.search.model.vo.ProjectInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/14 4:50 PM
 */
@Data
public class ProjectConverParam {

    //全部数量
    private Integer allCount;

    //拜访数量
    private Integer visitCount;

    //评价数量
    private Integer judgeCount;

    //拜访数量
    private Integer stationCount;

    //评价数量
    private Integer refuseCount;

    private List<ProjectInfo> visitAll ;

    //待拜访
    private List<ProjectInfo> visitData ;
    //待上报
    private List<ProjectInfo> reportData;
    //待研判
    private List<ProjectInfo> judgeData;
    //待落地
    private List<ProjectInfo> stationData;
    //未通过/叫停
    private List<ProjectInfo> refuseData;


    public ProjectConverParam() {
        this.allCount = 0;
        this.visitCount = 0;
        this.judgeCount = 0;
        this.stationCount = 0;
        this.refuseCount = 0;
        this.visitAll = new ArrayList<>();
        this.visitData = new ArrayList<>();
        this.reportData = new ArrayList<>();
        this.judgeData = new ArrayList<>();
        this.stationData = new ArrayList<>();
        this.refuseData = new ArrayList<>();
    }
}
