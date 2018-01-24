package com.dragsun.jedis.manager;

import com.dragsun.jedis.*;
import com.dragsun.jedis.proxy.JedisProxy;
import org.springframework.cglib.proxy.Enhancer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public class CommonJedisManager extends AbstractJedisManager {
    private volatile JedisCommandManager jedisCommandManager;
    private volatile JedisProxy jedisProxy;

    /** 读服务选择器 **/
    private ReadJedisPoolSelector readSelector = new DefaultSelector();


    //线程安全
    private Object lock = new Object();
    private volatile boolean initFlag = true;


    @Override
    protected JedisCommandManager doGetJedis() {
        if (!initFlag) {
            synchronized (lock) {
            }
        }
        return jedisCommandManager;
    }


    @Override
    public void init() {
        super.init();
        // 创建代理类
        JedisProxy proxy = buildJedisProxy(this.redisServerState , this.readSelector);
        this.jedisCommandManager = buildJedisCommandManager(proxy);
        this.jedisProxy = proxy;

    }


    public JedisProxy buildJedisProxy(RedisServerState serverState , ReadJedisPoolSelector selector){
        // 创建代理类
        JedisProxy proxy = new JedisProxy(serverState , selector );
        proxy.initJedisPool();
        return proxy;
    }

    public JedisCommandManager buildJedisCommandManager(JedisProxy proxy){
        //初始化动态代理 拦截器
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(proxy);
        enhancer.setSuperclass(JedisCommandManager.class);
        JedisCommandManager mJedisCommandManager = (JedisCommandManager)enhancer.create();
        return mJedisCommandManager;
    }


    @Override
    public synchronized RedisServerState addSlave() {
        synchronized (lock) {
            initFlag = false;
            RedisServerState newServerState = null;
            try {
                //原子操作替换代理拦截器实现无锁替换
                newServerState = super.addSlave();
                // 重新创建代理类
                JedisProxy proxy = buildJedisProxy( newServerState  , this.readSelector);
                JedisCommandManager newJedisCommandManager = buildJedisCommandManager(proxy);

                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                Unsafe unsafe = (Unsafe) unsafeField.get(null);

                //更新属性
                Field jedisCommandManagerField = CommonJedisManager.class.getDeclaredField("jedisCommandManager");
                Field redisServerStateField = AbstractJedisManager.class.getDeclaredField("redisServerState");
                long addrManagerField = unsafe.objectFieldOffset(jedisCommandManagerField);
                long addrStateField = unsafe.objectFieldOffset(redisServerStateField);

                unsafe.putObjectVolatile(this , addrManagerField ,newJedisCommandManager );
                unsafe.putObjectVolatile(this , addrStateField ,newServerState );
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                initFlag = true;
            }
            return newServerState;
        }
    }

    @Override
    public synchronized RedisServerState getCurRedisServerState() {
        return super.getCurRedisServerState();
    }
}
