package com.quantchi.tianji.service.search.helper;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.global.GlobalBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * es的帮助类，整体参考<br />
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html<br />
 * 本类只做一些低级封装，高级搜索请自己使用getClient()获取TransportClient进行编写，如aggregations等
 * @author huqitao
 *
 */
@Component
@Slf4j
public class ElasticsearchHelper implements InitializingBean {

	@Value("${es.cluster.hosts}")
	private String hosts;
	@Value("${es.cluster.name}")
	private String clusterName;
	private Settings settings;
	private TransportClient client;
	private static final float MIN_SCORE = 0.001f;

	/**
	 * bucket聚合统计字段名默认前缀
	 * why:不管是bucket统计还是metric统计,字段名都不会改变,避免混淆,使用前缀区分开来
	 */
	public static final String TERMS_BUCKET_PREFIX = "bucket.";

	/**
	 * 全局聚合统计命名
	 */
	public static final String GLOBAL_AGGREGATION = "global";

	/**
	 * 全局bucket聚合统计字段命名默认前缀
	 */
	public static final String GLOBAL_TERMS_BUCKET_PREFIX = "global.buckets.";
	/**
	 * metric统计字段名默认前缀
	 * why:与bucket统计同理
	 */
	public static final String METRIC_PREFIX = "metric.";

	@Override
	public void afterPropertiesSet() throws Exception {
		String[] hostArray = hosts.split(",");
		settings = Settings.settingsBuilder()
				.put("cluster.name", clusterName)
				.put("client.transport.sniff", false)
				.build();
		client = TransportClient.builder().settings(settings).build();
		for (String host : hostArray) {
			String ip = host.split(":")[0];
			Integer port = Integer.valueOf(host.split(":")[1]);
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
		}
	}



	/**
	 * 获取一条数据，默认开个新的线程去处理，后面将不在加入setOperationThreaded
	 * @param index 索引
	 * @param type 类型
	 * @param id id
	 * @return 响应
	 */
	public GetResponse get(String index, String type, String id) {
		return client.prepareGet(index, type, id).setOperationThreaded(true).get();
	}
	/**
	 * 新增一条数据不指定id
	 * @param index 索引
	 * @param type 类型
	 * @param json json数据
	 * @return 响应
	 */
	public IndexResponse index(String index, String type, String json) {
		return client.prepareIndex(index, type).setSource(json).get();
	}

	/**
	 * 在制定id重新索引一条数据
	 * @param index 索引
	 * @param type 类型
	 * @param id id
	 * @param json json数据
	 * @return 响应
	 */
	public IndexResponse index(String index, String type, String id, String json) {
		return client.prepareIndex(index, type, id).setSource(json).get();
	}

	/**
	 * 新增一条数据
	 * @param index 索引
	 * @param type 类型
	 * @param id id
	 * @param obj 数据
	 * @return 响应
	 */
	public IndexResponse index(String index, String type, String id, Object... obj) {
		return client.prepareIndex(index, type, id).setSource(obj).get();
	}

	/**
	 * 删除一条数据
	 * @param index 索引
	 * @param type 类型
	 * @param id id
	 * @return 响应
	 */
	public DeleteResponse delete(String index, String type, String id) {
		return client.prepareDelete(index, type, id).get();
	}

	/**
	 * 根据条件删除
	 * @param index 索引
	 * @param query QueryBuilder查询对象
	 * @return 响应
	 */
	public BulkIndexByScrollResponse deleteByQuery(String index, QueryBuilder query) {
		return DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(query).source(index).get();
	}

	/**
	 * 根据条件删除，可以自定义onResponse, onFailure等操作
	 * 详细可以参考：https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-delete-by-query.html
	 * @param index 索引
	 * @param query QueryBuilder查询对象
	 * @param listener
	 */
	public void deleteByQuery(String index, QueryBuilder query, ActionListener<BulkIndexByScrollResponse> listener) {
		DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(query).source(index).execute(listener);
	}
	/**
	 * 更新一个数据
	 * @param index 索引
	 * @param type 类型
	 * @param id id
	 * @param obj 数据
	 * @return 响应
	 */
	public UpdateResponse update(String index, String type, String id, Object obj) {
		return client.prepareUpdate(index, type, id).setDoc(obj).get();
	}

	/**
	 * 批量获取文档
	 * @param index 索引
	 * @param type 类型
	 * @param ids id数组
	 * @return 响应
	 */
	public MultiGetResponse multiGet(String index, String type, String... ids) {
		return client.prepareMultiGet().add(index, type, ids).get();
	}


	/**
	 * 组合查询
	 * @param builders 搜索对象数组
	 * @return 响应结果
	 */
	public MultiSearchResponse multiSearch(SearchRequestBuilder... builders) {
		MultiSearchRequestBuilder msrb = client.prepareMultiSearch();
		for (SearchRequestBuilder srb : builders) {
			msrb.add(srb);
		}
		return msrb.get();
	}

	/**
	 * 修改index一条数据信息
	 * @param index
	 * @param type
	 * @param id 数据的id
	 * @param field_name 被修改的字段
	 * @param update_value 修改的值
	 * @return
	 */
	public String  updateInfo (String index,String type,String id,String field_name,String update_value){
		try {
			client.prepareUpdate(index, type, id).setRefresh(true)
					.setDoc(jsonBuilder().startObject().field(field_name, update_value).endObject()).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";

	}

	/**
	 * 初始化搜索对象
	 * @param index 索引
	 * @param type 类型
	 * @param keyword 关键词
	 * @return 搜索对象
	 */
	public SearchRequestBuilder initSearchRequest(String index, String type, String keyword) {
		SearchRequestBuilder searchBuilder = null;
		if (StringUtils.isEmpty(keyword)) {
			searchBuilder = getClient().prepareSearch(index).setTypes(type)
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
			return searchBuilder;
		}
		searchBuilder = getClient().prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setMinScore(MIN_SCORE);
		return searchBuilder;
	}

	/**
	 * 添加过滤条件
	 *
	 * 达到的效果类似以下sql:
	 * 同一个filterBuilder调用一次:
	 * select ... from table where f1=v1 or f1=v2
	 * 同一个filterBuilder累计调用多次:
	 * select ... from table where (f1=v1 or f1=v2) and (f2=v3 or f2=v4) and (f3=v5 or f3=v6)...
	 *
	 * @param filterBuilder 查询过滤对象
	 * @param field         字段名
	 * @return 查询过滤对象
	 */
	public static BoolQueryBuilder addFilterCondition(BoolQueryBuilder filterBuilder, String field, Object[] values) {

		if (field == null || values == null || values.length == 0) {
			return filterBuilder;
		}
		BoolQueryBuilder temp = QueryBuilders.boolQuery();
		boolean flag = false;
		for (Object v : values) {
			if (v == null) {
				continue;
			}
			if(v instanceof String) {
				if(StringUtils.isBlank(v.toString())) {
					continue;
				}
			}
			temp.should(QueryBuilders.matchQuery(field, v));
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
		from = from == null || from <= 0 ? 0 : from - 1;
		size = size == null || size < 0 ? 5 : size;
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
	public static void addBucketAggrs(SearchRequestBuilder searchBuilder, String[] bucketsFields) {
		for (String field : bucketsFields) {
			searchBuilder.addAggregation(AggregationBuilders.terms(TERMS_BUCKET_PREFIX + field)
					.field(field).size(0));
		}
	}

	/**
	 * 添加全局聚合条件
	 * 全局聚合命名:GLOBAL_AGGREGATION常量
	 * bucket聚合字段命名前缀:GLOBAL_TERMS_BUCKET_PREFIX常量
	 */
	public static void addGlobalBucketAggrs(SearchRequestBuilder searchBuilder, String[] bucketsFields) {
		GlobalBuilder global = AggregationBuilders
				.global(GLOBAL_AGGREGATION);
		for (String field : bucketsFields) {
			global.subAggregation(AggregationBuilders.terms(GLOBAL_TERMS_BUCKET_PREFIX + field).field(field).size(0));
		}
		searchBuilder.addAggregation(global);
	}

	/**
	 * 获取全局bucket聚合结果
	 * @param searchResponse
	 * @param fields
	 * @return
	 */
	public static Map<String, Map<String, Long>> getGlobalBucketsAggretionsFromResponse(SearchResponse searchResponse, String...fields) {
		Map<String, Map<String, Long>> aggregationResult = new HashMap<>();
		if(searchResponse == null) {
			return aggregationResult;
		}
		Aggregations aggregations = searchResponse.getAggregations();
		Global global = aggregations.get(GLOBAL_AGGREGATION);
		for (String field : fields) {
			Terms terms = global.getAggregations().get(GLOBAL_TERMS_BUCKET_PREFIX + field);
			//按照文档数量降序添加
			Map<String, Long> aggregation = new LinkedHashMap<>();
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
	 * 只获取bucket聚合结果
	 * @param index
	 * @param type
	 * @param bucketsFields
	 * @return
	 */
	public Map<String, Map<String, Long>> getOnlyBucketsAggretions(String index,String type, String[] bucketsFields) {
		return getOnlyBucketsAggretions(index,type,bucketsFields,null,null,null,null);
	}

	/**
	 * 只获取bucket聚合结果
	 * @param index
	 * @param type
	 * @param bucketsFields
	 * @param keyword
	 * @param keywordQueryFields
	 * @return
	 */
	public Map<String, Map<String, Long>> getOnlyBucketsAggretions(String index,String type, String[] bucketsFields,String keyword,String[] keywordQueryFields,MatchQueryBuilder.Operator queryLogic) {
		return getOnlyBucketsAggretions(index,type,bucketsFields,keyword,keywordQueryFields,queryLogic,QueryBuilders.boolQuery());
	}
	/**
	 * 只获取bucket聚合结果
	 * @param index
	 * @param type
	 * @param bucketsFields
	 * @param keyword
	 * @param keywordQueryFields
	 * @param filterBuilder
	 * @return
	 */
	public Map<String, Map<String, Long>> getOnlyBucketsAggretions(String index, String type, String[] bucketsFields, String keyword, String[] keywordQueryFields, MatchQueryBuilder.Operator queryLogic, BoolQueryBuilder filterBuilder) {

		Map<String, Map<String, Long>> aggregationResult = new HashMap<>();
		// 初始化查询请求对象
		SearchRequestBuilder searchBuilder = initSearchRequest(index, type, null);
		ElasticsearchHelper.addBucketAggrs(searchBuilder,bucketsFields);

		//关键词
		if(queryLogic != null && StringUtils.isNotBlank(keyword) && keywordQueryFields != null && keywordQueryFields.length > 0) {
			keywordQueryWithOperator(filterBuilder,keyword, queryLogic,keywordQueryFields);
		}
		searchBuilder.setSize(0);
		if(filterBuilder != null) {
			searchBuilder.setQuery(filterBuilder);
		}

		// 获取结果
		SearchResponse searchResponse = null;

		try {
			searchResponse = searchBuilder.setExplain(false).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
			return aggregationResult;
		}


		if(searchResponse == null) {
			return aggregationResult;
		}
		aggregationResult = getBucketsAggretionsFromResponse(searchResponse, bucketsFields);

		return aggregationResult;
	}

	/**
	 * 只获取全局bucket聚合结果
	 * @param index
	 * @param type
	 * @param bucketsFields
	 * @return
	 */
	public Map<String, Map<String, Long>> getOnlyGlobalBucketsAggretions(String index,String type, String[] bucketsFields) {
		return getOnlyGlobalBucketsAggretions(index,type,bucketsFields,null,null,null,null);
	}

	/**
	 * 只获取全局bucket聚合结果
	 * @param index
	 * @param type
	 * @param bucketsFields
	 * @param keyword
	 * @param keywordQueryFields
	 * @return
	 */
	public Map<String, Map<String, Long>> getOnlyGlobalBucketsAggretions(String index,String type, String[] bucketsFields,String keyword,String[] keywordQueryFields,MatchQueryBuilder.Operator queryLogic) {
		return getOnlyGlobalBucketsAggretions(index,type,bucketsFields,keyword,keywordQueryFields,queryLogic,QueryBuilders.boolQuery());
	}
	/**
	 * 只获取全局bucket聚合结果
	 * @param index
	 * @param type
	 * @param bucketsFields
	 * @param keyword
	 * @param keywordQueryFields
	 * @param filterBuilder
	 * @return
	 */
	public Map<String, Map<String, Long>> getOnlyGlobalBucketsAggretions(String index, String type, String[] bucketsFields, String keyword, String[] keywordQueryFields, MatchQueryBuilder.Operator queryLogic, BoolQueryBuilder filterBuilder) {

		Map<String, Map<String, Long>> aggregationResult = new HashMap<>();
		// 初始化查询请求对象
		SearchRequestBuilder searchBuilder = initSearchRequest(index, type, null);
		ElasticsearchHelper.addGlobalBucketAggrs(searchBuilder,bucketsFields);

		//关键词
		if(queryLogic != null && StringUtils.isNotBlank(keyword) && keywordQueryFields != null && keywordQueryFields.length > 0) {
			keywordQueryWithOperator(filterBuilder,keyword, queryLogic,keywordQueryFields);
		}
		searchBuilder.setSize(0);
		if(filterBuilder != null) {
			searchBuilder.setQuery(filterBuilder);
		}

		// 获取结果
		SearchResponse searchResponse = null;

		try {
			searchResponse = searchBuilder.setExplain(false).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
			return aggregationResult;
		}


		if(searchResponse == null) {
			return aggregationResult;
		}
		aggregationResult = getGlobalBucketsAggretionsFromResponse(searchResponse, bucketsFields);

		return aggregationResult;
	}

	/**
	 * 获取bucket聚合结果
	 * @param searchResponse
	 * @param fields
	 * @return 类型:Map<String, Map<String, Long>>
	 *         结果格式:
	 *         {
	 *             "field":{
	 *                 "term1":100,//docCount
	 *                 "term2":100,
	 *             }
	 *         }
	 */
	public static Map<String, Map<String, Long>> getBucketsAggretionsFromResponse(SearchResponse searchResponse, String...fields) {
		Map<String, Map<String, Long>> aggregationResult = new HashMap<>();
		if(searchResponse == null) {
			return aggregationResult;
		}
		Aggregations aggregations = searchResponse.getAggregations();
		for (String field : fields) {
			Terms terms = aggregations.get(TERMS_BUCKET_PREFIX + field);
			//按照文档数量降序添加
			Map<String, Long> aggregation = new LinkedHashMap<>();
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
	 * 将两层聚合结果转成List,用户招商web
	 * @param searchResponse
	 * @param parentBucketName
	 * @param childBucketName
	 * @return
	 */
	public static  List<Map<String,Object>> getAggretionsFromResponse(SearchResponse searchResponse, String parentBucketName, String childBucketName) {
		Terms terms = searchResponse.getAggregations().get(parentBucketName);
		List<Map<String,Object>> list = new ArrayList<>();
		for(Terms.Bucket entry:terms.getBuckets()){
			Map<String,Object> map = new HashMap<>();
			map.put("parent", entry.getKeyAsString());
			map.put("count", entry.getDocCount());
			Terms child = entry.getAggregations().get(childBucketName);
			List<Terms.Bucket> buckets = child.getBuckets();
			List<String> childList = new ArrayList<>();
			for (Terms.Bucket childEntry:buckets) {
				childList.add(childEntry.getKeyAsString());
			}
			map.put("child", childList);
			list.add(map);
		}
		return list;
	}

	/**
	 * 适用于招商web中渠道信息处理
	 * 对渠道结果中的时间字段增加文字说明,如一周内,一个月内
	 * @param searchResponse
	 * @return
	 */
	public static JSONObject buildSearchResultByTime(SearchResponse searchResponse) {
		JSONObject searchResult = new JSONObject();
		SearchHits hits = searchResponse.getHits();
		List<Map<String,Object>> hitsTmp = new ArrayList<>();
		for (SearchHit hit : hits) {
			Map<String, Object> hitTmp = new HashMap<>();
			hitTmp.put("_index",hit.getIndex());
			hitTmp.put("_type",hit.getType());
			hitTmp.put("_id",hit.getId());
			Map<String, Object> source = hit.getSource();
			if(StringUtils.isNotBlank(source.get("channelTime").toString())) {
				SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
				Date endDate = new Date();
				try {
					endDate = format.parse(source.get("channelTime").toString() );
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				long day=(endDate.getTime()-System.currentTimeMillis())/(24*60*60*1000);
				if (day <0) {
					source.put("emergency","其他");
				}else if (day <= 3){
					source.put("emergency", "三天内");
				} else if (day <= 7) {
					source.put("emergency", "三天内");
				} else if (day <= 30) {
					source.put("emergency", "一个月内");
				} else {
					source.put("emergency", "其他");
				}
			} else {
				source.put("emergency", "暂未确定");
			}
			hitTmp.put("_source",hit.getSource());

			hitsTmp.add(hitTmp);
		}
		searchResult.put("hits",hitsTmp);
		searchResult.put("total",hits.totalHits());
		return searchResult;
	}



	/**
	 * 获取bucket的去重key聚合结果
	 * @param searchResponse
	 * @param fields 聚合字段数组
	 * @return
	 */
	public static Map<String, Set> getDistinctAggregationFromBucketsAggretionsResponse(SearchResponse searchResponse, String...fields) {
		Map<String, Set> aggregationResult = new HashMap<>();
		if(searchResponse == null) {
			return aggregationResult;
		}
		Aggregations aggregations = searchResponse.getAggregations();
		for (String field : fields) {
			Set set = new LinkedHashSet();
			Terms terms = aggregations.get(TERMS_BUCKET_PREFIX + field);
			//按照文档数量降序添加
			for (Terms.Bucket bucket : terms.getBuckets()) {
				Object key = bucket.getKey();
				set.add(key);
			}
			aggregationResult.put(field, set);
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
	public static Map<String,Double> getMetricAggregationsFromResponseWithType(SearchResponse searchResponse,String type,String...fields) {
		Map<String, Double> aggregationResult = new HashMap<>();
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
	 * 关键字匹配搜索,默认从keywords字段里面取
	 *
	 * @param filterBuilder 搜索对象
	 * @param keyword       关键词
	 * @return 筛选对象
	 */
	public static BoolQueryBuilder keywordQueryWithOperator(BoolQueryBuilder filterBuilder, String keyword, MatchQueryBuilder.Operator operator, String[] keywordFields) {
		BoolQueryBuilder tmpfilterBuilder = QueryBuilders.boolQuery();
		if(StringUtils.isNotBlank(keyword)) {
			for (String field : keywordFields) {
				tmpfilterBuilder.should(QueryBuilders.matchQuery(field, keyword).operator(operator));
			}
			tmpfilterBuilder.minimumNumberShouldMatch(1);
		}
		filterBuilder.must(tmpfilterBuilder);
		return filterBuilder;
	}

	/**
	 * 关键字匹配,默认从keywords字段里面取
	 * @param filterBuilder 搜索对象
	 * @return 筛选对象
	 */
	public static BoolQueryBuilder keywordQueryByTerm(BoolQueryBuilder filterBuilder, String[] terms, String[] keywordFields) {
		BoolQueryBuilder tmpfilterBuilder = QueryBuilders.boolQuery();
		if (terms != null && terms.length > 0) {
			for (String term : terms) {
				for (String field : keywordFields) {
					tmpfilterBuilder.should(QueryBuilders.termQuery(field, term));
				}
			}
			tmpfilterBuilder.minimumNumberShouldMatch(1);
		}
		filterBuilder.must(tmpfilterBuilder);
		return filterBuilder;
	}


	/**
	 * 封装搜索结果
	 * 格式eg:
	 * 		{
	 * 		   "total":100,
	 * 		   "hits":[
	 * 		   		{
	 * 		   		 	"_id":"1111",
	 * 		   		    "_type":"type",
	 * 		   		    "_index":"index",
	 * 		   		    "_source":{
	 * 		   		        "field1":"v1",
	 * 		   		        "field2":"v2"
	 * 		   		    }
	 * 		   		}
	 * 		   ]
	 * 		}
	 * @param searchResponse 搜索结果
	 * @return
	 */
	public static JSONObject buildSearchResult(SearchResponse searchResponse) {
		JSONObject searchResult = new JSONObject();
		SearchHits hits = searchResponse.getHits();
		List<Map<String,Object>> hitsTmp = new ArrayList<>();
		for (SearchHit hit : hits) {
			Map<String, Object> hitTmp = new HashMap<>();
			hitTmp.put("_index",hit.getIndex());
			hitTmp.put("_type",hit.getType());
			hitTmp.put("_id",hit.getId());
			hitTmp.put("_source",hit.getSource());
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			if(highlightFields != null) {
                Map<String,Object> highLight = new HashMap<>();
                for (String field : highlightFields.keySet()) {
                    List<String> highLightFragmentList = new ArrayList<>();
                    Text[] fragments = highlightFields.get(field).getFragments();
                    for (Text fragment : fragments) {
                        highLightFragmentList.add(fragment.string());
                    }
                    highLight.put(field,highLightFragmentList);
                }
                hitTmp.put("highlight",highLight);
            }
			hitsTmp.add(hitTmp);
		}
		searchResult.put("hits",hitsTmp);
		searchResult.put("total",hits.totalHits());
		return searchResult;
	}
	/**
	 * 封装搜索结果
	 * 格式eg:
	 * 		{
	 * 		   "total":100,
	 * 		   "hits":[
	 * 		   		{
	 * 		   		 	"_id":"1111",
	 * 		   		    "_type":"type",
	 * 		   		    "_index":"index",
	 * 		   		    "_source":{
	 * 		   		        "field1":"v1",
	 * 		   		        "field2":"v2"
	 * 		   		    }
	 * 		   		    distance: 1200m
	 * 		   		}
	 * 		   ]
	 * 		}
	 * @param searchResponse 搜索结果
	 * @return
	 */
	public static JSONObject buildGeoSearchResult(SearchResponse searchResponse) {
		JSONObject searchResult = new JSONObject();
		SearchHits hits = searchResponse.getHits();
		List<Map<String,Object>> hitsTmp = new ArrayList<>();
		for (SearchHit hit : hits) {
			Map<String, Object> hitTmp = new HashMap<>();
			hitTmp.put("distance", hit.getSortValues()[0]);
			hitTmp.put("_index",hit.getIndex());
			hitTmp.put("_type",hit.getType());
			hitTmp.put("_id",hit.getId());
			hitTmp.put("_source",hit.getSource());
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			if(highlightFields != null) {
                Map<String,Object> highLight = new HashMap<>();
                for (String field : highlightFields.keySet()) {
                    List<String> highLightFragmentList = new ArrayList<>();
                    Text[] fragments = highlightFields.get(field).getFragments();
                    for (Text fragment : fragments) {
                        highLightFragmentList.add(fragment.string());
                    }
                    highLight.put(field,highLightFragmentList);
                }
                hitTmp.put("highlight",highLight);
            }
			hitsTmp.add(hitTmp);
		}
		searchResult.put("hits",hitsTmp);
		searchResult.put("total",hits.totalHits());
		return searchResult;
	}


	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public TransportClient getClient() {
		return client;
	}
	public void setClient(TransportClient client) {
		this.client = client;
	}


}
