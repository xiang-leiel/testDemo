package com.quantchi.tianji.service.search.service.project;

import com.quantchi.tianji.service.search.model.UserInfo;
import com.quantchi.tianji.service.search.model.vo.ProjectInfo;
import com.quantchi.tianji.service.search.model.vo.ProjectQueryParam;

import java.util.List;

/**
 * @author leiel
 * @Description 获取项目不同数据接口
 * @Date 2020/1/8 4:28 PM
 */
public interface QueryProjectDataService {

    List<ProjectInfo> ReportingProjectData(List<ProjectInfo> visitData, List<ProjectInfo> reportData,
                                           ProjectQueryParam projectQueryParam, UserInfo userInfo, Boolean isLeader);

}
