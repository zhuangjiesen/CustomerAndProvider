package com.java.core.netty.websocket;

import com.alibaba.fastjson.JSONObject;

public class WebSocketMessage {
	
	private Integer messageType;
	private String sendName;
	private String receiveName ;
	private String content;
	private String ipAddress;

	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 生成json串传到前端
	 */
	public String toJSONString() {
		return JSONObject.toJSONString(this);
	}

}