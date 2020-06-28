package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmGlTzf;
import com.quantchi.tianji.service.search.model.XmTzf;

import java.util.List;

public interface XmGlTzfMapper {
    int deleteByPrimaryKey(String xmtzfId);

    int insert(XmGlTzf record);

    int insertSelective(XmGlTzf record);

    XmGlTzf selectByPrimaryKey(String xmtzfId);

    int updateByPrimaryKeySelective(XmGlTzf record);

    int updateByPrimaryKey(XmGlTzf record);

    List<XmGlTzf> selectByProjectId(String projectId);

    void updateQybjByTzfId(XmGlTzf xmGlTzf);

    void deleteByProjectId(String projectId);
}