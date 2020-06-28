package com.quantchi.tianji.service.search.interfaces.http;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.model.database.BusinessCardDO;
import com.quantchi.tianji.service.search.service.BusinessCardService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/card")
@Slf4j
public class CardController {

    @Autowired
    private BusinessCardService businessCardService;

    /**
     * 获取我的名片列表信息
     * @param page
     * @param pageSize
     * @param staffId
     * @param city
     * @param category
     * @param company
     * @return
     */
    @GetMapping("/getUserCardList")
    public ResultInfo<PageBean<BusinessCardDO>> getUserCardList(Integer page, Integer pageSize, String staffId,
                                                                String city, String category, String company){
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("city", city);
        conditionMap.put("category", category);
        conditionMap.put("company", company);
        conditionMap.put("staffId", staffId);
        PageBean<BusinessCardDO> pageBean = businessCardService.list(page, pageSize, conditionMap);
        return ResultUtils.success(pageBean);
    }

    @GetMapping("/getUserCardStatisticsList")
    public ResultInfo<PageBean<Map<String,Object>>> getUserCardStatisticsList(Integer page, Integer pageSize, String city,
                                                                              String staffId, String category){
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("city", city);
        conditionMap.put("category", category);
        conditionMap.put("staffId", staffId);
        PageBean<Map<String,Object>> pageBean = businessCardService.getUserCardStatisticsList(page, pageSize, conditionMap);
        return ResultUtils.success(pageBean);
    }
    @GetMapping("/insertUserCardInfo")
    public ResultInfo insertUserCardInfo(String staffId, int cardId, String accessToken){
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("staffId", staffId);
        conditionMap.put("cardId", cardId);
        Date date = new Date();
        conditionMap.put("createTime", date);
        conditionMap.put("updateTime", date);
        conditionMap.put("type", 0);
        conditionMap.put("accessToken", accessToken);
        try {
            businessCardService.insertUserCardInfo(conditionMap);
        }catch(Exception e) {
            if(e.getCause() instanceof MySQLIntegrityConstraintViolationException)
                return ResultUtils.fail(10088, "已申请过该名片");
        }
        return ResultUtils.success("success");
    }
    @GetMapping("/updateUserCardInfo")
    public ResultInfo updateUserCardInfo(int id, int type, String remarkText){
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("id", id);
        conditionMap.put("type", type);
        conditionMap.put("remarkText", remarkText);
        Date date = new Date();
        conditionMap.put("updateTime", date);
        businessCardService.updateUserCardInfo(conditionMap);
        return ResultUtils.success("success");
    }
    @GetMapping("/getLeaderCardList")
    public ResultInfo<PageBean<BusinessCardDO>> getLeaderCardList(Integer page, Integer pageSize, String staffId,
                                                                 String city, String category, String company){
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("staffId", staffId);
        conditionMap.put("city", city);
        conditionMap.put("category", category);
        conditionMap.put("company", company);
        PageBean<BusinessCardDO> pageBean = businessCardService.getLeaderCardList(page, pageSize, conditionMap);
        return ResultUtils.success(pageBean);
    }

    /**
     * 招商人员获取领导名片统计列表
     * @param page
     * @param pageSize
     * @param staffId
     * @param city
     * @param category
     * @return
     */
    @GetMapping("/getLeaderCardStatisticsList")
    public ResultInfo<PageBean<Map<String, Object>>> getLeaderCardStatisticsList(Integer page, Integer pageSize, String staffId,
                                                                 String city, String category){
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("staffId", staffId);
        conditionMap.put("city", city);
        conditionMap.put("category", category);
        PageBean<Map<String, Object>> pageBean = businessCardService.getLeaderCardStatisticsList(page, pageSize, conditionMap);
        return ResultUtils.success(pageBean);
    }

    /**
     * 获取申请名片的统计列表
     * @param staffId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getApplyCardStatisticsList")
    public ResultInfo<PageBean<Map<String, Object>>> getApplyCardStatisticsList(String staffId, Integer page, Integer pageSize) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("staffId", staffId);
        PageBean<Map<String, Object>> list = businessCardService.getApplyCardStatisticsList(page, pageSize,conditionMap);
        return ResultUtils.success(list);
    }

    @GetMapping("/getApplyCardList")
    public ResultInfo getApplyCardList(String staffId, int cardId, Integer page, Integer pageSize) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("staffId", staffId);
        conditionMap.put("cardId", cardId);
        PageBean<Map<String, Object>> list = businessCardService.getApplyCardList(page, pageSize, conditionMap);
        return ResultUtils.success(list);
    }

    @GetMapping("/getUserFollowCompanyCardNum")
    public ResultInfo getUserFollowCompanyCardNum(String userId) {
        int num = businessCardService.getUserFollowCompanyCardNum(userId);
        return ResultUtils.success(num);
    }

    @GetMapping("/getApplyCardNum")
    public ResultInfo getApplyCardNum(String userId) {
        int num = businessCardService.getApplyCardNum(userId);
        return ResultUtils.success(num);
    }

    @GetMapping("/getApplyLeaderCardNum")
    public ResultInfo getApplyLeaderCardNum(String userId) {
        Map<String, Object> num = businessCardService.getApplyLeaderCardNum(userId);
        return ResultUtils.success(num);
    }
    @GetMapping("/getUserCardStatisticsCountByCondition")
    public ResultInfo getUserCardStatisticsCountByCondition(String staffId, String type) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("staffId", staffId);
        condition.put("type", type);
        List<Map<String, Object>> list = businessCardService.getUserCardStatisticsCountByCondition(condition);
        return ResultUtils.success(list);
    }
    @GetMapping("/getCardNum")
    public ResultInfo getCardNum(String staffId) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("staffId", staffId);
        int num = businessCardService.getCardNum(condition);
        return ResultUtils.success(num);
    }
    @GetMapping("/getLeaderApplyedCount")
    public ResultInfo getLeaderApplyedCount(String staffId){
        int num = businessCardService.countLeaderApplyedNum(staffId);
        return ResultUtils.success(num);
    }
    @GetMapping("/getPassApplyCompanyLogList")
    public ResultInfo<PageBean<Map<String, Object>>> getPassApplyCompanyLogList(String staffId, Integer page, Integer pageSize){
        PageBean<Map<String, Object>> pageBean = businessCardService.listPassApplyCompanyLog(staffId, page, pageSize);
        return ResultUtils.success(pageBean);
    }
    @GetMapping("/addAutomaticPassApply")
    public ResultInfo addAutomaticPassApply(String staffId) {
        Map<String, Object> condition =  new HashMap<>();
        condition.put("staffId", staffId);
        condition.put("createTime", new Date());
        businessCardService.addAutomaticPassApply(condition);
        return ResultUtils.success("success");
    }
    @GetMapping("/deleteAutomaticPassApply")
    public ResultInfo deleteAutomaticPassApply(String staffId) {
        Map<String, Object> condition =  new HashMap<>();
        condition.put("staffId", staffId);
        businessCardService.deleteAutomaticPassApply(condition);
        return ResultUtils.success("success");
    }
    @GetMapping("/getRecommendApplyList")
    public ResultInfo getRecommendApplyList(String staffId){
        return businessCardService.getRecommendApplyList(staffId);
    }

}
