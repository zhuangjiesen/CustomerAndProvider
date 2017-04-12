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

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketMsgAdapter {

	
	
	private final static String REGIST_PREFIX = "w://websocket-regist";
	private final static String REGIST_RESULT = "w://websocket-regist-result";
	private final static String REGIST_NAME = "registName";
	
	
	private final static String ONLINE_USER_LIST = "online_user_list";
	
	private static ConcurrentHashMap<String, ChannelHandlerContext> sendNameAndContextMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
	private static ConcurrentHashMap<String, String> channelIdAndsendNameMap = new ConcurrentHashMap<String, String>();
	

	private static ConcurrentLinkedQueue<WebSocketHandlerContext> webSocketHandlerContextQueue = new ConcurrentLinkedQueue<WebSocketHandlerContext>();
	
	private static ScheduledExecutorService executorService = null;

	//2 秒推送
	private static int timeSeconds = 2;
	//用户新增
	private static AtomicBoolean hasOnlineUserAdd = new AtomicBoolean(false);
	//用户减少
	private static AtomicBoolean hasOnlineUserReduce = new AtomicBoolean(false); 
	
	public WebSocketMsgAdapter() {
		// TODO Auto-generated constructor stub
		startSchedule();
	}
	
	
	
	
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
				jsonObject.put(ONLINE_USER_LIST, values);
				TextWebSocketFrame tws = new TextWebSocketFrame(jsonObject.toJSONString());
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
			JSONObject requestJson = JSONObject.parseObject(requestContent);
			//判断是否注册
			if (requestJson.containsKey("registPrefix") && REGIST_PREFIX.equals(requestJson.get("registPrefix"))) {
				//注册名
				String registName = requestJson.getString(REGIST_NAME);
				if (registName != null && (!registName.trim().isEmpty())) {
					//注册用户
					String sendName = null;
					sendName = registName;
					System.out.println("regist : "+sendName);
					if (sendNameAndContextMap.containsKey(sendName)){
						System.out.println("已经存在注册用户");
						TextWebSocketFrame tws = new TextWebSocketFrame(REGIST_RESULT + "已经存在该用户,请重新注册");
						ctx.channel().writeAndFlush( tws);
						return false;
					}
					sendNameAndContextMap.put(sendName, ctx);
					channelIdAndsendNameMap.put(ctx.channel().id().asLongText(), sendName);
					ChannelHandlerContext handlerContext = sendNameAndContextMap.get(sendName);
					if (handlerContext != null) {
						System.out.println("注册成功");
						WebSocketHandlerContext webSocketHandlerContext = new WebSocketHandlerContext();
						webSocketHandlerContext.setName(registName);
						webSocketHandlerContext.setChannelHandlerContext(ctx);
						webSocketHandlerContextQueue.add(webSocketHandlerContext);
						
						if (!hasOnlineUserAdd.get()) {
							hasOnlineUserAdd.compareAndSet(false, true);
						}
						TextWebSocketFrame tws = new TextWebSocketFrame(REGIST_RESULT+"1");
						ctx.channel().writeAndFlush( tws);
						return true;
					} else {
						System.out.println("注册失败");
						TextWebSocketFrame tws = new TextWebSocketFrame(REGIST_RESULT+"0");
						ctx.channel().writeAndFlush( tws);
						return false;
					}
				} else {
					System.out.println("注册失败");
					TextWebSocketFrame tws = new TextWebSocketFrame(REGIST_RESULT+"请输入注册名");
					ctx.channel().writeAndFlush( tws);
					return false;
				}
			}
		}
		return false;
	}
	
	
	
	
	public WebSocketMsg getWebSocketMsg(WebSocketFrame frame ,ChannelHandlerContext ctx ){
		WebSocketMsg webSocketMsg = null;
		String requestContent = ((TextWebSocketFrame) frame).text();
		if (requestContent != null && (!requestContent.isEmpty())) {
			JSONObject requestJson = JSONObject.parseObject(requestContent);
			
			String receiveName = requestJson.getString("receiveName");
			String content = requestJson.getString("content");
			String sendName = channelIdAndsendNameMap.get(ctx.channel().id().asLongText());
			webSocketMsg = new WebSocketMsg();
			webSocketMsg.setReceiveName(receiveName);
			webSocketMsg.setSendName(sendName);
			webSocketMsg.setContent(content);
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
	 * 0 失败
	 * 1 成功
	 * 2 未找到接受者
	 * 3 未找到发送者
	 */
	public int sendMsg (WebSocketMsg webSocketMsg ){
		int result = 0;
		String sendName = webSocketMsg.getReceiveName();
		ChannelHandlerContext handlerContext = sendNameAndContextMap.get(sendName);
		if (handlerContext != null) {
			TextWebSocketFrame tws = new TextWebSocketFrame(JSONObject.toJSONString(webSocketMsg));
			handlerContext.channel().writeAndFlush( tws);
			result = 1;
		} 
		return result;
	}
	
	
	
}