package com.quantchi.tianji.service.search.aop.annotation;

import java.lang.annotation.*;

/**
 * @author leiel
 * @Description
 * @Date 2020/4/3 12:00 PM
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
    String name();
}