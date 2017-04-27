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
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangjiesen on 2017/3/17.
 */
public class ThriftConnectionPool implements InitializingBean {
    //默认协议
    private final Class DEFAULT_PROTOCOL_CLASS = TBinaryProtocol.class;
    private final int SCHEDULE_TIME=5;


    private String host;
    private int port;
    private int minConnections = 5;
    private int maxConnections = 10;


//    private volatile int connectionsCount = 0;
    private volatile AtomicInteger connectionsCount = new AtomicInteger(0);

    private int connectTimeout;
    private int socketTimeout;
    private int timeout;

    private Class protocolTypeClass;

    private int waitTimeout = 300;

    private int waitQueueSeconds = 10 ;

    //保持存活的连接时间 秒
    private int keepAlive=5;

    // TProtocol 连接
    private LinkedBlockingQueue<ProtocolManager> blockingQueue;
//    private LinkedBlockingQueue<Thread> waitingThreadBlockingQueue ;
    //公平锁 排队处理
    private Lock threadLock = new ReentrantLock(true);

    private Lock createLock = new ReentrantLock(true);


    private ThreadLocal<ProtocolManager> protocolLocal = new ThreadLocal<ProtocolManager>();


    //回收线程
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);;

    public ThriftConnectionPool() {

    }

    //初始化连接池
    public synchronized void initThriftConnectionPool(){
        blockingQueue = new LinkedBlockingQueue<ProtocolManager>();
        for (int i = 0 ; i < minConnections ; i++) {
            ProtocolManager protocolManager = new ProtocolManager();
            protocolManager.setProtocol(createNewProtocol());
            blockingQueue.add(protocolManager);
        }
        setDefaultProtocolClass();

        //回收线程
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {

                reducePool();


            }
        },SCHEDULE_TIME,SCHEDULE_TIME, TimeUnit.SECONDS);

    }

    public void setDefaultProtocolClass(){
        if (protocolTypeClass != null) {
            return ;
        }
        protocolTypeClass = DEFAULT_PROTOCOL_CLASS;
    }


    //创建协议
    public TProtocol createNewProtocol(){
        TProtocol protocol = null;


        createLock.lock();
        try {
            if (connectionsCount.get() < maxConnections) {

                TSocket socket = new TSocket(host,port);
                socket.setConnectTimeout(connectTimeout);
                socket.setSocketTimeout(socketTimeout);
                socket.setTimeout(timeout);

                TFramedTransport framedTransport = new TFramedTransport(socket);

                setDefaultProtocolClass();
                Constructor protocalConstructor = protocolTypeClass.getConstructor(TTransport.class);
                protocol =(TProtocol) protocalConstructor.newInstance(framedTransport);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (protocol != null) {
                connectionsCount.incrementAndGet();
                try {
                    protocol.getTransport().open();
                } catch (TTransportException e) {
                    e.printStackTrace();
                }
            }
            createLock.unlock();
        }



        return protocol;
    }



    //从连接池中获取Protocol
    public TProtocol getProtocolInternal(){
        protocolLocal.remove();

        TProtocol protocol = null;

        ProtocolManager protocolManager = blockingQueue.poll();
        if (protocolManager != null) {
            protocol = protocolManager.getProtocol();
            if (protocol != null && (!protocol.getTransport().isOpen())) {
                //取到 protocol 但是已经关闭 重新创建
                protocol = createNewProtocol();
                protocolManager.setProtocol(protocol);
            }

        } else {
            protocol = createNewProtocol();
            if (protocol != null) {
                //创建新的
                protocolManager = new ProtocolManager();
                protocolManager.setProtocol(protocol);
            } else {
                //没有就等待队列处理
                threadLock.lock();
                try {
//                    waitTimeout
                    // waitTimeout 等待
                    protocolManager = blockingQueue.poll(waitTimeout , TimeUnit.MILLISECONDS);
//                    protocolManager = blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    threadLock.unlock();
                }
                if (protocolManager == null) {
                    throw new RuntimeException("连接等待超时");
                }

                protocol = protocolManager.getProtocol();
                if (protocol != null && (!protocol.getTransport().isOpen())) {
                    //取到 protocol 但是已经关闭 重新创建
                    protocol = createNewProtocol();
                    protocolManager.setProtocol(protocol);
                }
            }

        }


        if (protocolManager != null) {
            protocol = protocolManager.getProtocol();
        }
        protocolLocal.set(protocolManager);
        return protocol;
    }






    public TProtocol getProtocol(String serviceName){
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(getProtocolInternal(),serviceName);
        return multiplexedProtocol;
    }




    public void recycleProtocol(){
        ProtocolManager protocolManager = protocolLocal.get();
        TProtocol protocol = protocolManager.getProtocol();
        if (protocolManager != null) {
            // Transport 已经关闭 重新创建
            if (protocolManager.getProtocol().getTransport() != null && (!protocolManager.getProtocol().getTransport().isOpen())) {
                protocol = createNewProtocol();
                protocolManager.setProtocol(protocol);
            }
            blockingQueue.add(protocolManager);
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

            ProtocolManager protocolManager = blockingQueue.take();
            while (protocolManager != null) {
                if (protocolManager.getProtocol().getTransport().isOpen()) {
                    protocolManager.getProtocol().getTransport().close();
                }
                protocolManager = blockingQueue.take();
                connectionsCount.decrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
    * 定时器处理多出的连接长期不适用 lru
    * */
    private void reducePool(){

        System.out.println("回收线程");
        if (connectionsCount.get() > minConnections) {

            ProtocolManager protocolManager = blockingQueue.peek();
            long nowTime = System.currentTimeMillis();

            int count = connectionsCount.get();
            while (protocolManager != null) {
                if (connectionsCount.get() <= minConnections) {
                    break;
                }
                //遍历完出队列
                if (count < 0) {
                    break;
                }

                long keepAliveTime = keepAlive * 1000;

                long lru = protocolManager.getLru();
                //已经 有 keepAlive 秒没有用到了  连接关闭
                if ((nowTime - lru) > keepAliveTime) {

                    System.out.println("开始回收连接...");

                    try {
                       TProtocol protocol = protocolManager.getProtocol();
                        //关闭连接
                        if (protocol != null && protocol.getTransport() != null && (protocol.getTransport().isOpen())){
                            protocol.getTransport().close();
                        }
                    } finally {
                        blockingQueue.poll();
                        //连接减一
                        connectionsCount.decrementAndGet();
                    }

                }

                protocolManager = blockingQueue.peek();
                count --;
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

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }


    public int getWaitTimeout() {
        return waitTimeout;
    }

    public void setWaitTimeout(int waitTimeout) {
        this.waitTimeout = waitTimeout;
    }
}
