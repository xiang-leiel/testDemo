package com.quantchi.tianji.service.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quantchi.tianji.service.search.helper.ElasticsearchHelper;
import com.quantchi.tianji.service.search.model.LeftTreeNode;
import com.quantchi.tianji.service.search.service.LeftTreeNodeBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author whuang
 * @date 2019/6/13
 */
@Service
public class LeftTreeNodeBuilderImpl implements LeftTreeNodeBuilder {

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    @Override
    public List<LeftTreeNode> buildLeftTreeNodeList() {
        List<LeftTreeNode> result = new ArrayList<>();
        String index = "deqing_catalog";
        for (String field : new String[]{"type", "level", "topic", "scale", "target"}) {
            //获取所有的文档
            // 初始化查询请求对象
            SearchRequestBuilder searchBuilder = elasticsearchHelper.initSearchRequest(index, field + "_catalog", null);
            searchBuilder.setSize(10000);
            // 初始化搜索模型
            BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
            searchBuilder.setQuery(filterBuilder);
            // 获取结果
            SearchResponse searchResponse = null;
            try {
                searchResponse = searchBuilder.setExplain(false).execute().actionGet();
            } catch (Exception e) {
                e.printStackTrace();
            }

            LeftTreeNode root = new LeftTreeNode();

            List<LeftTreeNode> children = new ArrayList<>();

            // 封装结果
            SearchHits hits = searchResponse.getHits();
            LeftTreeNode other = null;
            LeftTreeNode noLimit = null;
            for (SearchHit hit : hits) {
                Map<String, Object> source = hit.getSource();
                JSONObject sourceTmp = new JSONObject(source);
                LeftTreeNode leaf = new LeftTreeNode();
                String name = sourceTmp.getString("name");
                leaf.setName(sourceTmp.getString("name"));
                leaf.setField(field);
                if("其它".equals(name)) {
                    other = leaf;
                    continue;
                }
                if("不限".equals(name)){
                    noLimit = leaf;
                    continue;
                }
                children.add(leaf);
            }

            if(noLimit != null) {
                children.add(noLimit);
            }

            if(other != null) {
                children.add(other);
            }

            //对政策层次,适用规模,适用对象进行重新排序
            switch (field) {
                case "level":
                    children = reorderChildren(children,"国家级,省级,市级,区级（县级）".split(","));
                    break;
                case "scale":
                    children = reorderChildren(children,"大型企业,规上企业,中小企业,小微企业,不限".split(","));
                    break;
                case "target":
                    children = reorderChildren(children,"政府,园区,企业,个人,其它".split(","));
                    break;
                default:
                    break;
            }


            //封装导航树对象
            root.setChildren(children);
            root.setField(field);
            switch (field) {
                case "type":
                    root.setName("政策类型");
                    break;
                case "level":
                    root.setName("政策层级");
                    break;
                case "topic":
                    root.setName("政策主题");
                    break;
                case "scale":
                    root.setName("适用规模");
                    break;
                case "target":
                    root.setName("适用对象");
                    break;
                default:
                    throw new IllegalArgumentException("创建导航树对象参数错误,field:" + field);
            }
            result.add(root);
        }

        return result;
    }

    private List<LeftTreeNode> reorderChildren(List<LeftTreeNode> children, String[] fieldsOrder) {
        List<LeftTreeNode> orderedList = new ArrayList<>();
        for (String field : fieldsOrder) {
            for (int i = 0; i < children.size(); i++) {
                LeftTreeNode child = children.get(i);
                if(Objects.equals(field,child.getName())) {
                    orderedList.add(child);
                }
            }
        }
        return orderedList;
    }
}
