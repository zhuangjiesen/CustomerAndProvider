package com.java.core.netty.websocket;


import com.java.core.netty.websocket.handler.WebSocketRequestHandler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketServerHandler extends ChannelInboundHandlerAdapter    {
	
	
	private WebSocketRequestHandler requestHandler;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" WebSocketServerHandler _ channelActive.. ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		String localAddress = ctx.channel().remoteAddress().toString();
		if (requestHandler != null) {
			if (msg instanceof FullHttpRequest) {
				requestHandler.handleHttpRequest(ctx, ((FullHttpRequest) msg));
			} else if (msg instanceof WebSocketFrame) {
				requestHandler.handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
			} else if (msg instanceof CloseWebSocketFrame) {
				//关闭链路
				requestHandler.onWebSocketFrameColsed(ctx, (CloseWebSocketFrame)msg);
			} else {
			}
		} else {
			throw new RuntimeException("未设置请求处理器");
		}
	}
	

	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" WebSocketServerHandler _ channelReadComplete.. ");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" WebSocketServerHandler _ exceptionCaught..  cause : "+cause.getMessage());
	}

	public WebSocketRequestHandler getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(WebSocketRequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}


	
	
	
	
}