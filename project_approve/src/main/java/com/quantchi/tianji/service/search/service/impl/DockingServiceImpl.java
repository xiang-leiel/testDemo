package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quantchi.tianji.service.search.dao.StatusLogDao;
import com.quantchi.tianji.service.search.model.StatusLog;
import com.quantchi.tianji.service.search.service.DockingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Service
@Slf4j
public class DockingServiceImpl implements DockingService {
    @Autowired
    private StatusLogDao statusLogDao;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${pic.url}")
    private String picUrl;

    @Value("${dock.url}")
    private String dockUrl;
    @Override
    public Map<String, Object> getMedicineInfoList(Map<String, Object> paramMap) {
        Map<String,Object> result = new HashMap<>();
        if (paramMap.get("area") != null) {
            String cityCode = readJsonFile("/static/json/city_code");
            //String cityCode = readJsonFile("E:\\work\\AttractInvestmentEnterpriseSecond\\DeQing-InvestmentApp\\src\\main\\resources\\static\\json\\city_code");
            Map<String, Object> cityMap = (Map)JSON.parse(cityCode);
            paramMap.put("area", cityMap.get(paramMap.get("area")));
        }
        String url = dockUrl+"/third/lz/newsList";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestBody = new HttpEntity(paramMap, headers);
        ResponseEntity<Map> tokenMap = restTemplate.postForEntity(url,requestBody, Map.class);
        log.info("火石返回数据:{}", tokenMap);
        List<Map<String, Object>> hitList = new LinkedList<>();
        int total = 0;
        if (tokenMap.getStatusCodeValue() == 200) {
            if ( tokenMap.getBody().get("data") != null) {
                List<Map<String, Object>> body = (List<Map<String, Object>>) ((Map<String, Object>) tokenMap.getBody().get("data")).get("data");
                for (Map<String, Object> map : body) {
                    Map<String, Object> sourceMap = new HashMap<>();
                    Map<String, Object> bodyMap = new HashMap<>();
                    sourceMap.put("date",map.get("publish_time"));
                    sourceMap.put("subject", map.get("subject"));
                    List<Map<String, Object>> companyList = (List<Map<String, Object>>) map.get("entity_information");
                    if (companyList != null && companyList.size() > 0) {
                        sourceMap.put("company_name", companyList.get(0).get("stand_name"));
                        sourceMap.put("company_id", companyList.get(0).get("stand_id"));
                    }else {
                        sourceMap.put("company_name", null);
                        sourceMap.put("company_id", null);
                    }
                    sourceMap.put("domain", map.get("domain"));
                    if (map.get("cover_image_url") != null) {
                        sourceMap.put("logo", map.get("cover_image_url"));
                    }else {
                        sourceMap.put("logo", picUrl+"/pic/default/shengwuyiyao.png");
                    }
                    sourceMap.put("industry", map.get("industry"));
                    sourceMap.put("source", map.get("source"));
                    sourceMap.put("title", map.get("title"));
                    sourceMap.put("content", map.get("summary"));
                    bodyMap.put("_source", sourceMap);
                    bodyMap.put("_id", map.get("id"));
                    hitList.add(bodyMap);
                }
                total = (int) ((Map<String, Object>) tokenMap.getBody().get("data")).get("total");
            }
        }
        result.put("hits", hitList);
        result.put("total", total);
        return result;
    }

    @Override
    public Map<String, Object> getMedicineCompanyInfoList(Map<String, Object> condition) {
        if (condition.get("city") != null) {
            String cityCode = readJsonFile("/static/json/city_code");
            //String cityCode = readJsonFile("E:\\work\\AttractInvestmentEnterpriseSecond\\DeQing-InvestmentApp\\src\\main\\resources\\static\\json\\city_code");
            Map<String, Object> cityMap = (Map)JSON.parse(cityCode);
            condition.put("cityCodes", cityMap.get(condition.get("city")));
        }
        if (condition.get("page") != null) {
            condition.put("current", condition.get("page"));
            condition.remove("page");
        }
        Map<String,Object> result = new HashMap<>();
        String url = dockUrl+"/third/lz/companyList";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestBody = new HttpEntity(condition, headers);
        ResponseEntity<Map> tokenMap = restTemplate.postForEntity(url,requestBody, Map.class);
        List<Map<String, Object>> hitList = new LinkedList<>();
        int total = 0;
        if (tokenMap.getStatusCodeValue() == 200) {
            if (tokenMap.getBody().get("data") != null) {
                List<Map<String, Object>> body = (List<Map<String, Object>>) ((Map<String, Object>) tokenMap.getBody().get("data")).get("data");
                for (Map<String, Object> map : body) {
                    Map<String, Object> sourceMap = new HashMap<>();
                    Map<String, Object> bodyMap = new HashMap<>();
                    sourceMap.put("name",map.get("name"));
                    sourceMap.put("financeRound", map.get("financeRound"));
                    sourceMap.put("financeMoney", map.get("financeMoney"));
                    StatusLog statusLog = statusLogDao.getNewestLogByCompanyId(map.get("id").toString());
                    if (statusLog != null && statusLog.getStatus() != null) {
                        sourceMap.put("status", statusLog.getStatus());
                    }else {
                        sourceMap.put("status", "");
                    }
                    sourceMap.put("industry", map.get("industry"));
                    sourceMap.put("domain", map.get("domain"));
                    sourceMap.put("address", map.get("address"));
                    Map<String, Object> info = new HashMap<>();
                    info.put("成立日期", map.get("chengliriqi"));
                    info.put("注册资本", map.get("zhuceziben"));
                    info.put("公司规模", map.get("scale"));
                    sourceMap.put("information", info);
                    String[] loc = new String[2];
                    if (map.get("lng") != null & map.get("lat") != null) {
                        loc=new String[]{map.get("lng").toString(), map.get("lat").toString() };
                    }
                    sourceMap.put("loc", loc);
                    if (map.get("iconurl") == null) {
                        sourceMap.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
                    }else {
                        sourceMap.put("logo", map.get("iconurl"));
                    }
                    bodyMap.put("_source", sourceMap);
                    bodyMap.put("_id", map.get("id"));
                    hitList.add(bodyMap);
                }
                total = (int) ((Map<String, Object>) tokenMap.getBody().get("data")).get("total");
            }
        }
        result.put("hits", hitList);
        result.put("total", total);
        return result;
    }

    @Override
    public Map<String, Object> getMedicineDetailInfo(String id) {
        Map<String,Object> result = new HashMap<>();
        String url = dockUrl+"/third/lz/newsDetail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", id);
        HttpEntity requestBody = new HttpEntity(paramMap, headers);
        ResponseEntity<Map> tokenMap = restTemplate.postForEntity(url,requestBody, Map.class);
        if (tokenMap.getStatusCodeValue() == 200) {
            if ( tokenMap.getBody().get("data") != null) {
                Map<String, Object> body = (Map<String, Object>) tokenMap.getBody().get("data");
                result.put("title", body.get("title"));
                result.put("publishTime", body.get("publish_time"));
                result.put("content", body.get("content"));
                result.put("logo", body.get("cover_image_url"));
                result.put("domain", body.get("domain"));
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getMedicineCompanyDetail(String id) {
        Map<String,Object> result = new HashMap<>();
        String url = dockUrl+"/third/lz/companyDetail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", id);
        HttpEntity requestBody = new HttpEntity(paramMap, headers);
        ResponseEntity<Map> tokenMap = restTemplate.postForEntity(url,requestBody, Map.class);
        if (tokenMap.getStatusCodeValue() == 200) {
            if ( tokenMap.getBody().get("data") != null) {
                Map<String, Object> body = (Map<String, Object>) tokenMap.getBody().get("data");
                result.put("name", body.get("name"));
                Map<String, Object> industryMap = new HashMap<>();
                industryMap.put("parent", body.get("industry"));
                industryMap.put("child", new String[]{});
                List<Map<String, Object>> list = new ArrayList<>();
                result.put("industry", list.add(industryMap));
                result.put("area", body.get("address_area"));
                result.put("financeRound", body.get("financeRound"));
                result.put("financeMoney", body.get("financeMoney"));
                StatusLog statusLog = statusLogDao.getNewestLogByCompanyId(id);
                if (statusLog != null && statusLog.getStatus() != null) {
                    result.put("status", statusLog.getStatus());
                }else {
                    result.put("status", "");
                }
                result.put("tags", body.get("tags"));
                Map<String, Object> info = new HashMap<>();
                info.put("注册时间",body.get("zhuceshijian"));
                info.put("企业法人",body.get("fadingdaibiao"));
                info.put("注册资金",body.get("zhuceziben"));
                info.put("成立日期",body.get("chengliriqi"));
                info.put("企业规模",body.get("scale"));
                info.put("年营业额",body.get("annualTurnover"));
                info.put("发票抬头",body.get("unicode"));
                result.put("information", info);
                result.put("legalRepresentative", body.get("fadingdaibiao"));
                result.put("tel", body.get("phone"));
                result.put("email", body.get("email"));
                result.put("financeMoney", body.get("financeMoney"));
                result.put("location", body.get("address"));
                result.put("baseInfo", body.get("describer"));
                if (body.get("iconurl") == null) {
                    result.put("logo", picUrl+"/pic/default/webwxgetmsgimg.jpg");
                }else {
                    result.put("logo", body.get("iconurl"));
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getNearCompanyList(Map<String, Object> paramMap) {
        Map<String,Object> result = new HashMap<>();
        String url = dockUrl+"/third/lz/geoDistance";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestBody = new HttpEntity(paramMap, headers);
        ResponseEntity<Map> tokenMap = restTemplate.postForEntity(url,requestBody, Map.class);
        List<Map<String, Object>> hitList = new LinkedList<>();
        int total = 0;
        if (tokenMap.getStatusCodeValue() == 200) {
            if (tokenMap.getBody().get("data") != null){
                List<Map<String, Object>> body = (List<Map<String, Object>>) ((Map<String, Object>) tokenMap.getBody().get("data")).get("data");
                for (Map<String, Object> map : body) {
                    Map<String, Object> sourceMap = new HashMap<>();
                    Map<String, Object> bodyMap = new HashMap<>();
                    sourceMap.put("name",map.get("name"));
                    sourceMap.put("financeRound", map.get("financeRound"));
                    sourceMap.put("financeMoney", map.get("financeMoney"));
                    StatusLog statusLog = statusLogDao.getNewestLogByCompanyId(map.get("id").toString());
                    if (statusLog != null && statusLog.getStatus() != null) {
                        sourceMap.put("status", statusLog.getStatus());
                    }else {
                        sourceMap.put("status", "");
                    }
                    sourceMap.put("industry", map.get("industry"));
                    sourceMap.put("domain", map.get("domain"));
                    Map<String, Object> info = new HashMap<>();
                    info.put("企业地址", map.get("address"));
                    info.put("成立日期", map.get("chengliriqi"));
                    info.put("注册资本", map.get("zhuceziben"));
                    info.put("公司规模", map.get("scale"));
                    sourceMap.put("information", info);
                    String[] loc = new String[2];
                    if (map.get("lon") != null & map.get("lat") != null) {
                        loc=new String[]{map.get("lon").toString(), map.get("lat").toString() };
                    }
                    sourceMap.put("loc", loc);
                    sourceMap.put("distance", Double.parseDouble(map.get("distance").toString()) * 1000);
                    bodyMap.put("_source", sourceMap);
                    bodyMap.put("distance", Double.parseDouble(map.get("distance").toString()) * 1000);
                    bodyMap.put("_id", map.get("id"));
                    hitList.add(bodyMap);
                }
                total = (int) ((Map<String, Object>) tokenMap.getBody().get("data")).get("total");

            }

        }
        result.put("hits", hitList);
        result.put("total", total);
        return result;
    }

    @Override
    public Map<String, Object> getMeetingList(Map<String, Object> condition) {
        if (condition.get("city") != null) {
            String cityCode = readJsonFile("/static/json/city_code");
            //String cityCode = readJsonFile("E:\\work\\AttractInvestmentEnterpriseSecond\\DeQing-InvestmentApp\\src\\main\\resources\\static\\json\\city_code");
            Map<String, Object> cityMap = (Map)JSON.parse(cityCode);
            condition.put("city_code", cityMap.get(condition.get("city")));
        }
        if (condition.get("page") != null){
            condition.put("current", condition.get("page"));
            condition.remove("page");
        }
        if (condition.get("channelTime") != null) {
            condition.put("start_date", condition.get("channelTime"));
            condition.remove("channelTime");
        }
        Map<String,Object> result = new HashMap<>();
        String url = dockUrl+"/third/lz/activityList";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestBody = new HttpEntity(condition, headers);
        ResponseEntity<Map> tokenMap = restTemplate.postForEntity(url,requestBody, Map.class);
        List<Map<String, Object>> hitList = new LinkedList<>();
        int total = 0;
        if (tokenMap.getStatusCodeValue() == 200) {
            if ((Map<String, Object>) tokenMap.getBody().get("data") != null){
                List<Map<String, Object>> body = (List<Map<String, Object>>) ((Map<String, Object>) tokenMap.getBody().get("data")).get("data");
                for (Map<String, Object> map : body) {
                    Map<String, Object> sourceMap = new HashMap<>();
                    Map<String, Object> bodyMap = new HashMap<>();
                    sourceMap.put("city",map.get("city"));
                    if (map.get("others") != null){
                        String others = map.get("others").toString();
                        others.replace(" ", ",");
                        sourceMap.put("companys", others);
                    }else {
                        sourceMap.put("companys", "");
                    }

                    sourceMap.put("channelTime", map.get("financeMoney"));
                    sourceMap.put("url", map.get("url"));
                    sourceMap.put("industry", map.get("industry"));
                    sourceMap.put("domain", map.get("domain"));
                    sourceMap.put("type", map.get("type"));
                    sourceMap.put("content", map.get("title"));
                    bodyMap.put("_source", sourceMap);
                    bodyMap.put("_id", map.get("id"));
                    hitList.add(bodyMap);
                }
                total = (int) ((Map<String, Object>) tokenMap.getBody().get("data")).get("total");
            }
        }
        result.put("hits", hitList);
        result.put("total", total);
        return result;
    }

    private static String readJsonFile(String fileName) {
        /*String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
        InputStream in = DockingServiceImpl.class.getClassLoader().getResourceAsStream(fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String  input = new String(bytes);
        JSONObject jsonObject = JSON.parseObject(input);
        return jsonObject.toString();
    }
}
