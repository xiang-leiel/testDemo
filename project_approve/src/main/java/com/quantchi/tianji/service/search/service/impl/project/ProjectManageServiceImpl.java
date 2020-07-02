package com.quantchi.tianji.service.search.service.impl.project;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.CodeCountryMapper;
import com.quantchi.tianji.service.search.dao.CodeRegionMapper;
import com.quantchi.tianji.service.search.dao.project.CodeLabelMapper;
import com.quantchi.tianji.service.search.dao.project.ProjectInfoMapper;
import com.quantchi.tianji.service.search.dao.project.ProjectInvestMapper;
import com.quantchi.tianji.service.search.dao.project.ProjectLabelMapper;
import com.quantchi.tianji.service.search.dao.CodeDeptmentMapper;
import com.quantchi.tianji.service.search.entity.CodeCountry;
import com.quantchi.tianji.service.search.entity.CodeRegion;
import com.quantchi.tianji.service.search.entity.project.CodeLabel;
import com.quantchi.tianji.service.search.entity.project.ProjectInfo;
import com.quantchi.tianji.service.search.entity.project.ProjectInvest;
import com.quantchi.tianji.service.search.entity.project.ProjectLabel;
import com.quantchi.tianji.service.search.entity.CodeDeptment;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.enums.InvestmentScaleEnum;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.model.vo.ProjectVO;
import com.quantchi.tianji.service.search.model.vo.ReportDataCountVO;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.service.project.ProjectReportManageService;
import com.quantchi.tianji.service.search.service.project.ReportService;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import com.quantchi.tianji.service.search.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/29 2:39 PM
 */
@Slf4j
@Service
public class ProjectManageServiceImpl implements ProjectManageService {

    @Resource
    private ReportService reportService;

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Resource
    private ProjectInvestMapper projectInvestMapper;

    @Resource
    private ProjectLabelMapper projectLabelMapper;

    @Resource
    private ProjectReportManageService projectReportManageService;

    @Resource
    private CodeLabelMapper codeLabelMapper;

    @Resource
    private CodeDeptmentMapper codeDeptmentMapper;

    @Resource
    private CodeCountryMapper codeCountryMapper;

    @Resource
    private CodeRegionMapper codeRegionMapper;

    @Override
    public ResultInfo projectSearch(ProjectSearchParams projectSearchParams) {
        ProjectVO projectVO = new ProjectVO();

        projectVO = projectReportManageService.searchProject(projectSearchParams);

        return ResultUtils.success(projectVO);
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
        ProjectReportVO projectReportVO = new ProjectReportVO();

        //查询项目基本信息表
        ProjectInfo projectInfo = projectInfoMapper.selectById(projectId);

        List<ProjectInvest> xmGlTzfs = projectInvestMapper.selectByProjectId(projectId);

        List<InvestParams> investParamsList = new ArrayList<>();
        for(ProjectInvest projectInvest : xmGlTzfs) {
            //企业联系人
            List<String> investors = new ArrayList<>();
            InvestParams investParams = new InvestParams();

            String[] linkName = null;
            String[] linkJob = null;
            String[] linkMobile = null;
            if(StringUtils.isNotBlank(projectInvest.getRelateUserName())) {
                linkName = projectInvest.getRelateUserName().split("\\|");
            }
            if(StringUtils.isNotBlank(projectInvest.getRelateUserJob())) {
                linkJob = projectInvest.getRelateUserJob().split("\\|");
            }
            if(StringUtils.isNotBlank(projectInvest.getRelateUserMobile())) {
                linkMobile = projectInvest.getRelateUserMobile().split("\\|");
            }

            if(linkName != null && linkJob != null && linkMobile != null) {
                for(int i = 0; i < linkName.length; i++) {
                    String investor =  linkName[i]+"_"+linkJob[i]+"_"+linkMobile[i];
                    investors.add(investor);
                }
            }
            investParams.setInvestId(projectInvest.getId());
            investParams.setCountry(projectInvest.getCountry());
            investParams.setRegion(projectInvest.getRegion());
            investParams.setInvestName(projectInvest.getName());
            investParams.setInvestInfo(projectInvest.getIntroduction());
            investParams.setInvestor(investors);
            investParamsList.add(investParams);
        }
        projectReportVO.setInvestParamsList(investParamsList);

        //查询项目标签
        List<Integer> labels = projectLabelMapper.selectListByXmIdAndType(projectId, 1);

        //投资领域
        List<Integer> fields = projectLabelMapper.selectListByXmIdAndType(projectId, 2);

        //高层次人才
        List<Integer> talents = projectLabelMapper.selectListByXmIdAndType(projectId, 3);

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
        List<ProjectLabel> talentImport = projectLabelMapper.queryListByXmIdAndType(projectId, 5);
        List<TalentParams> talentParams = new ArrayList<>();
        for(ProjectLabel projectLabel : talentImport) {
            TalentParams talentParams1 = new TalentParams();
            talentParams1.setTalentId(projectLabel.getLabelId());
            talentParams1.setTalentCounts(projectLabel.getLabelCount());
            talentParams.add(talentParams1);
            if(projectLabel.getLabelId() == 1005000) {
                labels.add(1005000);
            }
        }

        projectReportManageService.copyProperties(null, projectInfo, projectReportVO);

        projectReportVO.setProjectLabels(labels);
        projectReportVO.setFields(fields);
        projectReportVO.setTalentsLabels(talents);
        projectReportVO.setInvestmentUnit(projectInfo.getInvestScale());
        projectReportVO.setTalentImportLabels(talentParams);

        //是否显示终止标记
        projectReportVO.setOverFlag(1);

        return ResultUtils.success(projectReportVO);
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
        Map<String, Integer> map = new LinkedHashMap<>();

        // 1项目标签、2投资规模、3投资领域 4落地平台
        switch(type){
            case 1:
                //项目标签
                List<CodeLabel> labelList = codeLabelMapper.selectBqlyAll();
                for(CodeLabel codeLabel : labelList) {
                    if(codeLabel.getType() == 1 && codeLabel.getPid() == 1001000){
                        map.put(codeLabel.getName(), codeLabel.getId());
                    }
                    if(codeLabel.getId() == 1003000){
                        map.put(codeLabel.getName(), codeLabel.getId());
                    }
                    if(codeLabel.getId() == 1005000){
                        map.put(codeLabel.getName(), codeLabel.getId());
                    }
                }
                break;
            case 2:
                //行业领域
                List<CodeLabel> dmCsBqlies2 = codeLabelMapper.selectBqlyAll();
                for(CodeLabel codeLabel : dmCsBqlies2) {
                    if(codeLabel.getType() == 2  && codeLabel.getLevel() == 2){
                        map.put(codeLabel.getName(), codeLabel.getId());
                    }
                }
                break;
            case 3:
                //落地平台
                List<CodeDeptment> deptList = codeDeptmentMapper.selectDeptByType(4);
                for(CodeDeptment CodeDeptment : deptList) {
                    map.put(CodeDeptment.getName(), CodeDeptment.getId());
                }
                break;
            case 4:
                //人才引进
                List<CodeLabel> dmCsBqlies3 = codeLabelMapper.selectBqlyAll();
                for(CodeLabel codeLabel : dmCsBqlies3) {
                    if(codeLabel.getType() == 5 && codeLabel.getPid() == 1005000){
                        map.put(codeLabel.getName(), codeLabel.getId());
                    }
                }
                break;
            case 5:
                //省级行政区
                List<CountryCode> countryCodeList = new ArrayList<>();
                List<CodeCountry> countryList = codeCountryMapper.getNation();
                for(CodeCountry codeCountry : countryList) {
                    CountryCode countryCode = new CountryCode();
                    countryCode.setName(codeCountry.getName());
                    countryCode.setCode(codeCountry.getId());
                    List<CodeRegion> regionList = codeRegionMapper.selectProvinceIds(1, countryCode.getCode());
                    List<RegionCode> provinceCodes = new ArrayList<>();
                    for(CodeRegion codeRegion : regionList) {
                        RegionCode provinceCode = new RegionCode();
                        provinceCode.setName(codeRegion.getName());
                        provinceCode.setCode(codeRegion.getId());
                        provinceCodes.add(provinceCode);
                    }
                    countryCode.setRegionCodeList(provinceCodes);
                    countryCodeList.add(countryCode);
                }
                return ResultUtils.success(countryCodeList);
            case 6:
                //高层次人才项目
                List<CodeLabel> dmCsBqlies4 = codeLabelMapper.selectBqlyAll();
                for(CodeLabel codeLabel : dmCsBqlies4) {
                    if(codeLabel.getType() == 3 && codeLabel.getPid() == 1003000){
                        map.put(codeLabel.getName(), codeLabel.getId());
                    }
                }
                break;
            default :
        }

        return ResultUtils.success(map);
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
        ReportDataCountVO reportDataCountVO = new ReportDataCountVO();

        dealBusinessData(reportDataCountVO, null , null);

        //投资规模数据
        Map<String, Integer> map = new LinkedHashMap<>();

        List<Integer> scaleList = InvestmentScaleEnum.getList();

        for(Integer scale : scaleList) {
            Integer value = projectInfoMapper.countInvestData(null, scale,null, null);

            map.put(InvestmentScaleEnum.getDescByCode(scale), value);
        }
        reportDataCountVO.setInvestData(map);

        return ResultUtils.success(reportDataCountVO);
    }

    @Override
    public ResultInfo downLoadExcel(ProjectSearchParams projectSearchParams, HttpServletResponse response) {
        return null;
    }

    @Override
    public ResultInfo reportOne(ProjectReportVO projectReportVO) {

        Integer status = reportService.reportOne(projectReportVO, projectReportVO.getUserId());

        if(status == null) {
            return ResultUtils.fail(ErrCode.NODE_NOT_SET);
        }
        //修改行程状态

        return ResultUtils.success("success");

    }

    @Override
    public ResultInfo hide(String projectId, int hideFlag) {
        //判断项目是否已经存在
        ProjectInfo projectInfo = projectInfoMapper.selectById(projectId);
        if(projectInfo == null) {
            return ResultUtils.fail(1001, "该项目不存在");
        }
        ProjectInfo projectInfo1 = new ProjectInfo();
        projectInfo1.setHideFlag(hideFlag);
        projectInfo1.setId(projectId);
        projectInfoMapper.updateById(projectInfo1);

        return ResultUtils.success("success");
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
        //主表更新
        projectInfoMapper.deleteByProjectId(projectId);

        //标签领域更新
        projectLabelMapper.deleteByProjectId(projectId);

        //投资方更新
        projectInvestMapper.deleteByProjectId(projectId);

        return ResultUtils.success("success");
    }

    public void updateProjectData(ProjectReportVO projectReportVO, Integer status) {

        //修改项目基本信息
        ProjectInfo projectInfo = new ProjectInfo();

        projectInfo.setId(projectReportVO.getXmId());
        if(projectReportVO.getAuditStatus() == 3) {
            //项目终止
            projectInfo.setStatus(1001);
        }else {
            projectInfo.setStatus(status);
        }
        projectInfo.setName(projectReportVO.getProjectName());
        projectInfo.setLandType(projectReportVO.getNeedLandType());
        projectInfo.setIndustryType(projectReportVO.getIndustryType());
        projectInfo.setContent(projectReportVO.getProjectInfo());
        projectInfo.setNeedLandArea(projectReportVO.getNeedLand());
        projectInfo.setLandUnit(projectReportVO.getNeedLandUnit());
        if(projectReportVO.getAssetInvest() != null){
            projectInfo.setInvestFixed(projectReportVO.getAssetInvest());
        }
        projectInfo.setCurrencyUnit(projectReportVO.getCurrencyUnit());
        projectInfo.setLandDeptId(projectReportVO.getSuggestLand());
        projectInfo.setUpdateTime(new Date());
        projectInfo.setOperatorId(projectReportVO.getUserId());
        projectInfo.setInvestTotal(projectReportVO.getInvestTotal());
        projectInfo.setMark(projectReportVO.getRemark());
        projectInfo.setInvestScale(projectReportVO.getInvestmentUnit());
        projectInfo.setOfficeArea(projectReportVO.getOfferArea());
        projectInfo.setOtherRequires(projectReportVO.getOtherRequire());
        projectInfo.setMasterUserDm(projectReportVO.getMasterUserId());
        if(projectReportVO.getMoveTime() != null) {
            projectInfo.setInvestDate(getTzyxrq(projectReportVO.getMoveTime()));
        }
        projectInfoMapper.updateById(projectInfo);

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
                    ProjectInvest projectInvest = new ProjectInvest();
                    projectInvest.setId(investParams.getInvestId());
                    projectInvest.setName(investParams.getInvestName());
                    projectInvest.setIntroduction(investParams.getInvestInfo());
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
                    //projectInvest.setCountry(investParams.getInvestNationId());
                    //projectInvest.setRegion(investParams.getInvestProvinceId());
                    projectInvest.setRelateUserName(name.length() > 1 ? name.substring(0, name.length()-1) : null);
                    projectInvest.setRelateUserJob(job.length() > 1 ? job.substring(0,job.length()-1) : null);
                    projectInvest.setRelateUserMobile(mobile.length() > 1 ? mobile.substring(0, mobile.length()-1) : null);
                    projectInvestMapper.updateById(projectInvest);

                } else if(investParams.getInvestName() != null){
                    //说明无该投资方，需新增
                    String id = UUIDUtils.genUuid("1020", 1).get(0);
                    ProjectInvest projectInvest = new ProjectInvest();
                    projectInvest.setId(id);
                    projectInvest.setName(investParams.getInvestName());
                    projectInvest.setIntroduction(investParams.getInvestInfo());
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
                    projectInvest.setRelateUserName(name.length() > 1 ? name.substring(0, name.length()-1) : null);
                    projectInvest.setRelateUserJob(job.length() > 1 ? job.substring(0,job.length()-1) : null);
                    projectInvest.setRelateUserMobile(mobile.length() > 1 ? mobile.substring(0, mobile.length()-1) : null);
                    projectInvest.setIsValid(1);
                    projectInvest.setProjectId(projectReportVO.getXmId());
                    projectInvestMapper.insert(projectInvest);
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
                        projectLabelMapper.updateInvalidByXmId(projectReportVO.getXmId(), 1);

                    }
                    if(CollectionUtils.isNotEmpty(projectReportVO.getFields())) {
                        bqlyList.addAll(projectReportVO.getFields());
                        //先更新原来的为失效
                        projectLabelMapper.updateInvalidByXmId(projectReportVO.getXmId(), 2);

                    }
                    //高层次人才
                    if(CollectionUtils.isNotEmpty(projectReportVO.getTalentsLabels())) {
                        bqlyList.addAll(projectReportVO.getTalentsLabels());
                        //先更新原来的为失效
                        projectLabelMapper.updateInvalidByXmId(projectReportVO.getXmId(), 3);

                    }
                    //人才引进
                    if(CollectionUtils.isNotEmpty(projectReportVO.getTalentImportLabels())) {
                        //先更新原来的为失效
                        projectLabelMapper.updateInvalidByXmId(projectReportVO.getXmId(), 5);
                        for(TalentParams talentParams : projectReportVO.getTalentImportLabels()) {
                            ProjectLabel projectLabel = new ProjectLabel();
                            projectLabel.setId(UUIDUtils.genUuid("1020",1).get(0));
                            projectLabel.setLabelId(talentParams.getTalentId());
                            projectLabel.setLabelCount(talentParams.getTalentCounts());
                            projectLabel.setIsValid(1);
                            projectLabel.setProjectId(projectReportVO.getXmId());
                            projectLabelMapper.insert(projectLabel);
                        }

                    }
                    if(CollectionUtils.isNotEmpty(bqlyList)) {
                        //先更新原来的为失效
                        for(Integer integer : bqlyList) {
                            ProjectLabel projectLabel = new ProjectLabel();
                            projectLabel.setId(UUIDUtils.genUuid("1020",1).get(0));
                            projectLabel.setLabelId(integer);
                            projectLabel.setIsValid(1);
                            projectLabel.setProjectId(projectReportVO.getXmId());
                            projectLabelMapper.insert(projectLabel);
                        }
                    }

                }catch (Exception e) {
                    log.error("更新数据失败, e={}", e);
                }
                log.info("异步同步信息结束");
            }
        }).start();
    }

    public Date getTzyxrq(String investmentDate){
        Date tzyxrq;
        SimpleDateFormat sdf = new SimpleDateFormat("yy-mm-dd");
        Calendar c1 = Calendar.getInstance();

        if(investmentDate.equals("三月")){
            c1.add(Calendar.MONTH,3);
        }
        else if(investmentDate.equals("半年")){
            c1.add(Calendar.MONTH,6);
        }
        else if(investmentDate.equals("一年")){
            c1.add(Calendar.YEAR,1);
        }
        else if(investmentDate.equals("三年")){
            c1.add(Calendar.YEAR,3);
        }
        else if(investmentDate.equals("五年")){
            c1.add(Calendar.YEAR,5);
        }
        // 未知
        else{
            return null;
        }
        tzyxrq = c1.getTime();
        return tzyxrq;
    }


    private void dealBusinessData(ReportDataCountVO reportDataCountVO, Date startTime, Date endTime) {
        //总上报数
        int reportTotal = 0;
        reportTotal = projectInfoMapper.countSignByDeptDm(null, null,startTime, endTime);

        //一产 二产 三产
        int oneIndustry = 0;
        oneIndustry = projectInfoMapper.countSignByDeptDm(null, 1,startTime, endTime);

        int twoIndustry = 0;
        twoIndustry = projectInfoMapper.countSignByDeptDm(null, 2,startTime, endTime);

        int threeIndustry = 0;
        threeIndustry = projectInfoMapper.countSignByDeptDm(null, 3,startTime, endTime);

        //人才
        int talent = 0;
        talent = projectInfoMapper.countSignByXmBq(null, 1002,startTime,endTime);

        //沪资5亿
        int shanghaiOverFive = 0;
        shanghaiOverFive = projectInfoMapper.countSignByXmBq(null, 1003,startTime,endTime);

        reportDataCountVO.setReportTotal(reportTotal);
        reportDataCountVO.setOneIndustry(oneIndustry);
        reportDataCountVO.setTwoIndustry(twoIndustry);
        reportDataCountVO.setThreeIndustry(threeIndustry);
        reportDataCountVO.setTalent(talent);
        reportDataCountVO.setShanghaiOverFive(shanghaiOverFive);
        reportDataCountVO.setReportTime(DateUtils.changeFormatDateToSec(new Date()));
    }
}
