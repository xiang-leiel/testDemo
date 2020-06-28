package com.quantchi.tianji.service.search.enums;

/**
 * 工作圈类型枚举类
 */
public enum TypeEnums {

    //工作圈来源于记录
    TYPE_RECORD(0),

    //工作圈来源于任务指派
    TYPE_ASSIGN(1),;

    private Integer type;

    TypeEnums(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
