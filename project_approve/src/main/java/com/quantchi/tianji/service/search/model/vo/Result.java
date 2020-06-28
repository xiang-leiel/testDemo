package com.quantchi.tianji.service.search.model.vo;


/**
 * @author whuang
 * @date 2019/4/16
 */
public class Result {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String message;

    /**
     * 具体内容.
     */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
