package com.dragsun.jedis.utils;

import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 *
 * 读服务的轮训器
 *
 * Created by zhuangjiesen on 2018/1/15.
 */
public class ReadJedisPoolLoadBalancer {

    private final Collection<JedisPool> readJedisPools;
    private Iterator<JedisPool> nextReadJedisPoolIterator;

    private JedisPool singleJedisPool;

    public ReadJedisPoolLoadBalancer(Collection<JedisPool> readJedisPools) {
        if (readJedisPools.isEmpty()) {
            throw new IllegalArgumentException("At least one selector thread is required");
        }
        this.readJedisPools = Collections.unmodifiableList(new ArrayList<JedisPool>(readJedisPools));
        nextReadJedisPoolIterator = this.readJedisPools.iterator();
    }

    public JedisPool nextJedisPool() {
        if (readJedisPools.size() == 1) {
            if (singleJedisPool == null) {
                if (!nextReadJedisPoolIterator.hasNext()) {
                    nextReadJedisPoolIterator = readJedisPools.iterator();
                }
                singleJedisPool = nextReadJedisPoolIterator.next();
            }
            return singleJedisPool;
        } else {
            // Choose a selector thread (round robin)
            if (!nextReadJedisPoolIterator.hasNext()) {
                nextReadJedisPoolIterator = readJedisPools.iterator();
            }
            return nextReadJedisPoolIterator.next();
        }
    }


    /*
    * 获取读连接池
    * */
    public List<JedisPool> getJedisPools(){
        return new ArrayList<>(readJedisPools);
    }

}
