package com.java.core.websocket.jetty;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.Future;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/6/1
 */
public class JettyWebSocketTest {



    public static void main(String[] args) {


        //用来处理wss 请求
        SslContextFactory sslContextFactory = new SslContextFactory();
        WebSocketClient client = new WebSocketClient(sslContextFactory);

        //通过注解定义监听websocket的事件
        SimpleEchoSocket simpleEchoSocket = new SimpleEchoSocket();
        try
        {
            client.start();
            URI echoUri = new URI("ws://127.0.0.1:38888");
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            //添加请求头参数
//            request.setHeader("" , "");
            //尝试连接
            Future<Session> fuq = client.connect(simpleEchoSocket , echoUri,request);
            Session session = fuq.get();

            System.out.println("================");

            session.getRemote().sendPartialString("i " , true);
            session.getRemote().sendPartialString("am " , false);
//            session.getRemote().sendStringByFuture("chinese " );
//            session.getRemote().sendStringByFuture("you know? " );
//            session.getRemote().flush();

        }
        catch (Throwable t)
        {
            t.printStackTrace();
            //关闭连接
            if (!client.isStopped()) {
//                client.stop();
            }
        }
        finally
        {

        }

    }



}
