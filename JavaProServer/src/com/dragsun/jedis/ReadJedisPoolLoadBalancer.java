package com.dragsun.jedis;

import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 *
 * 读服务的轮训器
 *
 * Created by zhuangjiesen on 2018/1/15.
 */
public class ReadJedisPoolLoadBalancer {

    private final Collection<JedisPoolWrapper> readJedisPoolWrappers;
    private Iterator<JedisPoolWrapper> nextReadJedisPoolWrapperIterator;

    private JedisPoolWrapper singleJedisPoolWrapper;

    public ReadJedisPoolLoadBalancer(Collection<JedisPoolWrapper> readJedisPoolWrappers) {
        if (readJedisPoolWrappers.isEmpty()) {
            throw new IllegalArgumentException("At least one selector thread is required");
        }
        this.readJedisPoolWrappers = Collections.unmodifiableList(new ArrayList<JedisPoolWrapper>(readJedisPoolWrappers));
        nextReadJedisPoolWrapperIterator = this.readJedisPoolWrappers.iterator();
    }

    public JedisPoolWrapper nextJedisPool() {
        if (readJedisPoolWrappers.size() == 1) {
            if (singleJedisPoolWrapper == null) {
                if (!nextReadJedisPoolWrapperIterator.hasNext()) {
                    nextReadJedisPoolWrapperIterator = this.readJedisPoolWrappers.iterator();
                }
                singleJedisPoolWrapper = nextReadJedisPoolWrapperIterator.next();
            }
            return singleJedisPoolWrapper;
        } else {
            // Choose a selector thread (round robin)
            if (!nextReadJedisPoolWrapperIterator.hasNext()) {
                nextReadJedisPoolWrapperIterator = this.readJedisPoolWrappers.iterator();
            }
            return nextReadJedisPoolWrapperIterator.next();
        }
    }


    /*
    * 获取读连接池
    * */
    public List<JedisPoolWrapper> getJedisPools(){
        return new ArrayList<>(readJedisPoolWrappers);
    }

}
