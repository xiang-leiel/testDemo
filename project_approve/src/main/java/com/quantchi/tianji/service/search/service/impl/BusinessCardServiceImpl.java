package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiAttendanceListRecordRequest;
import com.dingtalk.api.response.OapiAttendanceListRecordResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.BusinessCardDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.BusinessCard;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.model.User;
import com.quantchi.tianji.service.search.model.database.BusinessCardDO;
import com.quantchi.tianji.service.search.service.BusinessCardService;
import com.quantchi.tianji.service.search.utils.AlyOcrMingPian;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import com.taobao.api.ApiException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-11 15:30
 **/
@Service
public class BusinessCardServiceImpl implements BusinessCardService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BusinessCardDao businessCardDao;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private UserDao userDao;

    @Value("${pic.url}")
    private String picUrl;

    @Value("${recommend.url}")
    private String recommendUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BusinessCardDO recognizeCardAndSave(MultipartFile file, String staffId, int leaderFlag) {
        //String fileName = file.getOriginalFilename();
//      String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";
        String name = file.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf(".")+1);
        Timestamp timestamp= Timestamp.valueOf(LocalDateTime.now());
        String fileName = String.valueOf(timestamp.getTime()) ;
        fileName = fileName+"."+ext;
        String filePath = "./pic";
        try {
            fileUpload(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        String s = AlyOcrMingPian.generateMingPian(filePath + "/" + fileName);
        /*File picFile = new File(filePath+"/"+fileName);
        picFile.delete();*/
        BusinessCard businessCard = JSONObject.parseObject(s, BusinessCard.class);
        BusinessCardDO businessCardDO = transferToDTO(businessCard);
        businessCardDO.setStaffId(staffId);
        businessCardDO.setImgUrl("/pic/"+fileName);
        SearchRequestBuilder searchRequestBuilder = elasticsearchHelper.initSearchRequest("deqing_company_web2", "company", null);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.termsQuery("name", businessCardDO.getCompany()));
        SearchResponse response = searchRequestBuilder.setQuery(queryBuilder).get();
        String companyId = "";
        if (response.getHits().getTotalHits() > 0) {
            for (SearchHit hit : response.getHits().getHits()){
                Map<String, Object> map = hit.sourceAsMap();
                businessCardDO.setCity(map.get("areaScope").toString());
                businessCardDO.setCategory(map.get("industry").toString());
                companyId = map.get("id").toString();
                businessCardDO.setCompanyId(companyId);
                break;
            }
        }
        if (businessCardDO.getCity() == null) {
            businessCardDO.setCity("全部");
        }
        if (businessCardDO.getCategory() == null) {
            businessCardDO.setCategory("全部");
        }
        businessCardDO.setIsLeader(leaderFlag);
        businessCardDao.insert(businessCardDO);
        Map<String, Object> condition = new HashMap<>();
        condition.put("companyId", companyId);
        userDao.updateUserFollowCompanyCardNum(condition);
        if (leaderFlag == 0) {
            userDao.updateUserUploadLeaderCardNum(condition);
        }
        return businessCardDO;
    }

    @Override
    public PageBean<BusinessCardDO> list(Integer page, Integer pageSize, Map<String, Object> conditionMap) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<BusinessCardDO> cardDTOs = businessCardDao.list(conditionMap);
        for (BusinessCardDO businessCardDO : cardDTOs) {
            businessCardDO.setImgUrl(picUrl+businessCardDO.getImgUrl());
        }
        PageInfo<BusinessCardDO> pageInfo = new PageInfo<>(cardDTOs);
        PageBean<BusinessCardDO> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public BusinessCardDO getOne(Integer id) {
        BusinessCardDO businessCardDO = businessCardDao.getOne(id);
        return businessCardDO;
    }

    @Override
    public PageBean<Map<String, Object>> getUserCardStatisticsList(Integer page, Integer pageSize, Map<String, Object> conditionMap) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> list = businessCardDao.getUserCardStatisticsList(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public void insertUserCardInfo(Map<String, Object> conditionMap) {
        String accessToken =  conditionMap.get("accessToken").toString();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/listRecord");
        OapiAttendanceListRecordRequest request = new OapiAttendanceListRecordRequest();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(new Date());
        request.setCheckDateFrom(dateFormat.format(new Date())+" 00:00:00");
        request.setCheckDateTo(dateFormat.format(new Date())+" 23:59:59");
        request.setUserIds(Arrays.asList(conditionMap.get("staffId").toString()));
        try {
            OapiAttendanceListRecordResponse execute = client.execute(request,accessToken);
            List<OapiAttendanceListRecordResponse.Recordresult> list = execute.getRecordresult();
            if (list.size() > 0) {
                OapiAttendanceListRecordResponse.Recordresult recordresult = list.get(list.size() -1);
                conditionMap.put("location", recordresult.getUserAddress());
            }else {
                conditionMap.put("location", "-");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        businessCardDao.insertUserCardInfo(conditionMap);
        Map<String,Object> updateMap = new HashMap<>();
        updateMap.put("cardId", conditionMap.get("cardId"));
        updateMap.put("cardNum", "1");
        userDao.updateUserFollowCompanyCardNum(updateMap);
    }

    @Override
    public void updateUserCardInfo(Map<String, Object> conditionMap) {
        businessCardDao.updateUserCardInfo(conditionMap);
        if ((int)conditionMap.get("type") == 1) {
            Map<String,Object> updateMap = new HashMap<>();
            updateMap.put("cardNum", "1");
            updateMap.put("pass", "1");
            updateMap.put("id", conditionMap.get("id").toString());
            userDao.updateUserFollowCompanyCardNum(updateMap);
        }
    }

    @Override
    public PageBean<BusinessCardDO> getLeaderCardList(Integer page, Integer pageSize, Map<String, Object> conditionMap) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<BusinessCardDO> list = businessCardDao.getLeaderCardList(conditionMap);
        for (BusinessCardDO businessCardDO : list) {
            businessCardDO.setImgUrl(picUrl+businessCardDO.getImgUrl());
        }
        PageInfo<BusinessCardDO> pageInfo = new PageInfo<>(list);
        PageBean<BusinessCardDO> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public PageBean<Map<String, Object>> getLeaderCardStatisticsList(Integer page, Integer pageSize, Map<String, Object> conditionMap) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> list = businessCardDao.getLeaderCardStatisticsList(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public PageBean<Map<String, Object>> getApplyCardStatisticsList(Integer page, Integer pageSize,Map<String, Object> conditionMap) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> list = businessCardDao.getApplyCardStatisticsList(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public PageBean<Map<String, Object>> getApplyCardList(Integer page, Integer pageSize, Map<String, Object> conditionMap) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> list = businessCardDao.getApplyCardList(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public Integer getUserFollowCompanyCardNum(String userId) {
        User user = userDao.getUserInfo(userId);
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", userId);
        condition.put("followCompanyCardNum", 0);
        userDao.updateUserFollowCompanyCardNum(condition);
        condition.put("staffId", userId);
        userDao.updateUserUploadLeaderCardNum(condition);
        return user.getFollowCompanyCardNum();
    }

    @Override
    public Integer getApplyCardNum(String userId) {
        User user = userDao.getUserInfo(userId);
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", userId);
        condition.put("applyCardNum", 0);
        userDao.updateUserFollowCompanyCardNum(condition);
        return user.getApplyCardNum();
    }

    @Override
    public Map<String, Object> getApplyLeaderCardNum(String userId) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("staffId", userId);
        int applyTotal = businessCardDao.countApplyCardNum(condition);
        condition.put("type", 1);
        int applySuccess = businessCardDao.countApplyCardNum(condition);
        Map<String, Object> result = new HashMap<>();
        result.put("applyTotal", applyTotal);
        result.put("applySuccess", applySuccess);
        return result;
    }

    @Override
    public List<Map<String, Object>> getUserCardStatisticsCountByCondition(Map<String, Object> condition) {
        List<Map<String, Object>> list = businessCardDao.getUserCardStatisticsCountByCondition(condition);
        return list;
    }

    @Override
    public int getCardNum(Map<String, Object> condition) {
        User user = userDao.getUserInfo(condition.get("staffId").toString());
        //userDao.updateUserUploadLeaderCardNum(condition);
        return user.getUploadCardNum()+user.getApplyCardNum();
    }

    @Override
    public int countLeaderApplyedNum(String staffId) {
        int num = businessCardDao.countLeaderApplyedNum(staffId);
        return num;
    }

    @Override
    public PageBean<Map<String, Object>> listPassApplyCompanyLog(String staffId, Integer page, Integer pageSize) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> list = businessCardDao.getPassApplyCompanyList(staffId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public void addAutomaticPassApply(Map<String, Object> condition) {
        businessCardDao.addAutomaticPassApply(condition);
    }

    @Override
    public void deleteAutomaticPassApply(Map<String, Object> condition) {
        businessCardDao.deleteAutomaticPassApply(condition);
    }

    @Override
    public ResultInfo getRecommendApplyList(String staffId) {
        Map<String, Object> map = new HashMap<>();
        map.put("staffId", staffId);
        String responseEntity = restTemplate.getForObject(recommendUrl+"/match/card?staffId={staffId}", String.class, map);
        Map resultMap = (Map)JSON.parse(responseEntity);
        if (resultMap.get("return_status") == null){
            Map<String, Object> list = (Map<String, Object>) resultMap.get("body");
            return ResultUtils.success(list);
        }else {
            return ResultUtils.fail(10086, resultMap.get("error_reason").toString());
        }
    }

    private BusinessCardDO transferToDTO(BusinessCard businessCard) {
        BusinessCardDO businessCardDTO = new BusinessCardDO();
        //将地址列表拼接成字符串
        businessCardDTO.setAddr(transferListToString(businessCard.getAddr()));
        businessCardDTO.setCompany(transferListToString(businessCard.getCompany()));
        businessCardDTO.setTelCell(transferListToString(businessCard.getTel_cell()));
        businessCardDTO.setTelWork(transferListToString(businessCard.getTel_work()));
        businessCardDTO.setTitle(transferListToString(businessCard.getTitle()));
        businessCardDTO.setEmail(transferListToString(businessCard.getEmail()));
        businessCardDTO.setDepartment(transferListToString(businessCard.getDepartment()));
        businessCardDTO.setName(businessCard.getName());
        return businessCardDTO;
    }

    private String transferListToString(List<String> list) {
        String result = "";
        for (String s:list){
            result = result + s + ",";
        }
        if (result.length()>0) {
            result = result.substring(0, result.length()-1);
        }
        return result;
    }

    public void fileUpload(byte[] file,String filePath,String fileName) throws IOException {
        //目标目录
        File targetfile = new File(filePath);
        if(!targetfile.exists()) {
            targetfile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+"/"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
