package com.quantchi.tianji.service.search.exception;

/**
 * @author whuang
 * @date 2019/4/16
 */
public enum TianjiErrorCode implements ErrorCode {

    PARAM_NULL(10000, "参数为空"),
    SEARCH_ERROR(10001, "搜索出错"),
    NULL_RESULT(10002,"没有结果"),
    INSERT_ERROR(10000, "插入数据出错"),
    DING_ERROR(10004, "调用钉钉接口出错"),
    ASSIGNED_ERROR(10005, "该企业已被分配"),
    NOT_ALLOWED_ASSIGN_ERROR(10006, "不能分派任务给他人"),
    NOT_ASSIGNED_ERROR(10007, "该企业未被分派,不能添加任务"),;

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误描述
     */
    private String description;

    TianjiErrorCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
