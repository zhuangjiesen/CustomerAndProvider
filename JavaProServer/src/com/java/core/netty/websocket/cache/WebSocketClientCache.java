package com.java.core.netty.websocket.cache;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangjiesen on 2017/8/8.
 */
public class WebSocketClientCache {

    private static ConcurrentHashMap<String , WebSocketClientNode> channelsMap = new ConcurrentHashMap<>();
    //心跳 1 已发送
    private static ConcurrentHashMap<String , Integer> pingPongChannelsMap = new ConcurrentHashMap<>();


    //轮训时间 检测过期连接
    private final static int SCHEDULE_SECONDS = 180;
    private static ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(1);

    private static Lock pingPongLock = new ReentrantLock();


    /*标记状态*/
    private static volatile boolean isSent = true;


    static {
        scheduleService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println( "WebSocketClientCache scheduleWithFixedDelay starting  ...");
                if (isSent) {
                    isSent = false;
                    //定时发送心跳
                    sendPingMessageToAll();

                } else {
                    isSent = true;
                    clearNotPingPongMessage();
                }
            }
        } , 1L , SCHEDULE_SECONDS , TimeUnit.SECONDS);
    }





    public static void putChannelHandlerContext(String channelId ,ChannelHandlerContext channelHandlerContext  ,  WebSocketServerHandshaker handshaker ) {
        if (channelsMap.containsKey(channelId)) {
            return ;
        }
        try {
            WebSocketClientNode webSocketClientNode = new WebSocketClientNode();
            webSocketClientNode.handshaker = handshaker;
            webSocketClientNode.ctx = channelHandlerContext;
            channelsMap.put(channelId , webSocketClientNode);
            pingPongChannelsMap.remove(channelId);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("err : " + e.getMessage());
        }
    }



    public static ChannelHandlerContext getChannelHandlerContext(String channelId ) {
        WebSocketClientNode webSocketClientNode = channelsMap.get(channelId);
        if (webSocketClientNode != null && webSocketClientNode.ctx != null) {
            return webSocketClientNode.ctx;
        }
        return null;
    }


    public static void removeChannelHandlerContext(String channelId ) {
        channelsMap.remove(channelId);
    }


    public static List<ChannelHandlerContext> getChannelHandlerContextList() {
        if (channelsMap.isEmpty()) {
            return null;
        }
        Set<String> keySet = channelsMap.keySet();
        List<ChannelHandlerContext> list = new LinkedList<>();
        for (String key : keySet) {
            WebSocketClientNode webSocketClientNode = channelsMap.get(key);
            list.add(webSocketClientNode.ctx);
        }
        if (list.size() == 0) {
            return null;
        }
        return list;
    }



    /*
    * 全部发送心跳
    *
    * */
    public static void sendPingMessageToAll(){
        pingPongChannelsMap.clear();
        if (channelsMap.isEmpty()) {
            return ;
        }

        Set<String> keySet = channelsMap.keySet();
        for (String key : keySet) {
            WebSocketClientNode webSocketClientNode = channelsMap.get(key);
            //往客户端发ping 客户端会返回pong 可以用来判断客户端存活
            PingWebSocketFrame pingWebSocketFrame = new PingWebSocketFrame();
            webSocketClientNode.ctx.channel().writeAndFlush(pingWebSocketFrame);
            //标记为已发送
            pingPongChannelsMap.put(key , 1 );
        }
    }



    public static void getPongMessage(String channelId) {
        if (channelId == null) {
            return ;
        }
        pingPongChannelsMap.remove(channelId);
    }


    public static void clearNotPingPongMessage() {
        if (channelsMap.isEmpty()) {
            return ;
        }
        CloseWebSocketFrame closeWebSocketFrame = new CloseWebSocketFrame();
        Set<String> keySet = pingPongChannelsMap.keySet();
        for (String key : keySet) {
            Integer status = pingPongChannelsMap.get(key);
            if (status != null && status.intValue() == 1) {
                WebSocketClientNode webSocketClientNode = channelsMap.get(key);
                //关闭websocket // 握手关闭连接
                webSocketClientNode.handshaker.close(webSocketClientNode.ctx.channel() , closeWebSocketFrame);
            }
            pingPongChannelsMap.remove(key);
        }
        pingPongChannelsMap.clear();
    }







    private static class WebSocketClientNode {
        WebSocketServerHandshaker handshaker ;
        ChannelHandlerContext ctx ;
    }





    /*
    * 全部发送消息
    * 往客户端推送消息
    *
    * */
    public static void sendMessageToAll(String message){
        if (channelsMap.isEmpty()) {
            return ;
        }
        Set<String> keySet = channelsMap.keySet();
        for (String key : keySet) {
            WebSocketClientNode webSocketClientNode = channelsMap.get(key);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(message);
            if (webSocketClientNode.ctx.channel().isOpen() && webSocketClientNode.ctx.channel().isWritable()) {
                webSocketClientNode.ctx.channel().writeAndFlush(textWebSocketFrame);
            } else {
                channelsMap.remove(key);
            }
        }
    }





}
