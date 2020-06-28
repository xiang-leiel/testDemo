package com.quantchi.tianji.service.search.service.project.impl;

import com.quantchi.tianji.service.search.dao.VisitRecordMapper;
import com.quantchi.tianji.service.search.enums.ProjectStatusEnum;
import com.quantchi.tianji.service.search.model.UserInfo;
import com.quantchi.tianji.service.search.model.vo.ProjectInfo;
import com.quantchi.tianji.service.search.model.vo.ProjectQueryParam;
import com.quantchi.tianji.service.search.service.project.ProjectDealService;
import com.quantchi.tianji.service.search.service.project.QueryProjectDataService;
import com.quantchi.tianji.service.search.utils.ListPageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/8 4:30 PM
 */
@Service
public class QueryProjectDataServiceImpl implements QueryProjectDataService {

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private ProjectDealService projectDealService;

    @Override
    public List<ProjectInfo> ReportingProjectData(List<ProjectInfo> visitData, List<ProjectInfo> reportData, ProjectQueryParam projectQueryParam, UserInfo userInfo, Boolean isLeader) {

        List<ProjectInfo> visitDataAll = new ArrayList<>();

        List<Integer> statusList = new ArrayList<>();

        statusList.add(ProjectStatusEnum.WAIT_VISIT.getCode());
        statusList.add(ProjectStatusEnum.WAIT_REPORT.getCode());

        if(isLeader) {

            visitDataAll = visitRecordMapper.queryProjectData(statusList, projectQueryParam.getPage(), projectQueryParam.getPageSize());

        }else {

            visitDataAll = visitRecordMapper.fetchDataByGroup(statusList, userInfo.getStaffGroup());

        }

        //遍历数据区分
        for(ProjectInfo projectInfo : visitDataAll) {

            if(projectInfo.getStatus() == ProjectStatusEnum.WAIT_VISIT.getCode()) {
                visitData.add(projectInfo);
            }else {
                reportData.add(projectInfo);
            }

        }

        //待拜访数据处理
        projectDealService.dealVisitData(visitData);

        //待上报数据处理
        projectDealService.dealVisitData(reportData);

        //待拜访正序
        projectDealService.sortVisitData(visitData);

        //待上报正序
        projectDealService.sortVisitData(reportData);

        visitData.addAll(reportData);

        return visitData;
    }
}
