package com.quantchi.tianji.service.search.service.project.impl;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.enums.IndustyEnum;
import com.quantchi.tianji.service.search.enums.NeedLandEnums;
import com.quantchi.tianji.service.search.enums.ProjectStatusEnum;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.param.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.vo.*;
import com.quantchi.tianji.service.search.model.vo.project.ProjectReportVO;
import com.quantchi.tianji.service.search.model.vo.project.ProjectVO;
import com.quantchi.tianji.service.search.model.vo.sign.JudgeParam;
import com.quantchi.tianji.service.search.model.vo.sign.ProjectRecordParam;
import com.quantchi.tianji.service.search.service.department.DepartmentService;
import com.quantchi.tianji.service.search.service.project.ProjectDealService;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.service.project.ProjectReportManageService;
import com.quantchi.tianji.service.search.service.project.report.ReportService;
import com.quantchi.tianji.service.search.service.sign.impl.UserInfoService;
import com.quantchi.tianji.service.search.utils.CurrencyUtils;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import com.quantchi.tianji.service.search.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/16 2:32 PM
 */
@Slf4j
@Service
public class ProjectManageServiceImpl implements ProjectManageService {

    @Resource
    private ProjectDealService projectDealService;

    @Resource
    private ProjectRecordMapper projectRecordMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private PreferenceInfoMapper preferenceInfoMapper;

    @Resource
    private ProjectReportManageService projectReportManageService;

    @Resource
    private WfLsChbMapper wfLsChbMapper;

    @Resource
    private WfLsZbMapper wfLsZbMapper;

    @Resource
    private DmWfNodezbMapper dmWfNodezbMapper;

    @Resource
    private DmCsLcztMapper dmCsLcztMapper;

    @Resource
    private XmjbxxZbMapper xmjbxxZbMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private XmGlBqlyMapper xmGlBqlyMapper;

    @Resource
    private DmCsGwjsMapper dmCsGwjsMapper;

    @Resource
    private DmCsBqlyMapper dmCsBqlyMapper;

    @Resource
    private DmCsTzgmMapper dmCsTzgmMapper;

    @Resource
    private DmCsDeptMapper dmCsDeptMapper;

    @Resource
    private DmCsHbdwMapper dmCsHbdwMapper;

    @Resource
    private DmCsYhycMapper dmCsYhycMapper;

    @Resource
    private DmCsUserMapper dmCsUserMapper;

    @Resource
    private DmCsXzqhMapper dmCsXzqhMapper;

    @Resource
    private ReportService reportService;

    @Resource
    private SettingValueMapper settingValueMapper;

    @Resource
    private DmWfRygwdzbMapper dmWfRygwdzbMapper;

    @Override
    public ResultInfo preferenceSet(PreferenceParam preferenceParam) {

        PreferenceInfo preferenceInfo = new PreferenceInfo();
        BeanUtils.copyProperties(preferenceParam, preferenceInfo);

        PreferenceInfo InfoResult = preferenceInfoMapper.fetchOne(preferenceParam.getStaffId());

        if(InfoResult == null) {
            preferenceInfoMapper.insertSelective(preferenceInfo);
        } else {
            preferenceInfo.setId(InfoResult.getId());
            preferenceInfo.setUpdateTime(new Date());
            preferenceInfoMapper.updateByPrimaryKeySelective(preferenceInfo);
        }


        return ResultUtils.success(true);
    }

    @Override
    public ResultInfo queryPreference(String staffId) {

        PreferenceInfo preferenceInfo = preferenceInfoMapper.fetchOne(staffId);

        return ResultUtils.success(preferenceInfo);
    }

    @Override
    public ResultInfo getProjectAll(ProjectQueryParam projectQueryParam) {

        UserInfo userInfo = userInfoService.selectUserInfo(projectQueryParam.getStaffId());

        if (null == userInfo) {
            return ResultUtils.fail(ErrCode.DATA_QUERY_FAIL);
        }

        ProjectResultVO projectResultVO = new ProjectResultVO();

        if (projectQueryParam.getType() == null) {

            //最新进展数据获取及每个节点的总数据
            projectDealService.dealLastProject(projectResultVO, userInfo);

        }else {

            projectResultVO = projectDealService.getProjectData(projectQueryParam, userInfo);
        }

        return ResultUtils.success(projectResultVO);
    }

    @Override
    public ResultInfo updateSignData(String staffId, Long visitId) {

        Integer result = null;

        if(null == result) {
            return ResultUtils.fail(ErrCode.SIGNED);
        }

        return ResultUtils.success(true);
    }

    @Override
    public ResultInfo queryProjectRecord(Long visitId) {
        ProjectRecordParam projectRecordParam = new ProjectRecordParam();

        return ResultUtils.success(projectRecordParam);
    }

    @Override
    public ResultInfo saveProjectRecord(ProjectRecordParam projectRecordParam) {

        ProjectRecord projectRecord = new ProjectRecord();

        //只做新增
        BeanUtils.copyProperties(projectRecordParam, projectRecord);

        ProjectRecord project = projectRecordMapper.selectById(projectRecordParam.getId());

        Long id = null;

        if(null != project) {

            //上传图片后的更新
            projectRecordMapper.updateById(projectRecord);
        }else {

            projectRecordMapper.insertSelective(projectRecord);

            id = projectRecordMapper.selectLastOne();

        }
        //查看是否需将项目状态改为待上报  是否已完成签到、评价和记录
        projectDealService.updateProjectStatus(projectRecordParam.getId());

        return ResultUtils.success(id == null ? "success" : id);
    }

    @Override
    public ResultInfo judgeProject(JudgeParam judgeParam) {

        //修改visit_record状态
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setId(judgeParam.getVisitId());
        visitRecord.setUpdateTime(new Date());
        visitRecord.setStatus(ProjectStatusEnum.REFUSE.getCode());
        visitRecord.setStatusRemark(judgeParam.getMark());

        if (judgeParam.getResulut() == 1) {
            visitRecord.setStatus(ProjectStatusEnum.WAIT_STATION.getCode());
        }

        visitRecordMapper.updateByPrimaryKeySelective(visitRecord);

        return ResultUtils.success(true);
    }

    @Override
    public ResultInfo searchProject(String staffId, String visitName, Integer type, Integer page, Integer pageSize) {

        List<ProjectInfo> projectInfos = new ArrayList<>();

        if(CollectionUtils.isEmpty(projectInfos)) {
            return ResultUtils.success(projectInfos);
        }

        for (ProjectInfo projectInfo : projectInfos) {

            //判断是什么状态
            if(projectInfo.getStatus() == ProjectStatusEnum.WAIT_VISIT.getCode() || projectInfo.getStatus() == ProjectStatusEnum.WAIT_REPORT.getCode()) {

                projectDealService.dealTime(projectInfo);
            }

        }
        projectDealService.dealVisitData(projectInfos);

        return ResultUtils.success(projectInfos);
    }

    @Override
    public ResultInfo projectSearch(ProjectSearchParams projectSearchParams) {
        ProjectVO projectVO = new ProjectVO();

        projectVO = projectReportManageService.searchProject(projectSearchParams);

        return ResultUtils.success(projectVO);
    }

    @Override
    public ResultInfo searchLabels(Integer workFlowId, Integer userId) {

        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        //投资规模、项目标签、行业领域取枚举
        projectReportManageService.labelreport(resultMap, workFlowId, userId);

        return ResultUtils.success(resultMap);
    }

    /**
     * 审批流程 流程主表和子表
     * @param projectReportVO
     * @return
     */
    @Transactional
    public Integer insertWfData(ProjectReportVO projectReportVO) {

        DmCsLczt dmCsLczt = new DmCsLczt();

        if((projectReportVO.getStatus() != null && projectReportVO.getStatus() == 7)
                || projectReportVO.getAuditStatus() == 3) {
            WfLsChb wfLsChb = new WfLsChb();

            //根据项目基本信息id查询工作流子表获取最新流程主表id 为前序流程id
            wfLsChb.setXmjbxxId(projectReportVO.getXmId());
            wfLsChb = wfLsChbMapper.selectOne(wfLsChb);

            //更新前一个流程为终止
            WfLsZb wfLsZb = new WfLsZb();
            wfLsZb.setWfzbId(wfLsChb.getWfzbId());
            wfLsZb.setLczsbj(1);
            wfLsZb.setUserDm(projectReportVO.getUserId());
            wfLsZbMapper.updateByPrimaryKeySelective(wfLsZb);
        }else {
            //暂缓
            if(projectReportVO.getAuditStatus() == 2) {
                return dealPauseData(projectReportVO);
            }
            //获取人员岗位关系代码
            List<DmWfRygwdzb> dmWfRygwdzbList = dmWfRygwdzbMapper.selectByUserDm(projectReportVO.getUserId());
            if(CollectionUtils.isEmpty(dmWfRygwdzbList)) {
                return null;
            }

            //过滤
            Iterator<DmWfRygwdzb> it = dmWfRygwdzbList.iterator();
            while(it.hasNext()) {

                DmWfRygwdzb dmWfRygwdzb = it.next();
                DmCsGwjs dmCsGwjs = dmCsGwjsMapper.selectByPrimaryKey(dmWfRygwdzb.getGwjsDm());
                if(dmCsGwjs.getGwjsDm() != 10010003
                        && dmCsGwjs.getGwjsDm() != 10010004) {
                    it.remove();
                    continue;
                }
            }

            if(dmWfRygwdzbList.size() != 1) {
                return null;
            }

            //根据人员岗位关系获取获取流程节点
            DmWfNodezb record = new DmWfNodezb();
            record.setRygwdzDm(dmWfRygwdzbList.get(0).getRygwdzDm());
            record.setQybj(1);
            DmWfNodezb dmWfNode = dmWfNodezbMapper.selectOne(record);

            //人 岗位 部门 确定下一个工作流设置代码
            Integer next = dmWfNode.getNextWfnodezbDm();

            //next为0说明是商务局
            DmWfNodezb dmWfNodezb = new DmWfNodezb();
            //根据下一个流程设置代码获取状态
            dmWfNodezb = dmWfNodezbMapper.selectNextZtByNode(next);

            if(dmWfNodezb != null) {
                //根据流程状态代码 获取下级设置项目状态
                dmCsLczt = dmCsLcztMapper.selectByPrimaryKey(dmWfNodezb.getLcztDm());
            }

            //新增工作流主表 和流程子表
            dealWfData(projectReportVO, dmWfNodezb);
        }

        return dmCsLczt.getXmztDm();
    }

    private Integer dealPauseData(ProjectReportVO projectReportVO){
        XmjbxxZb xmjbxxZb = xmjbxxZbMapper.selectByPrimaryKey(projectReportVO.getXmId());
        if(xmjbxxZb.getXmztDm() == 4 || xmjbxxZb.getXmztDm() == 5 ||
                xmjbxxZb.getXmztDm() == 6 || xmjbxxZb.getXmztDm() == 7) {
            return null;
        }
        dealWfData(projectReportVO, null);
        return null;
    }

    private void dealWfData(ProjectReportVO projectReportVO, DmWfNodezb dmWfNodezb) {
        //新增工作流主表 和流程子表
        WfLsZb wfLsZb1 = new WfLsZb();
        WfLsChb wfLsChb = new WfLsChb();
        //更新上一条 新增一条待办，且流程还是上一条流程节点
        wfLsChb.setXmjbxxId(projectReportVO.getXmId());
        wfLsChb = wfLsChbMapper.selectOne(wfLsChb);

        WfLsZb wfLsZbEar = wfLsZbMapper.selectByPrimaryKey(wfLsChb.getWfzbId());

        //更新前一个流程为终止
        WfLsZb wfLsZb = new WfLsZb();
        wfLsZb.setWfzbId(wfLsChb.getWfzbId());
        wfLsZb.setLczsbj(1);
        wfLsZb.setXgrq(new Date());
        wfLsZbMapper.updateByPrimaryKeySelective(wfLsZb);

        if(wfLsChb != null) {
            wfLsZb1.setPreWflsId(wfLsChb.getWfzbId());
        }

        wfLsZb1.setUserDm(projectReportVO.getUserId());
        //设置下一节点状态为待办
        wfLsZb1.setLczsbj(0);
        wfLsZb1.setCzrq(new Date());
        wfLsZb1.setWfzbId(UUIDUtils.getZhaoshangUUId());
        wfLsZb1.setWfnodeszbDm(dmWfNodezb == null ? wfLsZbEar.getWfnodeszbDm() : dmWfNodezb.getWfnodezbDm());
        wfLsZbMapper.insertSelective(wfLsZb1);

        //新增子表均（项目基本信息id不变，其他均可变）
        WfLsChb wfLsChb1 = new WfLsChb();
        wfLsChb1.setSpzt(projectReportVO.getAuditStatus());
        wfLsChb1.setWfzbId(wfLsZb1.getWfzbId());
        wfLsChb1.setXmjbxxId(projectReportVO.getXmId());
        wfLsChb1.setUserDm(projectReportVO.getUserId());
        wfLsChb1.setYhycDm(projectReportVO.getYhycDm());
        wfLsChb1.setSpyj(projectReportVO.getAuditRemark());
        wfLsChb1.setCzrq(new Date());
        wfLsChbMapper.insertSelective(wfLsChb1);
    }

    @Transactional
    @Override
    public ResultInfo audit(ProjectReportVO projectReportVO) {

        Integer status = null;
        if(projectReportVO.getStatus() == null || projectReportVO.getStatus() == 7) {

            //如果为组员 则返回状态为3
            String type = "reportSwtich";
            SettingValue settingValue = settingValueMapper.queryByType(type);

            if(Integer.valueOf(settingValue.getValue()) == 1 && projectReportVO.getWorkFlowId().contains("1001001")) {
                //组长自动审批 根据部门确定下一个流程状态
                status = dmWfNodezbMapper.selectXmztDmByUserId(projectReportVO.getUserId());
                if(status == null){
                    status = 4;
                }

            }else{
                status = insertWfData(projectReportVO);
            }
        }
        if(projectReportVO.getStatus() != null ) {
            status = projectReportVO.getStatus();

            //操作加流程
            if(projectReportVO.getStatus() != 7) {
                dealWfData(projectReportVO, null);
            }
        }

        //更新项目信息
        updateProjectData(projectReportVO, status);

        //更新visit_record状态
        if(status != null && status > 3) {
            updateVisitRecordStatus(projectReportVO, status);
        }

        return ResultUtils.success("success");
    }

    public void updateVisitRecordStatus(ProjectReportVO projectReportVO, Integer status) {

        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setStatus(status);
        visitRecord.setId(projectReportVO.getXmId());
        visitRecordMapper.updateByPrimaryKeySelective(visitRecord);

    }

    public void updateProjectData(ProjectReportVO projectReportVO, Integer status) {

        //修改项目基本信息
        XmjbxxZb xmjbxxZb = new XmjbxxZb();

        xmjbxxZb.setXmjbxxId(projectReportVO.getXmId());
        if(projectReportVO.getAuditStatus() == 3) {
            //项目终止
            xmjbxxZb.setXmztDm(1001);
        }else {
            xmjbxxZb.setXmztDm(status);
        }
        xmjbxxZb.setXmmc(projectReportVO.getProjectName());
        xmjbxxZb.setXmYdlxbj(projectReportVO.getNeedLandType());
        xmjbxxZb.setCylxBj(projectReportVO.getIndustryType());
        xmjbxxZb.setXmNr(projectReportVO.getProjectInfo());
        xmjbxxZb.setXmXyd(projectReportVO.getNeedLand());
        xmjbxxZb.setXydUnit(projectReportVO.getNeedLandUnit());
        if(projectReportVO.getAssetInvest() != null){
            xmjbxxZb.setXmGdtzzc(projectReportVO.getAssetInvest());
        }
        xmjbxxZb.setHbdwDm(projectReportVO.getCurrencyUnit());
        xmjbxxZb.setLdptDeptDm(projectReportVO.getSuggestLand());
        xmjbxxZb.setXgrq(new Date());
        xmjbxxZb.setUserDm(projectReportVO.getUserId());
        xmjbxxZb.setXmZtz(projectReportVO.getInvestTotal());
        xmjbxxZb.setBz(projectReportVO.getRemark());
        xmjbxxZb.setTzgmDm(projectReportVO.getInvestmentUnit());
        xmjbxxZb.setXmBgmj(projectReportVO.getOfferArea());
        xmjbxxZb.setXmQtyq(projectReportVO.getOtherRequire());
        xmjbxxZb.setMasterUserDm(projectReportVO.getMasterUserId());
        if(projectReportVO.getMoveTime() != null) {
            xmjbxxZb.setTzyxrq(projectReportManageService.getTzyxrq(projectReportVO.getMoveTime()));
        }
        xmjbxxZbMapper.updateByPrimaryKeySelective(xmjbxxZb);

        //修改投资方信息
        dealInvestData(projectReportVO);

        //异步跟新扩展表信息
        updateProjectHub(projectReportVO);
    }

    private void dealInvestData(ProjectReportVO projectReportVO) {

        //遍历projectReportVO.getInvestParamsList()
        if(CollectionUtils.isNotEmpty(projectReportVO.getInvestParamsList())) {
            for(InvestParams investParams : projectReportVO.getInvestParamsList()) {
                if(investParams.getInvestId() != null) {
                    //说明是已有该投资方，需要修改
                    XmTzf xmTzf = new XmTzf();
                    xmTzf.setTzfId(investParams.getInvestId());
                    xmTzf.setTzfmc(investParams.getInvestName());
                    xmTzf.setTzfjj(investParams.getInvestInfo());
                    //更新投资方联系人
                    String name = "";
                    String job = "";
                    String mobile = "";
                    if(CollectionUtils.isNotEmpty(investParams.getInvestor())) {
                        for(int i = 0; i < investParams.getInvestor().size(); i++) {
                            String[] value = investParams.getInvestor().get(i).split("_");
                            name = name + value[0]+ "|";
                            job = job + value[1]+ "|";
                            mobile = mobile + value[2]+ "|";
                        }
                    }
                    xmTzf.setGjDm(investParams.getInvestNationId());
                    xmTzf.setXzqhDm(investParams.getInvestProvinceId());
                    xmTzf.setTzflxrxm(name.length() > 1 ? name.substring(0, name.length()-1) : null);
                    xmTzf.setTzflxrzw(job.length() > 1 ? job.substring(0,job.length()-1) : null);
                    xmTzf.setTzflxrdh(mobile.length() > 1 ? mobile.substring(0, mobile.length()-1) : null);
                    xmTzfMapper.updateByPrimaryKeySelective(xmTzf);

                } else if(investParams.getInvestName() != null){
                    //说明无该投资方，需新增
                    String id = UUIDUtils.genUuid("1020", 1).get(0);
                    XmTzf xmTzf = new XmTzf();
                    xmTzf.setTzfId(id);
                    xmTzf.setTzfmc(investParams.getInvestName());
                    xmTzf.setTzfjj(investParams.getInvestInfo());
                    String name = "";
                    String job = "";
                    String mobile = "";
                    if(CollectionUtils.isNotEmpty(investParams.getInvestor())) {
                        for(int i = 0; i < investParams.getInvestor().size(); i++) {
                            String[] value = investParams.getInvestor().get(i).split("_");
                            name = name + value[0]+ "|";
                            job = job + value[1]+ "|";
                            mobile = mobile + value[2]+ "|";
                        }
                    }
                    xmTzf.setTzflxrxm(name.length() > 1 ? name.substring(0, name.length()-1) : null);
                    xmTzf.setTzflxrzw(job.length() > 1 ? job.substring(0,job.length()-1) : null);
                    xmTzf.setTzflxrdh(mobile.length() > 1 ? mobile.substring(0, mobile.length()-1) : null);
                    xmTzf.setQybj(1);
                    xmTzfMapper.insertSelective(xmTzf);

                    XmGlTzf xmGlTzf = new XmGlTzf();
                    xmGlTzf.setXmjbxxId(projectReportVO.getXmId());
                    xmGlTzf.setXmtzfId(UUIDUtils.genUuid("1020", 1).get(0));
                    xmGlTzf.setTzfId(id);
                    xmGlTzf.setCzrq(new Date());
                    xmGlTzf.setXgrq(new Date());
                    xmGlTzf.setQybj(1);
                    xmGlTzfMapper.insertSelective(xmGlTzf);
                }

            }
        }

    }

    private void updateProjectHub(ProjectReportVO projectReportVO) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    //项目标签关联 和投资领域
                    List<Integer> bqlyList = new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(projectReportVO.getProjectLabels())) {
                        bqlyList.addAll(projectReportVO.getProjectLabels());
                        //先更新原来的为失效
                        xmGlBqlyMapper.updateInvalidByXmId(projectReportVO.getXmId(), 1);

                    }
                    if(CollectionUtils.isNotEmpty(projectReportVO.getFields())) {
                        bqlyList.addAll(projectReportVO.getFields());
                        //先更新原来的为失效
                        xmGlBqlyMapper.updateInvalidByXmId(projectReportVO.getXmId(), 2);

                    }
                    //高层次人才
                    if(CollectionUtils.isNotEmpty(projectReportVO.getTalentsLabels())) {
                        bqlyList.addAll(projectReportVO.getTalentsLabels());
                        //先更新原来的为失效
                        xmGlBqlyMapper.updateInvalidByXmId(projectReportVO.getXmId(), 3);

                    }
                    //人才引进
                    if(CollectionUtils.isNotEmpty(projectReportVO.getTalentImportLabels())) {
                        //先更新原来的为失效
                        xmGlBqlyMapper.updateInvalidByXmId(projectReportVO.getXmId(), 5);
                        for(TalentParams talentParams : projectReportVO.getTalentImportLabels()) {
                            XmGlBqly xmGlBqly = new XmGlBqly();
                            xmGlBqly.setXmbqlyId(UUIDUtils.genUuid("1020",1).get(0));
                            xmGlBqly.setBqlyDm(talentParams.getTalentId());
                            xmGlBqly.setBqlxSz(talentParams.getTalentCounts());
                            xmGlBqly.setQybj(1);
                            xmGlBqly.setXmjbxxId(projectReportVO.getXmId());
                            xmGlBqlyMapper.insertSelective(xmGlBqly);
                        }

                    }
                    if(CollectionUtils.isNotEmpty(bqlyList)) {
                        //先更新原来的为失效
                        for(Integer integer : bqlyList) {
                            XmGlBqly xmGlBqly = new XmGlBqly();
                            xmGlBqly.setXmbqlyId(UUIDUtils.genUuid("1020",1).get(0));
                            xmGlBqly.setBqlyDm(integer);
                            xmGlBqly.setQybj(1);
                            xmGlBqly.setXmjbxxId(projectReportVO.getXmId());
                            xmGlBqlyMapper.insertSelective(xmGlBqly);
                        }
                    }

                }catch (Exception e) {
                    log.error("更新数据失败, e={}", e);
                }
                log.info("异步同步信息结束");
            }
        }).start();
    }

    @Override
    public ResultInfo submitLeader(SubmitLeaderParam submitLeaderParam) {

        LabelDataVO labelDataVO = new LabelDataVO();

        //获取完成签约的项目数据 状态7签约完成
        Map<Integer, List<Integer>> resultMap = new HashMap<>();

        Map<Integer, Integer> totalMap = new HashMap<>();
        Map<Integer, Integer> lastMap = new HashMap<>();

        //获取总数
        totalMap = calCount(submitLeaderParam, null, null);

        //获取上周个数
        lastMap = calCount(submitLeaderParam, DateUtils.getBeginDayOfLastWeek(), DateUtils.getEndDayOfLastWeek());

        for(Integer key : totalMap.keySet()) {
            List<Integer> increase = new ArrayList<>();
            increase.add(totalMap.get(key));
            increase.add(lastMap.get(key));
            resultMap.put(key, increase);
        }

        labelDataVO.setTime(DateUtils.changeFormatDateToSec(new Date()));
        labelDataVO.setResultMap(resultMap);

        return ResultUtils.success(labelDataVO);
    }

    private Map<Integer, Integer> calCount(SubmitLeaderParam submitLeaderParam, Date startTime, Date endTime) {

        Map<Integer, Integer> map = new HashMap<>();
        //沪资5亿  人才项目  产业项目  外资项目  500强  国企央企
        if(CollectionUtils.isNotEmpty(submitLeaderParam.getProjectLabels())) {
            for (Integer label : submitLeaderParam.getProjectLabels()) {
                Integer count = xmjbxxZbMapper.countByXmBq(null, label, 7, startTime,  endTime);
                map.put(label, count);
            }
        }

        //投资规模的统计
        if(CollectionUtils.isNotEmpty(submitLeaderParam.getInvestScale())) {
            for (Integer scale : submitLeaderParam.getInvestScale()) {
                Integer count = xmjbxxZbMapper.countByScal(null, scale, 7, startTime,  endTime);
                map.put(scale, count);
            }
        }
        return map;
    }

    @Resource
    private XmGlTzfMapper xmGlTzfMapper;

    @Resource
    private XmTzfMapper xmTzfMapper;

    @Override
    public ResultInfo searchOne(String projectId, Integer userId) {

        ProjectReportVO projectReportVO = new ProjectReportVO();

        //查询项目基本信息表
        XmjbxxZb xmjbxxZb = xmjbxxZbMapper.selectByPrimaryKey(projectId);

        List<XmGlTzf> xmGlTzfs = xmGlTzfMapper.selectByProjectId(projectId);

        List<InvestParams> investParamsList = new ArrayList<>();
        for(XmGlTzf xmGlTzf : xmGlTzfs) {
            //企业联系人
            List<String> investors = new ArrayList<>();
            InvestParams investParams = new InvestParams();
            XmTzf xmTzf = xmTzfMapper.selectByPrimaryKey(xmGlTzf.getTzfId());
            if(xmTzf.getQybj() != 1) {
                continue;
            }

            String[] linkName = null;
            String[] linkJob = null;
            String[] linkMobile = null;
            if(StringUtils.isNotBlank(xmTzf.getTzflxrxm())) {
                linkName = xmTzf.getTzflxrxm().split("\\|");
            }
            if(StringUtils.isNotBlank(xmTzf.getTzflxrzw())) {
                linkJob = xmTzf.getTzflxrzw().split("\\|");
            }
            if(StringUtils.isNotBlank(xmTzf.getTzflxrdh())) {
                linkMobile = xmTzf.getTzflxrdh().split("\\|");
            }

            if(linkName != null && linkJob != null && linkMobile != null) {
                for(int i = 0; i < linkName.length; i++) {
                    String investor =  linkName[i]+"_"+linkJob[i]+"_"+linkMobile[i];
                    investors.add(investor);
                }
            }
            investParams.setInvestId(xmGlTzf.getTzfId());
            investParams.setInvestNationId(xmTzf.getGjDm());
            investParams.setInvestProvinceId(xmTzf.getXzqhDm());
            investParams.setInvestName(xmTzf.getTzfmc());
            investParams.setInvestInfo(xmTzf.getTzfjj());
            investParams.setInvestor(investors);
            investParamsList.add(investParams);
        }
        projectReportVO.setInvestParamsList(investParamsList);

        //查询项目标签
        List<Integer> labels = xmGlBqlyMapper.selectListByXmIdAndType(projectId, 1);

        //投资领域
        List<Integer> fields = xmGlBqlyMapper.selectListByXmIdAndType(projectId, 2);

        //高层次人才
        List<Integer> talents = xmGlBqlyMapper.selectListByXmIdAndType(projectId, 3);

        if(talents.contains(1003000)) {
            labels.add(1003000);
            Iterator<Integer> it = talents.iterator();
            while(it.hasNext()) {
                Integer value = it.next();
                if(value == 1003000) {
                    it.remove();
                }
            }
        }

        //人才引进
        List<XmGlBqly> talentImport = xmGlBqlyMapper.queryListByXmIdAndType(projectId, 5);
        List<TalentParams> talentParams = new ArrayList<>();
        for(XmGlBqly xmGlBqly : talentImport) {
            TalentParams talentParams1 = new TalentParams();
            talentParams1.setTalentId(xmGlBqly.getBqlyDm());
            talentParams1.setTalentCounts(xmGlBqly.getBqlxSz());
            talentParams.add(talentParams1);
            if(xmGlBqly.getBqlyDm() == 1005000) {
                labels.add(1005000);
            }
        }

        projectReportManageService.copyProperties(null, xmjbxxZb, projectReportVO);

        projectReportVO.setProjectLabels(labels);
        projectReportVO.setFields(fields);
        projectReportVO.setTalentsLabels(talents);
        projectReportVO.setInvestmentUnit(xmjbxxZb.getTzgmDm());
        projectReportVO.setTalentImportLabels(talentParams);

        //是否显示终止标记
        Integer overFlag = dmWfNodezbMapper.selectFlagDmByUserId(userId);
        projectReportVO.setOverFlag(overFlag);

        return ResultUtils.success(projectReportVO);
    }

    @Override
    public ResultInfo reportList(List<String> projectIds, Integer userId) {

        if(CollectionUtils.isEmpty(projectIds)) {
            return ResultUtils.fail(ErrCode.NOT_NULL);
        }

        for (String projectId : projectIds) {
            reportOne(projectId, userId);
        }
        return ResultUtils.success("success");
    }

    private void reportOne(String projectId, Integer userId) {
        ProjectReportVO projectReportVO = new ProjectReportVO();
        projectReportVO.setUserId(userId);
        projectReportVO.setXmId(projectId);
        projectReportVO.setAuditStatus(1);
        projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(new Date()));
        projectReportVO.setAuditRemark("审核通过");

        //新增流程表数据
        Integer status = insertWfData(projectReportVO);

        //将项目状态更新为2
        XmjbxxZb xmjbxxZb = new XmjbxxZb();
        xmjbxxZb.setXmjbxxId(projectId);
        xmjbxxZb.setXmztDm(status);
        xmjbxxZb.setCzrq(new Date());
        xmjbxxZb.setSbrq(new Date());
        xmjbxxZbMapper.updateByPrimaryKeySelective(xmjbxxZb);
    }

    @Override
    public ResultInfo labelDataOfDepartment(int type, Integer labelOrInvestDm) {

        LabelDataVO labelDataVO = new LabelDataVO();

        List<AreaDataVO> areaDataVOS = new ArrayList<>();

        areaDataVOS = getAreaData(type, labelOrInvestDm);

        labelDataVO.setAreaDataVOS(areaDataVOS);
        labelDataVO.setTime(DateUtils.changeFormatDateYYYYMM(new Date()));

        return ResultUtils.success(labelDataVO);
    }

    private List<AreaDataVO> getAreaData(int type, Integer labelOrInvestDm) {

        List<AreaDataVO> areaDataVOS = new ArrayList<>();

        //招商组片区
        Map<String, DmCsDept> areaMap = departmentService.getArea();

        //获取组
        for(String area : areaMap.keySet()) {

            AreaDataVO areaDataVO = new AreaDataVO();
            areaDataVO.setAreaName(area);
            areaDataVO.setAreaDeptDm(areaMap.get(area).getDeptDm());

            List<Integer> areaList = new ArrayList<>();

            if(areaMap.get(area).getDeptType() == 3) {
                //获取本周上报  本周签约  总签约
                dealShowDataByGroup(areaList, areaMap.get(area).getDeptDm(), type, labelOrInvestDm);
                areaDataVO.setAreaCounts(areaList);
            }
            //如果本身是片区部门则直接统计数据 否则查询下级组
            if(areaMap.get(area).getDeptType() == 2) {

                List<GroupDataVO> groupDataVOList = new ArrayList<>();
                //获取下级部门
                Map<String, DmCsDept> groupMap = departmentService.getGroup(areaMap.get(area).getDeptDm());

                for(String group : groupMap.keySet()) {
                    List<Integer> groupList = new ArrayList<>();

                    GroupDataVO groupDataVO = new GroupDataVO();
                    groupDataVO.setGroupName(group);
                    groupDataVO.setGroupDeptDm(groupMap.get(group).getDeptDm());
                    //获取本周上报  本周签约  总签约
                    dealShowDataByGroup(groupList, groupMap.get(group).getDeptDm(), type, labelOrInvestDm);
                    groupDataVO.setGroupCounts(groupList);
                    groupDataVOList.add(groupDataVO);
                }
                int reportOfWeek = 0;
                int signOfWeek = 0;
                int signOfTotal = 0;
                for(GroupDataVO groupDataVO : groupDataVOList) {
                    reportOfWeek += groupDataVO.getGroupCounts().get(0);
                    signOfWeek += groupDataVO.getGroupCounts().get(1);
                    signOfTotal += groupDataVO.getGroupCounts().get(2);
                }
                areaList.add(reportOfWeek);
                areaList.add(signOfWeek);
                areaList.add(signOfTotal);
                areaDataVO.setAreaCounts(areaList);
                areaDataVO.setGroupDataVO(groupDataVOList);
            }
            areaDataVOS.add(areaDataVO);

        }
        return areaDataVOS;
    }

    @Override
    public ResultInfo submitLeaderAll(SubmitLeaderParam submitLeaderParam) {

        Map<String, List<Integer>> totalMap = new HashMap<>();

        List<List<AreaDataVO>> totalDataList = new ArrayList<>();
        //项目标签
        for(Integer integer : submitLeaderParam.getProjectLabels()) {
            Map<String, List<Integer>> labelMap = new HashMap<>();
            labelMap = dealTotalData(1, integer);
            Set<String> set1 =labelMap.keySet();
            Iterator<String> it = set1.iterator();
            while(it.hasNext()){
                String str = it.next();
                if(totalMap.containsKey(str)){
                    totalMap.put(str, calCount(labelMap.get(str), totalMap.get(str)));
                }else {
                    totalMap.put(str, labelMap.get(str));
                }
            }
        }

        //投资规模
        for(Integer integer : submitLeaderParam.getInvestScale()) {
            Map<String, List<Integer>> investMap = new HashMap<>();
            investMap = dealTotalData(2, integer);
            Set<String> set1 =investMap.keySet();
            Iterator<String> it = set1.iterator();
            while(it.hasNext()){
                String str = it.next();
                if(totalMap.containsKey(str)){
                    totalMap.put(str, calCount(investMap.get(str), totalMap.get(str)));
                }
            }
        }
        List<AreaDataVO> areaDataVOS = new ArrayList<>();
        //招商组片区
        Map<String, DmCsDept> areaMap = departmentService.getArea();

        //获取组
        for(String area : areaMap.keySet()) {

            AreaDataVO areaDataVO = new AreaDataVO();
            areaDataVO.setAreaName(area);
            areaDataVO.setAreaDeptDm(areaMap.get(area).getDeptDm());
            if(areaMap.get(area).getDeptType() == 3) {
                //获取本周上报  本周签约  总签约
                areaDataVO.setAreaCounts(totalMap.get(area));
            }
            //如果本身是片区部门则直接统计数据 否则查询下级组
            if(areaMap.get(area).getDeptType() == 2) {

                List<GroupDataVO> groupDataVOList = new ArrayList<>();
                //获取下级部门
                Map<String, DmCsDept> groupMap = departmentService.getGroup(areaMap.get(area).getDeptDm());

                for(String group : groupMap.keySet()) {
                    GroupDataVO groupDataVO = new GroupDataVO();
                    groupDataVO.setGroupName(group);
                    groupDataVO.setGroupDeptDm(groupMap.get(group).getDeptDm());
                    //获取本周上报  本周签约  总签约
                    groupDataVO.setGroupCounts(totalMap.get(group));
                    groupDataVOList.add(groupDataVO);
                }
                areaDataVO.setGroupDataVO(groupDataVOList);
                areaDataVO.setAreaCounts(totalMap.get(area));
            }
            areaDataVOS.add(areaDataVO);

        }

        return ResultUtils.success(areaDataVOS);
    }

    private List<Integer> calCount(List<Integer> dataNew, List<Integer> dataOld) {

        List<Integer> list = new ArrayList<>();

        int reportOfWeek = 0;
        int signOfWeek = 0;
        int signOfTotal = 0;
        reportOfWeek = dataNew.get(0)+dataOld.get(0);
        signOfWeek = dataNew.get(1)+dataOld.get(1);
        signOfTotal = dataNew.get(2)+dataOld.get(2);

        list.add(reportOfWeek);
        list.add(signOfWeek);
        list.add(signOfTotal);
        return list;

    }

    private Map<String, List<Integer>>  dealTotalData(int type, Integer labelOrInvestDm) {

        Map<String, List<Integer>> totalMap = new HashMap<>();
        //招商组片区
        Map<String, DmCsDept> areaMap = departmentService.getArea();

        //获取组
        for(String area : areaMap.keySet()) {

            List<Integer> areaList = new ArrayList<>();
            //即是组 又是区
            if(areaMap.get(area).getDeptType() == 3) {
                //获取本周上报  本周签约  总签约
                dealShowDataByGroup(areaList, areaMap.get(area).getDeptDm(), type, labelOrInvestDm);
            }
            //查询下级组
            if(areaMap.get(area).getDeptType() == 2) {

                List<GroupDataVO> groupDataVOList = new ArrayList<>();
                //获取下级部门
                Map<String, DmCsDept> groupMap = departmentService.getGroup(areaMap.get(area).getDeptDm());

                for(String group : groupMap.keySet()) {
                    List<Integer> groupList = new ArrayList<>();

                    GroupDataVO groupDataVO = new GroupDataVO();
                    //获取本周上报  本周签约  总签约
                    dealShowDataByGroup(groupList, groupMap.get(group).getDeptDm(), type, labelOrInvestDm);
                    if(totalMap.containsKey(group)) {
                        //计算大小
                        totalMap.put(group, calCount(groupList, totalMap.get(group)));

                    }else{
                        totalMap.put(group, groupList);
                    }
                    groupDataVO.setGroupCounts(groupList);
                    groupDataVOList.add(groupDataVO);
                }
                int reportOfWeek = 0;
                int signOfWeek = 0;
                int signOfTotal = 0;
                for(GroupDataVO groupDataVO : groupDataVOList) {
                    reportOfWeek += groupDataVO.getGroupCounts().get(0);
                    signOfWeek += groupDataVO.getGroupCounts().get(1);
                    signOfTotal += groupDataVO.getGroupCounts().get(2);
                }
                areaList.add(reportOfWeek);
                areaList.add(signOfWeek);
                areaList.add(signOfTotal);
            }
            if(totalMap.containsKey(area)) {
                //计算大小
                totalMap.put(area, calCount(areaList, totalMap.get(area)));

            }else{
                totalMap.put(area, areaList);
            }
        }
        return totalMap;
    }

    @Override
    public ResultInfo departmentRelation(Integer deptId) {

        Map<String, Integer> map = new LinkedHashMap<>();
        List<DmCsDept> dmCsDepts = new ArrayList<>();
        if(deptId == 2001006) {
            dmCsDepts = dmCsDeptMapper.selectDeptByType(3);
        }else {
            dmCsDepts = dmCsDeptMapper.selectBySjDeptDm(deptId);
        }

        for(DmCsDept dmCsDept : dmCsDepts) {
            map.put(dmCsDept.getBmmc(), dmCsDept.getDeptDm());
        }

        return ResultUtils.success(map);
    }

    private void dealShowDataByGroup(List<Integer> dataCount, Integer groupDm, int type, Integer labelOrInvestDm) {

        Date startTime = DateUtils.getBeginDayOfWeek();
        Date endTime = DateUtils.getEndDayOfWeek();
        Integer reportOfWeek = 0;
        Integer signOfWeek = 0;
        Integer signOfTotal = 0;
        if(type == 1) {
            //1为项目标签
            //本周上报数据
            reportOfWeek = xmjbxxZbMapper.countReportByXmBq(groupDm, labelOrInvestDm, startTime, endTime);

            //本周签约
            signOfWeek = xmjbxxZbMapper.countByXmBq(groupDm, labelOrInvestDm, 7, startTime, endTime);

        }else {
            //2为投资规模
            //本周上报数据
            reportOfWeek = xmjbxxZbMapper.countReportByScal(groupDm, labelOrInvestDm, startTime, endTime);

            //本周签约
            signOfWeek = xmjbxxZbMapper.countByScal(groupDm, labelOrInvestDm, 7, startTime, endTime);
        }

        //总签约
        signOfTotal = xmjbxxZbMapper.countByScal(null, labelOrInvestDm, 7, null, null);
        dataCount.add(reportOfWeek);
        dataCount.add(signOfWeek);
        dataCount.add(signOfTotal);
    }

    @Override
    public ResultInfo queryProjectOne(ProjectSearchParams projectSearchParams) {

        ProjectVO projectVO = new ProjectVO();

        ProjectReportDTO projectReportDTO = new ProjectReportDTO();

        BeanUtils.copyProperties(projectSearchParams, projectReportDTO);

        List<XmjbxxZb> xmjbxxZbs = xmjbxxZbMapper.selectListLikeName(projectReportDTO);

        //过滤不是该部门的数据
        Iterator<XmjbxxZb> it = xmjbxxZbs.iterator();

        while(it.hasNext()) {

            XmjbxxZb xmjbxxZb = it.next();

            //过滤非该岗位能看到的数据
            Boolean deptFlag = departmentService.checkDepartment(projectSearchParams.getDepartmentId(), xmjbxxZb.getZszDeptDm());
            if(!deptFlag) {
                it.remove();
                continue;
            }

        }

        //数据转换
        projectReportManageService.turnToShowData(xmjbxxZbs, projectVO, projectSearchParams.getUserId(), projectSearchParams.getPage(), projectSearchParams.getPageSize());

        return ResultUtils.success(projectVO);
    }

    @Resource
    private DmCsGjMapper dmCsGjMapper;

    @Override
    public ResultInfo dataEnumsByType(int type) {

        Map<String, Integer> map = new LinkedHashMap<>();

        // 1项目标签、2投资规模、3投资领域 4落地平台
        switch(type){
            case 1:
                //项目标签
                List<DmCsBqly> dmCsBqlies = dmCsBqlyMapper.selectBqlyAll();
                for(DmCsBqly dmCsBqly : dmCsBqlies) {
                    if(dmCsBqly.getBqlybj() == 1 && dmCsBqly.getSjbqlyDm() == 1001000){
                        map.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
                    }
                    if(dmCsBqly.getBqlyDm() == 1003000){
                        map.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
                    }
                    if(dmCsBqly.getBqlyDm() == 1005000){
                        map.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
                    }
                }
                break;
            case 2:
                //投资规模、
                List<DmCsTzgm> dmCsTzgms = dmCsTzgmMapper.selectTzgmAll();
                for(DmCsTzgm dmCsTzgm : dmCsTzgms) {
                    map.put(dmCsTzgm.getTzgmMc(),dmCsTzgm.getTzgmDm());
                }
                Iterator<String> iterator = map.keySet().iterator();
                while(iterator.hasNext()) {
                    String name = iterator.next();
                    if(map.get(name) == 1) {
                        iterator.remove();
                    }
                }
                break;
            case 3:
                //行业领域
                List<DmCsBqly> dmCsBqlies2 = dmCsBqlyMapper.selectBqlyAll();
                for(DmCsBqly dmCsBqly : dmCsBqlies2) {
                    if(dmCsBqly.getBqlybj() == 2  && dmCsBqly.getCjbj() == 2){
                        map.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
                    }
                }
                break;
            case 4:
                //落地平台
                List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectDeptByType(4);
                for(DmCsDept dmCsDept : dmCsDepts) {
                    map.put(dmCsDept.getBmmc(), dmCsDept.getDeptDm());
                }
                break;
            case 5:
                //货币单位
                List<DmCsHbdw> hbdws = dmCsHbdwMapper.selectAll();
                for(DmCsHbdw dmCsHbdw : hbdws) {
                    map.put(dmCsHbdw.getHbdwMc(), dmCsHbdw.getHbdwDm());
                }
                break;
            case 6:
                //人才引进
                List<DmCsBqly> dmCsBqlies3 = dmCsBqlyMapper.selectBqlyAll();
                for(DmCsBqly dmCsBqly : dmCsBqlies3) {
                    if(dmCsBqly.getBqlybj() == 5 && dmCsBqly.getSjbqlyDm() == 1005000){
                        map.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
                    }
                }
                break;
            case 7:
                //审核不通过用词
                List<DmCsYhyc> dmCsYhycs = dmCsYhycMapper.selectYclyAll(1);
                for(DmCsYhyc DmCsYhyc : dmCsYhycs) {
                    if(DmCsYhyc.getYhycType() == 1){
                        map.put(DmCsYhyc.getYhycmc(), DmCsYhyc.getYhycDm());
                    }
                }
                break;
            case 8:
                //省级行政区
                List<CountryCode> countryCodeList = new ArrayList<>();
                List<DmCsGj> dmCsGjs = dmCsGjMapper.getNation();
                for(DmCsGj dmCsGj : dmCsGjs) {
                    CountryCode countryCode = new CountryCode();
                    countryCode.setName(dmCsGj.getGjmc());
                    countryCode.setCode(dmCsGj.getGjDm());
                    List<DmCsXzqh> dmCsXzqhs = dmCsXzqhMapper.selectProvinceIds(1, countryCode.getCode());
                    List<ProvinceCode> provinceCodes = new ArrayList<>();
                    for(DmCsXzqh dmCsXzqh : dmCsXzqhs) {
                        ProvinceCode provinceCode = new ProvinceCode();
                        provinceCode.setName(dmCsXzqh.getXzqhmc());
                        provinceCode.setCode(dmCsXzqh.getXzqhDm());
                        provinceCodes.add(provinceCode);
                    }
                    countryCode.setProvinceCodeList(provinceCodes);
                    countryCodeList.add(countryCode);
                }
                return ResultUtils.success(countryCodeList);
            case 9:
                //高层次人才项目
                List<DmCsBqly> dmCsBqlies4 = dmCsBqlyMapper.selectBqlyAll();
                for(DmCsBqly dmCsBqly : dmCsBqlies4) {
                    if(dmCsBqly.getBqlybj() == 3 && dmCsBqly.getSjbqlyDm() == 1003000){
                        map.put(dmCsBqly.getBqlyMc(), dmCsBqly.getBqlyDm());
                    }
                }
                break;
        }

        return ResultUtils.success(map);
    }

    @Override
    public ResultInfo groupReportData(Integer userId, Integer deptId) {

        ReportDataCountVO reportDataCountVO = new ReportDataCountVO();

        //总上报数
        int reportTotal = 0;
        //一产 二产 三产
        int oneIndustry = 0;

        int twoIndustry = 0;

        int threeIndustry = 0;
        //人才
        int talent = 0;
        //沪资5亿
        int shanghaiOverFive = 0;

        Boolean isLeader = departmentService.checkLeader(userId);
        if(isLeader) {
            reportTotal = xmjbxxZbMapper.countReportByDeptDm(deptId, null,null, null);

            oneIndustry = xmjbxxZbMapper.countReportByDeptDm(deptId, 1,null, null);

            twoIndustry = xmjbxxZbMapper.countReportByDeptDm(deptId, 2,null, null);

            threeIndustry = xmjbxxZbMapper.countReportByDeptDm(deptId, 3,null, null);

            talent = xmjbxxZbMapper.countReportByXmBq(deptId, 1002,null,null);

            shanghaiOverFive = xmjbxxZbMapper.countReportByXmBq(deptId, 1003,null,null);
        }else {

            List<String> projectMaster = visitRecordMapper.selectXmIdByMasterUser(userId);

            List<String> projectOther = visitRecordMapper.selectXmIdByOtherUser(userId);

            projectMaster.addAll(projectOther);

            if(CollectionUtils.isNotEmpty(projectMaster)) {

                reportTotal = xmjbxxZbMapper.countMemberDataByUserDm(null, projectMaster);

                oneIndustry = xmjbxxZbMapper.countMemberDataByUserDm(1, projectMaster);

                twoIndustry = xmjbxxZbMapper.countMemberDataByUserDm(2, projectMaster);

                threeIndustry = xmjbxxZbMapper.countMemberDataByUserDm(3, projectMaster);

                talent = xmjbxxZbMapper.countMemberDataByXmBq(1002, projectMaster);

                shanghaiOverFive = xmjbxxZbMapper.countMemberDataByXmBq(1003, projectMaster);

            }

        }

        reportDataCountVO.setReportTotal(reportTotal);
        reportDataCountVO.setOneIndustry(oneIndustry);
        reportDataCountVO.setTwoIndustry(twoIndustry);
        reportDataCountVO.setThreeIndustry(threeIndustry);
        reportDataCountVO.setTalent(talent);
        reportDataCountVO.setShanghaiOverFive(shanghaiOverFive);
        reportDataCountVO.setReportTime(DateUtils.changeFormatDateToSec(new Date()));

        //插入用户信息
        DmCsUser dmCsUser = dmCsUserMapper.selectByPrimaryKey(userId);
        reportDataCountVO.setName(dmCsUser.getXm());
        reportDataCountVO.setMobile(dmCsUser.getMobile());
        reportDataCountVO.setGroup(departmentService.selectDeptInfo(dmCsUser.getDeptDm()).getBmmc());

        return ResultUtils.success(reportDataCountVO);
    }

    @Override
    public ResultInfo getDetail(int labelType, Integer labelInvestId, int type, Integer deptId) {

        List<ProjectReportVO> reportVOS = new ArrayList<>();

        Date startTime = DateUtils.getBeginDayOfWeek();
        Date endTime = DateUtils.getEndDayOfWeek();
        List<XmjbxxZb> xmjbxxZbs = new ArrayList<>();
        switch (type) {
            case 1:
                //本周上报
                if(labelType == 1) {
                    xmjbxxZbs = xmjbxxZbMapper.selectReportByBqdm(deptId,labelInvestId, null, startTime, endTime);
                }else if(labelType == 2) {
                    xmjbxxZbs = xmjbxxZbMapper.selectReportByScal(deptId,labelInvestId, null, startTime, endTime);
                }
                break;
            case 2:
                //本周签约
                if(labelType == 1) {
                    xmjbxxZbs = xmjbxxZbMapper.selectSignByBqdm(deptId,labelInvestId, 7, startTime, endTime);
                }else if(labelType == 2) {
                    xmjbxxZbs = xmjbxxZbMapper.selectSignByScal(deptId,labelInvestId, 7, startTime, endTime);
                }
                break;
            case 3:
                //总签约
                if(labelType == 1) {
                    xmjbxxZbs = xmjbxxZbMapper.selectSignByBqdm(deptId,labelInvestId, 7, null, null);
                }else if(labelType == 2) {
                    xmjbxxZbs = xmjbxxZbMapper.selectSignByScal(deptId,labelInvestId, 7, null, null);
                }
                break;
        }
        for(XmjbxxZb xmjbxxZb : xmjbxxZbs) {
            ProjectReportVO projectReportVO = new ProjectReportVO();

            projectReportVO.setProjectName(xmjbxxZb.getXmmc());
            projectReportVO.setXmId(xmjbxxZb.getXmjbxxId());
            reportVOS.add(projectReportVO);
        }

        return ResultUtils.success(reportVOS);
    }

    @Override
    public ResultInfo countLeaderIndustry(Integer userId, Integer deptId) {

        ReportDataCountVO reportDataCountVO = new ReportDataCountVO();

        dealBusinessData(reportDataCountVO, null , null);

        //投资规模数据
        Map<String, Integer> map = new LinkedHashMap<>();
        List<DmCsTzgm> dmCsTzgms = dmCsTzgmMapper.selectTzgmAll();
        for(DmCsTzgm dmCsTzgm : dmCsTzgms) {
            Integer value = xmjbxxZbMapper.countInvestData(null, dmCsTzgm.getTzgmDm(),null, null);

            map.put(dmCsTzgm.getTzgmMc(), value);
        }
        reportDataCountVO.setInvestData(map);

        return ResultUtils.success(reportDataCountVO);
    }

    @Override
    public ResultInfo downLoadExcel(ProjectSearchParams projectSearchParams, HttpServletResponse response) {

        ProjectReportDTO projectReportDTO = new ProjectReportDTO();

        BeanUtils.copyProperties(projectSearchParams, projectReportDTO);

        //先根据时间、地区及行业领域获取相关数据
        if(projectSearchParams.getReportStartTime() != null && projectSearchParams.getReportEndTime() != null) {
            projectReportDTO.setReportStartTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportStartTime())));
            projectReportDTO.setReportEndTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportEndTime())));
        }
        projectReportDTO.setRegions(projectReportDTO.getRegions());

        Boolean signFlag = false;
        if(CollectionUtils.isEmpty(projectSearchParams.getStatusList())) {
            List<Integer> status = new ArrayList<>();
            status.add(7);
            projectReportDTO.setStatusList(status);
            signFlag = true;
        }

        if(projectSearchParams.getDepartmentId() != null) {

            List<Integer> list = new ArrayList<>();
            list.add(projectSearchParams.getDepartmentId());
            projectReportDTO.setDepartmentList(list);

        }

        //根据查询条件获取相应的项目id
        List<String> projects = xmjbxxZbMapper.queryReportDataAllDownExcel(projectReportDTO);

        //查询项目信息
        if(CollectionUtils.isNotEmpty(projects)) {

            List<DownLoadExcelParam> list = new ArrayList<>();

            for(String projectId : projects) {

                DownLoadExcelParam downLoadExcelParam = new DownLoadExcelParam();

                XmjbxxZb xmjbxxZb = xmjbxxZbMapper.selectByPrimaryKey(projectId);

                //项目名称
                downLoadExcelParam.setProjectName(xmjbxxZb.getXmmc());

                List<XmGlTzf> xmGlTzfs = xmGlTzfMapper.selectByProjectId(projectId);


                StringBuffer sbName = new StringBuffer();
                StringBuffer sbInfo = new StringBuffer();
                StringBuffer linkName = new StringBuffer();
                StringBuffer linkMobile = new StringBuffer();
                for(int i = 0; i < xmGlTzfs.size(); i++) {
                    XmTzf xmTzf = xmTzfMapper.selectByPrimaryKey(xmGlTzfs.get(i).getTzfId());
                    if(xmGlTzfs.size() > 1) {
                        String num = i+1 + ":";
                        sbName.append(num + xmTzf.getTzfmc());
                        sbInfo.append(num + xmTzf.getTzfjj());
                        linkName.append(num + xmTzf.getTzflxrxm());
                        linkMobile.append(num + xmTzf.getTzflxrdh());
                    }else {
                        sbName.append(xmTzf.getTzfmc());
                        sbInfo.append(xmTzf.getTzfjj());
                        linkName.append(xmTzf.getTzflxrxm());
                        linkMobile.append(xmTzf.getTzflxrdh());
                    }

                }

                //需用地
                String needLandUnit = "亩";
                if(xmjbxxZb.getXydUnit() == 2) {
                    needLandUnit = "平方米";
                }

                String type = NeedLandEnums.getDescByCode(xmjbxxZb.getXmYdlxbj());

                if(xmjbxxZb.getXmXyd() != null) {
                    if(StringUtils.isNotEmpty(type)){
                        downLoadExcelParam.setNeedLand(xmjbxxZb.getXmXyd() != null ? type + xmjbxxZb.getXmXyd().setScale(0, BigDecimal.ROUND_UP) + needLandUnit : null);
                    }
                }

                DmCsHbdw dmCsHbdw = dmCsHbdwMapper.selectByPrimaryKey(xmjbxxZb.getHbdwDm());

                if(dmCsHbdw != null && xmjbxxZb.getXmGdtzzc() != null) {
                    if(xmjbxxZb.getXmGdtzzc().compareTo(BigDecimal.ZERO) > 0) {
                        //固定资产投资
                        downLoadExcelParam.setAssetInvenst(xmjbxxZb.getXmGdtzzc() != null ? CurrencyUtils.getCurrency(xmjbxxZb.getXmGdtzzc().setScale(0, BigDecimal.ROUND_UP)) + dmCsHbdw.getHbdwMc() : null);
                    }

                }
                if(dmCsHbdw != null && xmjbxxZb.getXmZtz() != null) {

                    if(xmjbxxZb.getXmZtz().compareTo(BigDecimal.ZERO) > 0) {
                        //总投资
                        downLoadExcelParam.setTotalInvenst(xmjbxxZb.getXmZtz() != null ? CurrencyUtils.getCurrency(xmjbxxZb.getXmZtz().setScale(0, BigDecimal.ROUND_UP)) + dmCsHbdw.getHbdwMc() : null);
                    }

                }

                //投资方名称
                downLoadExcelParam.setInvestName(sbName != null ? sbName.toString() : null);

                //投资方简介
                downLoadExcelParam.setInvestInfo(sbInfo != null ? sbInfo.toString() : null);
                //项目内容
                downLoadExcelParam.setProjectInfo(xmjbxxZb.getXmNr());

                //企业联系人
                downLoadExcelParam.setLinkName(linkName != null ? linkName.toString() : null);
                //企业联系人手机号
                downLoadExcelParam.setLinkMobile(linkMobile != null ? linkMobile.toString() : null);

                //跟进人员
                String reportors = projectReportManageService.dealReportName(xmjbxxZb.getSbrUserDm());
                downLoadExcelParam.setStaffName(reportors);

                //首报人
                String masterName = dmCsUserMapper.selectNameByUserDm(xmjbxxZb.getMasterUserDm());
                downLoadExcelParam.setMasterName(masterName);

                //产业类型
                downLoadExcelParam.setIndustryType(xmjbxxZb.getCylxBj() != null ? IndustyEnum.getDescByCode(xmjbxxZb.getCylxBj()) : null);

                //招商组
                downLoadExcelParam.setGroup(departmentService.selectDeptByDeptDm(xmjbxxZb.getZszDeptDm()));
                if(xmjbxxZb.getLdptDeptDm() != null) {
                    //拟落户平台
                    downLoadExcelParam.setSuggestLand(departmentService.selectDeptByDeptDm(xmjbxxZb.getLdptDeptDm()));
                }
                list.add(downLoadExcelParam);
            }
            ReportDataCountVO reportDataCountVO = new ReportDataCountVO();

            if(signFlag) {
                dealBusinessData(reportDataCountVO, projectReportDTO.getReportStartTime(), projectReportDTO.getReportEndTime());
            }
            downLoad(reportDataCountVO, list, response, signFlag);

        }

        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo reportOne(ProjectReportVO projectReportVO) {

        String type = "reportSwtich";
        SettingValue settingValue = settingValueMapper.queryByType(type);

        Integer status = null;
        if(Integer.valueOf(settingValue.getValue()) == 1) {
            //组长自动审批
            status = reportService.reportOne(projectReportVO, projectReportVO.getUserId(), true);
        }else {
            status = reportService.reportOne(projectReportVO, projectReportVO.getUserId(), false);
        }
        if(status == null) {
            return ResultUtils.fail(ErrCode.NODE_NOT_SET);
        }
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setStatus(2);
        visitRecord.setId(projectReportVO.getXmId());
        visitRecordMapper.updateByPrimaryKeySelective(visitRecord);

        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo statisticData(ProjectSearchParams projectSearchParams) {

        List<AreaDataNewVO> areaDataNewVOList = new ArrayList<>();
        //权限通过  获取商务局下的下级部门
        Integer deptDm = 2001006;
        List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectBySjDeptDm(deptDm);

        //查询相关项目信息
        ProjectReportDTO projectReportDTO = new ProjectReportDTO();
        BeanUtils.copyProperties(projectSearchParams, projectReportDTO);
        //时间参数
        if(projectSearchParams.getReportStartTime() != null && projectSearchParams.getReportStartTime() != null) {
            projectReportDTO.setReportStartTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportStartTime())));
            projectReportDTO.setReportEndTime(DateUtils.getDateYYYYMMddHHMMSS(DateUtils.dealTimeData(projectSearchParams.getReportEndTime())));
        }

        //总计
        AreaDataNewVO areaDataNewVO = dealData(projectReportDTO, null, null, true);
        areaDataNewVOList.add(areaDataNewVO);

        //每个的签约 + 在谈
        for(DmCsDept dmCsDept : dmCsDepts){

            List<Integer> groupIds = new ArrayList<>();
            //度假区类型
            if(dmCsDept.getDeptType() == 3) {
                groupIds.add(dmCsDept.getDeptDm());
            }else {
                List<Integer> groups = departmentService.getGroupIds(dmCsDept.getDeptDm());
                groupIds.addAll(groups);
            }

            //处理数据
            AreaDataNewVO areaDataNewVO1 = dealData(projectReportDTO, groupIds, dmCsDept, false);
            areaDataNewVOList.add(areaDataNewVO1);
        }

        return ResultUtils.success(areaDataNewVOList);
    }

    @Override
    public ResultInfo hide(String projectId, int hideFlag) {

        //判断项目是否已经存在
        XmjbxxZb xmjbxxZb = xmjbxxZbMapper.selectByPrimaryKey(projectId);
        if(xmjbxxZb == null) {
            return ResultUtils.fail(1001, "该项目不存在");
        }
        XmjbxxZb xmjbxxZb1 = new XmjbxxZb();
        xmjbxxZb1.setXmYcbj(hideFlag);
        xmjbxxZb1.setXmjbxxId(projectId);
        xmjbxxZbMapper.updateByPrimaryKeySelective(xmjbxxZb1);

        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo deptList() {
        Map<String, Integer> map = new LinkedHashMap<>();

        List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectDeptByType(3);
        for(DmCsDept dmCsDept : dmCsDepts) {
            map.put(dmCsDept.getBmmc(), dmCsDept.getDeptDm());
        }

        return ResultUtils.success(map);

    }

    @Override
    public ResultInfo addRecord(ProjectReportVO projectReportVO) {

        //项目主表插入数据
        XmjbxxZb xmjbxxZb = new XmjbxxZb();
        xmjbxxZb.setXmjbxxId(projectReportVO.getXmId());
        xmjbxxZb.setXmmc(projectReportVO.getProjectName());
        xmjbxxZb.setCylxBj(projectReportVO.getIndustryType());
        xmjbxxZb.setXmNr(projectReportVO.getProjectInfo());
        xmjbxxZb.setXmXyd(projectReportVO.getNeedLand());
        xmjbxxZb.setXmYdlxbj(projectReportVO.getNeedLandType());
        xmjbxxZb.setXydUnit(projectReportVO.getNeedLandUnit());
        xmjbxxZb.setXmBgmj(projectReportVO.getOfferArea());
        xmjbxxZb.setXmZtz(projectReportVO.getInvestTotal());
        xmjbxxZb.setXmGdtzzc(projectReportVO.getAssetInvest());
        xmjbxxZb.setHbdwDm(projectReportVO.getCurrencyUnitTotal());
        xmjbxxZb.setTzgmDm(projectReportVO.getInvestmentUnit());
        xmjbxxZb.setLdptDeptDm(projectReportVO.getSuggestLand());
        xmjbxxZb.setTzfXzqhDm(null);
        if(projectReportVO.getUserId() != null){
            Integer deptDm = dmCsUserMapper.getDeptDmByUserId(projectReportVO.getUserId());
            xmjbxxZb.setZszDeptDm(deptDm);
        }
        xmjbxxZb.setXmQtyq(projectReportVO.getOtherRequire());
        // 三月、半年、一年、三年、五年转date
        if(projectReportVO.getMoveTime()!=null){
            xmjbxxZb.setTzyxrq(projectReportManageService.getTzyxrq(projectReportVO.getMoveTime()));
        }

        //查询visit_record表获取同行人
        VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(projectReportVO.getXmId());
        Integer userDm = dmCsUserMapper.getUserDmByStaffId(visitRecord.getStaffId());
        String sbrUserDm = String.valueOf(userDm);
        if(StringUtils.isNotEmpty(visitRecord.getVisitPartner())){

            List<Integer> parts = visitRecordMapper.selectByMasterId(projectReportVO.getXmId());
            for(Integer integer : parts) {
                sbrUserDm = sbrUserDm + "|" + integer;
            }

        }
        xmjbxxZb.setSbrUserDm(sbrUserDm);
        xmjbxxZb.setBz(projectReportVO.getRemark());
        xmjbxxZb.setUserDm(projectReportVO.getUserId());
        xmjbxxZb.setMasterUserDm(projectReportVO.getMasterUserId());
        /// 项目状态
        xmjbxxZb.setXmztDm(1);
        xmjbxxZbMapper.insertSelective(xmjbxxZb);

        //投资方信息
        for(InvestParams investParams :projectReportVO.getInvestParamsList()){
            String id = UUIDUtils.getZhaoshangUUId();
            XmTzf xmTzf = new XmTzf();
            xmTzf.setTzfId(id);
            xmTzf.setTzfmc(investParams.getInvestName());
            xmTzf.setTzfjj(investParams.getInvestInfo());
            xmTzf.setGjDm(investParams.getInvestNationId());
            xmTzf.setXzqhDm(investParams.getInvestProvinceId());
            String name = "";
            String job = "";
            String mobile = "";
            if(CollectionUtils.isNotEmpty(investParams.getInvestor())) {
                for(int i = 0; i < investParams.getInvestor().size(); i++) {
                    String[] value = investParams.getInvestor().get(i).split("_");
                    name = name + value[0]+ "|";
                    job = job + value[1]+ "|";
                    mobile = mobile + value[2]+ "|";
                }
            }
            xmTzf.setTzflxrxm(name.length() > 1 ? name.substring(0, name.length()-1) : null);
            xmTzf.setTzflxrzw(job.length() > 1 ? job.substring(0,job.length()-1) : null);
            xmTzf.setTzflxrdh(mobile.length() > 1 ? mobile.substring(0, mobile.length()-1) : null);
            xmTzf.setQybj(1);
            xmTzfMapper.insertSelective(xmTzf);

            XmGlTzf xmGlTzf = new XmGlTzf();
            xmGlTzf.setXmjbxxId(projectReportVO.getXmId());
            xmGlTzf.setXmtzfId(UUIDUtils.getZhaoshangUUId());
            xmGlTzf.setTzfId(id);
            xmGlTzf.setCzrq(new Date());
            xmGlTzf.setXgrq(new Date());
            xmGlTzf.setQybj(1);
            xmGlTzfMapper.insertSelective(xmGlTzf);
        }
        List<Integer> bqlyList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(projectReportVO.getTalentsLabels())) {
            bqlyList.addAll(projectReportVO.getTalentsLabels());
        }
        if(CollectionUtils.isNotEmpty(projectReportVO.getProjectLabels())) {
            bqlyList.addAll(projectReportVO.getProjectLabels());
        }
        if(CollectionUtils.isNotEmpty(projectReportVO.getFields())) {
            bqlyList.addAll(projectReportVO.getFields());
        }

        //项目标签表
        if(CollectionUtils.isNotEmpty(bqlyList)) {
            for(Integer integer : bqlyList) {
                XmGlBqly xmGlBqly = new XmGlBqly();
                xmGlBqly.setXmbqlyId(UUIDUtils.getZhaoshangUUId());
                xmGlBqly.setBqlyDm(integer);
                xmGlBqly.setQybj(1);
                xmGlBqly.setXmjbxxId(projectReportVO.getXmId());
                xmGlBqlyMapper.insertSelective(xmGlBqly);
            }
        }

        //高级人才
        if(CollectionUtils.isNotEmpty(projectReportVO.getTalentImportLabels())) {
            for(TalentParams talentParams : projectReportVO.getTalentImportLabels()) {
                XmGlBqly xmGlBqly = new XmGlBqly();
                xmGlBqly.setXmbqlyId(UUIDUtils.getZhaoshangUUId());
                xmGlBqly.setBqlyDm(talentParams.getTalentId());
                xmGlBqly.setBqlxSz(talentParams.getTalentCounts());
                xmGlBqly.setQybj(1);
                xmGlBqly.setXmjbxxId(projectReportVO.getXmId());
                xmGlBqlyMapper.insertSelective(xmGlBqly);
            }
        }

        return ResultUtils.success("success");
    }

    @Override
    public ResultInfo reportSearch(ProjectSearchParams projectSearchParams) {

        ProjectVO projectVO = new ProjectVO();

        projectVO = projectReportManageService.reportSearch(projectSearchParams);

        return ResultUtils.success(projectVO);
    }

    @Override
    public ResultInfo recordingSearch(Integer userId) {

        List<VisitRecord> visitRecords = new ArrayList<>();

        //校验是否为组长
        Boolean isLeader = departmentService.checkLeader(userId);

        //根据userId获取部门
        Integer deptId = dmCsUserMapper.getDeptDmByUserId(userId);

        List<String> visitIds = new ArrayList<>();
        if(isLeader) {
            //组长看所有人数据
            //所有行程数据
            visitIds = visitRecordMapper.getAllDataByDeptDm(deptId);

            //所有项目记录数据
            List<String> projectIds = xmjbxxZbMapper.getAllDataByDeptDm(deptId);

            visitIds.removeAll(projectIds);

        } else{
            //组员看自己或为同行人的数据
            List<String> projectMaster = visitRecordMapper.selectXmIdByMasterUser(userId);

            List<String> projectOther = visitRecordMapper.selectXmIdByOtherUser(userId);

            visitIds.addAll(projectOther);
            visitIds.addAll(projectMaster);

            //遍历查看哪些id没有项目记录
            Iterator<String> it = visitIds.iterator();
            while(it.hasNext()) {
                String projectId = it.next();

                XmjbxxZb xmjbxxZb = xmjbxxZbMapper.selectByPrimaryKey(projectId);

                if(xmjbxxZb != null) {
                    it.remove();
                    continue;
                }
            }

        }

        Iterator<String> it = visitIds.iterator();
        while(it.hasNext()) {
            String projectId = it.next();

            VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(projectId);

            if(visitRecord == null) {
                continue;
            }
            String name = dmCsUserMapper.selectNameByDingId(visitRecord.getStaffId());

            visitRecord.setVisitTimeDesc(DateUtils.changeFormatDate(visitRecord.getVisitTime()));
            visitRecord.setStaffIdName(name);
            visitRecords.add(visitRecord);
        }

        //按拜访时间正序
        if (CollectionUtils.isNotEmpty(visitRecords)) {
            Collections.sort(visitRecords, new Comparator<VisitRecord>() {
                @Override
                public int compare(VisitRecord o1, VisitRecord o2) {
                    int diff = o1.getVisitTime().compareTo(o2.getVisitTime());
                    if(diff > 0) {
                        return 1;
                    } else if(diff < 0 ) {
                        return -1;
                    }
                    return 0;
                }
            });

        }

        return ResultUtils.success(visitRecords);
    }

    @Transactional
    @Override
    public ResultInfo delete(String projectId) {

        //主表更新
        xmjbxxZbMapper.deleteByProjectId(projectId);

        //visit_record更新
        visitRecordMapper.deleteByProjectId(projectId);

        //同行人数据更新
        visitRecordMapper.deleteByMasterId(projectId);

        //标签领域更新
        xmGlBqlyMapper.deleteByProjectId(projectId);

        //投资方更新
        xmGlTzfMapper.deleteByProjectId(projectId);

        return ResultUtils.success("success");
    }

    private AreaDataNewVO dealData(ProjectReportDTO projectReportDTO, List<Integer> groupIds,
                                   DmCsDept dmCsDept, Boolean flag) {

        //在谈 + 待初审+待研判+待评价+待签约
        List<Integer> unsign = new ArrayList<>();
        unsign.add(3);
        unsign.add(4);
        unsign.add(5);
        unsign.add(6);
        //签约
        List<Integer> signed = new ArrayList<>();
        signed.add(7);

        AreaDataNewVO areaDataNewVO = new AreaDataNewVO();
        projectReportDTO.setDepartmentList(groupIds);
        //签约数量
        projectReportDTO.setStatusList(signed);
        Integer signedCount = xmjbxxZbMapper.countProjectDataByParam(projectReportDTO);
        List<XmjbxxZb> signedList = xmjbxxZbMapper.getProjectDataByParam(projectReportDTO);
        List<ProjectReportVO> signedIds = dealProject(signedList);
        //在谈数量
        projectReportDTO.setStatusList(unsign);
        Integer unSignCount = xmjbxxZbMapper.countProjectDataByParam(projectReportDTO);
        List<XmjbxxZb> unsignList = xmjbxxZbMapper.getProjectDataByParam(projectReportDTO);
        List<ProjectReportVO> unsignedIds = dealProject(unsignList);

        if(flag) {
            areaDataNewVO.setAreaName("总计");
        }else {
            areaDataNewVO.setAreaName(dmCsDept.getBmmc());
        }
        areaDataNewVO.setSignCounts(signedCount);
        areaDataNewVO.setUnsignCounts(unSignCount);
        areaDataNewVO.setSignedData(signedIds);
        areaDataNewVO.setUnsignData(unsignedIds);

        return areaDataNewVO;
    }

    private List<ProjectReportVO> dealProject(List<XmjbxxZb> xmjbxxZbs){
        List<ProjectReportVO> projectReportVOList = new ArrayList<>();
        //数据转换
        for(XmjbxxZb xmjbxxZb : xmjbxxZbs) {
            ProjectReportVO projectReportVO = new ProjectReportVO();
            projectReportManageService.copyProperties(null, xmjbxxZb, projectReportVO);
            //上报时间
            projectReportVO.setAuditTime(DateUtils.changeFormatDateToSec(xmjbxxZb.getSbrq()));
            VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(xmjbxxZb.getXmjbxxId());
            UserInfo userInfo = userInfoService.selectUserInfo(visitRecord.getStaffId());
            projectReportVO.setStaffName(userInfo.getStaffName());
            projectReportVO.setDepartment(departmentService.selectDeptByDeptDm(xmjbxxZb.getZszDeptDm()));
            projectReportVOList.add(projectReportVO);
        }
        return projectReportVOList;
    }


    private void downLoad(ReportDataCountVO reportDataCountVO, List<DownLoadExcelParam> list, HttpServletResponse response, Boolean signFlag) {

        int value = 1;

        //先定义创建excel表头
        String[] title={"序号", "项目名称", "投资方", "总投资", "固定资产投资","投资方简介",
                "项目内容","需用土地","企业联系人","联系方式","招商组/片区","跟进人员/招商员","首报人","产业类型","落地平台"};
        //创建excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表sheet
        HSSFSheet sheet=workbook.createSheet("签约数据");
        HSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //创建第一行，由于表头字段是固定好的，所以第一行的值单独插入就行了
        //第一行
        HSSFRow row=sheet.createRow(0);
        HSSFCell cell=row.createCell(0);
        String year = DateUtils.dateFormatYear(new Date());
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 14);
        cell.setCellStyle(style);
        cell.setCellValue(year+"年德清县驻点招商项目信息采集表");
        sheet.addMergedRegion(region);
        //第二行
        if(signFlag) {
            row = sheet.createRow(1);
            cell = row.createCell(0);
            region=new CellRangeAddress(1, 1, 0, 14);
            cell.setCellStyle(style);
            String projectValue = "共在谈项目"+reportDataCountVO.getReportTotal()+"个，其中一产"+reportDataCountVO.getOneIndustry()
                    +"个，二产"+reportDataCountVO.getTwoIndustry()+"个，三产"+reportDataCountVO.getThreeIndustry()
                    +"个，人才项目"+reportDataCountVO.getTalent()+"个，沪资5亿元以上项目"+reportDataCountVO.getShanghaiOverFive()+"个";
            cell.setCellValue(projectValue);
            sheet.addMergedRegion(region);
            value++;
        }
        //表头
        HSSFCellStyle listStyle=workbook.createCellStyle();
        listStyle.setAlignment(HorizontalAlignment.CENTER);
        listStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        listStyle.setBorderBottom(BorderStyle.THIN);
        listStyle.setBorderLeft(BorderStyle.THIN);
        listStyle.setBorderRight(BorderStyle.THIN);
        listStyle.setBorderTop(BorderStyle.THIN);
        row=sheet.createRow(value);
        //插入第一行数据的表头，用到上面的title数组
        for(int i=0;i<title.length;i++){
            //createCell(0)表示从左到右第一个空格哈，是依横向次插入的。
            cell=row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(listStyle);
        }
        //此时，excel表的表头部分就完成了。
        List<Map<String, String>> mapList = new ArrayList<>();
        //list转Map
        excelListMap(list, mapList);
        for (int i = 0; i < mapList.size(); i++) {
            row = sheet.createRow( i + value + 1);
            Map<String, String> map = mapList.get(i);
            for (int j = 0 ;j < title.length; j++) {
                cell = row.createCell(j);
                if (j == 0 ){
                    cell.setCellValue(i + 1);
                }else {
                    cell.setCellValue(map.get(title[j]));
                }
                cell.setCellStyle(listStyle);
            }
        }
        //至此，就完整的插入一行了。

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            //清空缓存
            response.reset();
            //定义浏览器响应表头，顺带定义下载名
            String name = DateUtils.changeFormatDate(new Date()) + "签约数据";
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            //定义下载的类型，标明是excel文件
            response.setContentType("application/ms-excel");
            //这时候把创建好的excel写入到输出流
            workbook.write(output);
            //养成好习惯，出门记得随手关门
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void dealBusinessData(ReportDataCountVO reportDataCountVO, Date startTime, Date endTime) {
        //总上报数
        int reportTotal = 0;
        reportTotal = xmjbxxZbMapper.countSignByDeptDm(null, null,startTime, endTime);

        //一产 二产 三产
        int oneIndustry = 0;
        oneIndustry = xmjbxxZbMapper.countSignByDeptDm(null, 1,startTime, endTime);

        int twoIndustry = 0;
        twoIndustry = xmjbxxZbMapper.countSignByDeptDm(null, 2,startTime, endTime);

        int threeIndustry = 0;
        threeIndustry = xmjbxxZbMapper.countSignByDeptDm(null, 3,startTime, endTime);

        //人才
        int talent = 0;
        talent = xmjbxxZbMapper.countSignByXmBq(null, 1002,startTime,endTime);

        //沪资5亿
        int shanghaiOverFive = 0;
        shanghaiOverFive = xmjbxxZbMapper.countSignByXmBq(null, 1003,startTime,endTime);

        reportDataCountVO.setReportTotal(reportTotal);
        reportDataCountVO.setOneIndustry(oneIndustry);
        reportDataCountVO.setTwoIndustry(twoIndustry);
        reportDataCountVO.setThreeIndustry(threeIndustry);
        reportDataCountVO.setTalent(talent);
        reportDataCountVO.setShanghaiOverFive(shanghaiOverFive);
        reportDataCountVO.setReportTime(DateUtils.changeFormatDateToSec(new Date()));
    }

    private void excelListMap(List<DownLoadExcelParam> list, List<Map<String, String>> mapList) {

        for(int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("项目名称", list.get(i).getProjectName());
            map.put("投资方", list.get(i).getInvestName());
            map.put("总投资", list.get(i).getTotalInvenst());
            map.put("固定资产投资", list.get(i).getAssetInvenst());
            map.put("投资方简介", list.get(i).getInvestInfo());
            map.put("项目内容", list.get(i).getProjectInfo());
            map.put("需用土地", list.get(i).getNeedLand());
            map.put("企业联系人", list.get(i).getLinkName());
            map.put("联系方式", list.get(i).getLinkMobile());
            map.put("招商组/片区", list.get(i).getGroup());
            map.put("跟进人员/招商员", list.get(i).getStaffName());
            map.put("首报人", list.get(i).getMasterName());
            map.put("产业类型", list.get(i).getIndustryType());
            map.put("落地平台", list.get(i).getSuggestLand());
            mapList.add(map);
        }

    }

}
