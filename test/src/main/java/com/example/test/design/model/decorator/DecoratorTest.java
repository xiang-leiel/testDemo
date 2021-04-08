package com.example.test.design.model.decorator;

import java.lang.reflect.Method;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/14 2:47 PM
 */

public class DecoratorTest {

    public static void main(String[] args) throws ClassNotFoundException {

        Battercake battercake;

        battercake = new BaseBattercake();

        battercake = new EggDecorator(battercake);

        battercake = new SausageDecorator(battercake);

        System.out.println(battercake.getMsg());
        System.out.println(battercake.getPrice());

        Class<?> battercate = Class.forName("com.example.test.design.model.decorator.Battercake");

        System.out.println(battercake.getPrice());

    }

}
