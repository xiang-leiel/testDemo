package com.quantchi.tianji.service.search.dao.mapper.authority;

import com.quantchi.tianji.service.search.entity.authority.DeqingAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leiel
 * @since 2020-02-08
 */
public interface DeqingAuthorityMapper extends BaseMapper<DeqingAuthority> {

    DeqingAuthority selectOneByMobile(@Param("mobile")String mobile);
}
