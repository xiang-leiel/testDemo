package com.quantchi.tianji.service.search.service;

import com.quantchi.tianji.service.search.model.PageBean;

import java.util.List;
import java.util.Map;

public interface AssistantService {
    List<Map<String,Object>> getClassifyInfo(String name);

    List<String> getGroupInfo();

    void addAssistantInfo(Map<String, Object> condition);

    PageBean<Map<String, Object>> getAssistantInfo(String classify, String userId, Integer from, Integer size);

    void addAssistantCommentInfo(Map<String,Object> condition);

    PageBean<Map<String,Object>> getAssistantCommentInfo(Integer assistantId, Integer from, Integer size);

    void addAssistantAgreeInfo(Map<String,Object> condition);

    int getAssistantAgreeInfoByCondition(Map<String,Object> condition);
}
