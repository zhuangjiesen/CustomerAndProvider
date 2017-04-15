package com.java.core.netty.websocket;


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

public class WebSocketServerHandler extends ChannelInboundHandlerAdapter    {

	/*websocket消息请求处理*/
	private WebSocketMessageManager webSocketMessageManager;


	public final static String WEBSOCKET= "websocket";
	public final static String Upgrade= "Upgrade";

	public final static String WEBSOCKET_ADDRESS_FORMAT="ws://%s/websocket";

	private WebSocketServerHandshaker handshaker;



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" WebSocketServerHandler _ channelActive.. ");
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
		System.out.println(" WebSocketServerHandler _ channelReadComplete.. ");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" WebSocketServerHandler _ exceptionCaught..  cause : "+cause.getMessage());
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
		}
	}

	/*
	* 处理websocket 请求
	* */
	public void handlerWebSocketFrame(ChannelHandlerContext ctx,
									  WebSocketFrame frame) {
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
		WebSocketMessage registMessage = webSocketMessageManager.isRegistWebSocket(frame , ctx);
		if (registMessage != null) {
			String host = WebSocketUtil.getHost(ctx);
			if (webSocketMessageManager.isIpRegistLimited(host)) {
				//ip限制
				WebSocketMessage webSocketMessage = new WebSocketMessage();
				webSocketMessage.setMessageType(9);
				webSocketMessage.setContent("ip限制");
				TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMessage.toJSONString());
				ctx.channel().writeAndFlush( tws);

				onWebSocketFrameColsed(ctx ,frame);
			} else {
				webSocketMessageManager.registIp(host);
				//注册
				webSocketMessageManager.registWebSocket(frame , ctx , registMessage);
			}

		} else {
			//发送消息
			WebSocketMessage webSocketMsg = webSocketMessageManager.getWebSocketMsg(frame, ctx);
			int result = webSocketMessageManager.sendMsg(webSocketMsg);
			if (result != 1) {
				//发送失败

				WebSocketMessage webSocketMessage = new WebSocketMessage();
				webSocketMessage.setMessageType(8);
				webSocketMessage.setContent("找不到接收人");
				TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMessage.toJSONString());
				ctx.channel().writeAndFlush( tws);
			} else {
				//发送成功 可以将数据保存到数据库

			}
		}
	}



	/*
	* 处理websocket 关闭请求
	* */
	public void onWebSocketFrameColsed(ChannelHandlerContext ctx,
									   WebSocketFrame frame) {
		webSocketMessageManager.removeSender(ctx.channel().id().asLongText());
		webSocketMessageManager.unregistIp(WebSocketUtil.getHost(ctx));

		if (handshaker != null){
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
		}
	}

	public WebSocketMessageManager getWebSocketMessageManager() {
		return webSocketMessageManager;
	}

	public void setWebSocketMessageManager(WebSocketMessageManager webSocketMessageManager) {
		this.webSocketMessageManager = webSocketMessageManager;
	}
}