package com.quantchi.tianji.service.search.utils;

import java.lang.reflect.Field;

/**
 * @author whuang
 * @date 2019/7/13
 */
public class ReflectUtils {
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return  field.get(object);
        } catch (Exception e) {

            return null;
        }
    }

}
