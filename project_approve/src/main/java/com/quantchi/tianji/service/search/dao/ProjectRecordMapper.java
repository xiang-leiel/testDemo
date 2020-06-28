package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.model.ProjectRecord;
import org.apache.ibatis.annotations.Param;

public interface ProjectRecordMapper {
    int insert(ProjectRecord record);

    int insertSelective(ProjectRecord record);

    ProjectRecord selectById(@Param("id") String id);

    int updateForPhotoUrl(@Param("id") String id, @Param("receImg") String receImg);

    int updateById(ProjectRecord record);

    Long selectLastOne();
}