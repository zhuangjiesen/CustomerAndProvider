package com.java.core.netty.websocket.adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.core.netty.websocket.WebSocketMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketMessageAdapter {
	
	public static ConcurrentHashMap<String, ChannelHandlerContext> sendNameAndContextMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
	private static ConcurrentHashMap<String, String> channelIdAndsendNameMap = new ConcurrentHashMap<String, String>();

	private static ConcurrentLinkedQueue<WebSocketHandlerContext> webSocketHandlerContextQueue = new ConcurrentLinkedQueue<WebSocketHandlerContext>();
	
	/** 在线人数统计 线程**/
	private static ScheduledExecutorService executorService = null;

	//2 秒推送
	private static int timeSeconds = 2;
	//用户新增
	private static AtomicBoolean hasOnlineUserAdd = new AtomicBoolean(false);
	//用户减少
	private static AtomicBoolean hasOnlineUserReduce = new AtomicBoolean(false); 
	
	public WebSocketMessageAdapter() {
		// TODO Auto-generated constructor stub
		startSchedule();
	}
	
	
	
	
	/**
	 * @Description: TODO
	 * 开启定时线程 统计在线人数并推送
	 * @author zhuangjiesen
	 * @date 2017年4月12日 下午3:39:04 
	 */
	public synchronized void startSchedule(){
		if (executorService == null) {
			executorService = Executors.newScheduledThreadPool(1);
			executorService.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//在线用户增加
					if (hasOnlineUserAdd.get()) {
						pushOnlineList();
						hasOnlineUserAdd.compareAndSet(true, false);
					} else {
					}
					
					//在线用户减少
					if (hasOnlineUserReduce.get()) {
						pushOnlineList();
						hasOnlineUserReduce.compareAndSet(true, false);
					} else {
					}
					
				}
			}, timeSeconds, timeSeconds,  TimeUnit.SECONDS);
		}
		
	}
	
	
	public void pushOnlineList(){
		Iterator<WebSocketHandlerContext> iterator = webSocketHandlerContextQueue.iterator();
		Collection values = channelIdAndsendNameMap.values();
		while (iterator.hasNext()) {
			WebSocketHandlerContext context = iterator.next();
			Channel channel = context.getChannelHandlerContext().channel();
			if (channel.isOpen() && channel.isOpen() && channel.isRegistered() && channel.isWritable()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("list", values);
				
				WebSocketMessage webSocketMessage = new WebSocketMessage();
				webSocketMessage.setMessageType(6);
				webSocketMessage.setContent(jsonObject.toJSONString());
				webSocketMessage.setSendName("管理员");
				TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMessage.toJSONString());
				channel.writeAndFlush( tws);
			}
			
		}
	}
	




	/**
	 * @Description: TODO
	 * 注册websocket 名称
	 * @author zhuangjiesen
	 * @date 2017年4月11日 上午10:32:57 
	 * @param request
	 * @return
	 */
	public boolean isRegistWebSocket(WebSocketFrame frame ,ChannelHandlerContext ctx ){
		String requestContent = ((TextWebSocketFrame) frame).text();

		if (requestContent != null && (!requestContent.isEmpty())) {
			WebSocketMessage webSocketMessage = JSONObject.parseObject(requestContent, WebSocketMessage.class);
			if (webSocketMessage != null) {
				Integer messageType = webSocketMessage.getMessageType();
				
				if (messageType != null) {
					switch (messageType.intValue()) {
						case 1:
						{
							//注册事件
							//注册用户
							String sendName = null;
							sendName = webSocketMessage.getSendName();
							System.out.println("regist : "+sendName);
							if (sendNameAndContextMap.containsKey(sendName)){
								System.out.println("已经存在注册用户");
								WebSocketMessage messageResult = new WebSocketMessage();
								messageResult.setMessageType(5);
								messageResult.setContent("已经存在注册用户");
								TextWebSocketFrame tws = new TextWebSocketFrame(messageResult.toJSONString());
								ctx.channel().writeAndFlush( tws);
								return false;
							}
							sendNameAndContextMap.put(sendName, ctx);
							channelIdAndsendNameMap.put(ctx.channel().id().asLongText(), sendName);
							ChannelHandlerContext handlerContext = sendNameAndContextMap.get(sendName);
							if (handlerContext != null) {
								System.out.println("注册成功");
								
								WebSocketHandlerContext webSocketHandlerContext = new WebSocketHandlerContext();
								webSocketHandlerContext.setName(sendName);
								webSocketHandlerContext.setChannelHandlerContext(ctx);
								webSocketHandlerContextQueue.add(webSocketHandlerContext);
								
								if (!hasOnlineUserAdd.get()) {
									hasOnlineUserAdd.compareAndSet(false, true);
								}
								
								WebSocketMessage messageResult = new WebSocketMessage();
								messageResult.setMessageType(3);
								messageResult.setContent("注册成功");
								TextWebSocketFrame tws = new TextWebSocketFrame(messageResult.toJSONString());
								ctx.channel().writeAndFlush( tws);
								return true;
							} else {
								System.out.println("注册失败");
								
								WebSocketMessage messageResult = new WebSocketMessage();
								messageResult.setMessageType(4);
								messageResult.setContent("注册失败");
								TextWebSocketFrame tws = new TextWebSocketFrame(messageResult.toJSONString());
								ctx.channel().writeAndFlush( tws);
								return false;
							}
						}
						default:
							break;
					}
				} else {
					throw new RuntimeException("消息类型为空");
				}
			}
			
			
			
		}
		return false;
	}
	
	
	
	
	/**
	 * @Description: TODO
	 * 将请求封装成消息类型
	 * @author zhuangjiesen
	 * @date 2017年4月12日 下午3:38:08 
	 * @param frame
	 * @param ctx
	 * @return
	 */
	public WebSocketMessage getWebSocketMsg(WebSocketFrame frame ,ChannelHandlerContext ctx ){
		WebSocketMessage webSocketMsg = null;
		String requestContent = ((TextWebSocketFrame) frame).text();
		if (requestContent != null && (!requestContent.isEmpty())) {
			WebSocketMessage webSocketMessage = JSONObject.parseObject(requestContent, WebSocketMessage.class);
			if (webSocketMessage != null) {
				Integer messageType = webSocketMessage.getMessageType();
				if (messageType != null) {
					switch (messageType.intValue()) {
						case 2:
							{
								//发送消息
								String remoteAddress = ctx.channel().remoteAddress().toString();
								String receiveName = webSocketMessage.getReceiveName();
								String content = webSocketMessage.getContent();
								String sendName = channelIdAndsendNameMap.get(ctx.channel().id().asLongText());
								webSocketMsg = new WebSocketMessage();
								webSocketMsg.setReceiveName(receiveName);
								webSocketMsg.setSendName(sendName);
								webSocketMsg.setContent(content);
								webSocketMsg.setIpAddress(remoteAddress);
								
							}
						default:
							break;
					}
				}
			} else {
				throw new RuntimeException("消息内容为空");
			}
			

		}
		return webSocketMsg;
	}
	
	
	
	
	
	/**
	 * @Description: TODO
	 * 移除发送者
	 * @author zhuangjiesen
	 * @date 2017年4月10日 下午4:01:49 
	 * @param channelId
	 */
	public void removeSender(String channelId){
		String sendName = channelIdAndsendNameMap.get(channelId);
		sendNameAndContextMap.remove(sendName);
		channelIdAndsendNameMap.remove(channelId);
		hasOnlineUserReduce.compareAndSet(false, true);
	}
	
	
	
	
	/**
	 * @Description: TODO
	 * 
	 * @author zhuangjiesen
	 * @date 2017年4月11日 上午11:20:14 
	 * @param webSocketMsg
	 * @return
	 * 
	 */
	public int sendMsg (WebSocketMessage webSocketMsg ){
		int result = 0;
		ChannelHandlerContext handlerContext = sendNameAndContextMap.get(webSocketMsg.getReceiveName());
		if (handlerContext != null) {
			webSocketMsg.setMessageType(7);
			TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMsg.toJSONString());
			handlerContext.channel().writeAndFlush( tws);
			result = 1;
		} 
		return result;
	}
	
	
	
}