package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.TQueryCatalog;import java.util.List;

public interface TQueryCatalogMapper {
    int deleteByPrimaryKey(Integer catalogId);

    int insert(TQueryCatalog record);

    int insertSelective(TQueryCatalog record);

    TQueryCatalog selectByPrimaryKey(Integer catalogId);

    int updateByPrimaryKeySelective(TQueryCatalog record);

    int updateByPrimaryKey(TQueryCatalog record);

    List<Integer> selectLevelCount();
}