package com.quantchi.tianji.service.search.service.impl.project;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.project.ProjectInfoMapper;
import com.quantchi.tianji.service.search.dao.project.ProjectInvestMapper;
import com.quantchi.tianji.service.search.dao.project.ProjectLabelMapper;
import com.quantchi.tianji.service.search.entity.project.ProjectInfo;
import com.quantchi.tianji.service.search.entity.project.ProjectInvest;
import com.quantchi.tianji.service.search.entity.project.ProjectLabel;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.model.InvestParams;
import com.quantchi.tianji.service.search.model.ProjectSearchParams;
import com.quantchi.tianji.service.search.model.SubmitLeaderParam;
import com.quantchi.tianji.service.search.model.TalentParams;
import com.quantchi.tianji.service.search.model.vo.ProjectReportVO;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.service.project.ReportService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import com.quantchi.tianji.service.search.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        Integer status = reportService.reportOne(projectReportVO, projectReportVO.getUserId(), true);

        if(status == null) {
            return ResultUtils.fail(ErrCode.NODE_NOT_SET);
        }
        //修改行程状态

        return ResultUtils.success("success");

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
}
