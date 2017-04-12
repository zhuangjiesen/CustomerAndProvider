package com.java.core.netty.websocket.adapter;

import java.util.Date;

public class WebSocketMsg {
	
	
	/**
	 * 发送人
	 */
	private String sendName ;
	/**
	 * 接收人
	 */
	private String receiveName ;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息状态
	 * 1 发送成功
	 * 2 发送失败
	 * 3未找到接收人
	 */
	private Integer msgStatus;
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
	public Date getCreateTime() {
		if (createTime == null) {
			createTime = new Date();
		}
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
	
	
	

}