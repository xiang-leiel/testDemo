package com.quantchi.tianji.service.search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


/**
 * @program: DeQing-InvestmentEnterprise
 * @description:
 * @author: mf
 * @create: 2019-07-10 15:39
 **/
@Data
public class AbstractWorkCircle implements WorkCircle {

    private Integer type;

    @JsonIgnore
    private Order order;

}
