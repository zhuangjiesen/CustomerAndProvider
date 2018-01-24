package com.java.main;

import com.java.helper.ThreadHelper;
import com.java.service.DistributedLocksService;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * Created by zhuangjiesen on 2017/6/3.
 */
public class DistributedLocksApp {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringInit.init();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                DistributedLocksService distributedLocksService = applicationContext.getBean(DistributedLocksService.class);
                distributedLocksService.robResource("distributedLocksService" , Thread.currentThread());
            }
        });
        t1.start();
        ThreadHelper.sleep(400);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                DistributedLocksService distributedLocksService = applicationContext.getBean(DistributedLocksService.class);
                distributedLocksService.robResource("distributedLocksService" , Thread.currentThread());
            }
        });
        t2.start();
        ThreadHelper.sleep(400);
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                DistributedLocksService distributedLocksService = applicationContext.getBean(DistributedLocksService.class);
                distributedLocksService.robResource("distributedLocksService" , Thread.currentThread());
            }
        });
        t3.start();


    }

}
