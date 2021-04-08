package com.example.test.concurrent.wait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/16 1:48 PM
 */

public class WaitNotifyDemo {

    static List list = new ArrayList<>();


    public static void main(String[] args) {

        Object lock = new Object();

/*        ThreadA threadA = new ThreadA(lock);
        threadA.start();
        ThreadB threadB = new ThreadB(lock);
        threadB.start();*/

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2启动");
                if(list.size() != 5) {
                    try {
                        lock.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                lock.notify();
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {

            synchronized (lock) {
                System.out.println("t1启动");
                for(int i = 0; i < 10; i++) {
                    list.add(new Object());
                    System.out.println("添加"+i);

                    if(list.size() == 5) {

                        lock.notify();
                        try {
                            //释放锁
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        },"t1").start();

    }

}
