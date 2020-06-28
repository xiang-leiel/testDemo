package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-12 16:44
 **/
public interface DepartDao {
    /**
     * 批量插入部门
     * @param departments
     */
    void insertList(@Param("departments") List<Department> departments);

    /**
     * 删除原表中所有数据
     */
    void deleteAll();
}
