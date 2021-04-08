package com.example.test.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @author leiel
 * @Date 2020/6/22 10:42 AM
 */

public class CountDownLatchTest extends Thread{

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 做并发测试，可以用countdownlatch来做
     */
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            new CountDownLatchTest().start();

        }

        countDownLatch.countDown();
    }

    @Override
    public void run() {

        try {

            countDownLatch.await(); //阻塞

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName());

    }
    /*    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 小明来吃饭了");
        }).start();

        new Thread(()->{
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 小红来吃饭了");
        }).start();

        new Thread(()->{
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 老王也来了");
        }).start();

        countDownLatch.await();

        Thread.sleep(1000);
        System.out.println("开始吃饭啦");

    }*/

}
