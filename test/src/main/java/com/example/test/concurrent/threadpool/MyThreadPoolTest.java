package com.example.test.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/28 8:33 PM
 */
@Slf4j
public class MyThreadPoolTest {

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(2,
                5, 10, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(5), new MyThreadFactory());

//        AtomicInteger index = new AtomicInteger(0);
//        for (int i = 0; i < 10; i++) {
//            pool.submit(() -> {
//
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("index: {}", index.incrementAndGet());
//            });
//        }

        MyThread myThread = new MyThread();

        Future<?> future = pool.submit(myThread);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
