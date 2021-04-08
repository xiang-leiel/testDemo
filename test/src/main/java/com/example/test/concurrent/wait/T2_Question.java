package com.example.test.concurrent.wait;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/12 10:47 PM
 */

public class T2_Question {

    /**
     * 输出A1B2C3
     * @param args
     */
    public static void main(String[] args) {

        String[] strs = {"A","B","C","D"};

        Object lock = new Object();

        new Thread(() -> {

            synchronized (lock) {

                for(String str : strs) {
                    System.out.println(str);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                }

            }

        },"t1").start();

        new Thread(() -> {

            synchronized (lock) {
                for(int i = 1; i <= 4; i++) {

                    System.out.println(i);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        },"t1").start();


    }

}
