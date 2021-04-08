package com.example.test.design.model.proxy._动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 10:21 AM
 */

public class DynamicProxyTest {

    public static void main(String[] args) {

        MaotaiJiu maotaijiu = new MaotaiJiu();


        InvocationHandler jingxiao1 = new GuitaiA(maotaijiu);

        SellWine dynamicProxy = (SellWine) Proxy.newProxyInstance(MaotaiJiu.class.getClassLoader(),
                MaotaiJiu.class.getInterfaces(), jingxiao1);

        dynamicProxy.maiJiu();

    }

}
