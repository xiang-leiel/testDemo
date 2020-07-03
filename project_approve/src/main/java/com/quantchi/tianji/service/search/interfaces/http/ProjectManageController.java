package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.aop.annotation.AccessLog;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.model.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.ReportListParam;
import com.quantchi.tianji.service.search.model.SubmitLeaderParam;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @Description 项目流转controller
 * @author leiel
 * @Date 2020/2/1 1:06 PM
 */
@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectManageController {

    @Resource
    private ProjectManageService projectManageService;

    /**
     * 项目查询条件显示
     * workFlowId 流程状态 userId 用户id
     * @param workFlowId
     * @param userId
     * @return
     */
    @AccessLog(name= "访问日志")
    @GetMapping("/searchLabels")
    public ResultInfo searchLabels(Integer workFlowId, Integer userId) {

        ResultInfo resultInfo = projectManageService.searchLabels(workFlowId, userId);

        return resultInfo;
    }

    /**
     * 上报页面查询
     * @param projectSearchParams
     * @return
     */
    @PostMapping("/reportSearch")
    public ResultInfo reportSearch(@RequestBody ProjectSearchParams projectSearchParams) {

        ResultInfo resultInfo = projectManageService.reportSearch(projectSearchParams);

        return resultInfo;
    }

    /**
     * 查询有行程没有录入项目的数据
     * @return
     */
    @GetMapping("/recordingSearch")
    public ResultInfo recordingSearch(Integer userId) {

        ResultInfo resultInfo = projectManageService.recordingSearch(userId);

        return resultInfo;
    }

    /**
     * 项目信息分页查询
     * @return
     */
    @PostMapping("/search")
    public ResultInfo search(@RequestBody ProjectSearchParams projectSearchParams) {

        ResultInfo resultInfo = projectManageService.projectSearch(projectSearchParams);

        return resultInfo;
    }

    /**
     * 按项目名称模糊查询
     * @return 需传当前节点的所有状态
     */
    @PostMapping("/queryProjectOne")
    public ResultInfo queryProjectOne(@RequestBody ProjectSearchParams projectSearchParamsint) {

        ResultInfo resultInfo = projectManageService.queryProjectOne(projectSearchParamsint);

        return resultInfo;
    }

    /**
     * 项目信息单条查询
     * @return
     */
    @GetMapping("/searchOne")
    public ResultInfo searchOne(String projectId, Integer userId) {

        ResultInfo resultInfo = projectManageService.searchOne(projectId, userId);

        return resultInfo;
    }

    /**
     * 项目信息新增
     * @return
     */
    @PostMapping("/addRecord")
    public ResultInfo addRecord(@RequestBody ProjectReportVO projectReportVO) {

        if(projectReportVO.getProjectName() != null && projectReportVO.getProjectName().length() > 50) {
            return ResultUtils.fail(ErrCode.NAME_LONG);
        }
        if(projectReportVO.getProjectInfo() != null && projectReportVO.getProjectInfo().length() > 1000) {
            return ResultUtils.fail(ErrCode.INFO_LONG);
        }
        if(projectReportVO.getRemark() != null && projectReportVO.getRemark().length() > 300) {
            return ResultUtils.fail(ErrCode.REMARK_LONG);
        }
        if(projectReportVO.getOtherRequire() != null && projectReportVO.getOtherRequire().length() > 255) {
            return ResultUtils.fail(ErrCode.OTHER_LONG);
        }
        if(projectReportVO.getAssetInvest() != null && new BigDecimal("10000").compareTo(projectReportVO.getAssetInvest()) > 0) {
            return ResultUtils.fail(ErrCode.ASSET_INVEST_SMALL);
        }
        if(projectReportVO.getInvestTotal() != null && new BigDecimal("10000").compareTo(projectReportVO.getInvestTotal()) > 0) {
            return ResultUtils.fail(ErrCode.TOTAL_INVEST_SMALL);
        }

        ResultInfo resultInfo = projectManageService.addRecord(projectReportVO);

        return resultInfo;
    }

    /**
     * 项目审核接口 组长审批  平台审批  商务局研判
     * @return
     */
    @PostMapping("/audit")
    public ResultInfo audit(@RequestBody ProjectReportVO projectReportVO) {

        if(projectReportVO.getProjectName() != null && projectReportVO.getProjectName().length() > 50) {
            return ResultUtils.fail(ErrCode.NAME_LONG);
        }
        if(projectReportVO.getProjectInfo() != null && projectReportVO.getProjectInfo().length() > 1000) {
            return ResultUtils.fail(ErrCode.INFO_LONG);
        }
        if(projectReportVO.getRemark() != null && projectReportVO.getRemark().length() > 300) {
            return ResultUtils.fail(ErrCode.REMARK_LONG);
        }
        if(projectReportVO.getOtherRequire() != null && projectReportVO.getOtherRequire().length() > 255) {
            return ResultUtils.fail(ErrCode.OTHER_LONG);
        }
        if(projectReportVO.getAssetInvest() != null && new BigDecimal("10000").compareTo(projectReportVO.getAssetInvest()) > 0) {
            return ResultUtils.fail(ErrCode.ASSET_INVEST_SMALL);
        }
        if(projectReportVO.getInvestTotal() != null && new BigDecimal("10000").compareTo(projectReportVO.getInvestTotal()) > 0) {
            return ResultUtils.fail(ErrCode.TOTAL_INVEST_SMALL);
        }

        ResultInfo resultInfo = projectManageService.audit(projectReportVO);

        return resultInfo;
    }

    /**
     * 隐藏、显示项目接口
     * hideFlag 0未隐藏 1隐藏
     * @return
     */
    @GetMapping("/hide")
    public ResultInfo hide(String projectId, int hideFlag) {

        ResultInfo resultInfo = projectManageService.hide(projectId, hideFlag);

        return resultInfo;
    }

    /**
     * 逻辑删除按钮
     * @return
     */
    @GetMapping("/delete")
    public ResultInfo delete(String projectId) {

        ResultInfo resultInfo = projectManageService.delete(projectId);

        return resultInfo;
    }

    /**
     * 项目上报
     * @return
     */
    @PostMapping("/reportOne")
    public ResultInfo reportOne(@RequestBody ProjectReportVO projectReportVO) {

        ResultInfo resultInfo = projectManageService.reportOne(projectReportVO);

        return resultInfo;
    }

    /**
     * 批量上报
     * @return
     */
    @PostMapping("/reportList")
    public ResultInfo reportList(@RequestBody ReportListParam reportListParam) {

        ResultInfo resultInfo = projectManageService.reportList(reportListParam.getProjectIds(), reportListParam.getUserId());

        return resultInfo;
    }

    /**
     * 报送领导一级页面  签约项目
     * @return
     */
    @PostMapping("/submitLeader")
    public ResultInfo submitLeader(@RequestBody SubmitLeaderParam submitLeaderParam) {

        ResultInfo resultInfo = projectManageService.submitLeader(submitLeaderParam);

        return resultInfo;
    }

    /**
     * 报送领导一级页面显示所有数据  签约项目
     * @return
     */
    @PostMapping("/submitLeaderAll")
    public ResultInfo submitLeaderAll(@RequestBody SubmitLeaderParam submitLeaderParam) {

        ResultInfo resultInfo = projectManageService.submitLeaderAll(submitLeaderParam);

        return resultInfo;
    }

    /**
     * 获取每个标签的详细信息
     * type 1为项目标签  2 投资规模
     * @return
     */
    @GetMapping("/labelDataOfDepartment")
    public ResultInfo labelDataOfDepartment(int type, Integer labelOrInvestDm) {

        ResultInfo resultInfo = projectManageService.labelDataOfDepartment(type, labelOrInvestDm);

        return resultInfo;
    }

    /**
     * 根据部门 数据类型获取项目名称和id
     * labelType 1为项目标签  2为投资规模
     * type 1本周上报  2本周签约   3总签约
     * @return
     */
    @GetMapping("/getDetail")
    public ResultInfo getDetail(int labelType, Integer labelInvestId, int type, Integer deptId) {

        ResultInfo resultInfo = projectManageService.getDetail(labelType, labelInvestId, type, deptId);

        return resultInfo;
    }

    /**
     * 获取当前部门的下级部门数据
     * @return
     */
    @GetMapping("/deptRelation")
    public ResultInfo departmentRelation(Integer userId, Integer deptId) {

/*        if(userId != null){
            //如果dm_wf_nodezb有数据，则用这里面的部门
            Integer departmentId = dmWfNodezbMapper.selectDeptDmByUserId(userId);
            if(departmentId != null) {
                deptId = departmentId;
            }
        }*/

        ResultInfo resultInfo = projectManageService.departmentRelation(deptId);

        return resultInfo;
    }

    /**
     * 获取商务局下的所有部门
     * @return
     */
    @GetMapping("/deptList")
    public ResultInfo deptList() {

        ResultInfo resultInfo = projectManageService.deptList();

        return resultInfo;
    }

    /**
     * 组员获取个人数据  组长获取本组的所有数据
     * 获取组上报总数  一产  二产  三产  人才项目  沪资5亿
     * @return
     */
    @GetMapping("/groupReportData")
    public ResultInfo groupReportData(Integer userId, Integer departmentId) {

        ResultInfo resultInfo = projectManageService.groupReportData(userId, departmentId);

        return resultInfo;
    }

    /**
     * 获取枚举 1项目标签、2行业领域 3落地平台 4人才项目  5国家/省级行政区
     * @return
     */
    @GetMapping("/dataEnums")
    public ResultInfo dataEnumsByType(int type) {

        ResultInfo resultInfo = projectManageService.dataEnumsByType(type);

        return resultInfo;
    }

    /**
     * 获取商务局签约总数  一产  二产  三产  人才项目  沪资5亿
     * @return
     */
    @GetMapping("/countLeaderIndustry")
    public ResultInfo countLeaderIndustry(Integer userId, Integer deptId) {

        ResultInfo resultInfo = projectManageService.countLeaderIndustry(userId, deptId);

        return resultInfo;
    }

    /**
     * 报送领导下载表格
     * @return
     */
    @PostMapping("/downLoadExcel")
    public ResultInfo downLoadExcel(@RequestBody ProjectSearchParams projectSearchParams, HttpServletResponse response) {

        ResultInfo resultInfo = projectManageService.downLoadExcel(projectSearchParams, response);

        return resultInfo;
    }

}
