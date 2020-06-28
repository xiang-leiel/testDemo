package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsTzgm;
import com.quantchi.tianji.service.search.model.DmCsXmbq;

import java.util.List;

public interface DmCsXmbqMapper {
    int deleteByPrimaryKey(Integer xmbqDm);

    int insert(DmCsXmbq record);

    int insertSelective(DmCsXmbq record);

    DmCsXmbq selectByPrimaryKey(Integer xmbqDm);

    int updateByPrimaryKeySelective(DmCsXmbq record);

    int updateByPrimaryKey(DmCsXmbq record);


}