package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.entity.CodeRegion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-07-02
 */
public interface CodeRegionMapper extends BaseMapper<CodeRegion> {

    List<CodeRegion> selectProvinceIds(@Param("type") Integer type, @Param("nation") Integer nation);
}
