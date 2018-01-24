package com.dragsun.jedis.manager;

import com.dragsun.jedis.JedisCommandManager;
import com.dragsun.jedis.RedisServerState;
import redis.clients.jedis.Jedis;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public interface JedisManager {


    /*
    * 添加slave
    *
    * */
    public RedisServerState addSlave();


    /*
    * 重置连接
    *
    * */
    public void reset();


    /*
    * 获取连接状态
    *
    * */
    public RedisServerState getCurRedisServerState();

    /*
    * 初始化
    *
    * */
    public void init();



    /*
    * 获取连接
    * */
    public JedisCommandManager getJedis();


}
