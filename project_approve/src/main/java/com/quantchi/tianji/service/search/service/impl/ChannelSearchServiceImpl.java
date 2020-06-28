package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.MeetingDao;
import com.quantchi.tianji.service.search.dao.UserDao;
import com.quantchi.tianji.service.search.dao.WorkTaskDao;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.Channel;
import com.quantchi.tianji.service.search.model.UserDetail;
import com.quantchi.tianji.service.search.service.ChannelSearchService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ChannelSearchServiceImpl implements ChannelSearchService {

    @Value("${channel.index}")
    private String index;

    @Value("${channel.type}")
    private String type;

    private final static String BUCKETS_FILEDS = "city,type";

    private final static String KEYWORD_QUERY_FILEDS = "companys";

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WorkTaskDao workTaskDao;

    @Autowired
    private MeetingDao meetingDao;

    @Override
    public ResultInfo index(String sort) {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, null);
        //对产业进行聚合
        searchBuilder.addAggregation(AggregationBuilders.terms("bucket_industry")
                .field("industry")
                .subAggregation(AggregationBuilders
                        .terms("bucket_domain")
                        .field("domain")
                ));

        // 统计聚合
        ElasticsearchHelper.addBucketAggrs(searchBuilder,BUCKETS_FILEDS.split(","));
        // 排序,默认按时间升序
        if (StringUtils.isBlank(sort)) {
            sort = "channelTime:asc";
        }
        ElasticsearchHelper.addSort(searchBuilder, "priority:desc");
        ElasticsearchHelper.addSort(searchBuilder, sort);
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder,0,10);
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResultByTime(searchResponse);
        Map<String, Map<String, Long>> bucketsAggretions = ElasticsearchHelper.getBucketsAggretionsFromResponse(searchResponse, BUCKETS_FILEDS.split(","));
        List<Map<String, Object>> list = ElasticsearchHelper.getAggretionsFromResponse(searchResponse, "bucket_industry", "bucket_domain");
        searchResult.put("industry", list);
        searchResult.put("aggretions", bucketsAggretions);
        return ResultUtils.success(searchResult);
    }

    @Override
    public ResultInfo search(Channel channel) {
        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, channel.getKeyword());
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 筛选产业
        if (StringUtils.isNotBlank(channel.getIndustry())) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"industry",channel.getIndustry().split(","));
        }
        // 筛选城市
        if (channel.getCity()!=null && channel.getCity().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"city",channel.getCity().split(","));
        }
        // 筛选产业
        if (channel.getDomain()!=null && channel.getDomain().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"domain",channel.getDomain().split(","));
        }
        // 筛选类型
        if (channel.getType()!=null && channel.getType().length() > 0) {
            ElasticsearchHelper.addFilterCondition(boolQuery,"type",channel.getType().split(","));
        }
        // 时间筛选
        ElasticsearchHelper.dateRangFilter(boolQuery, "channelTime", channel.getStartTime(), channel.getEndTime(), "yyyy-MM-dd");
        // 关键词搜索

        if (StringUtils.isNotBlank(channel.getKeyword())) {
            ElasticsearchHelper.keywordQueryWithOperator(boolQuery,channel.getKeyword(), MatchQueryBuilder.Operator.AND,KEYWORD_QUERY_FILEDS.split(","));
        }

        // 布尔查询
        searchBuilder.setQuery(boolQuery);
        // 排序,默认按时间升序
        if (StringUtils.isBlank(channel.getSort())) {
            channel.setSort("channelTime:asc");
        }
        ElasticsearchHelper.addSort(searchBuilder, "priority:desc");
        // 排序
        ElasticsearchHelper.addSort(searchBuilder,channel.getSort());
        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder, channel.getPage(), channel.getPageSize());
        // 获取结果
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            log.error("es搜索出错,错误信息:" + e.getMessage());
        }
        // 封装结果
        JSONObject searchResult = ElasticsearchHelper.buildSearchResultByTime(searchResponse);
        return ResultUtils.success(searchResult);
    }

    @Override
    public ResultInfo normalChannelMessage(String staffId, Integer page, Integer pageSize) {

        Map<String,Object> resultMap = new HashMap<>();

        UserDetail userDetail = userDao.getUserDetail(staffId);
        Date channelTime = null;
        if (userDetail!=null) {
             channelTime = userDetail.getChannelTime();
        }
        List<String> allCompanyIds = new ArrayList<>();
        //获取关注的公司,在channelTime之后
        List<String> followedIds = userDao.listCompanysFollowedByTime(staffId, channelTime);
        //获取新增的公司,在channelTime之后
        List<String> handleIds = workTaskDao.listCompanyIdsByTime(staffId, channelTime);
        allCompanyIds.addAll(followedIds);
        allCompanyIds.addAll(handleIds);

        if (allCompanyIds.size() > 0) {
            resultMap.put("isNew", 1);
        } else {
            resultMap.put("isNew", 0);
        }

        String[] companyIdArray = allCompanyIds.toArray(new String[allCompanyIds.size()]);

        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, null);
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        ElasticsearchHelper.addFilterCondition(boolQuery, "company_id", companyIdArray);

        //筛选时间
        if (channelTime!=null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = formatter.format(userDetail.getIndexNoticeTime());
            ElasticsearchHelper.dateRangFilter(boolQuery, "createTime", startTime, null, "yyyy-MM-dd HH:mm:ss");
        }

        ElasticsearchHelper.addSort(searchBuilder, "channelTime:desc");
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

        resultMap.put("channel", searchResult);

        return ResultUtils.success(resultMap);
    }

    @Override
    public ResultInfo leadChannelMessage(String staffId, Integer page, Integer pageSize) {
        Map<String,Object> resultMap = new HashMap<>();

        UserDetail userDetail = userDao.getUserDetail(staffId);
        Date channelTime = null;
        if (userDetail!=null) {
            channelTime = userDetail.getChannelTime();
        }
        List<String> allCompanyIds = new ArrayList<>();
        //获取关注的公司,在channelTime之后
        List<String> followedIds = userDao.listCompanysFollowedByTime(staffId, channelTime);
        //获取新增的公司,在channelTime之后
        List<String> handleIds = workTaskDao.listAssignCompanyIdsByTime(staffId, channelTime);
        allCompanyIds.addAll(followedIds);
        allCompanyIds.addAll(handleIds);

        if (allCompanyIds.size() > 0) {
            resultMap.put("isNew", 1);
        } else {
            resultMap.put("isNew", 0);
        }

        String[] companyIdArray = allCompanyIds.toArray(new String[allCompanyIds.size()]);

        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, null);
        // 初始化布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        ElasticsearchHelper.addFilterCondition(boolQuery, "company_id", companyIdArray);

        //筛选时间
        if (channelTime!=null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = formatter.format(userDetail.getIndexNoticeTime());
            ElasticsearchHelper.dateRangFilter(boolQuery, "createTime", startTime, null, "yyyy-MM-dd HH:mm:ss");
        }

        ElasticsearchHelper.addSort(searchBuilder, "channelTime:desc");
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

        resultMap.put("channel", searchResult);

        return ResultUtils.success(resultMap);
    }

    @Override
    public ResultInfo listMeetingInfoByCondition(Map<String, String> conditionMap) {
        List<String> result = meetingDao.listMeetingInfoByCondition(conditionMap);
        return ResultUtils.success(result);
    }
}
