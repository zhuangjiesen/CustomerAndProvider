package com.java.core.netty.websocket.common;

import com.dragsun.websocket.adapter.KeepAliveHandlerAdapter;
import com.dragsun.websocket.annotation.WSRequestMapping;
import com.dragsun.websocket.cache.WebSocketCacheManager;
import com.dragsun.websocket.cache.WebSocketClient;
import com.dragsun.websocket.utils.MessageUtils;
import com.java.service.TestService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zhuangjiesen on 2018/1/22.
 */
@WSRequestMapping(uri = "/dubbo")
public class DubboHandlerAdapter extends KeepAliveHandlerAdapter<TextWebSocketFrame> {


    @Override
    public void handleResponse(Map<String , Object> params) {

            System.out.println(" ---- FrameHandlerAdapter .....handleResponse ....");
            String message = (String) params.get("message");

            WebSocketCacheManager wcm = applicationContext.getBean(WebSocketCacheManager.class);
            String uri = getUri();
            Collection<WebSocketClient> clients = wcm.getClientsByUri(uri);
            //批量发送数据
            MessageUtils.sendMessage(clients , message);
    }

    @Override
    public void onUpgradeCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {

    }


    @Override
    public void handlerWebSocketFrameData(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
            System.out.println(" ---- FrameHandlerAdapter .....handlerWebSocketFrameData ....");
            String content = webSocketFrame.text();
        TestService testService=applicationContext.getBean(TestService.class);
        if ("doDubboInfoTest".equals(content)) {
            testService.doDubboInfoTest();
        }
        if ("doDubboTest".equals(content)) {
            testService.doDubboTest();
        }

        System.out.println("FrameHandlerAdapter ....content : " + content );


    }






}