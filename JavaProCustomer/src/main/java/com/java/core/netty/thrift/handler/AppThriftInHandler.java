package com.java.core.netty.thrift.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhuangjiesen on 2017/6/19.
 */
public class AppThriftInHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("  AppThriftInHandler /......channelRegistered.....");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);


        System.out.println("  AppThriftInHandler /......channelActive.....");
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);


        System.out.println("  AppThriftInHandler /......channelWritabilityChanged.....");

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);


        System.out.println("  AppThriftInHandler /......channelRead.....");


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);


        System.out.println("  AppThriftInHandler /......channelRead..... cause : "+cause);


    }
}
