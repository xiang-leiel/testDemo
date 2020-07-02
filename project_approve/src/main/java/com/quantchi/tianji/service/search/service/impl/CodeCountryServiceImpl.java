package com.quantchi.tianji.service.search.service.impl;

import com.quantchi.tianji.service.search.entity.CodeCountry;
import com.quantchi.tianji.service.search.dao.CodeCountryMapper;
import com.quantchi.tianji.service.search.service.ICodeCountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 国家编码：iso-3166-1标准 服务实现类
 * </p>
 *
 * @author leiel
 * @since 2020-07-02
 */
@Service
public class CodeCountryServiceImpl extends ServiceImpl<CodeCountryMapper, CodeCountry> implements ICodeCountryService {

}
