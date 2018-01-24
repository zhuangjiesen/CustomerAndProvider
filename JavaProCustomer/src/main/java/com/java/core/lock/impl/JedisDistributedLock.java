package com.java.core.lock.impl;

import com.java.core.lock.DistributedLock;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * redis 实现分布式锁
 * Created by zhuangjiesen on 2017/12/8.
 */
public class JedisDistributedLock implements DistributedLock , ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private ApplicationContext applicationContext;
    private static ThreadLocal<String> lockKeys = new ThreadLocal<>();
    private static ConcurrentHashMap<String , Object> disLocks = new ConcurrentHashMap<>();
    private JedisPool jedisPool;

    @Override
    public void lock(String key) {
        Jedis jedis = jedisPool.getResource();
        Long count = jedis.setnx( key , "1");
        if (count != null && count.longValue() > 0) {
            System.out.println("--lock--1. 获取到分布式锁.......");
            lockKeys.set(key);
        } else {
            System.out.println("--lock--2. not .......未获取到分布式锁.......");
            Object lock = null;
            if ((lock = disLocks.get(key)) == null) {
                // double check
                synchronized (disLocks) {
                    if ((lock = disLocks.get(key)) == null) {
                        lock = new Object();
                        disLocks.put(key , lock);
                    }
                }
            }
            synchronized (lock) {
                try {
                    //线程休眠
                    lock.wait();
                    System.out.println("-------------------------");
                    lock(key);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean tryLock(String key) {
        Jedis jedis = jedisPool.getResource();
        Long count = jedis.setnx( key , "1");
        if (count != null && count.longValue() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void unLock() {
        try {
            String key = lockKeys.get();
            unLock(key);
        } finally {
            lockKeys.remove();
        }
    }

    @Override
    public void unLock(String key) {
        Jedis jedis = jedisPool.getResource();
        Long del = jedis.del(key);
        Object lock = null;
        if (del != null && del.longValue() > 0 && (lock = disLocks.remove(key)) != null) {
            //解锁
            synchronized (lock) {
                lock.notifyAll();
            }
        }
        System.out.println("--unLock--3. key : " + key + " del  : " + del);
    }

    @Override
    public void scheduleTask() {
        //执行轮训死锁问题
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }


    /*
    * spring 容器加载完毕调用
    *
    * */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            jedisPool = applicationContext.getBean(JedisPool.class);
            Jedis jedis = jedisPool.getResource();
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    jedis.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            super.onMessage(channel, message);
                            Object lock = null;
                            System.out.println(" 1. channel : " + channel);
                            System.out.println(" 2. message : " + message);
                            if ("__keyevent@0__:del".equals(channel) && (lock = disLocks.remove(message)) != null) {
                                //解锁
                                synchronized (lock) {
                                    lock.notifyAll();
                                }
                            }
                        }
                    } , "__keyevent@0__:del");
                }
            });
            t1.start();
        }
    }
}
