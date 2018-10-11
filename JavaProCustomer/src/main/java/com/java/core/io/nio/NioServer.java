package com.java.core.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 *
 * nio服务端实现
 *
 *
 * @Date: Created in 2018/10/11
 */
public class NioServer {

    private static ReadWriteThread readWriteThread = new ReadWriteThread();

    private static ServerSocketChannel serverSocketChannel = null;

    public static void main(String[] args) throws Exception {
        //开启读写监听线程
        Thread t1 = new Thread(readWriteThread);
        t1.start();

        //开启服务端监听
        ExecutorService serverService = Executors.newSingleThreadExecutor();
        serverService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    serverSocketChannel = ServerSocketChannel.open();
                    //创建Selector
                    Selector acceptor = Selector.open();
                    serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 38888));

                    serverSocketChannel.configureBlocking(false);
                    //注册前 serverSocketChannel 必须设置成非阻塞
                    serverSocketChannel.register(acceptor, SelectionKey.OP_ACCEPT);

                    System.out.println(" server start ....");
                    while (true) {
                        int sel = acceptor.select();
                        if (sel > 0) {
                            // accept 事件触发
                            Set<SelectionKey> selectedKeys = acceptor.selectedKeys();
                            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                            while(keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();
                                //获取完需要清除，不然会循环取出自己的事件
                                keyIterator.remove();

                                // skip if not valid
                                if (!key.isValid()) {
                                    continue;
                                }

                                if(key.isAcceptable()) {
                                    // a connection was accepted by a ServerSocketChannel.
                                    System.out.println(" an accept event reach ....");
                                    //处理accept 事件，取出客户端请求的SocketChannel对象，绑定到新的selector中监听读写事件
                                    doAccept();
                                }
                            }

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });





    }


    public static void doAccept() throws Exception{
        //获取客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        //这里测试 socketChannel.read 操作是不阻塞的，直接返回
        /*
        socketChannel.configureBlocking(false);
        String message = null;
        message = NioServer.readChannelData(socketChannel);
        System.out.println("message1 : " + message);
        message = NioServer.readChannelData(socketChannel);
        System.out.println("message2 : " + message);
        message = NioServer.readChannelData(socketChannel);
        System.out.println("message3 : " + message);
        message = NioServer.readChannelData(socketChannel);
        System.out.println("message4 : " + message);


        * */

        //注册读写事件
        registReadWriter(socketChannel);
    }


    public static void registReadWriter(SocketChannel socketChannel) throws Exception{
        System.out.println(" doreading ..... ");
        //select先wakeup才能register
        readWriteThread.wakeUpSelect();
        //注册到读写selector
        readWriteThread.registerSocketChannel(socketChannel);
    }



    /**
     * 读写监听线程
     *
     * @author zhuangjiesen
     * @date 2018/10/11 下午5:27
     * @param
     * @return
     */
    public static class ReadWriteThread implements Runnable {
        /** **/
        private static Selector selector;
        private static AtomicBoolean isRegist = new AtomicBoolean(false);

        public void registerSocketChannel(SocketChannel socketChannel) {
            try {
                socketChannel.configureBlocking(false);
                socketChannel.register(selector , SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void wakeUpSelect() {
            try {
                //唤醒selector
                selector.wakeup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ReadWriteThread() {
            try {
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    //调用即阻塞
                    int sel = selector.select();
                    if (sel > 0) {
                        System.out.println("ReadWriteThread 有读写事件产生！");
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                        while(keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            keyIterator.remove();
                            // skip if not valid
                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                //处理读事件
                                this.doRead(key);
                            } else if(key.isWritable()) {
                                //处理写事件
                                this.doWrite(key);
                            } else {
                                System.out.println("没有事件匹配！");
                            }
                        }
                    } else {
                        //可以用别的方法实现
                        Thread.currentThread().sleep(300);
                    }

                }
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
            //将数据存到 SelectionKey 中，在write事件时可以返回
            key.attach(message);
            //触发写事件 OP_WRITE
            key.interestOps(SelectionKey.OP_WRITE);
        }





        /**
         * 发送 读消息的ack
         *
         * @author zhuangjiesen
         * @date 2018/10/11 下午2:41
         * @param
         * @return
         */
        private void doReadAck(SocketChannel socketChannel , String readmsg) throws Exception {
            System.out.println("读事件ack ");
            //拼接消息
            StringBuilder ackSb = new StringBuilder(readmsg);
            ackSb.append(":ack:");
            ackSb.append(System.currentTimeMillis());
            ackSb.append("\r\n");
            //写消息
            ByteBuffer buf = ByteBuffer.allocate(48);
            String response = ackSb.toString();
            System.out.println("写ack : " + response);
            buf.put(response.getBytes());
            buf.flip();
            while(buf.hasRemaining()) {
                socketChannel.write(buf);
            }
        }

        /**
         * 写操作
         *
         * @author zhuangjiesen
         * @date 2018/10/11 下午2:41
         * @param
         * @return
         */
        private void doWrite(SelectionKey key) throws Exception {
            System.out.println("写事件.... ");
            //获取 SelectionKey存的 attachment
            String message = (String) key.attachment();
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //发送ack
            doReadAck(socketChannel, message);
            //消息置成 OP_READ
            key.interestOps(SelectionKey.OP_READ);
        }
    }


    /**
     * 读取数据操作
     *
     * @author zhuangjiesen
     * @date 2018/10/11 下午5:45
     * @param
     * @return
     */
    public static String readChannelData(SocketChannel socketChannel){
        try {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            // configureBlocking() 设置成非阻塞 ，这里读不到数据直接返回
            int bytesRead = socketChannel.read(buf);
            buf.flip();
            byte[] data = new byte[buf.remaining()];
            buf.get(data);
            String message = new String(data);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
