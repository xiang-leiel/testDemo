package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmWfNodezb;

public interface DmWfNodezbMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmWfNodezb record);

    int insertSelective(DmWfNodezb record);

    DmWfNodezb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmWfNodezb record);

    int updateByPrimaryKey(DmWfNodezb record);

    /**
     * 查询语句
     *
     * @return
     */
    DmWfNodezb selectOne(DmWfNodezb record);

    Integer selectPreDmByUserId(Integer userDm);

    DmWfNodezb selectByPreNode(Integer preNode);

    DmWfNodezb selectNextZtByNode(Integer node);

    Integer selectStatusDmByUserId(Integer userDm);

    Integer selectFlagDmByUserId(Integer userId);

    Integer selectXmztDmByUserId(Integer userId);

    Integer selectNodDmByUserId(Integer userId);

    Integer selectNextByUserDm(Integer userId);

    Integer selectNodeByUserDm(Integer userId);

    Integer selectStatusByUserDm(Integer userId);

    Integer selectDeptDmByUserId(Integer userId);
}