package com.java.core.netty.websocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * websocket消息管理类
 * Created by zhuangjiesen on 2017/4/14.
 */
public class WebSocketMessageManager {


    //用户集合
    public static ConcurrentHashMap<String, WebSocketHandlerContext> sendNameAndContextMap = new ConcurrentHashMap<String, WebSocketHandlerContext>();
    //channelid与用户名map
    private static ConcurrentHashMap<String, String> channelIdAndsendNameMap = new ConcurrentHashMap<String, String>();


    /*注册限制方式爆刷*/
    private final static int ipRegistLimit = 3;

    //ip列表 ，一个ip登陆次数
    private static ConcurrentHashMap<String, Integer> ipMap = new ConcurrentHashMap<String, Integer>();


    /** 在线人数统计 线程**/
    private static ScheduledExecutorService executorService = null;

    //2 秒推送
    private static int timeSeconds = 2;
    //用户新增
    private static AtomicBoolean hasOnlineUserAdd = new AtomicBoolean(false);
    //用户减少
    private static AtomicBoolean hasOnlineUserReduce = new AtomicBoolean(false);




    public WebSocketMessageManager() {
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


    /*
    * 给前端推送在线用户
    * */
    public void pushOnlineList(){

        Enumeration<String> keys = sendNameAndContextMap.keys();
        Collection values = null;
        while (keys.hasMoreElements()) {
            if (values == null) {
                values = channelIdAndsendNameMap.values();
            }

            String key = keys.nextElement();
            WebSocketHandlerContext webSocketHandlerContext = sendNameAndContextMap.get(key);
            Channel channel = webSocketHandlerContext.getChannelHandlerContext().channel();
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

    /*
    * 注册聊天用户名
    * */
    public boolean registWebSocket(WebSocketFrame frame , ChannelHandlerContext ctx ,WebSocketMessage webSocketMessage ){
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
        WebSocketHandlerContext webSocketHandlerContext = new WebSocketHandlerContext();
        webSocketHandlerContext.setId(ctx.channel().id().asLongText());
        webSocketHandlerContext.setName(sendName);
        webSocketHandlerContext.setChannelHandlerContext(ctx);

        sendNameAndContextMap.put(sendName, webSocketHandlerContext);
        channelIdAndsendNameMap.put(ctx.channel().id().asLongText(), sendName);
        WebSocketHandlerContext handlerContext = sendNameAndContextMap.get(sendName);
        if (handlerContext != null) {
            System.out.println("注册成功");

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





    /**
     * @Description: TODO
     * 是否是注册消息
     * @author zhuangjiesen
     * @date 2017年4月11日 上午10:32:57
     * @param request
     * @return
     */
    public WebSocketMessage isRegistWebSocket(WebSocketFrame frame , ChannelHandlerContext ctx ){
        WebSocketMessage webSocketMessage = null;
        String requestContent = ((TextWebSocketFrame) frame).text();
        if (requestContent != null && (!requestContent.isEmpty())) {
            webSocketMessage = JSONObject.parseObject(requestContent, WebSocketMessage.class);
            if (webSocketMessage != null) {
                Integer messageType = webSocketMessage.getMessageType();
                if (messageType != null) {
                    switch (messageType.intValue()) {
                        case 1: {
                            return webSocketMessage;
                        }
                        default:
                            break;
                    }
                } else {
                    throw new RuntimeException("消息类型为空");
                }
            }

        }
        return null;
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
     * 发送消息
     * @author zhuangjiesen
     * @date 2017年4月11日 上午11:20:14
     * @param webSocketMsg
     * @return
     *
     */
    public int sendMsg (WebSocketMessage webSocketMsg ){
        int result = 0;
        WebSocketHandlerContext handlerContext = sendNameAndContextMap.get(webSocketMsg.getReceiveName());
        if (handlerContext != null) {
            webSocketMsg.setMessageType(7);
            TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMsg.toJSONString());
            handlerContext.getChannelHandlerContext().channel().writeAndFlush( tws);
            result = 1;
        } else {
            //找不到发送对象

            return 8;
        }
        return result;
    }



    /*
    * 是否被限制注册
    * */
    public boolean isIpRegistLimited (String host ){
        Integer times = ipMap.get(host);
        if (times != null && times.intValue() > (ipRegistLimit - 1)) {
            return true;
        }

        return false;
    }

    /*
    * 注册
    * */
    public void registIp (String host ){
        Integer times = ipMap.get(host);
        if (times != null) {
            ipMap.put(host , times.intValue() + 1);
        } else {
            ipMap.put(host , 1);
        }

    }


    /*
    * 注销ip
    * */
    public synchronized void unregistIp (String host ){
        Integer times = ipMap.get(host);
        if (times != null ) {
            if (times.intValue() > 1) {
                ipMap.put(host, times.intValue() - 1);
            } else {
                ipMap.remove(host);
            }

        }
    }




}
