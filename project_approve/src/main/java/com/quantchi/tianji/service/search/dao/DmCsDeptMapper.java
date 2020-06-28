package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsDept;import java.util.List;

public interface DmCsDeptMapper {
    int deleteByPrimaryKey(Integer deptDm);

    int insert(DmCsDept record);

    int insertSelective(DmCsDept record);

    DmCsDept selectByPrimaryKey(Integer deptDm);

    int updateByPrimaryKeySelective(DmCsDept record);

    int updateByPrimaryKey(DmCsDept record);

    String selectOneByUserDm(Integer userDm);

    Integer selectOneBydeptName(String deptName);

    List<Integer> selectOneBySjDeptDm(Integer deptDm);

    List<DmCsDept> selectBySjDeptDm(Integer deptDm);

    List<DmCsDept> selectByType();

    List<DmCsDept> selectByArea(Integer area);

    List<DmCsDept> selectDeptByType(int type);
}