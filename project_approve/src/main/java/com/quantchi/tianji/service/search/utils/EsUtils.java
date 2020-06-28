package com.quantchi.tianji.service.search.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortOrder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author whuang
 * @date 2019/4/17
 */
public final class EsUtils {

    private EsUtils() {
    }

    public static final String TERMS_BUCKET_PREFIX = "bucket.";

    public static final String METRIC_PREFIX = "metric.";

    /**
     * 添加过滤条件
     *
     * @param filterBuilder 查询过滤对象
     * @param field         字段名
     * @param value         筛选值
     * @return 查询过滤对象
     */
    public static BoolQueryBuilder addFilterCondition(BoolQueryBuilder filterBuilder, String field, Object... values) {

        if (field == null || values == null || values.length == 0) {
            return filterBuilder;
        }
        BoolQueryBuilder temp = QueryBuilders.boolQuery();
        boolean flag = false;
        for (Object v : values) {
            if (v == null) {
                break;
            }
            temp.filter(QueryBuilders.matchQuery(field, v));
            flag =true;
        }
        if (flag) {
            filterBuilder.filter(temp);
        }
        return filterBuilder;
    }

    /**
     * 日期过滤
     *
     * @param filterBuilder 查询过滤对象
     * @param fieldName     字段名
     * @param begin         开始日期
     * @param end           结束日期
     * @param format        日期格式
     * @return 查询过滤对象
     */
    public static BoolQueryBuilder dateRangFilter(BoolQueryBuilder filterBuilder, String fieldName, String begin, String end, String format) {
        begin = begin == null ? "" : begin;
        end = end == null ? "" : end;

        if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            filterBuilder.filter(QueryBuilders.rangeQuery(fieldName).from(begin).to(end).format(format));
        } else if (StringUtils.isNotBlank(begin)) {
            filterBuilder.filter(QueryBuilders.rangeQuery(fieldName).from(begin).format(format));
        } else if (StringUtils.isNotBlank(end)) {
            filterBuilder.filter(QueryBuilders.rangeQuery(fieldName).to(end).format(format));
        }
        return filterBuilder;
    }

    /**
     * 设置分页
     * @param searchBuilder
     * @param from 起始页数
     * @param size 每页显示数量
     */
    public static void pagingSet(SearchRequestBuilder searchBuilder, Integer from, Integer size) {
        from = from == null ? 0 : from - 1;
        size = size == null ? 5 : size;
        searchBuilder.setFrom(from * size);
        searchBuilder.setSize(size);
    }

    /**
     * 设置排序
     * @param searchBuilder
     * @param sort 排序字段:排序方式 eg: publishDate:desc
     */
    public static void addSort(SearchRequestBuilder searchBuilder,String sort) {
        //设置排序
        if (StringUtils.isNotBlank(sort)) {
            String[] sortSplit = sort.split(":");
            if (sortSplit != null && sortSplit.length == 2) {
                String field = sortSplit[0];
                String order = sortSplit[1].toLowerCase().trim();
                if(Objects.equals(order,"desc")) {
                    searchBuilder.addSort(field, SortOrder.DESC);
                } else if (Objects.equals(order,"asc")){
                    searchBuilder.addSort(field,SortOrder.ASC);
                }
            }
        }
    }

    /**
     * 添加聚合条件
     * !!!默认在字段名前面加上:bucket. !!!
     */
    public static void addBucketAggrs(SearchRequestBuilder searchBuilder, String... bucketsFields) {
        for (String field : bucketsFields) {
            searchBuilder.addAggregation(AggregationBuilders.terms(TERMS_BUCKET_PREFIX + field)
                    .field(field).size(0));
        }
    }

    /**
     * 获取bucket聚合结果
     * @param searchResponse
     * @param fields
     * @return
     */
    public static Map<String, Object> getBucketsAggretionsFromResponse(SearchResponse searchResponse, String...fields) {
        Map<String, Object> aggregationResult = new HashMap<>();
        Aggregations aggregations = searchResponse.getAggregations();
        for (String field : fields) {
            Terms terms = aggregations.get(TERMS_BUCKET_PREFIX + field);
            //按照文档数量降序添加
            Map<Object, Long> aggregation = new LinkedHashMap<>();
            for (Terms.Bucket bucket : terms.getBuckets()) {
                Object key = bucket.getKey();
                long docCount = bucket.getDocCount();
                aggregation.put(key.toString(), docCount);
            }
            aggregationResult.put(field, aggregation);
        }
        return aggregationResult;
    }

    /**
     * !!!默认在字段名前面加上:metric.!!!
     * 方便从聚合结果中获取字段
     * 获取metric统计
     * @param searchResponse
     * @param fields
     * @return
     */
    public static Map<String,Object> getMetricAggregationsFromResponseWithType(SearchResponse searchResponse,String type,String...fields) {
        Map<String, Object> aggregationResult = new HashMap<>();
        Aggregations aggregations = searchResponse.getAggregations();
        for (String field : fields) {
            NumericMetricsAggregation.SingleValue singleValue = aggregations.get(METRIC_PREFIX+field+"."+type);
            aggregationResult.put(field,singleValue.value());
        }
        return aggregationResult;
    }


    /**
     * !!!默认在字段名前面加上:metric.!!!
     * 方便从聚合结果中获取字段
     * 添加metric聚合
     * @param searchBuilder
     * @param type 聚合类型: sum/min/avg/max
     * @param fields 聚合字段
     */
    public static void addMetricAggrs(SearchRequestBuilder searchBuilder, String type, String...fields)  {
        for (String field : fields) {
            // 根据不同的type选择不同的metric
            if("min".equals(type)){
                searchBuilder.addAggregation(AggregationBuilders.min(METRIC_PREFIX+field+"."+type).field(field));
            }else if("max".equals(type)){
                searchBuilder.addAggregation(AggregationBuilders.max(METRIC_PREFIX+field+"."+type).field(field));

            }else if("sum".equals(type)){
                searchBuilder.addAggregation(AggregationBuilders.sum(METRIC_PREFIX+field+"."+type).field(field));

            }else if("avg".equals(type)){
                searchBuilder.addAggregation(AggregationBuilders.avg(METRIC_PREFIX+field+"."+type).field(field));

            }
        }
    }

    /**
     * 关键字匹配,默认从keywords字段里面取
     * @param filterBuilder 搜索对象
     * @param keyword       关键词
     * @return 筛选对象
     */
    public static BoolQueryBuilder keywordQueryWithOperator(BoolQueryBuilder filterBuilder, String keyword,MatchQueryBuilder.Operator operator, String...keywordFields) {
        if(StringUtils.isNotBlank(keyword)) {
            for (String field : keywordFields) {
                filterBuilder.should(QueryBuilders.matchQuery(field, keyword).operator(operator));
            }
            filterBuilder.minimumNumberShouldMatch(1);
        }
        return filterBuilder;
    }
}