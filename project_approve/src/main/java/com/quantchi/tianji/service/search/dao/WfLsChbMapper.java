package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.WfLsChb;

public interface WfLsChbMapper {
    int deleteByPrimaryKey(String wfzbId);

    int insert(WfLsChb record);

    int insertSelective(WfLsChb record);

    WfLsChb selectByPrimaryKey(String wfzbId);

    int updateByPrimaryKeySelective(WfLsChb record);

    int updateByPrimaryKey(WfLsChb record);

    WfLsChb selectOne(WfLsChb wfLsChb);

    Integer selectDeptDmByXmId(String xmjbxxId);

    Integer selectStatusDmByXmId(String xmjbxxId);

    Integer countByXmId(String xmjbxxId);
}