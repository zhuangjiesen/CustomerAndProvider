package com.java.core.netty.websocket.handler;


import com.alibaba.fastjson.JSON;
import com.java.core.netty.websocket.cache.WebSocketClientCache;
import com.sun.javafx.binding.StringFormatter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServerHandler extends ChannelInboundHandlerAdapter    {


	public final static String WEBSOCKET= "websocket";
	public final static String Upgrade= "Upgrade";

	public final static String WEBSOCKET_ADDRESS_FORMAT="ws://%s/websocket";

	private WebSocketServerHandshaker webSocketServerHandshaker;


	private WebSocketMessageHandler webSocketMessageHandler;




	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("WebSocketClientCache scheduleWithFixedDelay starting  ...");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof FullHttpRequest) {
			//处理http请求
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
		} else if (msg instanceof WebSocketFrame) {
			//处理websocket请求
			handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
		} else if (msg instanceof CloseWebSocketFrame) {
			//关闭链路
			onWebSocketFrameColsed(ctx, (CloseWebSocketFrame)msg);
		} else {
			throw new RuntimeException("无法处理的请求");
		}
	}
	

	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("WebSocketServerHandler channelReadComplete ...");

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("WebSocketServerHandler exceptionCaught ..."  + cause.getMessage() );
		cause.printStackTrace();
	}




	/*
	* 处理http请求
	* */
	public void handleHttpRequest(ChannelHandlerContext ctx,
								  FullHttpRequest req) {
		//不是websocket请求
		if (!req.decoderResult().isSuccess()
				|| (!this.WEBSOCKET.equals(req.headers().get(this.Upgrade).toString().toLowerCase()))) {

			DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

			// 返回应答给客户端
			if (defaultFullHttpResponse.status().code() != 200) {
				ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.status().toString(),
						CharsetUtil.UTF_8);
				defaultFullHttpResponse.content().writeBytes(buf);
				buf.release();
			}
			// 如果是非Keep-Alive，关闭连接
			ChannelFuture f = ctx.channel().writeAndFlush(defaultFullHttpResponse);

			boolean isKeepAlive = false;

			if ((!isKeepAlive) || defaultFullHttpResponse.status().code() != 200) {
				f.addListener(ChannelFutureListener.CLOSE);
			}
			return ;
		}



		String host =req.headers().get("Host").toString();
		String webAddress = StringFormatter.format(WEBSOCKET_ADDRESS_FORMAT, host).getValueSafe();
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				webAddress, null, false);
		WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory
					.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
			webSocketServerHandshaker = handshaker;
			WebSocketClientCache.putChannelHandlerContext(ctx.channel().id().asLongText() , ctx  , handshaker);
		}
	}

	/*
	* 处理websocket 请求
	* */
	public void handlerWebSocketFrame(ChannelHandlerContext ctx,
									  WebSocketFrame frame) {
		if (frame == null) {
			return ;
		}
		// 判断是否关闭链路的指令
		if ((frame instanceof CloseWebSocketFrame)) {
			System.out.println("关闭连接....");
			onWebSocketFrameColsed(ctx, frame);
			return ;
		}

		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			System.out.println( "i am ping message ....");
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (frame instanceof PongWebSocketFrame) {
			System.out.println( "i am pong message ...." );
			ctx.channel().write(
					new PingWebSocketFrame(frame.content().retain()));

			//获取pong
			WebSocketClientCache.getPongMessage(ctx.channel().id().asLongText());
			return;
		}
		System.out.println("frame class : " + frame.getClass().getName());


		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("not support other frame data : " + JSON.toJSONString(frame));
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", frame.getClass().getName()));
		}

		String channelId = ctx.channel().id().asLongText();
		//每次插入，保证可靠
		WebSocketClientCache.putChannelHandlerContext(channelId , ctx  , webSocketServerHandshaker);
		//获取pong
		WebSocketClientCache.getPongMessage(ctx.channel().id().asLongText());
		if (frame != null) {
			TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) frame;
//			String frameContent = textWebSocketFrame.text();
//			Channel channel = ctx.channel();
//			String channelId = channel.id().asLongText();
//			//获取pong
//			WebSocketClientCache.getPongMessage(ctx.channel().id().asLongText());
//			TextWebSocketFrame tws = new TextWebSocketFrame("我回应了。。。。");
//			channel.writeAndFlush(tws);
//			LogUtils.logDebug(WebSocketServerHandler.class , "frameContent : " + frameContent);
			webSocketMessageHandler.onMessage(textWebSocketFrame);
		}


	}



	/*
	* 处理websocket 关闭请求
	* */
	public void onWebSocketFrameColsed(ChannelHandlerContext ctx,
									   WebSocketFrame frame) {
		if (webSocketServerHandshaker != null){
			WebSocketClientCache.removeChannelHandlerContext(ctx.channel().id().asLongText());
			webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
		}
	}






	public WebSocketMessageHandler getWebSocketMessageHandler() {
		return webSocketMessageHandler;
	}

	public void setWebSocketMessageHandler(WebSocketMessageHandler webSocketMessageHandler) {
		this.webSocketMessageHandler = webSocketMessageHandler;
	}
}