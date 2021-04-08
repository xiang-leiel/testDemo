package com.example.test.concurrent.thread;

import com.example.test.concurrent.ThreadTest;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/12 12:12 PM
 */

public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {

        ThreadTest t1 = new ThreadTest("t1");

        ThreadTest t2 = new ThreadTest("t2");

        ThreadTest t3 = new ThreadTest("t3");

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();

    }

}
