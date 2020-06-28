package com.quantchi.tianji.service.search.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AssistantDao {
    List<Map<String,Object>> getClassifyInfo(String type);

    void addAssistantInfo(Map<String,Object> condition);

    List<Map<String,Object>> getAssistantInfo(Map<String,Object> condition);

    void addAssistantCommentInfo(Map<String,Object> condition);

    List<Map<String,Object>> getAssistantCommentInfo(Map<String, Object> assistantId);

    void addAssistantAgreeInfo(Map<String,Object> condition);

    int getAssistantAgreeInfoByCondition(Map<String,Object> condition);

    List<Map<String,Object>> getClassifyDetailInfo(Map<String,String> condition);
}
