package com.quantchi.tianji.service.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.tianji.service.search.dao.WeeklyDao;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.service.WeeklyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeeklyServiceImpl implements WeeklyService {
    @Autowired
    private WeeklyDao weeklyDao;
    @Override
    public Map<String, Object> getWeeklyNumInfo(String staffId) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 9);
        Date date = getFirstDayOfWeek(calendar.getTime(), 6);
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> condition = new HashMap<>();
        condition.put("createTime", sim.format(date));
        condition.put("staffId", staffId);
        //int unfinishedJourneyNum =  weeklyDao.getUnfinishedJourneyNum(condition);
        //int finishedJounrneyNum = weeklyDao.getFinishedJourneyNum(condition);
        int noRecordedProjectNum = weeklyDao.getNoRecordedProjectNum(condition);
        int recordedProjectNum = weeklyDao.getRecordedProjectNum(condition);
        int signNum = weeklyDao.getSignNum(condition);
        Map<String, Object> result = new HashMap<>();
        //result.put("未完成行程", unfinishedJourneyNum);
        //result.put("已完成行程", finishedJounrneyNum);
        result.put("未上报项目", noRecordedProjectNum);
        result.put("已上报项目", recordedProjectNum);
        result.put("签到", signNum);
        return result;
    }

    @Override
    public Map<String, Object> getAllNumInfo(String staffId) {
        int signNum = weeklyDao.getJourneySignNum(staffId);
        int effectiveNum = weeklyDao.getEffectiveNum(staffId);
        int projectNum = weeklyDao.getProjectNum(staffId);
        Map<String, Object> result = new HashMap<>();
        result.put("走访企业数", signNum);
        result.put("有效信息数", effectiveNum);
        result.put("洽谈项目数", projectNum);
        return result;
    }

    @Override
    public PageBean<Map<String, Object>> getWeeklyHistoryNum(String staffId, Integer from, Integer size) {
        from = from == null ? 1 : from;
        size = size == null ? 10 : size;
        PageHelper.startPage(from, size);
        List<Map<String, Object>> list = weeklyDao.getWeeklyHistoryNum(staffId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public void automaticUploadWeekly(Map<String, Object> condition) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 9);
        Date date = getFirstDayOfWeek(calendar.getTime(), 6);
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> weeklyCondition = new HashMap<>();
        weeklyCondition.put("createTime", sim.format(date));
        List<Map<String, Object>> projectInfoNum = weeklyDao.getProjectInfoNum(weeklyCondition);
        weeklyDao.addWeeklyHistory(projectInfoNum);
        weeklyDao.updateProjectStatus();
    }

    public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.setFirstDayOfWeek(firstDayOfWeek);//设置一星期的第一天是哪一天
        cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);//指示一个星期中的某天
        cal.set(Calendar.HOUR_OF_DAY, 10);//指示一天中的小时。HOUR_OF_DAY 用于 24 小时制时钟。例如，在 10:04:15.250 PM 这一时刻，HOUR_OF_DAY 为 22。
        cal.set(Calendar.MINUTE, 0);//指示一小时中的分钟。例如，在 10:04:15.250 PM 这一时刻，MINUTE 为 4。
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
