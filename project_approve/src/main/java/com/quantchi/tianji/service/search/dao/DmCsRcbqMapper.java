package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsHbdw;
import com.quantchi.tianji.service.search.model.DmCsRcbq;

import java.util.List;

public interface DmCsRcbqMapper {
    int deleteByPrimaryKey(Integer crbqDm);

    int insert(DmCsRcbq record);

    int insertSelective(DmCsRcbq record);

    DmCsRcbq selectByPrimaryKey(Integer crbqDm);

    int updateByPrimaryKeySelective(DmCsRcbq record);

    int updateByPrimaryKey(DmCsRcbq record);

    List<DmCsRcbq> selectAll();
}