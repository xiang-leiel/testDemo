package com.quantchi.tianji.service.search.service;

import com.quantchi.tianji.service.search.model.PageBean;

import java.util.Map;

public interface WeeklyService {
    Map<String,Object> getWeeklyNumInfo(String staffId);

    Map<String,Object> getAllNumInfo(String staffId);

    PageBean<Map<String, Object>> getWeeklyHistoryNum(String staffId, Integer from, Integer size);

    void automaticUploadWeekly(Map<String,Object> condition);
}
