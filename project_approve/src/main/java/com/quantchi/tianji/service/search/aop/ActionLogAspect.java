package com.quantchi.tianji.service.search.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.dao.LoginLogMapper;
import com.quantchi.tianji.service.search.dao.UseAccessRecordMapper;
import com.quantchi.tianji.service.search.model.LoginLog;
import com.quantchi.tianji.service.search.model.UseAccessRecord;
import com.quantchi.tianji.service.search.utils.JsonUtil;
import com.quantchi.tianji.service.search.utils.JwtUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/31 9:42 AM
 */
@Slf4j
@Component
@Aspect
public class ActionLogAspect {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private UseAccessRecordMapper useAccessRecordMapper;

    @Pointcut("@annotation(com.quantchi.tianji.service.search.aop.annotation.LoginLog)")
    private void loginLogPointCut() {
    }

    @Pointcut("execution (* com.quantchi.tianji.service.search.interfaces.http.*.*(..))")
    private void actionPointCut() {
    }

    @AfterReturning(value = "loginLogPointCut()", returning = "result")
    public void recordLoginLog(JoinPoint joinPoint, ResultInfo result) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //入参
        Object inParam = JSONObject.toJSON(joinPoint.getArgs());

        String ip = request.getRemoteAddr();

        //记录用户登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(ip);
        loginLog.setLoginTime(new Date());

        Map<String, Object> map = (HashMap)result.getBody();

        if(map == null || map.get("userId") == null) {
            return;
        }
        loginLog.setUserDm(Integer.parseInt(map.get("userId").toString()));
        loginLogMapper.insertSelective(loginLog);

    }

    @Before("actionPointCut()")
    public void recordActionData(JoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();

        String url = request.getServletPath();

        //记录访问接口数据
        UseAccessRecord useAccessRecord = new UseAccessRecord();
        //入参
        try{
            Object inParam = Arrays.toString(joinPoint.getArgs());
            useAccessRecord.setAccessParam(inParam.toString());
        }catch (Exception e){
            log.error("参数转换出错{}",url);
        }

        String ip = request.getRemoteAddr();

        useAccessRecord.setAccessIp(ip);
        useAccessRecord.setAccessUrl(url);

        //token解析获取userId
        String authorization = request.getHeader("Authorizations");

        String userId = null;
        if(StringUtils.isNotEmpty(authorization)) {
            userId = JwtUtils.getUserId(authorization);
            useAccessRecord.setUserDm(Integer.valueOf(userId));
            useAccessRecord.setSource(2);

            useAccessRecordMapper.insertSelective(useAccessRecord);
        }

    }

}
