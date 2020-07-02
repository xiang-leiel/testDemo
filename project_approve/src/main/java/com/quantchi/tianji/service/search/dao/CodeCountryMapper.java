package com.quantchi.tianji.service.search.dao;

import com.quantchi.tianji.service.search.entity.CodeCountry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 国家编码：iso-3166-1标准 Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-07-02
 */
public interface CodeCountryMapper extends BaseMapper<CodeCountry> {

    List<CodeCountry> getNation();
}
