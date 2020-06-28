package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.model.WorkTask;

import java.util.List;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-09 10:05
 **/
public interface WorkTaskService {

    /**
     * 获取某个用户的工作任务列表
     * @param userid 用户id
     * @param intervalTime 间隔时间,从现在到intervalTime之间的任务列表
     * @param isAccept
     * @param page
     * @param pageSize
     * @return 工作任务列表
     */
    Map<String,Object> listWorkTasksByUserid(String userid, Long intervalTime, Integer isAccept, Integer page, Integer pageSize);

    /**
     * 给一个或多个用户指派任务
     * @param workTask 工作任务实体
     */
    ResultInfo assign(WorkTask workTask);

    /**
     * 更新工作任务
     * @param workTask 工作任务实体
     */
    void updateWorkTask(WorkTask workTask);

    /**
     * 列出我分配的工作任务列表
     * @param userid
     * @param page
     * @param pageSize
     */
    Map<String, Object> listMyAssignTasks(String userid, Integer page, Integer pageSize);

    WorkTask getNormalNewestLogTask(String staffId);

    WorkTask getLeadNewestLogTask(String staffId);

    PageBean listAllNormalTasks(String staffId, Integer isAccept, Integer page, Integer pageSize);

    PageBean listAllLeadTasks(String staffId, Integer isAccept, Integer page, Integer pageSize);

    StatusLog getNewestLog(String companyId);

    ResultInfo listNewLeadTasks(String staffId, Integer page, Integer pageSize);

    ResultInfo listNewNormalTasks(String staffId, Integer page, Integer pageSize);

    ResultInfo getNormalWorkNum(String staffId);

    ResultInfo getLeadWorkNum(String staffId);

    ResultInfo countNormalMessage(String staffId);

    String isRecordable(String companyId);

    ResultInfo getTypeValue(String type);

    List<Map<String,Object>> listAllWorkStationByGroup();

    List<Map<String,Object>> getNewWorkStationByGroup();

    Map<String,List<Map<String,Object>>> getNewaddLogByGroup();

    Map<String,List<Map<String,Object>>> getNewAverageAddLogByGroup();

    ResultInfo countNoramlLoggedTasksToday(String staffId);

    ResultInfo countLeadLoggedTasksToday(String staffId);
}
