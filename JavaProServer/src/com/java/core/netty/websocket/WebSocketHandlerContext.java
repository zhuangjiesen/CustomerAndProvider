package com.java.core.netty.websocket;

import io.netty.channel.ChannelHandlerContext;


/*
* websocket
* ChannelHandlerContext 对象
*
* */
public class WebSocketHandlerContext {
	private String id;
	private String name;
	private ChannelHandlerContext channelHandlerContext;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}
	public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}
	
	
	
}