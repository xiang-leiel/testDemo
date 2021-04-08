package com.example.test.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 
 * @author leiel
 * @Date 2020/7/2 9:05 AM
 */

public class RedisDemo {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Redisson redisson;

    /**
     * 基于redis的实现
     */
    public void test() {

        String lock = "product_001";

        String clientID = UUID.randomUUID().toString();

        try {
            //获取一把锁
            Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(lock, clientID, 10, TimeUnit.SECONDS);

            if(!aBoolean) {
                //获取不到锁
                return;
            }
            //如何解决过期时间不够用，下一个线程又获取到锁，前一个线程释放后一个线程的锁
            //解决办法，在线程获取到锁之后，开启一个线程，每隔一段时间(过期时间的1/3)来获取一次锁，获取到锁之后增加锁的过期时间
            //TODO 业务代码处理逻辑


        }finally {

            if(stringRedisTemplate.opsForValue().get(lock).equals(clientID)) {
                //释放锁
                stringRedisTemplate.delete(lock);
            }

        }

    }

    /**
     * 基于redisson实现  主从架构
     */
    public void redissonTest() {
        String lock = "product_001";

        //获取锁
        RLock redissonLock = redisson.getLock(lock);

        try{
            //设置过期时间
            redissonLock.lock(10,TimeUnit.SECONDS);

        }finally {

            //释放锁
            redissonLock.unlock();

        }

    }

}
