package com.quantchi.tianji.service.search.filter;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

/**
 * @Description 
 * @author leiel
 * @Date 2020/4/1 2:01 PM
 */
@Slf4j
@Component
public class TokenFilter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = "";
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorizations");

        if (StringUtils.isBlank(authorization)) {
            return true;
        }else {
            try {
                userId = JwtUtils.getUserId(authorization);
            } catch (JWTDecodeException e) {
                log.info("token解析异常,异常信息:{}",e.getMessage());
                return false;
            }
            boolean verify = JwtUtils.verify(authorization, userId, "deqingswjweb");
            if (verify) {
                return true;
            } else {

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null ;

                ResultInfo resultInfo = new ResultInfo(ErrCode.WRONG_TOKEN_ERROR.getCode(), ErrCode.WRONG_TOKEN_ERROR.getDesc(), "fail");
                try{
                    String json = JSON.toJSONString(resultInfo);
                    response.setContentType("application/json");
                    out = response.getWriter();
                    // 返回json信息给前端
                    out.append(json);
                    out.flush();
                    return false;
                } catch (Exception e){
                    e.printStackTrace();
                    response.sendError(500);
                    return false;
                }
            }
        }

    }

}
