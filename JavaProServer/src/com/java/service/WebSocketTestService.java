package com.java.service;

import com.java.core.netty.websocket.cache.WebSocketClientCache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhuangjiesen on 2017/8/15.
 */
public class WebSocketTestService {

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


    static {

        //测试发送消息
        System.out.println("WebSocketTestService .....start....");

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("WebSocketTestService .....定时发送消息....");


                WebSocketClientCache.sendMessageToAll("我是定时消息....");

            }
        } , 1L , 10 , TimeUnit.SECONDS);

    }


}
