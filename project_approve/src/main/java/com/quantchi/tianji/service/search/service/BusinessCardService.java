package com.quantchi.tianji.service.search.service;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.model.database.BusinessCardDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BusinessCardService {

    BusinessCardDO recognizeCardAndSave(MultipartFile fileList, String staffId, int leaderFlag);

    PageBean<BusinessCardDO> list(Integer page, Integer pageSize, Map<String, Object> conditionMap);

    BusinessCardDO getOne(Integer id);

    PageBean<Map<String,Object>> getUserCardStatisticsList(Integer page, Integer pageSize, Map<String,Object> conditionMap);

    void insertUserCardInfo(Map<String,Object> conditionMap);

    void updateUserCardInfo(Map<String,Object> conditionMap);

    PageBean<BusinessCardDO> getLeaderCardList(Integer page, Integer pageSize, Map<String,Object> conditionMap);

    PageBean<Map<String, Object>> getLeaderCardStatisticsList(Integer page, Integer pageSize, Map<String,Object> conditionMap);

    PageBean<Map<String, Object>> getApplyCardStatisticsList(Integer page, Integer pageSize,Map<String,Object> conditionMap);

    PageBean<Map<String, Object>> getApplyCardList(Integer page, Integer pageSize, Map<String, Object> conditionMap);

    Integer getUserFollowCompanyCardNum(String userId);

    Integer getApplyCardNum(String userId);

    Map<String, Object> getApplyLeaderCardNum(String userId);

    List<Map<String,Object>> getUserCardStatisticsCountByCondition(Map<String,Object> condition);

    int getCardNum(Map<String,Object> condition);

    int countLeaderApplyedNum(String staffId);

    PageBean<Map<String,Object>> listPassApplyCompanyLog(String staffId, Integer page, Integer pageSize);

    void addAutomaticPassApply(Map<String,Object> condition);

    void deleteAutomaticPassApply(Map<String,Object> condition);

    ResultInfo getRecommendApplyList(String staffId);
}
