package com.java.core.common;

import com.java.core.properties.Config;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.ThreadFactory;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/20
 */
public class HookMain {

    public static void main(String[] args) {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        System.out.println("runtime : " + runtime.getName());
        ThreadFactory tf1 = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t1 = new Thread(r);
                t1.setName("hook-thread-1");
                return t1;
            }
        };


//        Runtime.getRuntime().addShutdownHook(tf1.newThread(new Runnable() {
//            @Override
//            public void run() {
//                //死循环后kill 命令无法关闭，只能执行kill -9
//                System.out.println("hooking...");
//                while(true) {
//
//                }
//            }
//        }));
//
//        while (true) {
//
//        }



    }


}
