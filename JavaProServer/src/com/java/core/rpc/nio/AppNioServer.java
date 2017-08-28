package com.java.core.rpc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * nio
 * Created by zhuangjiesen on 2017/6/14.
 */
public class AppNioServer extends Thread {


    private int port = 19999;

    private static ReaderThread readerThread;
    private static WritorThread writorThread;

    public AppNioServer() {


    }
    @Override
    public void run() {
        readerThread = new ReaderThread();
        readerThread.start();
        writorThread = new WritorThread();
        writorThread.start();


        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().setReuseAddress(true);
            Selector connectorSelector = Selector.open();
            serverSocketChannel.register(connectorSelector , SelectionKey.OP_ACCEPT);
            // set options

            System.out.println("...AppNioServer start.... ");
            while (true) {
                int readyChannels = connectorSelector.select();
                if(readyChannels == 0) continue;
                System.out.println("...a connect get.... readyChannels : "+readyChannels);
                Iterator<SelectionKey> connectorKeys = connectorSelector.selectedKeys().iterator();
                while (connectorKeys.hasNext()){

                    SelectionKey selectionKey = connectorKeys.next();
                    if ( selectionKey.isAcceptable()) {
                        System.out.println("  isAcceptable  ");
                        serverSocketChannel.accept().close();
                    }
//                    readerThread.registSelector(serverSocketChannel.accept());
                }
                connectorKeys.remove();
            }





        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private static class ReaderThread extends Thread {

        private Selector readSelector ;
        private volatile boolean select = false;

        public ReaderThread() {

            try {
                readSelector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {

            System.out.println(" ReaderThread.select...");

            try {
                while (true){
                    if (select) {
                        select = false;
                        System.out.println(" register.... SelectorThread ..run...");

                        int readyChannels = readSelector.select();
                        if(readyChannels == 0) continue;
                        Iterator<SelectionKey> readKeys = readSelector.selectedKeys().iterator();
                        while(readKeys.hasNext()) {
                            SelectionKey readKey =  readKeys.next();

                            if (readKey.isReadable()) {
                                System.out.println(" 可读 ....");
                                System.out.println(" 读取完毕...开始写 ....");
                                writorThread.registSelector(((SocketChannel)readKey.channel()).configureBlocking(false));
                            }

                            readKeys.remove();
                        }



                        System.out.println(" SelectorThread ..run...");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        public void registSelector(SelectableChannel channel){
            select = true;
            try {
                channel.configureBlocking(false);
                channel.register(readSelector , SelectionKey.OP_READ);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }
    private static class WritorThread extends Thread {
        private Selector writeSelector ;
        private volatile boolean select = false;

        public WritorThread() {
            try {
                writeSelector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {

            System.out.println(" WritorThread.select...");

            try {
                while (true){
                    if (select) {
                        select = false;
                        System.out.println(" register.... SelectorThread ..run...");

                        int readyChannels = writeSelector.select();
                        if(readyChannels == 0) continue;
                        Iterator<SelectionKey> readKeys = writeSelector.selectedKeys().iterator();
                        while(readKeys.hasNext()) {
                            SelectionKey readKey =  readKeys.next();

                            if (readKey.isWritable()) {
                                System.out.println(" 可写 ....");
                                System.out.println(" 写完毕.. 关闭 ....");
                                readKey.channel().close();
                            }


                            readKeys.remove();
                        }



                        System.out.println(" SelectorThread ..run...");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }




        }




        public void registSelector(SelectableChannel channel){
            select = true;
            try {
                channel.configureBlocking(false);
                channel.register(writeSelector , SelectionKey.OP_WRITE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }









}
