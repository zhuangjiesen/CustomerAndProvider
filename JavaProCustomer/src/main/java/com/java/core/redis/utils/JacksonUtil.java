package com.java.core.redis.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.core.redis.SerializeMain;
import com.java.core.redis.entity.BizEvent;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/10
 */
public class JacksonUtil {



    private static JSONObject result = new JSONObject();
    private static String KEY_PREFIX = "jackson_";
    private static String data = null;

    public static void jacksonToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        jedis.flushAll();
        result.put(SerializeMain.NAME, KEY_PREFIX);
        setToRedis(jedis, count, sourceData);
        getFromRedis(jedis, count, sourceData);
        SerializeMain.printMemroy(KEY_PREFIX, jedis, result);
        SerializeMain.list.add(result);
    }



    public static void setToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String text = objectMapper.writeValueAsString(sourceData);
            data = text;
            String key = KEY_PREFIX + i;
            jedis.set(key, text);
        }
        long setTime = (System.currentTimeMillis() - start);
        System.out.println("jackson redis set time : " + setTime);
        result.put(SerializeMain.SET_TIME, setTime);
    }


    public static void getFromRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            String text = jedis.get(key);
            if (text != null) {
                data = text;
            }
            BizEvent bizEventCache = objectMapper.readValue(data, BizEvent.class);
        }
        long getTime = (System.currentTimeMillis() - start);
        System.out.println("jackson redis get time : " + getTime);
        result.put(SerializeMain.GET_TIME, getTime);
    }



}
