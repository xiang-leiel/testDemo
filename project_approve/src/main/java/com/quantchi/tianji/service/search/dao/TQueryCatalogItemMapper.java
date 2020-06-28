package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.TQueryCatalogItem;

import java.util.List;

public interface TQueryCatalogItemMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(TQueryCatalogItem record);

    int insertSelective(TQueryCatalogItem record);

    TQueryCatalogItem selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(TQueryCatalogItem record);

    int updateByPrimaryKey(TQueryCatalogItem record);

    List<TQueryCatalogItem> selectListByCatalogId(Integer catalogId);
}