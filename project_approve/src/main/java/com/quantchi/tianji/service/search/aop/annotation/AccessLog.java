package com.quantchi.tianji.service.search.aop.annotation;

/**
 * @author leiel
 * @Description
 * @Date 2020/4/3 1:40 PM
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLog {

    String name();
}
