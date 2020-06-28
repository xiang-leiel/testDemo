package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.config.AppConfig;
import com.quantchi.tianji.service.search.dao.CompanyFollowerDao;
import com.quantchi.tianji.service.search.exception.TianjiErrorCode;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.CompanyFollower;
import com.quantchi.tianji.service.search.service.CompanyFollowerService;
import com.quantchi.tianji.service.search.service.FollowingCompanyEventService;
import com.quantchi.tianji.service.search.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author whuang
 * @date 2019/7/13
 */
@Service
public class FollowingCompanyEventServiceImpl implements FollowingCompanyEventService {

    @Value("${esIndexEvent}")
    private String index;

    @Value("${esTypeEvent}")
    private String type;

    @Value("${eventKeywordQueryFileds}")
    private String keywordQueryFileds;

    @Autowired
    private CompanyFollowerDao companyFollowerDao;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Autowired
    private AppConfig appConfig;

    @Override
    public ResultInfo listFollowingCompanyEvent(String staffId,String keyword,Integer page, Integer pageSize) {

        List<CompanyFollower> companyFollowers = companyFollowerDao.listMyFollowedCompany(staffId);
        if(companyFollowers == null || companyFollowers.size() == 0) {
            JSONObject result = new JSONObject();
            result.put("total",0);
            result.put("list",new ArrayList<>());
            return ResultUtils.success(result);
        }
        //获取事件文档
        List<String> companyIds = new ArrayList<>();
        if(companyFollowers != null) {
            for (CompanyFollower companyFollower : companyFollowers) {
                companyIds.add(companyFollower.getCompanyId());
            }
        }

        // 初始化查询请求对象
        SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, type, keyword);

        // 初始化布尔查询
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();

        // 关键词搜索(可以设置匹配逻辑 and: Operator.AND, or: Operator.OR)
        ElasticsearchHelper.keywordQueryWithOperator(filterBuilder,keyword, MatchQueryBuilder.Operator.AND,keywordQueryFileds.split(","));

        // 企业id筛选
        ElasticsearchHelper.addFilterCondition(filterBuilder,"companyId",companyIds.toArray());

        searchBuilder.setQuery(filterBuilder);

        // 按照时间排序
        ElasticsearchHelper.addSort(searchBuilder,"publishTime:desc");

        // 分页
        ElasticsearchHelper.pagingSet(searchBuilder,page,pageSize);

        // 获取结果
        SearchResponse searchResponse = null;

        try {
            searchResponse = searchBuilder.setExplain(false).execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.fail(TianjiErrorCode.SEARCH_ERROR);
        }

        List<JSONObject> hits = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            JSONObject hitTmp = new JSONObject(hit.getSource());
            hitTmp.put("id",hit.getId());
            String companyId = hitTmp.getString("companyId");
            if(StringUtils.isNotBlank(companyId)) {
                GetResponse getResponse = elasticsearchHelper.get(appConfig.getEsIndexCompany(), appConfig.getEsTypeCompany(), companyId);
                if(getResponse.isExists()) {
                    hitTmp.put("company",getResponse.getSource());
                }
            }
            hits.add(hitTmp);
        }
        JSONObject result = new JSONObject();
        result.put("total",searchResponse.getHits().getTotalHits());
        result.put("list",hits);
        return ResultUtils.success(result);
    }
}
