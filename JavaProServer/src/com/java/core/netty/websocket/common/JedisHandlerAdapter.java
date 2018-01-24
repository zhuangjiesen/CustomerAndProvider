package com.java.core.netty.websocket.common;

import com.dragsun.jedis.JedisCommandManager;
import com.dragsun.jedis.JedisPoolWrapper;
import com.dragsun.jedis.RedisServerState;
import com.dragsun.jedis.manager.JedisManager;
import com.dragsun.jedis.utils.RedisServerHelper;
import com.dragsun.websocket.adapter.KeepAliveHandlerAdapter;
import com.dragsun.websocket.annotation.WSRequestMapping;
import com.dragsun.websocket.cache.WebSocketCacheManager;
import com.dragsun.websocket.cache.WebSocketClient;
import com.dragsun.websocket.utils.MessageUtils;
import com.java.helper.BeanHelper;
import com.java.service.JedisTestService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * 定位数据处理器
 * Created by zhuangjiesen on 2017/9/13.
 */

@WSRequestMapping(uri = "/jedis")
public class JedisHandlerAdapter extends KeepAliveHandlerAdapter<TextWebSocketFrame> {



    @Override
    public void handlerWebSocketFrameData(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
        System.out.println(" ---- LocationHandlerAdapter .....handlerWebSocketFrameData ....");

        String content = webSocketFrame.text();
        switch (content) {
            //测试读
            case "testRead":
                {
                    String result = applicationContext.getBean(JedisTestService.class).getString("mykeytest");
                    TextWebSocketFrame text = new TextWebSocketFrame(result);
                    ctx.channel().writeAndFlush(text);
                }
                break;
            // 测试写
            case "testWrite":
                {
                    String result = applicationContext.getBean(JedisTestService.class).setString("mykeytest" , "zhuangjiesen" );
                    TextWebSocketFrame text = new TextWebSocketFrame(result);
                    ctx.channel().writeAndFlush(text);
                }
                break;
            // 测试其他命令
            case "testOther":
                {
                    Long result = applicationContext.getBean(JedisTestService.class).incr("mykeytest-ince");
                    TextWebSocketFrame text = new TextWebSocketFrame(result.toString());
                    ctx.channel().writeAndFlush(text);
                }
                break;
            //测试重新检测服务状态
            case "testManagerReset":
                {
                    Long result = applicationContext.getBean(JedisTestService.class).incr("mykeytest-ince");
                    TextWebSocketFrame text = new TextWebSocketFrame(result.toString());
                    ctx.channel().writeAndFlush(text);
                }
                break;
            //测试添加slave 并生效
            case "testAddSlave":
                {
                    RedisServerState jedisState = applicationContext.getBean(JedisManager.class).addSlave();
                    List<JedisPoolWrapper> slaves = jedisState.getSlaves();
                    StringJoiner joiner = new StringJoiner("\n\r  <br> ");
                    if (slaves != null) {
                        for (JedisPoolWrapper wrapper : slaves) {
                            Jedis jedis = null;
                            try {
                                jedis = wrapper.getJedisPool().getResource();

                                String result = RedisServerHelper.logRedisInfo(jedis);
                                result = " slave : " + result;
                                joiner.add(result);
                            } finally {
                                if (jedis != null) {
                                    jedis.close();
                                }
                            }
                        }
                    } else {
                        String result = " not slaves  " ;
                        joiner.add(result);
                    }

                    Jedis jedis = null;
                    try {
                        jedis = jedisState.getMaster().getJedisPool().getResource();
                        String result = RedisServerHelper.logRedisInfo(jedis);
                        result = " master : " + result;
                        joiner.add(result);
                    } finally {
                        if (jedis != null) {
                            jedis.close();
                        }
                    }


                    TextWebSocketFrame text = new TextWebSocketFrame(joiner.toString());
                    ctx.channel().writeAndFlush(text);
                }
                break;
            //查看服务器连接测试
            case "testManagerInfo":
                {
                    RedisServerState jedisState = applicationContext.getBean(JedisManager.class).getCurRedisServerState();
                    List<JedisPoolWrapper> slaves = jedisState.getSlaves();
                    StringJoiner joiner = new StringJoiner("\n\r  <br> ");
                    if (slaves != null) {
                        for (JedisPoolWrapper wrapper : slaves) {
                            Jedis jedis = null;
                            try {
                                jedis = wrapper.getJedisPool().getResource();

                                String result = RedisServerHelper.logRedisInfo(jedis);
                                result = " slave : " + result;
                                joiner.add(result);
                            } finally {
                                if (jedis != null) {
                                    jedis.close();
                                }
                            }
                        }
                    } else {
                        String result = " not slaves  " ;
                        joiner.add(result);
                    }

                    Jedis jedis = null;
                    try {
                        jedis = jedisState.getMaster().getJedisPool().getResource();
                        String result = RedisServerHelper.logRedisInfo(jedis);
                        result = " master : " + result;
                        joiner.add(result);
                    } finally {
                        if (jedis != null) {
                            jedis.close();
                        }
                    }


                    TextWebSocketFrame text = new TextWebSocketFrame(joiner.toString());
                    ctx.channel().writeAndFlush(text);
                }
                break;
            default:
                break;
        }

        System.out.println("LocationHandlerAdapter ....content : " + content );


    }


    @Override
    public void handleResponse(Map<String, Object> params) {
        System.out.println(" ---- LocationHandlerAdapter .....handleResponse ....");

        String message = (String) params.get("message");
        WebSocketCacheManager wcm = applicationContext.getBean(WebSocketCacheManager.class);
        String uri = getUri();
        Collection<WebSocketClient> clients = wcm.getClientsByUri(uri);
        //批量发送数据
        MessageUtils.sendMessage(clients , message);


    }


    @Override
    public void onUpgradeCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {

    }

}
