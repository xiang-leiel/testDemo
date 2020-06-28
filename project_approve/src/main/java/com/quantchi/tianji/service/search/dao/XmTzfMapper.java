package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmTzf;

public interface XmTzfMapper {
    int deleteByPrimaryKey(String tzfId);

    int insert(XmTzf record);

    int insertSelective(XmTzf record);

    XmTzf selectByPrimaryKey(String tzfId);

    int updateByPrimaryKeySelective(XmTzf record);

    int updateByPrimaryKey(XmTzf record);

    void updateQybjByTzfId(XmTzf xmTzf);
}