package com.quantchi.tianji.service.search.service.project;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.DownLoadParam;
import com.quantchi.tianji.service.search.model.SubmitLeaderParam;
import com.quantchi.tianji.service.search.model.param.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.vo.PreferenceParam;
import com.quantchi.tianji.service.search.model.vo.ProjectQueryParam;
import com.quantchi.tianji.service.search.model.vo.project.ProjectReportVO;
import com.quantchi.tianji.service.search.model.vo.sign.JudgeParam;
import com.quantchi.tianji.service.search.model.vo.sign.ProjectRecordParam;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/16 2:31 PM
 */

public interface ProjectManageService {

    /**
     * 设置偏好数据
     * @param preferenceParam
     * @return
     */
    ResultInfo preferenceSet(PreferenceParam preferenceParam);

    /**
     * 查询偏好数据
     * @param staffId
     * @return
     */
    ResultInfo queryPreference(String staffId);

    /**
     * 获取所有数据
     * @param projectQueryParam
     * @return
     */
    ResultInfo getProjectAll(ProjectQueryParam projectQueryParam);

    /**
     * 更新签到数据
     * @param staffId
     * @return
     */
    ResultInfo updateSignData(String staffId, Long visitId);

    /**
     * 查看项目记录数据
     * @param visitId
     * @return
     */
    ResultInfo queryProjectRecord(Long visitId);

    /**
     * 保存项目记录数据
     * @param projectRecordParam
     * @return
     */
    ResultInfo saveProjectRecord(ProjectRecordParam projectRecordParam);

    /**
     * 研判
     * @param judgeParam
     * @return
     */
    ResultInfo judgeProject(JudgeParam judgeParam);

    ResultInfo searchProject(String staffId, String visitName, Integer type, Integer page, Integer pageSize);

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
    ResultInfo submitLeader( SubmitLeaderParam submitLeaderParam);


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
     *
     * @param projectSearchParams
     * @return
     */
    ResultInfo statisticData(ProjectSearchParams projectSearchParams);

    ResultInfo hide(String projectId, int hideFlag);

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

    ResultInfo recordingSearch(Integer userId);

    /**
     * 逻辑删除
     * @param projectId
     * @return
     */
    ResultInfo delete(String projectId);
}
