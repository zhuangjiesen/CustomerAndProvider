package com.java.core.io.nio;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/25
 */
public class MuiltiBioClient {

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t1 start...");
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("127.0.0.1", 3333));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t1.start();



        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t2 start...");
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("127.0.0.1", 3333));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t2.start();



        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t3 start...");
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("127.0.0.1", 3333));

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
        t3.start();

        while (true) {}
    }

}
