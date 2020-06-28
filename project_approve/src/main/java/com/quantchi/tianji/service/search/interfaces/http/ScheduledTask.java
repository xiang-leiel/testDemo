package com.quantchi.tianji.service.search.interfaces.http;

import com.quantchi.tianji.service.search.dao.*;
import com.quantchi.tianji.service.search.dao.mapper.project.ProjectReportMapper;
import com.quantchi.tianji.service.search.entity.project.ProjectReport;
import com.quantchi.tianji.service.search.enums.GroupRelationEnum;
import com.quantchi.tianji.service.search.enums.IndustyEnum;
import com.quantchi.tianji.service.search.model.*;
import com.quantchi.tianji.service.search.model.vo.JobInfo;
import com.quantchi.tianji.service.search.model.vo.ProjectInfo;
import com.quantchi.tianji.service.search.service.UserService;
import com.quantchi.tianji.service.search.service.WeeklyService;
import com.quantchi.tianji.service.search.service.department.DepartmentService;
import com.quantchi.tianji.service.search.service.impl.UserServiceImpl;
import com.quantchi.tianji.service.search.service.project.ProjectManageService;
import com.quantchi.tianji.service.search.service.sign.impl.UserInfoService;
import com.quantchi.tianji.service.search.utils.AddressUntils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
@Service
public class ScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BusinessCardDao businessCardDao;
    @Autowired
    private WeeklyService weeklyService;

    @Resource
    private VisitRecordMapper visitRecordMapper;

    @Resource
    private ProjectRecordMapper projectRecordMapper;

    @Resource
    private ProjectReportMapper projectReportMapper;

    @Resource
    private UserInfoService userInfoService;

    @Scheduled(cron = "0 0 23 * * ?")
    public void automaticPassApplyTask(){
        Map<String, Object> condition = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_WEEK,  -1);
        logger.info("automaticPassApplyTask开始");
        condition.put("date", dateFormat.format(calendar.getTime())+" 00:00:00");
        businessCardDao.automaticPassApplyTask(condition);
    }
    //@Scheduled(cron = "0 0 10 ? * FRI")
    //@Scheduled(cron = "0 0 20 * * ? ")
    @Scheduled(cron = "0 0 18 * * ? ")
    public void automaticUploadWeekly(){
        Map<String, Object> condition = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        logger.info("automaticUploadWeekly开始");
        condition.put("date", dateFormat.format(calendar.getTime())+" 10:00:00");
        weeklyService.automaticUploadWeekly(condition);
    }

    @Resource
    private XmjbxxZbMapper xmjbxxZbMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private XmGlTzgmMapper xmGlTzgmMapper;

    @Resource
    private WfLsZbMapper wfLsZbMapper;

    @Resource
    private WfLsChbMapper wfLsChbMapper;

    @Resource
    private ProjectManageService projectManageService;

    @Resource
    private UserServiceImpl userServiceImpl;

    @Resource
    private SettingValueMapper settingValueMapper;

    @Resource
    private DmCsUserMapper dmCsUserMapper;

    /**
     * 同步待上报数据到project_report表中
     */
/*    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void syncReportingData(){

        //获取所有待上报的数据
        List<ProjectInfo> visitRecordList = visitRecordMapper.fetchListByStatus(1);

        //数据处理并插入到project_report记录中
        for(ProjectInfo projectInfo : visitRecordList) {

            XmjbxxZb xmjbxxZb = new XmjbxxZb();

            ProjectRecord projectRecord = projectRecordMapper.selectById(projectInfo.getProjectId().longValue());

            xmjbxxZb.setXmjbxxId(projectInfo.getProjectId());
            xmjbxxZb.setXgrq(new Date());
            xmjbxxZb.setCzrq(projectRecord.getUpdateTime());
            xmjbxxZb.setCylxBj(IndustyEnum.getCodeByDesc(projectRecord.getType()));
            xmjbxxZb.setXmztDm(1);
            xmjbxxZb.setXmXyd(new BigDecimal("100"));
            xmjbxxZb.setXydUnit(1);
            xmjbxxZb.setXmGdtzzc(new BigDecimal("1000"));
            xmjbxxZb.setHbdwDm(1001);
            xmjbxxZb.setXmTzfmc(projectRecord.getName());
            xmjbxxZb.setXmTzfjj(projectRecord.getFieldInfo());
            xmjbxxZb.setXmNr(projectRecord.getProjectInfo());

            //获取当前项目所在端口（哪个组对应的就是哪个端口）
            UserInfo userInfo = userInfoService.selectUserInfo(projectInfo.getStaffId());
            Integer deptDm = departmentService.getDeptByDeptName(userInfo.getStaffGroup());
            xmjbxxZb.setZszDeptDm(deptDm);

            //获取省份
            VisitRecord visitRecord1 = visitRecordMapper.selectByPrimaryKey(projectInfo.getProjectId().longValue());
 *//*           String province = AddressUntils.getAdd(visitRecord1.getVisitLongitude().toString(), visitRecord1.getVisitLatitude().toString());
            if(StringUtils.isNotBlank(province)) {
                xmjbxxZb.setXmProvince(province);
            }*//*

            //插入招商员
            String staffNames = userInfo.getStaffName();
            if(StringUtils.isNotBlank(visitRecord1.getVisitPartner())) {
                staffNames = staffNames+","+visitRecord1.getVisitPartner();
            }
            xmjbxxZb.setXmmc(visitRecord1.getVisitName());
            xmjbxxZbMapper.insertSelective(xmjbxxZb);

            //插入项目及投资规模关联表
            XmGlTzgm xmGlTzgm = new XmGlTzgm();
            xmGlTzgm.setXmjbxxId(projectInfo.getProjectId());
            xmGlTzgm.setTzgmDm(projectRecord.getInvestmentUnit());
            xmGlTzgmMapper.insertSelective(xmGlTzgm);

            //TODO 插入项目标签和行业领域  先不改

            //插入流程主表  流程子表
            WfLsZb wfLsZb1 = new WfLsZb();
            WfLsChb wfLsChb = new WfLsChb();

            //根据项目基本信息id查询工作流子表获取最新流程主表id 为前序流程id
            wfLsChb.setXmjbxxId(projectInfo.getProjectId());
            //初始状态为待办
            wfLsZb1.setLczsbj(0);
            wfLsZb1.setCzrq(new Date());
            //上报的工作流设置代码
            wfLsZb1.setWfnodeszbDm(1);
            wfLsZbMapper.insertSelective(wfLsZb1);

            //新增子表均（项目基本信息id不变，其他均可变）
            WfLsChb wfLsChb1 = new WfLsChb();
            wfLsChb1.setWfzbId(wfLsZb1.getWfzbId());
            wfLsChb1.setXmjbxxId(projectInfo.getProjectId());
            wfLsChb1.setCzrq(new Date());
            wfLsChbMapper.insertSelective(wfLsChb1);

            //修改原visit记录为已上报
            VisitRecord visitRecord = new VisitRecord();
            visitRecord.setId(projectInfo.getProjectId().longValue());
            visitRecord.setStatus(2);
            visitRecordMapper.updateByPrimaryKeySelective(visitRecord);

*//*            String type = "reportSwtich";
            SettingValue settingValue = settingValueMapper.queryByType(type);

            if(Integer.valueOf(settingValue.getValue()) == 1) {
                //获取组长id
                List<Integer> projectIds = new ArrayList<>();
                projectIds.add(xmjbxxZb.getXmjbxxId());

                UserInfo userInfo1 = userInfoService.selectUserInfo(projectInfo.getStaffId());
                JobInfo jobInfo = userServiceImpl.queryLeaderInfo(userInfo1.getStaffId());
                if(jobInfo != null) {
                    DmCsUser dmCsUser = dmCsUserMapper.selectBymobile(jobInfo.getMobile());
                    //组长审批
                    projectManageService.reportList(projectIds, dmCsUser.getUserDm());
                }
            }*//*
        }
    }*/
}
