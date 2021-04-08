package com.example.test.concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/22 11:17 AM
 */

public class SemaphoreDemo {

    static class Car extends Thread {

        private int num;

        private Semaphore semaphore;

        public Car(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {

            try {
                semaphore.acquire(); //获取令牌 如果拿不到令牌就会阻塞
                System.out.println("第"+num+"个车抢占到车位");
                Thread.sleep(1000);
                System.out.println("第"+num+"个车开走了");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(4);

        for (int i = 0; i < 10; i++) {

            new Car(i, semaphore).start();

        }

    }

}
