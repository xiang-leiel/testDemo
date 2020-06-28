package com.quantchi.tianji.service.search.helper;

import com.alibaba.fastjson.JSON;
import com.quantchi.tianji.service.search.model.WorkTask;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-09-10 13:59
 **/
@Component
@Slf4j
public class SearchUtil {

    @Value("${hs.url}")
    private String hsUrl;

    @Value("${hs.appUrl}")
    private String hsAppUrl;

    @Value("${index}")
    private String index;

    @Value("${type}")
    private String type;

    @Value("${pic.url}")
    private String picUrl;


    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 从es中获取公司详情
     * @param workTask
     * @param index
     * @param type
     * @return
     */
    public Map<String, Object> getCompanyDetail(WorkTask workTask, String index, String type) {
        GetResponse response = elasticsearchHelper.get(index, type, workTask.getCompanyId());
        Map<String, Object> responseMap = response.getSourceAsMap();
        return responseMap;
    }


    /**
     * 整理火石接口返回数据格式
     * @param hsCompanyId
     * @return
     */
    public Map<String, Object> getOneHsCompanyDetail(String hsCompanyId) {
        List<String> hsIdList = new ArrayList<>();
        hsIdList.add(hsCompanyId);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyIds", hsIdList);
        HttpEntity<Map<String,Object>> formEntity = new HttpEntity<Map<String,Object>>(paramMap, headers);
        String result = "";
        Map<String, Object> respMap = new HashMap<>();
        try {
            result = restTemplate.postForObject(hsUrl, formEntity, String.class);
            log.info("调用火石WEB接口成功,返回result:{}",result);
        } catch (Throwable e) {
            log.error("调用火石WEB数据接口失败,参数信息:{},异常信息:{}", paramMap, e.getMessage());
        }
        respMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> respList = (List<Map<String, Object>>) respMap.get("data");
        Map<String, Object> body = respList.get(0);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("area", body.get("address_city"));
        resultMap.put("id", body.get("company_id"));
        if (body.get("iconurl") == null || body.get("iconurl").toString().equals("")) {
            resultMap.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
        } else {
            resultMap.put("logo", body.get("iconurl"));
        }
        resultMap.put("name", body.get("name"));
        resultMap.put("briefIntroduction", body.get("describer"));
        resultMap.put("baseInfo", body.get("describer"));
        resultMap.put("financeRound", body.get(""));
        resultMap.put("location", body.get("address"));
        Map<String,Object> informationMap = new HashMap<>();
        informationMap.put("成立日期", body.get("chengliriqi"));
        resultMap.put("information", informationMap);
        resultMap.put("recommendReason", body.get("investmentRecommendReason"));
        resultMap.put("parent", "");
        resultMap.put("industry", body.get("industry"));
        return resultMap;
    }


    /**
     * 整理火石接口返回数据格式
     * @param hsCompanyIds
     * @return
     */
    public List<Map<String, Object>> listHsCompanyDetails(List<String> hsCompanyIds) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyIds", hsCompanyIds);
        HttpEntity<Map<String,Object>> formEntity = new HttpEntity<Map<String,Object>>(paramMap, headers);
        String result = "";
        Map<String, Object> respMap = new HashMap<>();
        try {
            result = restTemplate.postForObject(hsUrl, formEntity, String.class);
            log.info("调用火石WEB接口成功,返回result:{}",result);
        } catch (Throwable e) {
            log.error("调用火石WEB数据接口失败,参数信息:{},异常信息:{}", paramMap, e.getMessage());
        }
        respMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> respList = (List<Map<String, Object>>) respMap.get("data");
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Map<String,Object> body: respList) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("area", body.get("address_city"));
            resultMap.put("id", body.get("company_id"));
            if (body.get("iconurl") == null || body.get("iconurl").toString().equals("")) {
                resultMap.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
            } else {
                resultMap.put("logo", body.get("iconurl"));
            }
            resultMap.put("name", body.get("name"));
            resultMap.put("briefIntroduction", body.get("describer"));
            resultMap.put("baseInfo", body.get("describer"));
            resultMap.put("financeRound", body.get(""));
            resultMap.put("location", body.get("address"));
            Map<String,Object> informationMap = new HashMap<>();
            informationMap.put("成立日期", body.get("chengliriqi"));
            resultMap.put("information", informationMap);
            resultMap.put("recommendReason", body.get("investmentRecommendReason"));
            resultMap.put("parent", "");
            resultMap.put("industry", body.get("industry"));
            resultList.add(resultMap);
        }
        return resultList;
    }


    /**
     * 整理火石app接口返回数据格式
     * @param hsCompanyIds
     * @return
     */
    public List<Map<String, Object>> listHsCompanyDetailsByApp(List<String> hsCompanyIds) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String,Object> paramMap = new HashMap<>();
        if (hsCompanyIds==null||hsCompanyIds.size()<=0) {
            return new ArrayList<>();
        }
        String ids="";
        for (String id:hsCompanyIds) {
            ids = ids + id +",";
        }
        ids = ids.substring(0, ids.length()-1);
        paramMap.put("ids", ids);
        HttpEntity<Map<String,Object>> formEntity = new HttpEntity<Map<String,Object>>(paramMap, headers);
        String result = "";
        Map<String, Object> respMap = new HashMap<>();
        try {
            log.info("调用火石APP接口,参数ids:{}", ids);
            result = restTemplate.postForObject(hsAppUrl, formEntity, String.class);
            log.info("调用火石接口成功,返回result:{}",result);
        } catch (Throwable e) {
            log.error("调用火石数据接口失败,参数信息:{},异常信息:{}", paramMap, e.getMessage());
        }
        respMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> respList =
                (List<Map<String, Object>>) ((Map<String, Object>) respMap.get("data")).get("data");
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Map<String,Object> body: respList) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("area", body.get("address_area"));
            resultMap.put("id", body.get("id"));
            if (body.get("iconurl") == null || body.get("iconurl").toString().equals("")) {
                resultMap.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
            } else {
                resultMap.put("logo", body.get("iconurl"));
            }
            resultMap.put("name", body.get("name"));
            resultMap.put("briefIntroduction", body.get("describer"));
            resultMap.put("baseInfo", body.get("describer"));
            resultMap.put("financeRound", body.get("financeRound"));
            resultMap.put("location", body.get("address"));
            Map<String,Object> informationMap = new HashMap<>();
            informationMap.put("成立日期", body.get("chengliriqi"));
            resultMap.put("information", informationMap);
            resultMap.put("recommendReason", body.get("investmentRecommendReason"));
            resultMap.put("parent", "");
            resultMap.put("industry", body.get("industry"));
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 整理火石app接口返回数据格式
     * @param hsCompanyId
     * @return
     */
    public Map<String, Object> getOneHsCompanyDetailByApp(String hsCompanyId) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ids", hsCompanyId);
        HttpEntity<Map<String,Object>> formEntity = new HttpEntity<Map<String,Object>>(paramMap, headers);
        String result = "";
        Map<String, Object> respMap = new HashMap<>();
        try {
            result = restTemplate.postForObject(hsAppUrl, formEntity, String.class);
            log.info("调用火石APP接口成功,返回result:{}",result);
        } catch (Throwable e) {
            log.error("调用火石APP数据接口失败,参数信息:{},异常信息:{}", paramMap, e.getMessage());
        }
        respMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> respList =
                (List<Map<String, Object>>) ((Map<String, Object>) respMap.get("data")).get("data");
        Map<String, Object> body = respList.get(0);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("area", body.get("address_area"));
        resultMap.put("id", body.get("id"));
        if (body.get("iconurl") == null || body.get("iconurl").toString().equals("")) {
            resultMap.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
        } else {
            resultMap.put("logo", body.get("iconurl"));
        }
        resultMap.put("name", body.get("name"));
        resultMap.put("briefIntroduction", body.get("describer"));
        resultMap.put("baseInfo", body.get("describer"));
        resultMap.put("financeRound", body.get("financeRound"));
        resultMap.put("location", body.get("address"));
        Map<String,Object> informationMap = new HashMap<>();
        informationMap.put("成立日期", body.get("chengliriqi"));
        resultMap.put("information", informationMap);
        resultMap.put("recommendReason", body.get("investmentRecommendReason"));
        resultMap.put("parent", "");
        resultMap.put("industry", body.get("industry"));
        return resultMap;
    }





}
