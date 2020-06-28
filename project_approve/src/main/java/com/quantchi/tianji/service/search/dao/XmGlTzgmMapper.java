package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmGlTzgm;import org.apache.ibatis.annotations.Param;

public interface XmGlTzgmMapper {
    int deleteByPrimaryKey(String tzgmId);

    int insert(XmGlTzgm record);

    int insertSelective(XmGlTzgm record);

    XmGlTzgm selectByPrimaryKey(String tzgmId);

    int updateByPrimaryKeySelective(XmGlTzgm record);

    int updateByPrimaryKey(XmGlTzgm record);

    XmGlTzgm selectOneByXmjbxxId(@Param("xmjbxxId") String xmjbxxId);

    void updateByXmId(XmGlTzgm xmGlTzgm);
}