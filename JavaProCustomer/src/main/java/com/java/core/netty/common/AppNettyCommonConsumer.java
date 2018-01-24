package com.java.core.netty.common;

import com.java.core.netty.common.handler.CommonCustomerChannelHandler;
import com.java.core.netty.common.handler.CommonMessageToByteEncoder;
import com.java.core.netty.thrift.handler.HttpClientInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuangjiesen on 2017/6/19.
 */
public class AppNettyCommonConsumer  {

    private ExecutorService service = Executors.newCachedThreadPool();

    private static EventLoopGroup loopGroup = new NioEventLoopGroup(50);



    private static Bootstrap bootstrap;
    static {
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup);
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY , true)
//                .option(ChannelOption.SO_KEEPALIVE , true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();

                        p.addLast(new CommonMessageToByteEncoder());
                        p.addLast(new CommonCustomerChannelHandler());
                    }
                });
        try {
            // Start the client.
            ChannelFuture f = bootstrap.connect( "127.0.0.1", 18888 );
            System.out.println(" class : " + f.channel().getClass().getName());

            f.sync();

            // 发送http请求
            String channelId = f.channel().id().asLongText();
            System.out.println("channelId .. : " + channelId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
