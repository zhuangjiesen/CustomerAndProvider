package com.dragsun.jedis.proxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragsun.jedis.JedisPoolWrapper;
import com.dragsun.jedis.ReadJedisPoolSelector;
import com.dragsun.jedis.RedisServerState;
import com.dragsun.jedis.utils.RedisServerHelper;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangjiesen on 2018/1/15.
 */
public class JedisProxy implements MethodInterceptor {

    private RedisServerState redisServerState;

    private ReadJedisPoolSelector readSelector;

    private final HashSet<String> objectMethods = new HashSet<>();
    private final HashSet<String> readCommands = new HashSet<>();
    private final HashSet<String> writeCommands = new HashSet<>();

//    private volatile JedisPool writeJedisPool;
    private volatile JedisPoolWrapper writeJedisPoolWrapper;

    /** 初始化锁 **/
    private ReentrantLock lock = new ReentrantLock();
    private volatile boolean isInitSuccess = false;



    /*
    * 初始化object 与默认请求 方法名，在拦截时过滤掉 只拦截读写请求
    * */
    private void initObjectMethods () {
        Method[] methods = Object.class.getDeclaredMethods();
        for (Method method : methods) {
            objectMethods.add(method.getName());
        }
        objectMethods.add("close");
    }


    /*
    * 初始化读写请求列表
    * */
    private void initReadWriteList () {
        InputStream inputStream = null;
        ByteArrayOutputStream outs = null;
        byte[] buf = null;
        try {
            // 读取读写请求配置
            inputStream = JedisProxy.class.getResourceAsStream("command.json");
            int len = -1;
            buf = new byte[1024];
            outs = new ByteArrayOutputStream();
            while ( (len = inputStream.read(buf)) > -1) {
                outs.write(buf , 0 , len);
            }
            String commandContent = new String(outs.toByteArray() , "utf-8");
            //清空空格和换行
            commandContent = commandContent.replaceAll("\r\n" , "");
            commandContent = commandContent.replaceAll(" " , "");
            JSONObject commandListJson = JSONObject.parseObject(commandContent);
            JSONArray readList = (JSONArray) commandListJson.get("read");
            if (readList != null && readList.size() > 0) {
                for (int i = 0 ; i < readList.size() ; i ++) {
                    String readCommand = (String) readList.get(i);
                    readCommands.add(readCommand);
                }
            }
            JSONArray writeList = (JSONArray) commandListJson.get("write");
            if (writeList != null && writeList.size() > 0) {
                for (int i = 0 ; i < writeList.size() ; i ++) {
                    String writeCommand = (String) writeList.get(i);
                    writeCommands.add(writeCommand);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            buf = null;
        }

    }

    /*
    * 初始化状态
    *
    * */
    public void initJedisPool(){
        if (!isInitSuccess ) {
            lock.lock();
            try {
                //double check
                if (!isInitSuccess ) {
                    initObjectMethods();
                    initReadWriteList();

                    isInitSuccess = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public JedisProxy(RedisServerState redisServerState, ReadJedisPoolSelector readSelector) {
        this.redisServerState = redisServerState;
        this.readSelector = readSelector;
        this.writeJedisPoolWrapper = this.redisServerState.getMaster();
        if (this.redisServerState.getSlaves() != null && this.redisServerState.getSlaves().size() > 0) {
            readSelector.init(this.redisServerState.getSlaves());
        } else {
            List<JedisPoolWrapper> slaves = new ArrayList<>(1);
            slaves.add(this.redisServerState.getMaster());
            readSelector.init(slaves);
        }
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String methodName = method.getName();
        //拦截默认请求
        if (objectMethods.contains(methodName)) {
            return methodProxy.invokeSuper(o , objects);
        }
        initJedisPool();
        System.out.println(" methodName : " + methodName);
        Jedis jedis = null;
        Object result = null;
        JedisPoolWrapper wrapper = null;
        try {

            if (isReadCommand(methodName)) {
                wrapper = readSelector.select();
            } else if (isWriteCommand(methodName)) {
                wrapper = this.writeJedisPoolWrapper;
            } else {
                wrapper = this.writeJedisPoolWrapper;
            }

            jedis = wrapper.getJedisPool().getResource();
            RedisServerHelper.logRedisInfo(jedis);
            result = method.invoke(jedis , objects);
        } catch (InvocationTargetException e ) {
            Throwable target = e.getTargetException();
            //连接出问题
            if (target instanceof JedisConnectionException) {
                //可能是主服务挂
                //TODO 异常处理
                RedisServerHelper.logRedisInfo(wrapper);
            }
            System.out.println(" ========== exception : " + target.getClass().getName());
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        System.out.println(" === CommonJedisProxy === intercept() ...... result ... : " + result);
        return result;
    }


    private boolean isReadCommand(String methodName) {
        return readCommands.contains(methodName);
    }

    private boolean isWriteCommand(String methodName) {
        return writeCommands.contains(methodName);
    }


    public RedisServerState getRedisServerState() {
        return redisServerState;
    }

    public void setRedisServerState(RedisServerState redisServerState) {
        this.redisServerState = redisServerState;
    }


    public ReadJedisPoolSelector getReadSelector() {
        return readSelector;
    }

    public void setReadSelector(ReadJedisPoolSelector readSelector) {
        this.readSelector = readSelector;
    }


    public JedisPoolWrapper getWriteJedisPoolWrapper() {
        return writeJedisPoolWrapper;
    }

    public void setWriteJedisPoolWrapper(JedisPoolWrapper writeJedisPoolWrapper) {
        this.writeJedisPoolWrapper = writeJedisPoolWrapper;
    }
}
