package com.java.core.netty.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public abstract class AbstractWebSocketRequestHandler implements WebSocketRequestHandler {

	@Override
	public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// TODO Auto-generated method stub

	}
	
	
	public void onWebSocketFrameColsed(ChannelHandlerContext ctx,
			WebSocketFrame frame) {
		
	}

}