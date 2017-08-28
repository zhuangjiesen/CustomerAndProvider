package com.java.core.netty.websocket.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhuangjiesen on 2017/8/9.
 */


public class WebSocketServerHandlerFactory {


    private WebSocketMessageHandler webSocketMessageHandler;

    public WebSocketServerHandler getWebSocketServerHandler() {
        WebSocketServerHandler webSocketServerHandler = new WebSocketServerHandler();
        webSocketServerHandler.setWebSocketMessageHandler(webSocketMessageHandler);
        return webSocketServerHandler;
    }


    public WebSocketMessageHandler getWebSocketMessageHandler() {
        return webSocketMessageHandler;
    }

    public void setWebSocketMessageHandler(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }
}
