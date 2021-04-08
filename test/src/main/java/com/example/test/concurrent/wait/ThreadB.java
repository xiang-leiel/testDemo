package com.example.test.concurrent.wait;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/16 1:51 PM
 */

public class ThreadB extends Thread{

    /**
     * 锁对象
     */
    private Object lock;

    public ThreadB(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        synchronized (lock) {

            System.out.println("start ThreadB");

            lock.notify(); //notify会唤醒等待队列的一个线程，并放入到同步队列，
                           // 而notifyAll将等待队列的所有线程唤醒至同步队列
            System.out.println("end ThreadB");

        }

    }
}
