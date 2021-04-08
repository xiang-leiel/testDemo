package com.example.test.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/17 11:19 AM
 */

public class ReentrantLockDemo {

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        //获取锁
        lock.lock();

        //释放锁
        lock.unlock();
    }

}
