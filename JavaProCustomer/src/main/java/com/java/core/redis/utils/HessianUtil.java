package com.java.core.redis.utils;

import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectInput;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectOutput;
import com.alibaba.fastjson.JSONObject;
import com.java.core.redis.SerializeMain;
import com.java.core.redis.entity.BizEvent;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/10
 */
public class HessianUtil {


    private static JSONObject result = new JSONObject();
    private static String KEY_PREFIX = "hessian_";
    private static byte[] data = null;

    public static void hessianToRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
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
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            Hessian2ObjectOutput hessianOutput = new Hessian2ObjectOutput(byteArrayOutputStream);
            hessianOutput.writeObject(sourceData);
            hessianOutput.flushBuffer();
            data = byteArrayOutputStream.toByteArray();
            jedis.set(key.getBytes(), data);
            byteArrayOutputStream.reset();
        }
        byteArrayOutputStream.close();
        long setTime = (System.currentTimeMillis() - start);
        System.out.println("hessian redis set time : " + setTime);
        result.put(SerializeMain.SET_TIME, setTime);

    }


    public static void getFromRedis(Jedis jedis, int count, BizEvent sourceData) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String key = KEY_PREFIX + i;
            byte[] text = jedis.get(key.getBytes());
            if (text != null) {
                data = text;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            Hessian2ObjectInput hessianInput = new Hessian2ObjectInput(byteArrayInputStream);
            BizEvent bizEventCache = hessianInput.readObject(BizEvent.class);
            byteArrayInputStream.close();
        }
        long getTime = (System.currentTimeMillis() - start);
        System.out.println("hessian redis get time : " + getTime);
        result.put(SerializeMain.GET_TIME, getTime);
    }



}
