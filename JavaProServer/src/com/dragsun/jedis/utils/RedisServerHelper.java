package com.dragsun.jedis.utils;

import com.dragsun.jedis.*;
import com.dragsun.jedis.constant.RedisConstant;
import com.dragsun.jedis.constant.RedisMode;
import com.dragsun.jedis.constant.RedisRole;
import com.dragsun.jedis.manager.AbstractJedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * redis 的状态检查
 * Created by zhuangjiesen on 2018/1/16.
 */
public class RedisServerHelper {



    public static String logRedisInfo(JedisPoolWrapper wrapper ){
        String result = null;

        String host = wrapper.getHost();
        if (RedisConstant.LOCALHOST.equals(host)) {
            host = AbstractJedisManager.LOCALHOST_ALIAS;
        }
        Integer port = wrapper.getPort();
        String role = wrapper.getRole();
        result = " =========== redis getted : " + host + " : " + port + "  role : " + role;
        System.out.println(result);
        return result;
    }

    public static String logRedisInfo(Jedis jedis ){
        String result = null;

        String host = jedis.getClient().getHost();
        if (RedisConstant.LOCALHOST.equals(host)) {
            host = AbstractJedisManager.LOCALHOST_ALIAS;
        }
        Integer port = jedis.getClient().getPort();
        result = " =========== redis getted : " + host + " : " + port;
        System.out.println(result);
        return result;
    }

    public static String getPassword(Jedis jedis ){
        List<String> passwords = jedis.configGet("requirepass");
        String password = null;
        if (passwords != null && passwords.size() > 1) {
            password = passwords.get(1);
        }
        return password;
    }


    public static RedisMode getRedisMode(Jedis master){
        String replicationInfo = master.info("Replication");
        Map<String, String> replicationInfoMap = RedisParser.parseInfoServer(replicationInfo);
        if (!replicationInfoMap.get("role").equals("master")) {
            throw new RuntimeException(" getRedisMode needs master ");
        } else {
            Integer connectedSlaves = Integer.valueOf(replicationInfoMap.get("connected_slaves"));
            if (connectedSlaves.intValue() == 0) {
                return RedisMode.SINGLE;
            } else if (connectedSlaves.intValue() > 0) {
                return RedisMode.MASTERSLAVE;
            } else {
                return null;
            }
        }
    }





}
