package com.java.core.netty.thrift.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Created by zhuangjiesen on 2017/9/19.
 */
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String channelId = ctx.channel().id().asLongText();
        System.out.println("ChannelHandlerContext ====== channelId .. : " + channelId);

        if (msg instanceof HttpResponse)
        {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }
    }
}
