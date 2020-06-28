package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.WfLsZb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WfLsZbMapper {
    int deleteByPrimaryKey(String wfzbId);

    int insert(WfLsZb record);

    int insertSelective(WfLsZb record);

    WfLsZb selectByPrimaryKey(String wfzbId);

    int updateByPrimaryKeySelective(WfLsZb record);

    int updateByPrimaryKey(WfLsZb record);

    WfLsZb selectOne(WfLsZb wfLsZb);

    List<String> selectListByNode(@Param("node") Integer node, @Param("status") Integer status);
}