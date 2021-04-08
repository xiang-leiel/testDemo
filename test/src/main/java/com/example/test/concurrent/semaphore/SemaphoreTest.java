package com.example.test.concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/7 7:42 PM
 */

public class SemaphoreTest {

    public static void main(String[] args) {
        AnotherDummy ad = new AnotherDummy();

        Thread t1 = new Thread(() -> {
            try {
                ad.one(() -> {
                    System.out.println("One");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                ad.two(() -> {
                    System.out.println("Two");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                ad.three(() -> {
                    System.out.println("Three");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t2.start();
        t1.start();
        t3.start();
    }
}

class AnotherDummy {
    // The count of permits is 0, the threads will be blocked.
    // Until, the resource can be acquired.
    private Semaphore first = new Semaphore(0);
    private Semaphore second = new Semaphore(0);
    private Semaphore third = new Semaphore(0);

    public void one(Runnable runnable) throws InterruptedException {
        runnable.run();
        first.release();
        second.release();
    }

    public void two(Runnable runnable) throws InterruptedException {
        second.acquire();
        runnable.run();
        second.release();
        third.release();
    }

    public void three(Runnable runnable) throws InterruptedException {
        third.acquire();
        runnable.run();
        third.release();
    }

}
