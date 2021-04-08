package com.example.test.concurrent;
/**
 * @Description 
 * @author leiel
 * @Date 2020/8/12 12:23 PM
 */

public class ThreadTest extends Thread {

    private String name;

    public ThreadTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            System.out.println(name+"线程运行--"+i);
        }

    }
}
