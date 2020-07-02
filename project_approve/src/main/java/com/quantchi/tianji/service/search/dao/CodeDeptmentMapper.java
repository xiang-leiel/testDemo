package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.entity.CodeDeptment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门或落地部门表 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-07-01
 */
public interface CodeDeptmentMapper extends BaseMapper<CodeDeptment> {

    String getParentDeptRole(Integer deptId);

    List<CodeDeptment> selectDeptByType(int type);

    List<Integer> selectOneBySjDeptDm(Integer deptId);

    String selectOneByUserDm(Integer userDm);

    Integer selectOneBydeptName(String deptName);

    List<CodeDeptment> selectByType();

    List<CodeDeptment> selectByArea(Integer area);
}
