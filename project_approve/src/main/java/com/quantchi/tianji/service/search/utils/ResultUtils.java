package com.quantchi.tianji.service.search.utils;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.enums.ErrCode;
import com.quantchi.tianji.service.search.exception.ErrorCode;

/**
 * @author whuang
 * @date 2019/6/13
 */
public class ResultUtils {

    public static final Integer SUCCESS_CODE = 200;

    public static ResultInfo success(Object data) {
        ResultInfo resultVo = new ResultInfo(SUCCESS_CODE,null,data);
        return resultVo;
    }

    public static ResultInfo fail(Integer code,String msg){
        ResultInfo resultVo = new ResultInfo(code,msg,null);
        return resultVo;
    }

    public static ResultInfo fail(ErrorCode errorCode){
        ResultInfo resultVo = new ResultInfo(errorCode.getCode(),errorCode.getDescription(),null);
        return resultVo;
    }

    public static ResultInfo fail(ErrCode errCode){
        ResultInfo resultVo = new ResultInfo(errCode.getCode(),errCode.getDesc(),null);
        return resultVo;
    }
}
