package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.database.BusinessCardDO;

import java.util.List;
import java.util.Map;

public interface BusinessCardDao {

    void insert(BusinessCardDO businessCard);

    List<BusinessCardDO> list(Map<String, Object> conditionMap);

    BusinessCardDO getOne(Integer id);

    List<Map<String,Object>> getUserCardStatisticsList(Map<String,Object> conditionMap);

    void insertUserCardInfo(Map<String,Object> conditionMap);

    void updateUserCardInfo(Map<String,Object> conditionMap);

    List<BusinessCardDO> getLeaderCardList(Map<String,Object> conditionMap);

    List<Map<String,Object>> getLeaderCardStatisticsList(Map<String,Object> conditionMap);

    List<Map<String,Object>> getApplyCardStatisticsList(Map<String,Object> conditionMap);

    List<Map<String,Object>> getApplyCardList(Map<String,Object> conditionMap);

    int countApplyCardNum(Map<String, Object> condition);

    List<Map<String,Object>> getUserCardStatisticsCountByCondition(Map<String,Object> condition);

    int countLeaderApplyedNum(String staffId);

    List<Map<String, Object>> getPassApplyCompanyList(String staffId);

    void automaticPassApplyTask(Map<String, Object> condition);

    void addAutomaticPassApply(Map<String,Object> condition);

    void deleteAutomaticPassApply(Map<String,Object> condition);
}
