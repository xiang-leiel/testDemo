package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.XmGlBqly;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface XmGlBqlyMapper {
    int deleteByPrimaryKey(String xmbqlyId);

    int insert(XmGlBqly record);

    int insertSelective(XmGlBqly record);

    XmGlBqly selectByPrimaryKey(String xmbqlyId);

    int updateByPrimaryKeySelective(XmGlBqly record);

    int updateByPrimaryKey(XmGlBqly record);

    List<XmGlBqly> selectListByXmId(@Param("xmId") String xmId, @Param("type") Integer type);

    List<Integer> selectIdListByXmId(@Param("xmId") String xmId);

    List<Integer> selectListByXmIdAndType(@Param("xmId") String xmId, @Param("type") Integer type);

    List<XmGlBqly> queryListByXmIdAndType(@Param("xmId") String xmId, @Param("type") Integer type);

    void deleteByXmId(String xmId);

    void updateInvalidByXmId(@Param("xmId") String xmId, @Param("type") Integer type);

    void deleteByProjectId(String projectId);
}