package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsXzqh;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface DmCsXzqhMapper {
    int deleteByPrimaryKey(Integer xzqhDm);

    int insert(DmCsXzqh record);

    int insertSelective(DmCsXzqh record);

    DmCsXzqh selectByPrimaryKey(Integer xzqhDm);

    int updateByPrimaryKeySelective(DmCsXzqh record);

    int updateByPrimaryKey(DmCsXzqh record);

    List<DmCsXzqh> selectProvinceIds(@Param("type") Integer type, @Param("nation") Integer nation);
}