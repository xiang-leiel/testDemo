package com.quantchi.tianji.service.search.service.project;

import com.quantchi.tianji.service.search.dao.project.ProjectInfoMapper;
import com.quantchi.tianji.service.search.dao.project.ProjectInvestMapper;
import com.quantchi.tianji.service.search.dao.user.UserInfoMapper;
import com.quantchi.tianji.service.search.entity.project.ProjectInfo;
import com.quantchi.tianji.service.search.entity.project.ProjectInvest;
import com.quantchi.tianji.service.search.entity.CodeDeptment;
import com.quantchi.tianji.service.search.entity.user.UserInfo;
import com.quantchi.tianji.service.search.enums.CurrencyUnitEnums;
import com.quantchi.tianji.service.search.model.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.model.vo.ProjectVO;
import com.quantchi.tianji.service.search.service.department.DepartmentService;
import com.quantchi.tianji.service.search.utils.CurrencyUtils;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ListPageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description 项目上报数据服务类
 * @author leiel
 * @Date 2020/2/3 11:08 AM
 */
@Service
public class ProjectReportManageService {

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ProjectInvestMapper projectInvestMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 项目查询服务
     * @param projectSearchParams
     * @return
     */
    public ProjectVO searchProject(ProjectSearchParams projectSearchParams) {

        List<String> projectIds = new ArrayList<>();

        ProjectVO projectVO = new ProjectVO();

        ProjectReportDTO projectReportDTO = new ProjectReportDTO();

        BeanUtils.copyProperties(projectSearchParams, projectReportDTO);

        //先根据时间、地区及行业领域获取相关数据
        if(projectSearchParams.getReportStartTime() != null && projectSearchParams.getReportStartTime() != null) {
            projectReportDTO.setReportStartTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportStartTime())));
            projectReportDTO.setReportEndTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportEndTime())));
        }
        projectReportDTO.setRegions(projectReportDTO.getRegions());
        //项目状态 岗位获取流转状态代码对应项目状态  大于当前岗位状态的数据
        projectReportDTO.setStatusList(projectSearchParams.getStatusList());

        if(StringUtils.isEmpty(projectReportDTO.getProjectName())) {
            projectReportDTO.setProjectName(null);
        }

        List<ProjectInfo> projects = projectInfoMapper.queryReportDataAll(projectReportDTO);

        Iterator<ProjectInfo> it = projects.iterator();

        while(it.hasNext()) {

            ProjectInfo projectInfo = it.next();
            if(CollectionUtils.isNotEmpty(projectIds) && !projectIds.contains(projectInfo.getId())) {
                it.remove();
                continue;
            }

            //过滤非该岗位能看到的数据
/*            if(projectSearchParams.getDepartmentId() != null){
                Boolean deptFlag = departmentService.checkDepartment(projectSearchParams.getDepartmentId(), xmjbxxZb.getZszDeptDm());
                if(!deptFlag) {
                    it.remove();
                    continue;
                }
            }*/

            //查询终止数据 是否是当前节点或者大于当前节点可查数据
/*            if(projectInfo.getStatus() == 1001 && projectSearchParams.getStatusList().contains(1001)) {

                Integer deptDm = wfLsChbMapper.selectDeptDmByXmId(xmjbxxZb.getXmjbxxId());

                Boolean endFlag = departmentService.checkDepartment(deptDm, projectSearchParams.getDepartmentId());

                if(!deptDm.equals(projectSearchParams.getDepartmentId()) && !endFlag) {
                    it.remove();
                    continue;
                }

            }*/

        }

        turnToShowData(projects, projectVO, projectSearchParams.getUserId(), projectSearchParams.getPage(), projectSearchParams.getPageSize());

        return projectVO;

    }

    public void turnToShowData(List<ProjectInfo> projectInfos, ProjectVO projectVO, Integer userId, int page, int pageSize){

        List<ProjectReportVO> projectReportVOList = new ArrayList<>();
        //数据转换
        for(ProjectInfo projectInfo : projectInfos) {
            ProjectReportVO projectReportVO = new ProjectReportVO();
            copyProperties(userId, projectInfo, projectReportVO);
            //上报时间
            projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(projectInfo.getReportTime()));
            if(projectInfo.getLandDeptId() != null) {
                projectReportVO.setDepartment(departmentService.selectDeptByDeptDm(projectInfo.getLandDeptId()));
            }
            projectReportVOList.add(projectReportVO);
        }

        projectVO.setTotal(projectInfos.size());

        //分页返回
        if(CollectionUtils.isNotEmpty(projectReportVOList)) {
            projectReportVOList = ListPageUtil.getListPage(page, pageSize, projectReportVOList);

        }
        projectVO.setProjectReportVOs(projectReportVOList);
    }

    public void copyProperties(Integer userId, ProjectInfo projectInfo, ProjectReportVO projectReportVO) {

        projectReportVO.setXmId(projectInfo.getId());
        projectReportVO.setStatus(projectInfo.getStatus());

        //如果状态和当前登录部门对应的状态一样，查询最新的一条流程记录是否暂缓
        //如果为暂缓，则将返回的数据状态设置为1002
        if(userId != null) {
/*            Integer userStatus = dmWfNodezbMapper.selectStatusDmByUserId(userId);
            if(xmjbxxZb.getXmztDm().equals(userStatus)) {
                //查询最新的一条记录是否为暂缓
                Integer flowStatus = wfLsChbMapper.selectStatusDmByXmId(xmjbxxZb.getXmjbxxId());
                if(flowStatus != null && flowStatus == 2) {
                    projectReportVO.setStatus(1002);
                }
            }*/
        }
        projectReportVO.setProjectName(projectInfo.getName());
        projectReportVO.setIndustryType(projectInfo.getIndustryType());
        projectReportVO.setProjectInfo(projectInfo.getContent());
        projectReportVO.setNeedLand(projectInfo.getNeedLandArea());
        projectReportVO.setNeedLandUnit(projectInfo.getLandUnit());

        String needLandUnit = "亩";
        if(projectInfo.getLandUnit() == 2) {
            needLandUnit = "平方米";
        }
        if(projectInfo.getNeedLandArea() != null) {
            projectReportVO.setNeedLandNew(projectInfo.getNeedLandArea().setScale(0, BigDecimal.ROUND_UP) + needLandUnit);
        }
        projectReportVO.setNeedLandType(projectInfo.getLandType());

        String unitName = CurrencyUnitEnums.getDescByCode(projectInfo.getCurrencyUnit());

        if(unitName != null && projectInfo.getInvestTotal() != null) {
            if(projectInfo.getInvestTotal().compareTo(BigDecimal.ZERO) > 0) {
                //处理为万元  或亿元
                projectReportVO.setAssetInvestNew(CurrencyUtils.getCurrency(projectInfo.getInvestTotal().setScale(0, BigDecimal.ROUND_UP)) + unitName);
            }
        }
        if(projectInfo.getInvestFixed() != null) {
            projectReportVO.setAssetInvest(projectInfo.getInvestFixed() );
        }
        projectReportVO.setCurrencyUnit(projectInfo.getCurrencyUnit());
        projectReportVO.setCurrencyUnitTotal(projectInfo.getCurrencyUnit());

        //根据上报人id获取对应的中文名称
        if(projectInfo.getReportUserId() != null) {
            String reportors = dealReportName(projectInfo.getReportUserId());
            projectReportVO.setStaffName(reportors);
        }

        CodeDeptment codeDeptment = departmentService.selectDeptInfo(projectInfo.getLandDeptId());

        if(codeDeptment!= null) {
            projectReportVO.setGroup(codeDeptment.getName());
            projectReportVO.setRegionFlag(codeDeptment.getId());
        }

        projectReportVO.setSuggestLand(projectInfo.getLandDeptId());
        if(projectInfo.getLandDeptId() != null) {
            projectReportVO.setSuggestLandNew(departmentService.selectDeptByDeptDm(projectInfo.getLandDeptId()));
        }

        List<String> address = new ArrayList<>();
        if(projectInfo.getRegion() != null) {
            address.add("中国");
            address.add(projectInfo.getRegion());
        }
        //总投资
        projectReportVO.setInvestTotal(projectInfo.getInvestTotal());
        if(projectInfo.getInvestTotal() != null) {
            if(projectInfo.getInvestTotal().compareTo(BigDecimal.ZERO) > 0) {
                projectReportVO.setInvestTotalNew(CurrencyUtils.getCurrency(projectInfo.getInvestTotal().setScale(0, BigDecimal.ROUND_UP)) + unitName);
            }
        }
        projectReportVO.setRemark(projectInfo.getMark());


        List<ProjectInvest> xmGlTzfs = projectInvestMapper.selectByProjectId(projectInfo.getId());

        if(CollectionUtils.isNotEmpty(xmGlTzfs)) {
            projectReportVO.setInvestName(xmGlTzfs.get(0).getName());
        }

        //新增字段
        if(projectInfo.getInvestDate() != null) {
            projectReportVO.setMoveTime(getInvestmentDate(projectInfo.getInvestDate(), projectInfo.getCreateTime()));
        }
        projectReportVO.setOfferArea(projectInfo.getOfficeArea());
        projectReportVO.setOtherRequire(projectInfo.getOtherRequires());


        //项目是否已被隐藏
        projectReportVO.setHide(projectInfo.getHideFlag());

        //首报人
        if(projectInfo.getMasterUserDm() != null) {
            UserInfo userInfo = userInfoMapper.selectById(projectInfo.getMasterUserDm());
            projectReportVO.setMasterName(userInfo.getName());
            projectReportVO.setMasterUserId(projectInfo.getMasterUserDm());
        }

    }

    public String dealReportName(String reportIds) {

        if(StringUtils.isBlank(reportIds)) {
            return null;
        }
        String[] reportor = reportIds.split("\\|");

        StringBuffer sb = new StringBuffer();
        for(String str : reportor) {
            UserInfo userInfo  = userInfoMapper.selectById(Integer.valueOf(str));
            if(userInfo == null) {
                continue;
            }
            sb.append(userInfo.getName());
            sb.append(",");
        }
        if(sb == null || sb.length() == 0) {
            return null;
        }

        String result = sb.toString();

        return result.substring(0, result.length()-1);

    }

    private String getInvestmentDate(Date tzyxrq, Date czrq){
        String investmentDate = "";
        Calendar now = Calendar.getInstance();
        now.setTime(czrq);
        Calendar future = Calendar.getInstance();
        future.setTime(tzyxrq);

        int futureYear = future.get(Calendar.YEAR);
        int futureMonth = future.get(Calendar.MONTH);

        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);

        int month = (futureYear-nowYear)*12+futureMonth-nowMonth;

        if(futureYear-nowYear==5){
            return "五年";
        }
        else if(futureYear-nowYear==3){
            return "三年";
        }
        else if(futureYear-nowYear==1){
            return "一年";
        }
        else if(month==6){
            return "半年";
        }
        else {
            return "三月";
        }

    }

}
