package com.java.core.redis.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
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
public class GsonUtil {

    private static JSONObject result = new JSONObject();
    private static String KEY_PREFIX = "gson_";
    private static String data = null;


    public static void gsonToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        jedis.flushAll();
        result.put(SerializeMain.NAME, KEY_PREFIX);
        setToRedis(jedis, count, sourceData);
        getFromRedis(jedis, count, sourceData);

        SerializeMain.printMemroy(KEY_PREFIX, jedis, result);
        SerializeMain.list.add(result);
    }




    public static void setToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        Gson gson = new Gson();
        for (int i = 0; i < count; i++) {
            String text = gson.toJson(sourceData);
            data = text;
            String key = KEY_PREFIX + i;
            jedis.set(key, text);
        }
        long setTime = (System.currentTimeMillis() - start);
        System.out.println("gson redis set time : " + setTime);
        result.put(SerializeMain.SET_TIME, setTime);
    }


    public static void getFromRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        AtomicBoolean hasPrint = new AtomicBoolean(false);
        Gson gson = new Gson();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            String text = jedis.get(key);
            if (text != null) {
                data = text;
            }
            BizEvent bizEventCache = gson.fromJson(data, BizEvent.class);
        }
        long getTime = (System.currentTimeMillis() - start);
        System.out.println("gson redis get time : " + getTime);
        result.put(SerializeMain.GET_TIME, getTime);
    }



}
