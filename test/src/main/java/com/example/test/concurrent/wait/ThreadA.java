package com.example.test.concurrent.wait;


/**
 * @Description 
 * @author leiel
 * @Date 2020/6/16 1:49 PM
 */

public class ThreadA extends Thread {

    /**
     * 锁对象
     */
    private Object lock;

    public ThreadA(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        synchronized (lock) {

            System.out.println("start ThreadA");

            try {
                lock.wait();  //wait会释放锁资源 并将线程A放入等待队列里
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("end ThreadA");

        }

    }

}
