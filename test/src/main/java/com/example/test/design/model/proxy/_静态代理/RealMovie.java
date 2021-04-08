package com.example.test.design.model.proxy._静态代理;

import com.example.test.design.model.proxy._静态代理.Movie;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 8:40 AM
 */

public class RealMovie implements Movie {

    @Override
    public void play() {

        System.out.println("您正在观看电影 《肖申克的救赎》");

    }
}
