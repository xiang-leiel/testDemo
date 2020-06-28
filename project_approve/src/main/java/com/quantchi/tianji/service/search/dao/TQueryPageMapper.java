package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.TQueryCatalog;
import com.quantchi.tianji.service.search.model.TQueryPage;

import java.util.List;

public interface TQueryPageMapper {
    int deleteByPrimaryKey(Integer pageId);

    int insert(TQueryPage record);

    int insertSelective(TQueryPage record);

    TQueryPage selectByPrimaryKey(Integer pageId);

    int updateByPrimaryKeySelective(TQueryPage record);

    int updateByPrimaryKey(TQueryPage record);

    List<TQueryCatalog> selectListAll(int pageId);

    List<TQueryCatalog> selectByCatalogId(int catalogId);

    TQueryCatalog selectOneByCatalogId(int catalogId);
}