package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.DmCsLczt;
import org.apache.ibatis.annotations.Param;

public interface DmCsLcztMapper {
    int deleteByPrimaryKey(Integer lcztDm);

    int insert(DmCsLczt record);

    int insertSelective(DmCsLczt record);

    DmCsLczt selectByPrimaryKey(Integer lcztDm);

    int updateByPrimaryKeySelective(DmCsLczt record);

    int updateByPrimaryKey(DmCsLczt record);

    /**
     * 根据工作流主表id获取下一节点状态id
     * @return
     */
    Integer getStatusByWfzbId(@Param("wfzbId") Integer wfzbId);
}