package com.java.core.netty.thrift;

import com.java.core.netty.thrift.handler.AppThriftInHandler;
import com.java.core.netty.thrift.handler.AppThriftOutHandler;
import com.java.core.netty.thrift.handler.HttpClientInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuangjiesen on 2017/6/19.
 */
public class AppThriftNettyConsumer {

    private ExecutorService service = Executors.newCachedThreadPool();

    private static EventLoopGroup loopGroup = new NioEventLoopGroup(50);



    private static Bootstrap bootstrap;
    static {


    }


    public void connect(String host , int port , String subUri) {

        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup);
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY , true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {

                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        ch.pipeline().addLast(new HttpResponseDecoder());
                        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                        ch.pipeline().addLast(new HttpRequestEncoder());
                        ch.pipeline().addLast(new HttpClientInboundHandler());

                    }
                });

        try {
            // Start the client.
//            String host = "10.11.165.101";
            ChannelFuture f = bootstrap.connect(host, 48888 ).sync();

            URI uri = new URI("http://10.11.165.101:38888/chat.do");
            String msg = "Are you ok?";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
//            request.setUri("/chat.do");
            request.setUri(subUri);

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());


            request.headers().set("Sec-WebSocket-Extensions" , "permessage-deflate; client_max_window_bits");
            request.headers().set("Sec-WebSocket-Version" , "13");
            request.headers().set("Upgrade" , "websocket");
            request.headers().set("Connection" , "Upgrade");
            request.headers().set("Sec-WebSocket-Key" , "Bmn+O0ZG2kMINbJJebWsFQ==");


            // 发送http请求
            String channelId = f.channel().id().asLongText();
            System.out.println("channelId .. : " + channelId);
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
