package com.java.core.io.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileLock;
import java.util.WeakHashMap;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/11
 */
public class BioClient {


    public static void main(String[] args) throws Exception {

        Socket client = new Socket();
        client.setKeepAlive(true);
        client.setSendBufferSize(1024);
        client.setTcpNoDelay(true);
        client.setReuseAddress(false);
        client.setReceiveBufferSize(48);

        client.connect(new InetSocketAddress("dongjian-api-dev.netease.com", 80));
        Thread readThread = new Thread(new ReadThread(client.getInputStream()));
        Thread writeThread = new Thread(new WriteThread(client.getOutputStream()));

        readThread.setDaemon(false);
        writeThread.setDaemon(false);

        readThread.start();
        writeThread.start();

//        while(true) {}

    }


    public static class ReadThread implements Runnable {

        private InputStream inputStream;

        public ReadThread (InputStream ins) {
            this.inputStream = ins;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
                    System.out.println("尝试读取数据...");
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("读取数据: " + line);
                    }
                    Thread.currentThread().sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public static class WriteThread implements Runnable {

        private OutputStream outputStream;

        public WriteThread (OutputStream outs) {
            this.outputStream = outs;
        }


        @Override
        public void run() {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.outputStream));
                while (true) {
                    String line = "定时数据_:" + System.currentTimeMillis();
                    System.out.println(line);
                    writer.write(line);
                    writer.flush();
                    Thread.currentThread().sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
