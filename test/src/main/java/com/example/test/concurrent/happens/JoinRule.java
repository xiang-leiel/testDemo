package com.example.test.concurrent.happens;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/17 8:40 AM
 */

public class JoinRule {

    static int x = 0;

    public static void main(String[] args) throws InterruptedException {

/*        Thread thread = new Thread(()->{
            x=100;
        });

        thread.start();
        thread.join();

        System.out.println(x);*/

        //线程顺序访问
        Thread t1 = new Thread(()->{
            System.out.println("t1线程");
        });
        Thread t2 = new Thread(()->{
            System.out.println("t2线程");
        });
        Thread t3 = new Thread(()->{
            System.out.println("t3线程");
        });

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        

    }

}
