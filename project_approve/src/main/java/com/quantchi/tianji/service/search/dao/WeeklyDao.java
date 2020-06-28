package com.quantchi.tianji.service.search.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface WeeklyDao {
    int getUnfinishedJourneyNum(Map<String,Object> condition);

    int getFinishedJourneyNum(Map<String,Object> condition);

    int getNoRecordedProjectNum(Map<String,Object> condition);

    int getRecordedProjectNum(Map<String,Object> condition);

    int getSignNum(Map<String,Object> condition);

    int getJourneySignNum(String staffId);

    int getEffectiveNum(String staffId);

    int getProjectNum(String staffId);

    List<Map<String,Object>> getWeeklyHistoryNum(String staffId);

    List<Map<String,Object>> getProjectInfoNum(Map<String,Object> weeklyCondition);

    void addWeeklyHistory(List<Map<String,Object>> projectInfoNum);

    void updateProjectStatus();
}
