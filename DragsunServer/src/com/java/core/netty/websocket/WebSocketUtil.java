package com.java.core.netty.websocket;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhuangjiesen on 2017/4/15.
 */
public class WebSocketUtil {


    public static String getHost(ChannelHandlerContext ctx){
        String host = null;

        String remoteAddress = ctx.channel().remoteAddress().toString();

        System.out.println("remoteAddress : "+remoteAddress);

        int hostIndex = 0;
        if ((hostIndex = remoteAddress.indexOf(":")) > -1) {
            host = remoteAddress.substring(0 , hostIndex);
        } else {
            host = remoteAddress;
        }
        return host;
    }


}
