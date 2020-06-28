package com.quantchi.tianji.service.search.service;

import com.quantchi.tianji.service.search.model.PageBean;

import java.util.List;
import java.util.Map;

public interface ExportService {
    void getProjectInfo(Map<String, Object> condition, String email);

    void getPerformanceInfo(Map<String, Object> condition, String email);

    void getImportInfo(Map<String, Object> condition, String email);

    void getVisitInfo(Map<String, Object> condition, String email);

    List<Map<String,String>> getExportGroupList();

    PageBean<Map<String,Object>> getExportHistory(String userId, Integer from, Integer size);

    void addExportHistory(String staffId, int model);
}
