package com.java.core.netty.common.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhuangjiesen on 2017/9/13.
 */
public class CommonCustomerChannelHandler extends ChannelInboundHandlerAdapter {

    public static Channel customerChannel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        customerChannel = ctx.channel();
        System.out.println("---My2InboundChannelHandler ....channelActive.... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        System.out.println("---My2InboundChannelHandler ....channelRead.... ");


        super.channelRead(ctx, msg);



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        System.out.println("---My2InboundChannelHandler ....channelReadComplete.... ");


        super.channelReadComplete(ctx);
    }
}
