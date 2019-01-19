package com.java.core.redis.utils;

import com.alibaba.fastjson.JSONObject;
import com.java.core.redis.SerializeMain;
import com.java.core.redis.protobuf.BizEventProtoc;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/10
 */
public class ProtobufUtil {


    private static JSONObject result = new JSONObject();
    private static String KEY_PREFIX = "protobuf_";
    private static byte[] data = null;

    public static void protobufToRedis(Jedis jedis, int count, BizEventProtoc.BizEvent sourceData) throws Exception {
        jedis.flushAll();
        result.put(SerializeMain.NAME, KEY_PREFIX);
        setToRedis(jedis, count, sourceData);
        getFromRedis(jedis, count, sourceData);
        SerializeMain.printMemroy(KEY_PREFIX, jedis, result);
        SerializeMain.list.add(result);
    }

    public static void setToRedis(Jedis jedis, int count, BizEventProtoc.BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            data = sourceData.toByteArray();
            jedis.set(key.getBytes(), data);
        }
        long setTime = (System.currentTimeMillis() - start);
        System.out.println("protobuf redis set time : " + setTime);
        result.put(SerializeMain.SET_TIME, setTime);
    }


    public static void getFromRedis(Jedis jedis, int count, BizEventProtoc.BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            byte[] text = jedis.get(key.getBytes());
            if (text != null) {
                data = text;
            }
            BizEventProtoc.BizEvent bizEventCache = BizEventProtoc.BizEvent.parseFrom(data);
        }
        long getTime = (System.currentTimeMillis() - start);
        System.out.println("protobuf redis get time : " + getTime);
        result.put(SerializeMain.GET_TIME, getTime);
    }




}
