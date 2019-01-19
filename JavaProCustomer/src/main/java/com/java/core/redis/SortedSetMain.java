package com.java.core.redis;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/11
 */
public class SortedSetMain {

    private static SortedSet sortedSet = new TreeSet();

    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        String hostname = "127.0.0.1";
        int port = 6379;
        String password = "redis";
//        AppJedisPool jedisPool = new AppJedisPool(poolConfig, hostname, port, 30 * 1000, password, 0);
        JedisPool jedisPool = new JedisPool(poolConfig, hostname, port, 30 * 1000, password, 0);
        Jedis jedis = jedisPool.getResource();
        jedis.flushAll();
        int count = 200 * 1000;
        String sortedKey = "sortedKey_";
        for (int i = 0 ; i < count ; i++) {
            String sortedValue = "sortedValue_" + i + "_tmp:" + RandomStringUtils.random(3000);
            long start = System.currentTimeMillis();

            int score = RandomUtils.nextInt(30 * 1000);
            jedis.zadd(sortedKey, score, sortedValue);
            long time = System.currentTimeMillis() - start;
            if (time > 10) {
                System.out.println("time : " + time);
            }
        }



    }

}
