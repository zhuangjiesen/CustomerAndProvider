package com.java.core.redis.cluster;

import com.java.core.lock.impl.JedisDistributedLock;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2019/1/10
 */
public class RedisClusterMain {

    public static void main(String[] args) {



        Set<HostAndPort> nodes = new LinkedHashSet<>();
        nodes.add(new HostAndPort("" , 6101));
        nodes.add(new HostAndPort("" , 6102));
        nodes.add(new HostAndPort("" , 6103));

        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        JedisCluster jedis = new JedisCluster(nodes, jedisPoolConfig);
        String result = null;
        result = jedis.get("cc-test-key");
//        result = jedis.set("test-afs-redis-cluster", "hello");
        System.out.println("result : " + result);



    }

}
