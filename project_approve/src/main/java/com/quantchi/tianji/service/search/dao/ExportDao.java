package com.quantchi.tianji.service.search.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExportDao {
    Map<String,Object> getProjectHeadInfo(Map<String,Object> condition);

    List<Map<String,Object>> getProjectList(Map<String,Object> condition);

    List<Map<String,Object>> getPerformanceList(Map<String,Object> condition);

    List<Map<String, Object>> getSignTudeList(Map<String,Object> condition);

    List<String> getForeignList(Map<String,Object> condition);

    List<Map<String,Object>> getImportList(Map<String, Object> condition);

    List<Map<String,Object>> getStudentList(Map<String,Object> condition);

    Map<String,Object> getTotalImportList(Map<String,Object> condition);

    List<Map<String,String>> getExportGroupList();

    List<Map<String,Object>> getExportHistory(String userId);

    void addExportHistory(Map<String,Object> condition);

    List<Map<String,Object>> getVisitInfo(Map<String,Object> condition);
}
