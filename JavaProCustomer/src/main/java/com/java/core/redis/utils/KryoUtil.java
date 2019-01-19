package com.java.core.redis.utils;

import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.java.core.redis.SerializeMain;
import com.java.core.redis.entity.BizEvent;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/10
 */
public class KryoUtil {

    private static JSONObject result = new JSONObject();
    private static String KEY_PREFIX = "kryo_";
    private static byte[] data = null;

    public static void kryoToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        jedis.flushAll();
        result.put(SerializeMain.NAME, KEY_PREFIX);
        setToRedis(jedis, count, sourceData);
        getFromRedis(jedis, count, sourceData);
        SerializeMain.printMemroy(KEY_PREFIX, jedis, result);
        SerializeMain.list.add(result);
    }




    public static void setToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        Output output = new Output(byteArrayOutputStream);
        Kryo kryo = new Kryo();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            kryo.writeObject(output, sourceData);
            output.close();
            data = byteArrayOutputStream.toByteArray();
            jedis.set(key.getBytes(), data);
            byteArrayOutputStream.reset();
            kryo.reset();
        }
        byteArrayOutputStream.close();
        long setTime = (System.currentTimeMillis() - start);
        System.out.println("kryo redis set time : " + setTime);
        result.put(SerializeMain.SET_TIME, setTime);
    }


    public static void getFromRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        Kryo kryo = new Kryo();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            byte[] text = jedis.get(key.getBytes());
            if (text != null) {
                data = text;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            Input input = new Input(byteArrayInputStream);
            BizEvent bizEventCache = kryo.readObject(input, BizEvent.class);
            input.close();
            kryo.reset();
            byteArrayInputStream.close();
        }
        long getTime = (System.currentTimeMillis() - start);
        System.out.println("kryo redis get time : " + getTime);
        result.put(SerializeMain.GET_TIME, getTime);
    }


}
