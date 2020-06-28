package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsBqly;import java.util.List;

public interface DmCsBqlyMapper {
    int deleteByPrimaryKey(Integer bqlyDm);

    int insert(DmCsBqly record);

    int insertSelective(DmCsBqly record);

    DmCsBqly selectByPrimaryKey(Integer bqlyDm);

    int updateByPrimaryKeySelective(DmCsBqly record);

    int updateByPrimaryKey(DmCsBqly record);

    List<DmCsBqly> selectBqlyAll();
}