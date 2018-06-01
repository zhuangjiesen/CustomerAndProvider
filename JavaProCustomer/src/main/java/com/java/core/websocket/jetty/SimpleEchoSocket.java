package com.java.core.websocket.jetty;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/6/1
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class SimpleEchoSocket {


    private final CountDownLatch closeLatch;
    @SuppressWarnings("unused")
    /** websocket 连接对象 **/
    private Session session;

    public SimpleEchoSocket()
    {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException
    {
        return this.closeLatch.await(duration,unit);
    }


    @OnWebSocketError
    public void onError(Throwable throwable)
    {
        System.out.printf("throwable : %s ", throwable);

    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.printf("Connection closed: %d - %s%n",statusCode,reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session)
    {
        System.out.printf("Got connect: %s%n",session);
        this.session = session;
        try
        {
            Future<Void> fut;

            fut = session.getRemote().sendStringByFuture("hello websocket server ....");
            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.


        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg)
    {
        System.out.printf("Got msg: %s%n",msg);
        System.out.println("----------------- \r\n \r\n\r\n ");
    }


}
