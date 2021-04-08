package com.example.test.concurrent.concurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/22 3:14 PM
 */

public class ConcurrentHashMapDemo {

    public static void main(String[] args) {

        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();

        map.put("name", "rose");

        map.put("name", "jack");

        System.out.println(map);

    }

}
