package com.dragsun.jedis;

import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public interface ReadJedisPoolSelector {

    public void init(List<JedisPoolWrapper> slaves);

    public JedisPoolWrapper select();

}
