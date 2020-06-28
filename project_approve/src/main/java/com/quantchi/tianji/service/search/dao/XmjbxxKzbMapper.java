package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmjbxxKzb;

public interface XmjbxxKzbMapper {
    int deleteByPrimaryKey(Integer xmkzbId);

    int insert(XmjbxxKzb record);

    int insertSelective(XmjbxxKzb record);

    XmjbxxKzb selectByPrimaryKey(Integer xmkzbId);

    int updateByPrimaryKeySelective(XmjbxxKzb record);

    int updateByPrimaryKey(XmjbxxKzb record);
}