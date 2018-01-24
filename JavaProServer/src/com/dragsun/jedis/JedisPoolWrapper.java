package com.dragsun.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * JedisPool 封装类
 * 封装连接池的信息
 *
 * Created by zhuangjiesen on 2018/1/16.
 */
public class JedisPoolWrapper {

    private JedisPool jedisPool;
    private GenericObjectPoolConfig poolConfig;
    private String role;
    private String mode;
    /** 地址，非127.0.0.1 **/
    private String host;
    private Integer port;
    private String password;

    /** 如果有从服务 **/
    private List<JedisPoolWrapper> slaveJedisPoolWrappers;
    private List<JedisPoolWrapper> sentinelJedisPoolWrappers;


    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<JedisPoolWrapper> getSlaveJedisPoolWrappers() {
        return slaveJedisPoolWrappers;
    }

    public void setSlaveJedisPoolWrappers(List<JedisPoolWrapper> slaveJedisPoolWrappers) {
        this.slaveJedisPoolWrappers = slaveJedisPoolWrappers;
    }

    public List<JedisPoolWrapper> getSentinelJedisPoolWrappers() {
        return sentinelJedisPoolWrappers;
    }

    public void setSentinelJedisPoolWrappers(List<JedisPoolWrapper> sentinelJedisPoolWrappers) {
        this.sentinelJedisPoolWrappers = sentinelJedisPoolWrappers;
    }

    public GenericObjectPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}
