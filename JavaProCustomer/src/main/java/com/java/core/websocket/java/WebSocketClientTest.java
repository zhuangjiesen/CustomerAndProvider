package com.java.core.websocket.java;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description: websocket 客户端模拟
 * @Date: Created in 2018/5/30
 */
public class WebSocketClientTest {

    public static void main(String[] args) throws Exception {


        WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:38888/"), new Draft_17()) {


            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("onOpen : " + serverHandshake.getHttpStatusMessage());
                System.out.println("onOpen : " + serverHandshake.getHttpStatus());



            }

            @Override
            public void onMessage(String message) {
                System.out.println("接收到消息："+ message);
            }

            @Override
            public void onClose(int i, String s, boolean b) {

            }

            @Override
            public void onError(Exception e) {
                System.err.printf(e.toString());

            }

        };
        webSocketClient.connect();
        Thread.currentThread().sleep(3000);
        System.out.println(webSocketClient.isOpen());
        while(webSocketClient.isOpen()) {
            webSocketClient.send("helloworld");
            break;
        }


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {}
            }
        });
        t1.start();
    }

}
