package com.quantchi.tianji.service.search.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MeetingDao {
    List<String> listMeetingInfoByCondition(Map<String, String> conditionMap);
}
