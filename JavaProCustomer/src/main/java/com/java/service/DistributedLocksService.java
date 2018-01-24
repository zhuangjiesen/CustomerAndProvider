package com.java.service;

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
 * Created by zhuangjiesen on 2017/12/8.
 */
public class DistributedLocksService {
    private DistributedLock distributedLock;
    /*
    *
    * 抢占资源
    * 涉及竞争
    *
    * */
    public void robResource(String key , Object params ) {
        Thread thread = (Thread)params;
        try {
            System.out.println("1. ------准备获取锁-------- : " + thread.getId());
            distributedLock.lock(key);
            System.out.println("2. ------成功获取锁-------- : " + thread.getId());
        } finally {
            distributedLock.unLock();
            System.out.println("3. ------成功释放锁-------- : " + thread.getId());
        }

    }
    public DistributedLock getDistributedLock() {
        return distributedLock;
    }
    public void setDistributedLock(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }
}
