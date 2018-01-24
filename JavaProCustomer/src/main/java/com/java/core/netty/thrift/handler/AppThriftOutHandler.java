package com.java.core.netty.thrift.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created by zhuangjiesen on 2017/6/19.
 */
public class AppThriftOutHandler extends ChannelOutboundHandlerAdapter {




    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);


        System.out.println("AppThriftOutHandler /......write.....");
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);


        System.out.println("AppThriftOutHandler /......flush.....");

    }
}
