package com.quantchi.tianji.service.search.service.impl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quantchi.tianji.service.search.entity.user.LoginLog;
import com.quantchi.tianji.service.search.dao.user.LoginLogMapper;
import com.quantchi.tianji.service.search.service.user.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leiel
 * @since 2020-06-29
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
