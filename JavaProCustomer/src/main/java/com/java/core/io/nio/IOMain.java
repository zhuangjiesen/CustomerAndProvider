package com.java.core.io.nio;

import sun.nio.ch.SelectionKeyImpl;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/26
 */
public class IOMain {

    public static void main(String[] args) throws InterruptedException {



        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(" i am a hook ");

                while (true) {
                    try {

                        Thread.currentThread().sleep(3000);
                        System.out.println("wakeup...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }));



        while (true) {
            Thread.currentThread().sleep(3000);
            System.out.println("wakeup...");
        }



    }

}
