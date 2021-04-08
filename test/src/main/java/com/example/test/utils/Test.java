package com.example.test.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/14 10:30 PM
 */

public class Test {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        Class<?> aClass = Class.forName("com.example.test.utils.PropertiesUtils");

        //若getStringTest方法前修饰符是public 则用getMethod
        Method method3 = aClass.getMethod("getStringTest", String.class);

        //实例化对象，验证方法是否获取成功
        Object object = aClass.getConstructor().newInstance();

        //调用方法
        Object invoke = method3.invoke(object,"测试反射");

        System.out.println(invoke);

    }

}
