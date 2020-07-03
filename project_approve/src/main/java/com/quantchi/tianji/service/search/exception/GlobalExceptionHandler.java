package com.quantchi.tianji.service.search.exception;

import com.quantchi.core.message.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 
 * @author leiel
 * @Date 2020/7/3 10:05 AM
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultInfo exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return new ResultInfo(200, "空指针异常", null);
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultInfo exceptionHandler(HttpServletRequest req, Exception e){
        log.error("未知异常！原因是:",e);
        return new ResultInfo(200, "未知异常，请联系管理员", null);
    }

}
