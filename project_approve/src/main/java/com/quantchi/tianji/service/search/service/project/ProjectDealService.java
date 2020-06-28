package com.quantchi.tianji.service.search.service.project;

import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.enums.ProjectStatusEnum;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.vo.ProjectInfo;
import com.quantchi.tianji.service.search.model.vo.ProjectQueryParam;
import com.quantchi.tianji.service.search.model.vo.ProjectResultVO;
import com.quantchi.tianji.service.search.service.sign.impl.UserInfoService;
import com.quantchi.tianji.service.search.utils.DateUtils;
import com.quantchi.tianji.service.search.utils.ListPageUtil;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/18 1:31 PM
 */
@Slf4j
@Service
public class ProjectDealService {

    @Resource
    private ProjectRecordMapper projectRecordMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private ReceptionistInfoMapper receptionistInfoMapper;

    @Resource
    private ReceptionistEvaluateMapper receptionistEvaluateMapper;

    @Resource
    private SignRecordMapper signRecordMapper;

    @Resource
    private UserDao userDao;

    @Resource
    private WorkTaskDao workTaskDao;

    @Resource
    private QueryProjectDataService queryProjectDataService;

    public ProjectResultVO getProjectData(ProjectQueryParam projectQueryParam, UserInfo userInfo) {

        ProjectResultVO projectResultVO = new ProjectResultVO();

        //全部数量
        Integer allCount = 0;

        //拜访数量
        Integer visitCount = 0;

        //评价数量
        Integer judgeCount = 0;

        List<Integer> statusList = new ArrayList<>();

        statusList.add(ProjectStatusEnum.WAIT_VISIT.getCode());
        statusList.add(ProjectStatusEnum.WAIT_REPORT.getCode());
        statusList.add(ProjectStatusEnum.WAIT_JUDGE.getCode());;

        List<ProjectInfo> visitAll = new ArrayList<>();

        //待拜访
        List<ProjectInfo> visitData = new ArrayList<>();
        //待上报
        List<ProjectInfo> reportData = new ArrayList<>();
        //待研判
        List<ProjectInfo> judgeData = new ArrayList<>();
        //待落地
        List<ProjectInfo> stationData = new ArrayList<>();
        //未通过/叫停
        List<ProjectInfo> refuseData = new ArrayList<>();

        if("招商领导".equals(userInfo.getStaffRole())) {

            switch (projectQueryParam.getType()) {

                case 0:
                    //处理全部数据
                    visitAll = visitRecordMapper.queryProjectData(statusList, projectQueryParam.getPage(), projectQueryParam.getPageSize());

                    allCount = visitRecordMapper.countProjectData(statusList);

                    //遍历修改显示时间
                    dealTimeAllDate(visitAll);

                    break;

                case 1:
                    //处理待拜访数据
                    visitData = queryProjectDataService.ReportingProjectData(visitData, reportData, projectQueryParam, userInfo, true);

                    List<Integer> status = new ArrayList<>();

                    status.add(ProjectStatusEnum.WAIT_VISIT.getCode());
                    status.add(ProjectStatusEnum.WAIT_REPORT.getCode());

                    visitCount = visitRecordMapper.countProjectData(status);

                    break;

                case 2:
                    //处理待研判数据

                    judgeData = visitRecordMapper.fetchData(ProjectStatusEnum.WAIT_JUDGE.getCode(),null,null,
                            projectQueryParam.getPage(), projectQueryParam.getPageSize());
                    dealProjectTime(judgeData);

                    judgeCount = visitRecordMapper.countByStatffId(null, ProjectStatusEnum.WAIT_JUDGE.getCode());

                    break;

                case 3:
                    //处理待落地数据
            }

        }else if ("平台".equals(userInfo.getStaffRole())) {

            //获取待研判数据
            if(projectQueryParam.getType() == 0 || projectQueryParam.getType() == 2) {
                judgeData = visitRecordMapper.fetchData(ProjectStatusEnum.WAIT_JUDGE.getCode(), DateUtils.getWeekStart(), new Date(),
                        projectQueryParam.getPage(), projectQueryParam.getPageSize());

            }
            //获取待落地数据
            if(projectQueryParam.getType() == 0 || projectQueryParam.getType() == 3) {
                stationData = visitRecordMapper.fetchData(ProjectStatusEnum.WAIT_STATION.getCode(), DateUtils.getWeekStart(), new Date(),
                        projectQueryParam.getPage(), projectQueryParam.getPageSize());
            }
        }else {

            if(projectQueryParam.getType() == 0) {

                visitAll =  visitRecordMapper.fetchDataByGroup(statusList, userInfo.getStaffGroup());

                //待拜访数据处理
                dealVisitData(visitAll);

                //去除和该staffId无关的数据
                delJudgeData(visitAll,userInfo);

                allCount = visitAll.size();

                //遍历修改显示时间
                dealTimeAllDate(visitAll);

                //分页返回
                if(CollectionUtils.isNotEmpty(visitAll)) {
                    visitAll = ListPageUtil.getListPage(projectQueryParam.getPage(), projectQueryParam.getPageSize(), visitAll);
                }
            }

            //获取待拜访数据
            if(projectQueryParam.getType() == 1) {

                visitData = queryProjectDataService.ReportingProjectData(visitData, reportData, projectQueryParam, userInfo, false);

                //处理数据
                delVisitData(visitData, userInfo,false);

                visitCount = visitData.size();

                //分页返回
                if(CollectionUtils.isNotEmpty(visitData)) {
                    visitData = ListPageUtil.getListPage(projectQueryParam.getPage(), projectQueryParam.getPageSize(), visitData);
                }

            }

            //获取待研判数据
            if(projectQueryParam.getType() == 2) {

                //获取总的研判数据
                judgeData = visitRecordMapper.fetchByGroupNoPage(ProjectStatusEnum.WAIT_JUDGE.getCode(), userInfo.getStaffGroup());

                //去除和该staffId无关的数据
                Integer removeCount = delJudgeData(judgeData,userInfo);

                judgeCount = judgeData.size() - removeCount;

                //处理时间
                dealProjectTime(judgeData);

                //分页返回
                if(CollectionUtils.isNotEmpty(judgeData)) {
                    judgeData = ListPageUtil.getListPage(projectQueryParam.getPage(), projectQueryParam.getPageSize(), judgeData);
                }

            }
        }

        //处理所有数据，显示是发起人，还是分派人
        dealRelateData(visitAll);
        dealRelateData(visitData);
        dealRelateData(judgeData);
        dealRelateData(stationData);

        projectResultVO.setAllCount(allCount);
        projectResultVO.setAllData(visitAll);
        projectResultVO.setVisitCount(visitCount);
        projectResultVO.setVisitData(visitData);
        projectResultVO.setJudgeCount(judgeCount);
        projectResultVO.setJudgeData(judgeData);
        projectResultVO.setLandCount(0);
        projectResultVO.setLandData(stationData);
        projectResultVO.setRefuseCount(0);
        projectResultVO.setRefuseData(refuseData);

        return projectResultVO;

    }

    private void dealTimeAllDate(List<ProjectInfo> visitAll) {

        for (ProjectInfo projectInfo : visitAll) {
            projectInfo.setShowTime(projectInfo.getUpdateTime());
        }

    }

    private Integer delJudgeData(List<ProjectInfo> projectInfos, UserInfo userInfo) {

        return delVisitData(projectInfos, userInfo,true);

    }

    public Integer delVisitData(List<ProjectInfo> projectInfos, UserInfo userInfo, Boolean judgeFlag) {

        int count = 0;
        if (CollectionUtils.isNotEmpty(projectInfos)) {

            Iterator<ProjectInfo> it = projectInfos.iterator();
            while(it.hasNext()) {
                ProjectInfo projectInfo = it.next();

                //校验是否为partner
                Boolean isPartner = checkPartner(projectInfo, userInfo, judgeFlag);

                if(!userInfo.getStaffId().equals(projectInfo.getStaffId()) && !isPartner) {
                    it.remove();
                    count++;
                }
            }

        }
        return count;

    }

    private Boolean checkPartner(ProjectInfo projectInfo, UserInfo userInfo, Boolean judgeFlag) {

        //partners为空则说明是没有同行人的数据
        if(projectInfo.getPartners() == null) {
            return false;
        }

        //partners不为空则是有同行人的数据  此时当前用户若为发起人
        String[] partners = projectInfo.getPartners().split(",");

        for (String name : partners) {

            //根据名称获取staffId
            String staffId = userDao.getStaffIdByName(name);

            UserInfo partnerInfo = userInfoService.selectUserInfo(staffId);
            if(partnerInfo == null) {
                log.info("当前同行人数据未完善 partner={}", name);
                return false;
            }

            if (userInfo.getStaffId().equals(partnerInfo.getStaffId())) {

                //研判数据处理
                if(judgeFlag) {
                    return true;
                }

                return true;
            }

        }
        return false;

    }

    private void dealRelateData(List<ProjectInfo> projectInfos) {

        if (CollectionUtils.isNotEmpty(projectInfos)) {

            //查询是为分派数据还是发起数据
            for(ProjectInfo projectInfo : projectInfos) {
                UserInfo userInfo = new UserInfo();

                if(projectInfo.getVisitType() == 1) {
                    //则为分派数据  根据项目id找到被分派的人
                    VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(projectInfo.getProjectId());

                    //获取分派人
                    WorkTask workTask = workTaskDao.getTaskByCompanyIdAndHandler(visitRecord.getCompanyId(), visitRecord.getStaffId());

                    //获取分派人的信息
                    userInfo = userInfoService.selectUserInfo(workTask.getStaffId());

                } else {
                    VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(projectInfo.getProjectId());

                    //获取分派人的信息
                    userInfo = userInfoService.selectUserInfo(visitRecord.getStaffId());

                }
                projectInfo.setUserInfo(userInfo);
            }

            //处理时间显示
            dealProjectTime(projectInfos);
        }

    }

    public void sortVisitData(List<ProjectInfo> visitData) {

        if (CollectionUtils.isNotEmpty(visitData)) {
            Collections.sort(visitData, new Comparator<ProjectInfo>() {
                @Override
                public int compare(ProjectInfo o1, ProjectInfo o2) {
                    int diff = o1.getShowTime().compareTo(o2.getShowTime());
                    if(diff > 0) {
                        return 1;
                    } else if(diff < 0 ) {
                        return -1;
                    }
                    return 0;
                }
            });

        }

    }

    public void dealProjectTime(List<ProjectInfo> projectInfos) {

        //将时间转换为字符串
        for (ProjectInfo projectInfo : projectInfos) {

            dealTime(projectInfo);

        }
    }

    public void dealTime(ProjectInfo projectInfo) {

        projectInfo.setTimeDesc(DateUtils.changeFormatDate(projectInfo.getShowTime()));

        //如果在今天展示上午、下午
/*        Boolean isToday = DateUtils.belongCalendar(projectInfo.getShowTime(), DateUtils.getDayAm(new Date()), DateUtils.getDayPm(new Date()));
        if (isToday) {

            int hour = DateUtils.getHour(projectInfo.getShowTime());
            if (hour < 12) {
                projectInfo.setTimeDesc("上午 " + DateUtils.changeFormatHour(projectInfo.getShowTime()));
            }else {
                projectInfo.setTimeDesc("下午 " + DateUtils.changeFormatHour(projectInfo.getShowTime()));
            }

        }else {
            //否则显示年月日
            projectInfo.setTimeDesc(DateUtils.changeFormatDate(projectInfo.getShowTime()));

        }*/

    }


    public void dealVisitData(List<ProjectInfo> visitData) {

        if (CollectionUtils.isNotEmpty(visitData)) {

            for(ProjectInfo projectInfo : visitData) {

                dealVisitDataOne(projectInfo);
            }

        }

    }

    private Date dealVisitingShowTime(ProjectInfo projectInfo, VisitRecord visitRecord) {

        List<SignRecord> signRecords = new ArrayList<>();

        String partners = visitRecord.getVisitPartner();
        if (StringUtils.isNotEmpty(partners)) {
            String[] pars = partners.split(",");
            List<String> parsList = Arrays.asList(pars);
            List<String> arrList = new ArrayList(parsList);

            UserInfo userInfo = userInfoService.selectUserInfo(visitRecord.getStaffId());
            arrList.add(userInfo.getStaffName());
            for (String name : arrList) {

                //根据名称获取staffId
                String staffId = userDao.getStaffIdByName(name);
                SignRecord signRecord = new SignRecord();

/*                VisitRecord vi = visitRecordMapper.selectByMasterId(projectInfo.getProjectId(), staffId, 1);

                if(vi == null) {
                    signRecord = signRecordMapper.selectByVisitId(projectInfo.getProjectId(), staffId,null,null);
                } else {
                    signRecord = signRecordMapper.selectByVisitId(vi.getId(), staffId,null,null);
                }
                if (signRecord == null) {
                    continue;
                }*/
                signRecords.add(signRecord);

            }
            sortSignRecordList(signRecords);
            return CollectionUtils.isNotEmpty(signRecords) ? signRecords.get(0).getCreateTime() : null;
        }
        SignRecord signRecord = signRecordMapper.selectByVisitId(projectInfo.getProjectId(), visitRecord.getStaffId(),null,null);

        return signRecord.getCreateTime();

    }

    public void sortSignRecordList(List<SignRecord> signRecords) {
        Collections.sort(signRecords, new Comparator<SignRecord>() {
            @Override
            public int compare(SignRecord o1, SignRecord o2) {
                int diff = o1.getCreateTime().compareTo(o2.getCreateTime());
                if(diff > 0) {
                    return -1;
                } else if(diff < 0 ) {
                    return 1;
                }
                return 0;
            }
        });
    }

    private void dealVisitDataOne(ProjectInfo projectInfo) {

        //判断是否已签到
        VisitRecord visitRecord = visitRecordMapper.selectByPrimaryKey(projectInfo.getProjectId());
        projectInfo.setSignStatus(0);
        if (visitRecord.getVisitStatus() == 1) {
            projectInfo.setSignStatus(1);

            //取最新的一个人的签到时间
            Date showTime = dealVisitingShowTime(projectInfo, visitRecord);
            projectInfo.setShowTime(showTime == null ? null : showTime);
        }

        //设置当前人签到状态
        SignRecord signRecord = signRecordMapper.selectByVisitId(projectInfo.getProjectId(), projectInfo.getStaffId(), null, null);
        if(visitRecord.getVisitStatus() == 1 && signRecord != null) {
            projectInfo.setPersonSignStatus(1);
        }else if(visitRecord.getVisitStatus() == 1 && signRecord == null) {
            projectInfo.setPersonSignStatus(2);
        }

    }

    /**
     * 查看当前是否完成 签到、记录、评价
     * @param visitId
     */
    public void updateProjectStatus(String visitId) {

        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(visitId);
        dealVisitDataOne(projectInfo);

        if(projectInfo.getSignStatus() != null && projectInfo.getSignStatus() == 1 &&
                projectInfo.getRecordStatus() != null && projectInfo.getRecordStatus() == 1&&
                projectInfo.getEvaluationStatus() != null && projectInfo.getEvaluationStatus() == 1) {

            //修改visit_record状态为待上报
            //visitRecordMapper.updateByKeyId(visitId, new Date(), ProjectStatusEnum.WAIT_REPORT.getCode());

        }

    }

    public void dealLastProject(ProjectResultVO projectResultVO, UserInfo userInfo) {

        //装载用户最新进展数据
        ProjectInfo latestProject = new ProjectInfo();

        //拜访数量
        Integer visitCount = 0;

        //评价数量
        Integer judgeCount = 0;

        //处理待拜访数据
        List<Integer> status = new ArrayList<>();

        status.add(ProjectStatusEnum.WAIT_VISIT.getCode());
        status.add(ProjectStatusEnum.WAIT_REPORT.getCode());

        List<ProjectInfo> projectInfos = new ArrayList<>();

        //招商领导看所有人的最新进展数据  招商员看个人的最新进展数据
        if("招商领导".equals(userInfo.getStaffRole())) {

            ProjectInfo projectInfo = new ProjectInfo();

            projectInfo = visitRecordMapper.fetchLastDataLeader(ProjectStatusEnum.WAIT_JUDGE.getCode());

            if(projectInfo == null) {

                projectInfo = visitRecordMapper.fetchLastDataLeader(ProjectStatusEnum.WAIT_REPORT.getCode());

                if(projectInfo == null) {

                    projectInfo = visitRecordMapper.fetchLastDataLeader(ProjectStatusEnum.WAIT_VISIT.getCode());

                }
            }
            projectInfos.add(projectInfo);

            //获取各个节点的总数

            visitCount = visitRecordMapper.countProjectData(status);
            //处理待研判数据
            judgeCount = visitRecordMapper.countByStatffId(null, ProjectStatusEnum.WAIT_JUDGE.getCode());


        }else if("招商员".equals(userInfo.getStaffRole())) {
            projectInfos = visitRecordMapper.fetchLastData(ProjectStatusEnum.WAIT_JUDGE.getCode(), userInfo.getStaffGroup());

            if(CollectionUtils.isEmpty(projectInfos)) {

                projectInfos = visitRecordMapper.fetchLastData(ProjectStatusEnum.WAIT_REPORT.getCode(), userInfo.getStaffGroup());

                if(CollectionUtils.isEmpty(projectInfos)) {

                    projectInfos = visitRecordMapper.fetchLastData(ProjectStatusEnum.WAIT_VISIT.getCode(), userInfo.getStaffGroup());

                }
            }

            //处理掉和自己无关的数据
            Iterator<ProjectInfo> it = projectInfos.iterator();
            while(it.hasNext()) {
                ProjectInfo project = it.next();

                //校验是否为partner
                Boolean isPartner = checkPartnerSimple(project, userInfo);

                if(!userInfo.getStaffId().equals(project.getStaffId()) && !isPartner) {
                    it.remove();
                }
            }
            //获取待拜访数据
            List<ProjectInfo> visitDataAll = visitRecordMapper.fetchDataByGroup(status, userInfo.getStaffGroup());

            //处理数据
            delVisitData(visitDataAll, userInfo,true);

            visitCount = visitDataAll.size();

            //获取总的研判数据
            List<ProjectInfo> judgeData = visitRecordMapper.fetchByGroupNoPage(ProjectStatusEnum.WAIT_JUDGE.getCode(), userInfo.getStaffGroup());

            //去除和该staffId无关的数据
            delJudgeData(judgeData,userInfo);

            judgeCount = judgeData.size();


        }

        if(CollectionUtils.isNotEmpty(projectInfos) && StringUtils.isNotBlank(projectInfos.get(0).getProjectName())) {
            latestProject.setProjectName(projectInfos.get(0).getProjectName());
            latestProject.setShowTime(projectInfos.get(0).getUpdateTime());
            latestProject.setProjectImgUrl(projectInfos.get(0).getProjectImgUrl());
            latestProject.setStatus(projectInfos.get(0).getStatus());
        }

        projectResultVO.setLatestProject(latestProject);
        projectResultVO.setVisitCount(visitCount);
        projectResultVO.setJudgeCount(judgeCount);
    }

    private Boolean checkPartnerSimple(ProjectInfo projectInfo, UserInfo userInfo) {
        //partners为空则说明是没有同行人的数据
        if(projectInfo.getPartners() == null) {
            return false;
        }

        //partners不为空则是有同行人的数据  此时当前用户若为发起人
        String[] partners = projectInfo.getPartners().split(",");

        for (String name : partners) {

            //根据名称获取staffId
            String staffId = userDao.getStaffIdByName(name);

            UserInfo partnerInfo = userInfoService.selectUserInfo(staffId);
            if(partnerInfo == null) {
                log.info("当前同行人数据未完善 partner={}", name);
                return false;
            }

            if (userInfo.getStaffId().equals(partnerInfo.getStaffId())) {
                return true;
            }

        }
        return false;
    }

}
