package com.java.core.netty.websocket.handler;

import com.java.core.netty.websocket.WebSocketMessage;
import com.java.core.netty.websocket.adapter.WebSocketMessageAdapter;
import com.sun.javafx.binding.StringFormatter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class AppWebSocketRequestHandler extends AbstractWebSocketRequestHandler {
	
	public final static String WEBSOCKET= "websocket";
	public final static String Upgrade= "Upgrade";
	
	public final static String WEBSOCKET_ADDRESS_FORMAT="ws://%s/websocket";

	
	private WebSocketServerHandshaker handshaker;
	
	private WebSocketMessageAdapter webSocketMsgAdapter;
	
	
	
	@Override
	public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// TODO Auto-generated method stub
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
		}
	}
	
	

	@Override
	public void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// TODO Auto-generated method stub
		// 判断是否关闭链路的指令
		if ((frame instanceof CloseWebSocketFrame)) {
			System.out.println("关闭连接.... ");
			onWebSocketFrameColsed(ctx, frame);
			return ;
		}
		
		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (frame instanceof PongWebSocketFrame) {
			ctx.channel().write(
					new PingWebSocketFrame(frame.content().retain()));
			return;
		}
		System.out.println("frame : "+frame.getClass().getName());

		
		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("本例程仅支持文本消息，不支持二进制消息");
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", frame.getClass().getName()));
		}
		
		// 是否注册用户
		if (webSocketMsgAdapter.isRegistWebSocket(frame , ctx)) {
			//注册成功
			
		} else {
			//发送消息
			WebSocketMessage webSocketMsg = webSocketMsgAdapter.getWebSocketMsg(frame, ctx);
			int result = webSocketMsgAdapter.sendMsg(webSocketMsg);
			if (result != 1) {
				//发送失败
				TextWebSocketFrame tws = new TextWebSocketFrame("消息发送失败");
				ctx.channel().writeAndFlush( tws);
			} else {
				//发送成功 可以将数据保存到数据库
				
			}
		}
		
	}
	
	
	
	
	public void onWebSocketFrameColsed(ChannelHandlerContext ctx,
			WebSocketFrame frame) {
		webSocketMsgAdapter.removeSender(ctx.channel().id().asLongText());
		if (handshaker != null){
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
		}
	}


	
	
	

	public WebSocketMessageAdapter getWebSocketMsgAdapter() {
		return webSocketMsgAdapter;
	}



	public void setWebSocketMsgAdapter(WebSocketMessageAdapter webSocketMsgAdapter) {
		this.webSocketMsgAdapter = webSocketMsgAdapter;
	}
	
	
	

}