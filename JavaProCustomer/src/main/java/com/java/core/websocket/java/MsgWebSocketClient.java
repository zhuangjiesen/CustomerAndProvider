package com.java.core.websocket.java;

import org.java_websocket.WebSocketListener;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/5/30
 */
public class MsgWebSocketClient extends WebSocketClient implements WebSocketListener {

    public MsgWebSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public MsgWebSocketClient(URI serverURI) {
        super(serverURI);
    }






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
}
