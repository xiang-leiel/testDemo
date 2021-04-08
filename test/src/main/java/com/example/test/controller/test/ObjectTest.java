package com.example.test.controller.test;

import com.example.test.controller.test.User;
import org.junit.Test;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/19 1:52 PM
 */

public class ObjectTest {

    @Test
    public void testObjectFactory() {

        User user = new User();
        user.setName("项磊");
        user.setUid("001");

        CarVerifyServiceImpl carVerifyService = new CarVerifyServiceImpl();
        carVerifyService.verify(user);

        ThreadA threadA = new ThreadA();

        ThreadB threadB = new ThreadB();

        threadA.start();
        threadB.start();

    }

    class ThreadA extends Thread{

        @Override
        public void run() {

            User user = new User();
            user.setName("项磊");
            user.setUid("001");

            CarVerifyServiceImpl carVerifyService = new CarVerifyServiceImpl();
            carVerifyService.verify(user);
        }
    }

    class ThreadB extends Thread{

        @Override
        public void run() {
            User user = new User();
            user.setName("后天");
            user.setUid("002");
            CarVerifyServiceImpl carVerifyService = new CarVerifyServiceImpl();
            carVerifyService.verify(user);
        }
    }

}
