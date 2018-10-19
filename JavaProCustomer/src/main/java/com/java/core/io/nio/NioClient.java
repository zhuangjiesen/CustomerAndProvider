package com.java.core.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/17
 */
public class NioClient {


    public static void main(String[] args) throws Exception {
        //创建客户端socket连接
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 38888));
        socketChannel.configureBlocking(false);

        //读线程管理
        ReadThread readThread = new ReadThread();
        Thread t1 = new Thread(readThread);
        t1.setDaemon(false);
        t1.start();
        readThread.registRead(socketChannel);


        //模拟定时发送消息
        while (true) {
            String message = "nio_client_messag_" + System.currentTimeMillis();
            //写消息
            ByteBuffer buf = ByteBuffer.allocate(128);
            System.out.println("send : " + message);
            buf.put(message.getBytes());
            buf.flip();
            while(buf.hasRemaining()) {
                socketChannel.write(buf);
            }

            Thread.currentThread().sleep(3000);
        }
    }



    public static class ReadThread implements Runnable {

        private Selector readSelector;

        public ReadThread() {
            try {
                this.readSelector = Selector.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void registRead(SocketChannel socketChannel) {
            try {
                this.readSelector.wakeup();
                socketChannel.register(this.readSelector,  SelectionKey.OP_READ);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 读操作
         *
         * @author zhuangjiesen
         * @date 2018/10/11 下午2:41
         * @param
         * @return
         */
        private void doRead(SelectionKey key) throws Exception {
            //读取socketChannel 的数据
            SocketChannel socketChannel = (SocketChannel) key.channel();
            String message = NioServer.readChannelData(socketChannel);
            System.out.println("读事件:内容: " + message);
        }






        @Override
        public void run() {
            try {
                while (true) {
                    int sec = readSelector.select();
                    if (sec > 0) {

                        // accept 事件触发
                        Set<SelectionKey> selectedKeys = readSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                        while(keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            //获取完需要清除，不然会循环取出自己的事件
                            keyIterator.remove();

                            // skip if not valid
                            if (!key.isValid()) {
                                continue;
                            }
                            if(key.isReadable()) {
                                // a connection was accepted by a ServerSocketChannel.
                                System.out.println(" an READ event reach ....");
                                doRead(key);
                            }
                        }
                    } else {
                        Thread.currentThread().sleep(3000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
