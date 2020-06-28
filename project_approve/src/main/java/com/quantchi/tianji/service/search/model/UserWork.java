package com.quantchi.tianji.service.search.model;

import lombok.Data;

@Data
public class UserWork {
    private Integer uwId;
    private String userId;
    private String company;
    private String position;
    private String startTime;
    private String endTime;
}
