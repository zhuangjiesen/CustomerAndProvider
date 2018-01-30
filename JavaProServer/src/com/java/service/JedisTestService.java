package com.java.service;


import com.dragsun.jedis.JedisCommandManager;
import com.dragsun.jedis.manager.JedisManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.Jedis;

/**
 * Created by zhuangjiesen on 2017/8/15.
 */
public class JedisTestService implements InitializingBean , ApplicationContextAware{


    private static ApplicationContext applicationContext;





    public String getString(String key) {
        JedisCommandManager jedis = applicationContext.getBean(JedisManager.class).getJedis();
        String result = jedis.get(key);
        System.out.println(" result : " + result);
        return result;
    }

    public String setString(String key ,String value) {
        JedisCommandManager jedis = applicationContext.getBean(JedisManager.class).getJedis();
        String result = jedis.set(key , value);
        System.out.println(" result : " + result);
        return result;
    }


    public Long incr(String key ) {
        JedisCommandManager jedis = applicationContext.getBean(JedisManager.class).getJedis();
        Long result = jedis.incr(key);
        System.out.println(" result : " + result);
        return result;
    }




    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
