package com.quantchi.tianji.service.search;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/25 5:19 PM
 */
@Configuration
public class CORSConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String origin = httpRequest.getHeader("Origin");
        List<String> allowOrigins = new ArrayList<>();
        allowOrigins.add("http://47.111.147.99:8126");
        allowOrigins.add("http://47.111.147.99:8124");
        if("OPTIONS".equalsIgnoreCase(httpRequest.getMethod()) ||
                (StringUtils.isNotBlank(origin) && allowOrigins.contains(origin))){
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Methods", "*");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Authorizations");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
