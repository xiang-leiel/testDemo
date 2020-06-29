package com.quantchi.tianji.service.search.service.project;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.SubmitLeaderParam;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author leiel
 * @Description
 * @Date 2020/6/29 10:26 AM
 */

public interface ProjectManageService {

    /**
     * 项目搜索（web端）
     * @param projectSearchParams
     * @return
     */
    ResultInfo projectSearch(ProjectSearchParams projectSearchParams);

    /**
     * 获取项目标签详细信息
     * @param workFlowId
     * @param userId
     * @return
     */
    ResultInfo searchLabels(Integer workFlowId, Integer userId);

    /**
     * 项目审核
     * @param projectReportVO
     * @return
     */
    ResultInfo audit(ProjectReportVO projectReportVO);

    /**
     * 报送领导一级页面
     * @param
     * @return
     */
    ResultInfo submitLeader(SubmitLeaderParam submitLeaderParam);


    ResultInfo submitLeaderAll(SubmitLeaderParam submitLeaderParam);


    /**
     * 查询某一条项目的详情
     * @param
     * @return
     */
    ResultInfo searchOne(String projectId, Integer userId);

    /**
     * 报送领导一级页面
     * @param
     * @return
     */
    ResultInfo reportList(List<String> projectIds, Integer userId);

    /**
     * 获取每个标签的详细信息
     * @param type
     * @return
     */
    ResultInfo labelDataOfDepartment(int type, Integer labelOrInvestDm);

    /**
     * 下级部门的关系
     * @param deptId
     * @return
     */
    ResultInfo departmentRelation(Integer deptId);

    /**
     * web端 按项目名称查询
     * @param
     * @return
     */
    ResultInfo queryProjectOne(ProjectSearchParams projectSearchParamsint);

    /**
     * 根据类型获取相关枚举
     * @param type
     * @return
     */
    ResultInfo dataEnumsByType(int type);

    /**
     * 获取组上报数据
     * @param deptId
     * @return
     */
    ResultInfo groupReportData(Integer userId, Integer deptId);

    /**
     * 获取项目名称和id
     * @param type
     * @param deptId
     * @return
     */
    ResultInfo getDetail(int labelType, Integer labelInvestId, int type, Integer deptId);

    /**
     * 报送领导页面
     * @param userId
     * @param deptId
     * @return
     */
    ResultInfo countLeaderIndustry(Integer userId, Integer deptId);

    /**
     * 下载表格
     * @param
     * @return
     */
    ResultInfo downLoadExcel(ProjectSearchParams projectSearchParams, HttpServletResponse response);

    /**
     * 项目上报
     *
     * @return
     */
    ResultInfo reportOne(ProjectReportVO projectReportVO);

    /**
     * 隐藏项目
     */
    ResultInfo hide(String projectId, int hideFlag);

    /**
     * 获取部门数据
     * @return
     */
    ResultInfo deptList();

    /**
     * 新增行程按钮
     * @return
     */
    ResultInfo addRecord(ProjectReportVO projectReportVO);

    /**
     * 上报页面的查询
     * @param projectSearchParams
     * @return
     */
    ResultInfo reportSearch(ProjectSearchParams projectSearchParams);

    /**
     * 记录数据查询
     */
    ResultInfo recordingSearch(Integer userId);

    /**
     * 逻辑删除
     * @param projectId
     * @return
     */
    ResultInfo delete(String projectId);

}
