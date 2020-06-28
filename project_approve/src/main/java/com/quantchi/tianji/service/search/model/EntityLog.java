package com.quantchi.tianji.service.search.model;

import java.io.Serializable;

/**
 * entity_log
 * @author 
 */
public class EntityLog implements Serializable {
    /**
     * 实体id
     */
    private String id;

    /**
     * 实体名称
     */
    private String name;

    /**
     * 实体类型
     */
    private String type;

    /**
     * 查看入口,搜索:search,点击:view
     */
    private String entry;

    /**
     * 记录次数
     */
    private Integer logTimes;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Integer getLogTimes() {
        return logTimes;
    }

    public void setLogTimes(Integer logTimes) {
        this.logTimes = logTimes;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", entry=").append(entry);
        sb.append(", logTimes=").append(logTimes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}