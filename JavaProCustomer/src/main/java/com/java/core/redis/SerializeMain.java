package com.java.core.redis;

import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectInput;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectOutput;
import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.java.core.redis.customer.AppJedisPool;
import com.java.core.redis.entity.BizEvent;
import com.java.core.redis.protobuf.BizEventProtoc;
import com.java.core.redis.thrift.BizEventThrift;
import com.java.core.redis.utils.*;
import com.java.core.rpc.thrift.supports.utils.ThriftUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 *
 * 序列化
 * @Date: Created in 2018/12/6
 */
public class SerializeMain {


    public static String NAME = "name";
    public static String SET_TIME = "SET_TIME";
    public static String GET_TIME = "GET_TIME";
    public static String USED_MEMORY_HUMAN = "USED_MEMORY_HUMAN";
    public static List<JSONObject> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        String hostname = "127.0.0.1";
        int port = 6379;
        String password = "redis";
        AppJedisPool jedisPool = new AppJedisPool(poolConfig, hostname, port, 30 * 1000, password, 0);
//        JedisPool jedisPool = new JedisPool(poolConfig, hostname, port, 30 * 1000, password, 0);
        Jedis jedis = jedisPool.getResource();
//        MyJedis jedis = (MyJedis) jedisPool.getResource();
//        int count = 1;
        int count = 1000 * 1000;
        BizEvent bizEvent = newBizEvent();
        BizEventThrift bizEventThrift = newBizEventThrift();
        BizEventProtoc.BizEvent bizEventProtoc = newBizEventProtobuf();

        OriginUtil.originToRedis(jedis, count, bizEvent);

        KryoUtil.kryoToRedis(jedis, count, bizEvent);

        GsonUtil.gsonToRedis(jedis, count, bizEvent);

        FastjsonUtil.fastjsonToRedis(jedis, count, bizEvent);

        JacksonUtil.jacksonToRedis(jedis, count, bizEvent);

        HessianUtil.hessianToRedis(jedis, count, bizEvent);

        ThriftUtil.thriftToRedis(jedis, count, bizEventThrift);

        ProtobufUtil.protobufToRedis(jedis, count, bizEventProtoc);

        jedis.flushAll();


        System.out.println();
        System.out.println();
        System.out.println();
        for (JSONObject json : list) {
            String text = String.format("%s , setTime : %d , getTime : %d , memory : %s " ,
                    json.getString(NAME), json.getLongValue(SET_TIME), json.getLongValue(GET_TIME), json.getString(USED_MEMORY_HUMAN));
            System.out.println(text);
        }
    }


    public static void printMemroy(String key, Jedis jedis, JSONObject result) {
        String memoryInfo = jedis.info("memory");
        JSONObject memory = parseInfo(memoryInfo);
        String humanMemory = memory.getString("used_memory_human");
        System.out.println(String.format("%s used_memory_human:%s", key, humanMemory));
        result.put(USED_MEMORY_HUMAN, humanMemory);
    }


    private static JSONObject parseInfo(String source) {
        Map<String, Object> props = new HashMap<>();
        String[] propsArr = source.split("\\r\\n");
        for (String line : propsArr) {
            if (line.contains(":")) {
                String[] lineItem = line.split(":");
                props.put(lineItem[0], lineItem[1]);
            }
        }
        return new JSONObject(props);
    }


    private static BizEvent newBizEvent(){
        BizEvent bizEvent = new BizEvent();
        bizEvent.setId(1L);
        bizEvent.setType(1);
        bizEvent.setCoverType(1);
        bizEvent.setCoverObj(RandomStringUtils.random(20));
        bizEvent.setAndroidVersion(RandomStringUtils.random(10));
        bizEvent.setCoverResourceList(RandomStringUtils.random(20));
        bizEvent.setCreateTime(new Date());
        bizEvent.setCategory(1);
        bizEvent.setDescription(RandomStringUtils.random(100));
        bizEvent.setDirection(3);
        bizEvent.setHasAndroid(1);
        bizEvent.setHasIOS(2);
        bizEvent.setInstId(29L);
        bizEvent.setAuthorInfo(RandomStringUtils.random(20));
        bizEvent.setInstSearchStatus(17);
        bizEvent.setListStatus(2);
        bizEvent.setName("redis序列化测试对象");
        bizEvent.setSequence(20);
        bizEvent.setTitle(RandomStringUtils.random(10));
        bizEvent.setPlatformType(77);
        bizEvent.setTeam(RandomStringUtils.random(20));
        return bizEvent;
    }



    private static BizEventThrift newBizEventThrift(){
        BizEventThrift bizEvent = new BizEventThrift();
        bizEvent.setId(1L);
        bizEvent.setType(1);
        bizEvent.setCoverType(1);
        bizEvent.setCoverObj(RandomStringUtils.random(20));
        bizEvent.setAndroidVersion(RandomStringUtils.random(10));
        bizEvent.setCoverResourceList(RandomStringUtils.random(20));
        bizEvent.setDate(RandomStringUtils.random(20));
        bizEvent.setCategory(1);
        bizEvent.setDescription(RandomStringUtils.random(100));
        bizEvent.setDirection(3);
        bizEvent.setHasAndroid(1);
        bizEvent.setHasIOS(2);
        bizEvent.setInstId(29L);
        bizEvent.setAuthorInfo(RandomStringUtils.random(20));
        bizEvent.setInstSearchStatus(17);
        bizEvent.setListStatus(2);
        bizEvent.setName("redis序列化测试对象");
        bizEvent.setSequence(20);
        bizEvent.setTitle(RandomStringUtils.random(10));
        bizEvent.setPlatformType(77);
        bizEvent.setTeam(RandomStringUtils.random(20));
        return bizEvent;
    }




    private static BizEventProtoc.BizEvent newBizEventProtobuf(){
        BizEventProtoc.BizEvent.Builder bizEvent = new BizEventProtoc.BizEvent.Builder();
        bizEvent.setId(1L);
        bizEvent.setType(1);
        bizEvent.setCoverType(1);
        bizEvent.setCoverObj(RandomStringUtils.random(20));
        bizEvent.setAndroidVersion(RandomStringUtils.random(10));
        bizEvent.setCoverResourceList(RandomStringUtils.random(20));
        bizEvent.setDate(RandomStringUtils.random(20));
        bizEvent.setCategory(1);
        bizEvent.setDescription(RandomStringUtils.random(100));
        bizEvent.setDirection(3);
        bizEvent.setHasAndroid(1);
        bizEvent.setHasIOS(2);
        bizEvent.setInstId(29L);
        bizEvent.setAuthorInfo(RandomStringUtils.random(20));
        bizEvent.setInstSearchStatus(17);
        bizEvent.setListStatus(2);
        bizEvent.setName("redis序列化测试对象");
        bizEvent.setSequence(20);
        bizEvent.setTitle(RandomStringUtils.random(10));
        bizEvent.setPlatformType(77);
        bizEvent.setTeam(RandomStringUtils.random(20));
        return bizEvent.build();
    }


    private interface Callback{

        public void handle(Jedis jedis);

    }

}
