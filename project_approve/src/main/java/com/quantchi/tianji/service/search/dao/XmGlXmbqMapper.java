package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmGlXmbq;

import java.util.List;

public interface XmGlXmbqMapper {
    int deleteByPrimaryKey(Integer xmglbqId);

    int insert(XmGlXmbq record);

    int insertSelective(XmGlXmbq record);

    XmGlXmbq selectByPrimaryKey(Integer xmglbqId);

    int updateByPrimaryKeySelective(XmGlXmbq record);

    int updateByPrimaryKey(XmGlXmbq record);

    List<XmGlXmbq> selectListById(Integer xmjbxxId);
}