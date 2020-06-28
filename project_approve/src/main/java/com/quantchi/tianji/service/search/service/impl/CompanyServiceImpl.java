package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.Company;
import com.quantchi.tianji.service.search.service.CompanyService;
import com.quantchi.tianji.service.search.service.StatusLogService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author whuang
 * @date 2019/7/15
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Value("${hs.url}")
    private String hsUrl;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private StatusLogService statusLogService;

    @Value("${index}")
    private String index;

    @Value("${type}")
    private String type;

    @Value("${relation.index}")
    private String detailRelationIndex;

    private final static String KEYWORD_FIELDS = "name";

    private final static String BUCKETS_FILEDS = "city,type,financeRound";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResultInfo getCompanyDetail(String staffId, String companyId, String from) {
        if (StringUtils.isBlank(from)) {
            from = "量知";
        }
        Map<String, Object> source = new HashMap<>();
        switch (from) {
            case "量知" :
                GetResponse getResponse = elasticsearchHelper.get(index, type, companyId);
                source = getResponse.getSource();
                List<Object> dimScore = (List<Object>) source.get("dim_score");
                List<Object> avgScore = (List<Object>) source.get("avg_score");
                String[] str = new String[]{"业务能力强 ","人才创新性高 ","管理水平高 ","资金充足 ","善于合作交流 "};
                StringBuffer sb = new StringBuffer();
                if(Double.valueOf(dimScore.get(0).toString())> Double.valueOf(avgScore.get(0).toString())){
                    sb.append(str[0]);
                }
                if(Double.valueOf(dimScore.get(1).toString()) > Double.valueOf(avgScore.get(1).toString())){
                    sb.append(str[1]);
                }
                if(Double.valueOf(dimScore.get(2).toString()) > Double.valueOf(avgScore.get(2).toString())){
                    sb.append(str[2]);
                }
                if(Double.valueOf(dimScore.get(3).toString()) > Double.valueOf(avgScore.get(3).toString())){
                    sb.append(str[3]);
                }
                if(Double.valueOf(dimScore.get(4).toString()) >Double.valueOf(avgScore.get(4).toString())){
                    sb.append(str[4]);
                }
                source.put("recommendReason", sb.toString().split(" "));
                break;
            case "火石":
                List<String> hsIdList = new ArrayList<>();
                hsIdList.add(companyId);
                source = getHsCompanyDetail(hsIdList);
            default:
                break;
        }
        return ResultUtils.success(source);

    }


    @Override
    public ResultInfo search(Company company) {

        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, company.getKeyword());
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 筛选产业
        if (StringUtils.isNotBlank(company.getIndustry())) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"industry", company.getIndustry().split(","));
        }
        // 筛选发展阶段
        if (company.getFinanceRound()!=null && company.getFinanceRound().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"financeRound", company.getFinanceRound().split(","));
        }
        // 筛选城市
        if (company.getCity()!=null && company.getCity().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"city", company.getCity().split(","));
        }
        // 筛选企业属性
        if (company.getType()!=null && company.getType().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"type", company.getType().split(","));
        }
        // 关键词搜索(可以设置匹配逻辑 and: Operator.AND, or: Operator.OR)
        if (company.getDomain()!=null && company.getDomain().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"domain", company.getDomain().split(","));
        }
        // 关键词搜索(可以设置匹配逻辑 and: Operator.AND, or: Operator.OR)
        ElasticsearchHelper.keywordQueryWithOperator(boolQuery,company.getKeyword(), MatchQueryBuilder.Operator.AND,KEYWORD_FIELDS.split(","));
        // 布尔查询
        searchBuilder.setQuery(boolQuery);
        // 排序
        if(StringUtils.isBlank(company.getSort())) {
            company.setSort("migrate:desc");
        }
        ElasticsearchHelper.addSort(searchBuilder, company.getSort());
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder, company.getPage(), company.getPageSize());
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResult(searchResponse);
        return ResultUtils.success(searchResult);
    }

    @Override
    public ResultInfo listCities() {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, null);

        // 统计聚合
        ElasticsearchHelper.addBucketAggrs(searchBuilder,new String[]{"city"});

        // 获取结果
        SearchResponse searchResponse = null;

        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取聚合结果
        Map<String, Map<String, Long>> bucketsAggretions = ElasticsearchHelper.getBucketsAggretionsFromResponse(searchResponse, new String[]{"city"});

        Set<String> cities = bucketsAggretions.get("city").keySet();
        return ResultUtils.success(cities);
    }

    @Override
    public ResultInfo listCompanysNearBy(String industry,String keyword,Double latitude, Double longitude, String distance,
                                         Integer from,
                                         Integer size) {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, keyword);
        // 初始化布尔查询
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
        // 布尔查询
        searchBuilder.setQuery(filterBuilder);

        //搜索附近的公司
        GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("loc");
        geoDistanceQueryBuilder.point(latitude, longitude);
        geoDistanceQueryBuilder.distance(distance);

        if (StringUtils.isNotBlank(distance)) {
            filterBuilder.filter(geoDistanceQueryBuilder);
        }

        //按距离降序
        GeoDistanceSortBuilder distanceSortBuilder =
                new GeoDistanceSortBuilder("loc");
        distanceSortBuilder.point(latitude, longitude);
        distanceSortBuilder.unit(DistanceUnit.METERS);
        distanceSortBuilder.order(SortOrder.ASC);

        ElasticsearchHelper.pagingSet(searchBuilder, from, size);

        searchBuilder.addSort(distanceSortBuilder);

        SearchResponse searchResponse = new SearchResponse();

        log.info("查询语句:" + searchBuilder.toString());
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 封装结果

        JSONObject searchResult = ElasticsearchHelper.buildGeoSearchResult(searchResponse);

        return ResultUtils.success(searchResult);
    }

    @Override
    public ResultInfo index(Integer pageSize) {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, null);
        //对产业进行聚合
        searchBuilder.addAggregation(AggregationBuilders.terms("industry")
                .field("industry")
                .subAggregation(AggregationBuilders
                        .terms("domain")
                        .field("domain")
                ));
        // 统计聚合
        ElasticsearchHelper.addBucketAggrs(searchBuilder,BUCKETS_FILEDS.split(","));
        // 排序
        ElasticsearchHelper.addSort(searchBuilder, "migrate:desc");
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder,0,pageSize);
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResult(searchResponse);
        Map<String, Map<String, Long>> bucketsAggretions = ElasticsearchHelper.getBucketsAggretionsFromResponse(searchResponse, BUCKETS_FILEDS.split(","));
        List<Map<String, Object>> list = ElasticsearchHelper.getAggretionsFromResponse(searchResponse, "industry", "domain");
        searchResult.put("industry", list);
        searchResult.put("aggretions", bucketsAggretions);
        return ResultUtils.success(searchResult);
    }

    @Override
    public Map<String, Object> getCompanyDetailRelation(String companyId) {
        Map<String, Object> result = new HashMap<>();
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(detailRelationIndex, type, null);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termsQuery("id", companyId));
        String[] include = new String[]{"financing","alumni_association","commerce_chamber","society_association", "competitive_company", "cooperative_company"};
        searchBuilder.setFetchSource(include, null);
        searchBuilder.setQuery(queryBuilder);
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        System.out.println(detailRelationIndex + "213123"+type+"erewrere"+searchBuilder+"weweqe"+searchResponse);
        if (searchResponse != null && searchResponse.getHits().getTotalHits() > 0) {
            for (SearchHit hits :searchResponse.getHits().getHits()){
                result = hits.sourceAsMap();
            }
        }
        return result;
    }


    /**
     * 整理火石接口返回数据格式
     * @param hsIdList
     * @return
     */
    private Map<String, Object> getHsCompanyDetail(List<String> hsIdList) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyIds", hsIdList);
        HttpEntity<Map<String, Object>> formEntity = new HttpEntity<Map<String, Object>>(paramMap, headers);
        String result = "";
        Map<String, Object> respMap = new HashMap<>();
        try {
            result = restTemplate.postForObject(hsUrl, formEntity, String.class);
            log.info("调用火石接口成功,返回result:{}",result);
        } catch (Throwable e) {
            log.error("调用火石数据接口失败,参数信息:{},异常信息:{}", paramMap, e.getMessage());
        }
        respMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> respList = (List<Map<String, Object>>) respMap.get("data");
        Map<String, Object> body = respList.get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("area", body.get("address_city"));
        resultMap.put("id", body.get("id"));
        resultMap.put("logo", body.get("iconurl"));
        resultMap.put("name", body.get("name"));
        resultMap.put("briefIntroduction", body.get("describer"));
        resultMap.put("baseInfo", body.get("describer"));
        resultMap.put("financeRound", body.get(""));
        resultMap.put("location", body.get("address"));
        Map<String, Object> informationMap = new HashMap<>();
        informationMap.put("成立日期", body.get("chengliriqi"));
        resultMap.put("information", informationMap);
        resultMap.put("recommendReason", body.get("investmentRecommendReason"));
        List<Map<String, Object>> industryList = new ArrayList<>();
        Map<String, Object> industryMap = new HashMap<>();
        industryMap.put("parent", "");
        industryMap.put("child", new ArrayList<String>());
        industryList.add(industryMap);
        resultMap.put("industry", industryList);
        return resultMap;
    }
}
