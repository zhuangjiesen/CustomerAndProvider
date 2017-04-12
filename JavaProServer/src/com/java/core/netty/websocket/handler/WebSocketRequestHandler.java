package com.java.core.netty.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public interface WebSocketRequestHandler {
	

	
	public void handleHttpRequest(ChannelHandlerContext ctx,
			FullHttpRequest req) ;
	
	public void handlerWebSocketFrame(ChannelHandlerContext ctx,
			WebSocketFrame frame);

	public void onWebSocketFrameColsed(ChannelHandlerContext ctx,
			WebSocketFrame frame);
	

}