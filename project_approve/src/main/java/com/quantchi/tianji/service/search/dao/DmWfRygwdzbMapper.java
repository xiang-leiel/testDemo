package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmWfRygwdzb;

import java.util.List;

public interface DmWfRygwdzbMapper {
    int deleteByPrimaryKey(Integer rygwdzDm);

    int insert(DmWfRygwdzb record);

    int insertSelective(DmWfRygwdzb record);

    DmWfRygwdzb selectByPrimaryKey(Integer rygwdzDm);

    int updateByPrimaryKeySelective(DmWfRygwdzb record);

    int updateByPrimaryKey(DmWfRygwdzb record);

    List<DmWfRygwdzb> selectByUserDm(Integer userDm);

    Integer checkLeader(Integer userDm);
}