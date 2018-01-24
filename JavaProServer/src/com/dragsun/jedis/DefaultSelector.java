package com.dragsun.jedis;

import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public class DefaultSelector implements ReadJedisPoolSelector {



    /*
    * 负载均衡轮训从服务
    * */
    private ReadJedisPoolLoadBalancer readJedisPoolLoadBalancer;


    @Override
    public void init(List<JedisPoolWrapper> slaves) {
        readJedisPoolLoadBalancer = new ReadJedisPoolLoadBalancer(slaves);
    }


    @Override
    public JedisPoolWrapper select() {
        synchronized (this) {
            return readJedisPoolLoadBalancer.nextJedisPool();
        }
    }
}
