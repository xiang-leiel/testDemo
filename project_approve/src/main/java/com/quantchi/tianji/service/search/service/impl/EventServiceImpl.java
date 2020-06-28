package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.StatusLogDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.dao.WorkTaskDao;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.CompanyFollower;
import com.quantchi.tianji.service.search.model.Event;
import com.quantchi.tianji.service.search.model.User;
import com.quantchi.tianji.service.search.model.WorkTask;
import com.quantchi.tianji.service.search.service.DockingService;
import com.quantchi.tianji.service.search.service.EventService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: DeQing-InvestmentWeb
 * @description:
 * @author: mf
 * @create: 2019-07-20 15:47
 **/
@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Value("${event.index}")
    private String index;

    @Value("${event.type}")
    private String esType;

    @Value("${index}")
    private String companyIndex;

    @Value("${type}")
    private String companyType;

    @Autowired
    private RestTemplate restTemplate;

    private final static String BUCKETS_FILEDS = "area,subject,park";

    private final static String KEYWORD_FIELDS = "company_name,tags,title";

    @Autowired
    private UserDao userDao;

    @Autowired
    private WorkTaskDao workTaskDao;

    @Autowired
    private StatusLogDao statusLogDao;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private WorkServiceImpl workTaskService;

    @Autowired
    private DockingService dockingService;

    @Override
    public ResultInfo index(Integer pageSize) {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, esType, null);
        //对产业进行聚合
        searchBuilder.addAggregation(AggregationBuilders.terms("bucket_industry")
                .field("industry")
                .subAggregation(AggregationBuilders
                        .terms("bucket_domain")
                        .field("domain")
                ));
        // 统计聚合
        ElasticsearchHelper.addBucketAggrs(searchBuilder,BUCKETS_FILEDS.split(","));
        // 默认按时间降序
        ElasticsearchHelper.addSort(searchBuilder, "date:desc");
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder,0,pageSize);
        // 获取结果
        SearchResponse searchResponse = null;
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResult(searchResponse);
        Map<String, Map<String, Long>> bucketsAggretions = ElasticsearchHelper.getBucketsAggretionsFromResponse(searchResponse, BUCKETS_FILEDS.split(","));
        List<Map<String, Object>> list = ElasticsearchHelper.getAggretionsFromResponse(searchResponse, "bucket_industry", "bucket_domain");
        searchResult.put("industry", list);
        searchResult.put("aggretions", bucketsAggretions);
        return ResultUtils.success(searchResult);
    }

    @Override
    public ResultInfo search(Event event) {

        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, esType, event.getKeyword());
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //筛选行业
        if (StringUtils.isNotBlank(event.getIndustry())) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"industry", event.getIndustry().split(","));
        }
        //筛选关注的公司
        if (StringUtils.isNotBlank(event.getCompanyId())) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"company_id", event.getCompanyId().split(","));
        }
        //筛选关注的园区
        if (StringUtils.isNotBlank(event.getPark())) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"park", event.getPark().split(","));
        }
        // 筛选城市
        if (StringUtils.isNotBlank(event.getArea())){
            ElasticsearchHelper.addFilterCondition(boolQuery,"area", event.getArea().split(","));
        }
        // 筛选主题
        if (StringUtils.isNotBlank(event.getSubject())){
            ElasticsearchHelper.addFilterCondition(boolQuery,"subject", event.getSubject().split(","));
        }
        // 筛选细分产业
        if (StringUtils.isNotBlank(event.getDomain())) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"domain", event.getDomain().split(","));
        }
        // 关键词搜索(可以设置匹配逻辑 and: Operator.AND, or: Operator.OR)
        ElasticsearchHelper.keywordQueryWithOperator(boolQuery,event.getKeyword(), MatchQueryBuilder.Operator.AND,KEYWORD_FIELDS.split(","));
        // 按时间筛选
        if (event.getStartTime()!=null&&event.getEndTime()!=null) {
            ElasticsearchHelper.dateRangFilter(boolQuery, "date", event.getStartTime(), event.getEndTime(), "yyyy-MM-dd");
        }
        // 布尔查询
        searchBuilder.setQuery(boolQuery);
        // 排序
        if (StringUtils.isBlank(event.getSort())) {
            event.setSort("date:desc");
        }
        ElasticsearchHelper.addSort(searchBuilder, event.getSort());
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder, event.getPage(), event.getPageSize());
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResult(searchResponse);
        return ResultUtils.success(searchResult);
    }

    @Override
    public ResultInfo listNewsByCompanyName(String name,Integer page, Integer pageSize) {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, esType, null);
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        ElasticsearchHelper.addFilterCondition(boolQuery,"company_name", new String[]{name});
        // 布尔查询
        searchBuilder.setQuery(boolQuery);
        // 排序
        ElasticsearchHelper.addSort(searchBuilder, "date:desc");
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder, page, pageSize);
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
    public ResultInfo listAllEvents(Event event) {
        String keyword ="park,industry,companyIds";
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, esType, null);
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
        BoolQueryBuilder tmpfilterBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isBlank(event.getIndustry())&&StringUtils.isBlank(event.getPark())&&StringUtils.isBlank(event.getCompanyId())) {
            return ResultUtils.success(new HashMap<>());
        }
        if (StringUtils.isNotBlank(event.getPark())) {
            for (String term:event.getPark().split(",")) {
                tmpfilterBuilder.should(QueryBuilders.termQuery("park", term));
            }
        }
        if (StringUtils.isNotBlank(event.getCompanyId())) {
            for (String term:event.getCompanyId().split(",")) {
                tmpfilterBuilder.should(QueryBuilders.termQuery("company_id", term));
            }
        }
        if (StringUtils.isNotBlank(event.getIndustry())) {
            for (String term:event.getIndustry().split(",")) {
                tmpfilterBuilder.should(QueryBuilders.termQuery("industry", term));
            }
        }
        tmpfilterBuilder.minimumNumberShouldMatch(1);
        filterBuilder.must(tmpfilterBuilder);
        searchBuilder.setQuery(filterBuilder);
        ElasticsearchHelper.pagingSet(searchBuilder, event.getPage(), event.getPageSize());
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();
        log.info(searchBuilder.toString());
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
    public ResultInfo listNewNormalEventsFromLz(String staffId, Integer page, Integer pageSize) {
        List<String> companyIds = workTaskDao.listHandleTaskCompanyIds(staffId, "量知");
        ResultInfo resultInfo = listNewEvents(staffId, companyIds, page, pageSize);
        return resultInfo;
    }

    @Override
    public ResultInfo listNewLeadEventsFromLz(String staffId, Integer page, Integer pageSize) {
        List<String> companyIds = workTaskDao.listAssignTaskCompanyIds(staffId, "量知");
        ResultInfo resultInfo = listNewEvents(staffId, companyIds, page, pageSize);
        return resultInfo;
    }

    @Override
    public ResultInfo listNewNormalEventsFromHs(String staffId, Integer page, Integer pageSize) {
        List<String> companyIds = workTaskDao.listHandleTaskCompanyIds(staffId, "火石");
        String hsCompanyIds = "";
        for(String companyId:companyIds) {
            hsCompanyIds = hsCompanyIds + companyId;
        }
        if (StringUtils.isNotBlank(hsCompanyIds)) {
            hsCompanyIds= hsCompanyIds.substring(0, hsCompanyIds.length()-1);
        } else {
            log.info("**************当前用户没有关注的公司***************");
            return ResultUtils.success(new HashMap<>());
        }
        User userDetail = userDao.getUserInfo(staffId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(userDetail.getIndexNoticeTime());
        String endTime = formatter.format(System.currentTimeMillis());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyIds", hsCompanyIds);
        paramMap.put("current", page);
        paramMap.put("pageSize", pageSize);
        paramMap.put("beginTime", startTime);
        paramMap.put("endTime", endTime);
        log.info("向火石发送请求,companyIds:{},beginTime:{},endTime:{}", companyIds,startTime,endTime);
        Map<String, Object> medicineInfoList = dockingService.getMedicineInfoList(paramMap);
        return ResultUtils.success(medicineInfoList);
    }

    @Override
    public ResultInfo listNewLeadEventsFromHs(String staffId, Integer page, Integer pageSize) {
        List<String> companyIds = workTaskDao.listAssignTaskCompanyIds(staffId, "火石");
        String hsCompanyIds = "";
        for(String companyId:companyIds) {
            hsCompanyIds = hsCompanyIds + companyId;
        }
        if (StringUtils.isNotBlank(hsCompanyIds)) {
            hsCompanyIds= hsCompanyIds.substring(0, hsCompanyIds.length()-1);
        } else {
            log.info("**************当前用户没有关注的公司***************");
            return ResultUtils.success(new HashMap<>());
        }
        User userDetail = userDao.getUserInfo(staffId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(userDetail.getIndexNoticeTime());
        String endTime = formatter.format(System.currentTimeMillis());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyIds", hsCompanyIds);
        paramMap.put("current", page);
        paramMap.put("pageSize", pageSize);
        paramMap.put("beginTime", startTime);
        paramMap.put("endTime", endTime);
        log.info("向火石发送请求,companyIds:{},beginTime:{},endTime:{}", companyIds,startTime,endTime);
        Map<String, Object> medicineInfoList = dockingService.getMedicineInfoList(paramMap);
        return ResultUtils.success(medicineInfoList);
    }


    private ResultInfo listNewEvents(String staffId, List<String> ids, Integer page, Integer pageSize) {

       // UserDetail userDetail = userDao.getUserDetail(staffId);
        User userDetail = userDao.getUserInfo(staffId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(userDetail.getIndexNoticeTime());

        List<CompanyFollower> companyFollowers = userDao.listFollowCompanys(staffId);
        List<String> companyIds = new ArrayList<>();
        // 关注的公司的id
        if (companyFollowers!=null) {
            companyIds = companyFollowers.stream().map(CompanyFollower::getCompanyId).collect(Collectors.toList());
            companyIds.addAll(ids);
        } else {
            companyIds = ids;
        }
        if (companyIds==null) {
            return ResultUtils.success(new HashMap<>());
        }
        String[] companyIdArray = companyIds.toArray(new String[companyIds.size()]);
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, esType, null);
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        ElasticsearchHelper.addFilterCondition(boolQuery, "company_id", companyIdArray);

        //筛选时间
        ElasticsearchHelper.dateRangFilter(boolQuery, "createTime", startTime, null, "yyyy-MM-dd HH:mm:ss");

        ElasticsearchHelper.addSort(searchBuilder, "date:desc");
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder, page, pageSize);

        // 布尔查询
        searchBuilder.setQuery(boolQuery);
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();

        log.info(searchBuilder.toString() );

        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResult(searchResponse);

        return ResultUtils.success(searchResult);
    }

    private WorkTask getLzCompanyDetail(WorkTask workTask) {
        String companyId = workTask.getCompanyId();
        GetResponse response = elasticsearchHelper.get(companyIndex, companyType, companyId);
        Map<String, Object> sourceAsMap = response.getSourceAsMap();
        workTask.setCompany(sourceAsMap);
        return workTask;
    }

}
