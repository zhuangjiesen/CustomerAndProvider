package com.dragsun.jedis;

import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 *
 * redis 的状态监测
 * Created by zhuangjiesen on 2018/1/16.
 */
public class RedisServerState {

    /** 服务的模式 single master/slave cluster **/
    private volatile String mode;
    private volatile JedisPoolWrapper master;
    private volatile List<JedisPoolWrapper> slaves;
    private volatile List<JedisPoolWrapper> sentinel;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public JedisPoolWrapper getMaster() {
        return master;
    }

    public void setMaster(JedisPoolWrapper master) {
        this.master = master;
    }

    public List<JedisPoolWrapper> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<JedisPoolWrapper> slaves) {
        this.slaves = slaves;
    }

    public List<JedisPoolWrapper> getSentinel() {
        return sentinel;
    }

    public void setSentinel(List<JedisPoolWrapper> sentinel) {
        this.sentinel = sentinel;
    }
}
