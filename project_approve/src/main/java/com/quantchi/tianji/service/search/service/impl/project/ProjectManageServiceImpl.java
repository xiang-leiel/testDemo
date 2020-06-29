package com.quantchi.tianji.service.search.service.impl.project;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.SubmitLeaderParam;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/29 2:39 PM
 */
@Service
public class ProjectManageServiceImpl implements ProjectManageService {
    @Override
    public ResultInfo projectSearch(ProjectSearchParams projectSearchParams) {
        return null;
    }

    @Override
    public ResultInfo searchLabels(Integer workFlowId, Integer userId) {
        return null;
    }

    @Override
    public ResultInfo audit(ProjectReportVO projectReportVO) {
        return null;
    }

    @Override
    public ResultInfo submitLeader(SubmitLeaderParam submitLeaderParam) {
        return null;
    }

    @Override
    public ResultInfo submitLeaderAll(SubmitLeaderParam submitLeaderParam) {
        return null;
    }

    @Override
    public ResultInfo searchOne(String projectId, Integer userId) {
        return null;
    }

    @Override
    public ResultInfo reportList(List<String> projectIds, Integer userId) {
        return null;
    }

    @Override
    public ResultInfo labelDataOfDepartment(int type, Integer labelOrInvestDm) {
        return null;
    }

    @Override
    public ResultInfo departmentRelation(Integer deptId) {
        return null;
    }

    @Override
    public ResultInfo queryProjectOne(ProjectSearchParams projectSearchParamsint) {
        return null;
    }

    @Override
    public ResultInfo dataEnumsByType(int type) {
        return null;
    }

    @Override
    public ResultInfo groupReportData(Integer userId, Integer deptId) {
        return null;
    }

    @Override
    public ResultInfo getDetail(int labelType, Integer labelInvestId, int type, Integer deptId) {
        return null;
    }

    @Override
    public ResultInfo countLeaderIndustry(Integer userId, Integer deptId) {
        return null;
    }

    @Override
    public ResultInfo downLoadExcel(ProjectSearchParams projectSearchParams, HttpServletResponse response) {
        return null;
    }

    @Override
    public ResultInfo reportOne(ProjectReportVO projectReportVO) {
        return null;
    }

    @Override
    public ResultInfo hide(String projectId, int hideFlag) {
        return null;
    }

    @Override
    public ResultInfo deptList() {
        return null;
    }

    @Override
    public ResultInfo addRecord(ProjectReportVO projectReportVO) {
        return null;
    }

    @Override
    public ResultInfo reportSearch(ProjectSearchParams projectSearchParams) {
        return null;
    }

    @Override
    public ResultInfo recordingSearch(Integer userId) {
        return null;
    }

    @Override
    public ResultInfo delete(String projectId) {
        return null;
    }
}
