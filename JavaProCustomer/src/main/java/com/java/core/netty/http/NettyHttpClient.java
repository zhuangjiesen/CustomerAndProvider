package com.java.core.netty.http;

import com.java.core.rpc.thrift.supports.utils.ThriftUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 *
 * netty 调用http请求
 * @Date: Created in 2018/11/6
 */
public class NettyHttpClient {

    private static SettableListenableFuture<FullHttpResponse> responseFuture;

    public static void main(String[] args) throws Exception {
        responseFuture = new SettableListenableFuture<>();

        ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                System.out.println("-- operationComplete --");
                if (future.isSuccess()) {


                    RequestExecuteHandler requestExecuteHandler = new RequestExecuteHandler(responseFuture);
                    Channel channel = future.channel();
                    channel.pipeline().addLast(requestExecuteHandler);
                    channel.writeAndFlush(createFullHttpRequest());
                } else {
                    System.out.println("http连接失败");
                }
            }
        };
        //配置客户端请求 Bootstrap 等于一个客户端连接
        Bootstrap clientBootstrap = new Bootstrap();
        NioEventLoopGroup worker = new NioEventLoopGroup(8);
        clientBootstrap.group(worker);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                //http 报文解析
                pipeline.addLast(new HttpClientCodec());
                pipeline.addLast(new HttpObjectAggregator(1024));
                pipeline.addLast(new ReadTimeoutHandler(10 * 1000,
                        TimeUnit.MILLISECONDS));
            }
        });
        ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 7474)).addListener(channelFutureListener);
        channelFuture.get();
        FullHttpResponse response = responseFuture.get();

        if (channelFuture.isSuccess()) {
            //模拟长连接请求
            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(" -- scheduleAtFixedRate -- ");
                    try {

                        Channel channel = channelFuture.channel();
                        System.out.println("isActive : " + channel.isActive());
                        channel.writeAndFlush(createFullHttpRequest());
                        FullHttpResponse response = responseFuture.get();
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            } , 1 , 5 ,TimeUnit.SECONDS);
        }


        System.out.println("----");
        System.out.println("isSuccess : " + channelFuture.isSuccess());


    }



    /**
     * A SimpleChannelInboundHandler to update the given SettableListenableFuture.
     */
    private static class RequestExecuteHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

        private final SettableListenableFuture<FullHttpResponse> responseFuture;

        public RequestExecuteHandler(SettableListenableFuture<FullHttpResponse> responseFuture) {
            this.responseFuture = responseFuture;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext context, FullHttpResponse response) throws Exception {
            ByteBuf bf = response.content();
            byte[] byteArray = new byte[bf.capacity()];
            bf.readBytes(byteArray);
            String result = new String(byteArray);
            System.out.println("result : " + result);
            this.responseFuture.set(response);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
            this.responseFuture.setException(cause);
        }

    }


    public static FullHttpRequest createFullHttpRequest(){
        String healthStatus = "/health/status";
        String path = healthStatus;
        FullHttpRequest nettyRequest = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, path);
        nettyRequest.headers().set(org.apache.http.HttpHeaders.HOST , "127.0.0.1");
//        nettyRequest.headers().set(org.apache.http.HttpHeaders.CONNECTION , "keep-alive");
        //如果这个设置为close 连接关闭
        nettyRequest.headers().set(org.apache.http.HttpHeaders.CONNECTION , "close");
        return nettyRequest;
    }

}
