package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmQylxr;import java.util.List;

public interface XmQylxrMapper {
    int deleteByPrimaryKey(String qylxrId);

    int insert(XmQylxr record);

    int insertSelective(XmQylxr record);

    XmQylxr selectByPrimaryKey(String qylxrId);

    int updateByPrimaryKeySelective(XmQylxr record);

    int updateByPrimaryKey(XmQylxr record);

    List<XmQylxr> selectListByXmId(String projectId);

    void updateInvalidByXmId(String xmId);
}