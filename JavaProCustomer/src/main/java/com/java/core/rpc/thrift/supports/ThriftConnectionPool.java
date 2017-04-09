package com.java.core.rpc.thrift.supports;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangjiesen on 2017/3/17.
 */
public class ThriftConnectionPool implements InitializingBean {
    //默认协议
    private final Class DEFAULT_PROTOCOL_CLASS = TBinaryProtocol.class;


    private String host;
    private int port;
    private int minConnections = 5;
    private int maxConnections = 10;
    private volatile int connectionsCount = 0;
    private int connectTimeout;
    private int socketTimeout;
    private int timeout;

    private Class protocolTypeClass;



    private int waitQueueSeconds = 10 ;
    private int recycleSeconds=10;

    // TProtocol 连接
    private LinkedBlockingQueue<TProtocol> blockingQueue;
//    private LinkedBlockingQueue<Thread> waitingThreadBlockingQueue ;
    //公平锁 排队处理
    private Lock threadLock = new ReentrantLock(true);

    private ThreadLocal<TProtocol> protocolLocal = new ThreadLocal<TProtocol>();


    //回收线程
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);;

    public ThriftConnectionPool() {

    }

    //初始化连接池
    public synchronized void initThriftConnectionPool(){
        blockingQueue = new LinkedBlockingQueue<TProtocol>();
        for (int i = 0 ; i < minConnections ; i++) {
            blockingQueue.add(createNewProtocol());
        }
        setDefaultProtocolClass();


        //回收线程
        scheduledExecutorService.schedule(new Runnable() {
            public void run() {


                reducePool();


            }
        },recycleSeconds, TimeUnit.SECONDS);

    }

    public void setDefaultProtocolClass(){
        if (protocolTypeClass != null) {
            return ;
        }
        protocolTypeClass = DEFAULT_PROTOCOL_CLASS;
    }


    //创建协议
    public synchronized TProtocol createNewProtocol(){
        TProtocol protocol = null;
        if (connectionsCount < maxConnections) {
            try {
                TSocket socket = new TSocket(host,port);
                socket.setConnectTimeout(connectTimeout);
                socket.setSocketTimeout(socketTimeout);
                socket.setTimeout(timeout);



                TFramedTransport framedTransport = new TFramedTransport(socket);




                setDefaultProtocolClass();
                Constructor protocalConstructor = protocolTypeClass.getConstructor(TTransport.class);
                protocol =(TProtocol) protocalConstructor.newInstance(framedTransport);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (protocol != null) {
                    connectionsCount ++ ;
                    try {
                        protocol.getTransport().open();
                    } catch (TTransportException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return protocol;
    }

    //从连接池中获取Protocol
    public TProtocol getProtocolInternal(){
        protocolLocal.remove();
        TProtocol protocol = null;
        protocol = blockingQueue.poll();
        if (protocol == null) {
            protocol = createNewProtocol();
            //大于最大连接数创建 protocol = null
            if (protocol == null) {
                threadLock.lock();
                try {
                    protocol = blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    threadLock.unlock();
                }

            }
//            waitingThreadBlockingQueue

        } else if (protocol != null && (!protocol.getTransport().isOpen())) {
            //取到 protocol 但是已经关闭 重新创建
            protocol = createNewProtocol();
        }
        protocolLocal.set(protocol);
        return protocol;
    }


    public TProtocol getProtocol(String serviceName){
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(getProtocolInternal(),serviceName);
        return multiplexedProtocol;
    }

    public void recycleProtocol(){
        TProtocol protocol = protocolLocal.get();
        if (protocol != null) {
            if (protocol.getTransport() != null && (!protocol.getTransport().isOpen())) {
                protocol = createNewProtocol();
            }
            blockingQueue.add(protocol);
        }
        protocolLocal.remove();
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }


    public int getWaitQueueSeconds() {
        return waitQueueSeconds;
    }

    public void setWaitQueueSeconds(int waitQueueSeconds) {
        this.waitQueueSeconds = waitQueueSeconds;
    }

    public void afterPropertiesSet() throws Exception {
        initThriftConnectionPool();
    }


    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    //关闭连接
    public synchronized void destroy(){
        try {
            TProtocol protocol = blockingQueue.take();
            while (protocol != null) {
                if (protocol.getTransport().isOpen()) {
                    protocol.getTransport().close();
                }
                protocol = blockingQueue.take();
                connectionsCount --;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private synchronized void reducePool(){
        if (connectionsCount > minConnections) {
            int connectionsCountTemp = connectionsCount;
            TProtocol protocol = null;
            for (int i = connectionsCountTemp ; i > minConnections ; i--) {
                try {
                    protocol = blockingQueue.poll(waitQueueSeconds,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
                //关闭连接
                if (protocol != null && protocol.getTransport() != null && (protocol.getTransport().isOpen())){
                    protocol.getTransport().close();
                }

            }

        }

    }


    public Class getProtocolTypeClass() {
        return protocolTypeClass;
    }

    public void setProtocolTypeClass(Class protocolTypeClass) {
        this.protocolTypeClass = protocolTypeClass;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
