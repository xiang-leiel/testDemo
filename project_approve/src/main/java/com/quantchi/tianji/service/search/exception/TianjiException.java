package com.quantchi.tianji.service.search.exception;

/**
 * @author whuang
 * @date 2019/4/16
 */
public class TianjiException extends RuntimeException {

    private ErrorCode errorCode;

    public TianjiException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public TianjiException(ErrorCode errorCode, String errorMessage) {
        super(errorCode.getDescription() + " - " + errorMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
