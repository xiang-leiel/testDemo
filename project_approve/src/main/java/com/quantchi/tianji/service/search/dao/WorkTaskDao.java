package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.WorkTask;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: DeQing-InvestmentEnterprise
 * @author: mf
 * @create: 2019-07-09 10:12
 **/
public interface WorkTaskDao {

    List<WorkTask> listWorkTasksByUserid(@Param("userid") String userid, @Param("startTime") Date startTime,
                                         @Param("isAccept") Integer isAccept);

    void insert(WorkTask workTask);

    void updateWorkTask(WorkTask workTask);

    List<WorkTask> listMyAssignWorkTasks(String userid);

    //最新的有人添加过记录的工作任务
    WorkTask getNormalNewestLogTask(String staffId);

    //我分派的最新有人跟进的任务
    WorkTask getLeadNewestLogTask(String staffId);

    //获取分派给我的任务的公司id
    List<String> listHandleTaskCompanyIds(@Param("staffId") String staffId, @Param("from") String from);

    //获取我分派的任务的公司id
    List<String> listAssignTaskCompanyIds(@Param("staffId") String staffId, @Param("from") String from);

    WorkTask getTaskByCompanyId(String companyId);

    List<String> listCompanyIdsByTime(@Param("staffId") String staffId, @Param("channelTime") Date channelTime);

    List<String> listAssignCompanyIdsByTime(@Param("staffId") String staffId, @Param("channelTime") Date channelTime);

    List<WorkTask> listAllNormalTasksWithStatus(@Param("staffId") String staffId, @Param("isAccept") Integer isAccept);

    List<WorkTask> listAllLeadTasksWithStatus(@Param("staffId") String staffId, @Param("isAccept") Integer isAccept);

    List<WorkTask> listTasksByCompanyIds(@Param("companyIds") List<String> companyIds);

    List<WorkTask> listNewHandleTasksByTime(@Param("staffId") String staffId, @Param("messageTime") Date messageTime);

    Integer countNewTask(@Param("staffId") String staffId, @Param("messageTime") Date messageTime);

    Integer getLeadWorkNum(@Param("staffId") String staffId, @Param("messageTime") Date messageTime);

    String isRecordable(@Param("companyId") String companyId);

    List<String> getTypeValue(String type);

    List<Map<String,Object>> listAllWorkStationByGroup(Map<String,Object> conditon);

    List<Map<String,Object>> getNewaddLogByGroup(Map<String, Object> conditon);

    Integer countNoramlLoggedTasksToday(String staffId);

    Integer countLeadLoggedTasksToday(String staffId);

    WorkTask getTaskByTaskId(Integer id);

    WorkTask getSimpleTaskByCompanyId(String companyId);

    WorkTask getTaskByCompanyIdAndHandler(@Param("companyId")String companyId, @Param("handlerId") String handlerId);
}
