package com.java.core.rpc.thrift.supports.pool;

import com.java.core.rpc.thrift.supports.pool.bo.TSocketFactory;
import com.java.core.rpc.thrift.supports.pool.bo.ThriftConnection;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangjiesen on 2017/4/30.
 */
public class AppThriftConnectionPool  implements InitializingBean ,ConnectionPool {

    private TSocketFactory socketFactory;
    private TTransportFactory transportFactory;
    private TProtocolFactory protocolFactory;

    private int minConnections = 5;
    private int maxConnections = 10;


    //线程轮询时间 秒
    private final int recycle_time=5;

    // 原子类型 连接总数
    private volatile AtomicInteger connectionsCount = new AtomicInteger(0);


    // 等待响应时间 秒
    private int waitTimeout = 300;

    // 线程等待时间 （等待连接池 ） 秒
    private int waitQueueSeconds = 10 ;

    //保持存活的连接时间 秒 根据 Lru 时间
    private int keepAlive=5;


    // TProtocol 连接
    private LinkedBlockingQueue<ThriftConnection> blockingQueue;


    //公平锁 排队处理
    private final Lock threadLock = new ReentrantLock(true);

    private final Lock createLock = new ReentrantLock(true);


    private ThreadLocal<ThriftConnection> protocolLocal = new ThreadLocal<ThriftConnection>();


    //回收线程
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);;



    public TProtocol getProtocol(String serviceName){


        return new TMultiplexedProtocol(getProtocolInternal(),serviceName);
    }



    //从连接池中获取Protocol
    public TProtocol getProtocolInternal(){
        protocolLocal.remove();

        TProtocol protocol = null;

        ThriftConnection thrfitConnection = blockingQueue.poll();
        if (thrfitConnection != null) {
            protocol = thrfitConnection.getProtocol();
            if (protocol != null && (!protocol.getTransport().isOpen())) {
                //取到 protocol 但是已经关闭 重新创建
                protocol = createNewProtocol();
                thrfitConnection.setProtocol(protocol);
            }

        } else {
            protocol = createNewProtocol();
            if (protocol != null) {
                //创建新的
                thrfitConnection = new ThriftConnection();
                thrfitConnection.setProtocol(protocol);
            } else {
                //没有就等待队列处理
                threadLock.lock();
                try {
//                    waitTimeout
                    // waitTimeout 等待
                    thrfitConnection = blockingQueue.poll(waitTimeout , TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    threadLock.unlock();
                }
                if (thrfitConnection == null) {
                    throw new RuntimeException("连接等待超时");
                }

                protocol = thrfitConnection.getProtocol();
                if (protocol != null && (!protocol.getTransport().isOpen())) {
                    //取到 protocol 但是已经关闭 重新创建
                    protocol = createNewProtocol();
                    thrfitConnection.setProtocol(protocol);
                }
            }

        }


        if (thrfitConnection != null) {
            protocol = thrfitConnection.getProtocol();
        }
        protocolLocal.set(thrfitConnection);
        return protocol;
    }






    public void recycleProtocol(){

        ThriftConnection thriftConnection = protocolLocal.get();

        TProtocol protocol = thriftConnection.getProtocol();
        if (thriftConnection != null) {
            // Transport 已经关闭 重新创建
            if (thriftConnection.getProtocol().getTransport() != null && (!thriftConnection.getProtocol().getTransport().isOpen())) {
                protocol = createNewProtocol();
                thriftConnection.setProtocol(protocol);
            }
            blockingQueue.add(thriftConnection
            );
        }
        protocolLocal.remove();
    }




    //创建协议
    public TProtocol createNewProtocol(){
        TProtocol protocol = null;


        createLock.lock();
        try {
            if (connectionsCount.get() < maxConnections) {

                TSocket socket = socketFactory.newTSocket();
                TTransport transport = transportFactory.getTransport(socket);
                protocol =protocolFactory.getProtocol(transport);
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




    public void initDefault(){



    }




    //初始化连接池
    public synchronized void initThriftConnectionPool(){
        blockingQueue = new LinkedBlockingQueue<ThriftConnection>();
        for (int i = 0 ; i < minConnections ; i++) {
            ThriftConnection ThriftConnection = new ThriftConnection();
            ThriftConnection.setProtocol(createNewProtocol());
            blockingQueue.add(ThriftConnection);
        }

        //回收线程
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {

                reducePool();


            }
        },recycle_time,recycle_time, TimeUnit.SECONDS);

    }




    /*
    * 定时器处理多出的连接长期不适用 lru
    * */
    private void reducePool(){

        System.out.println("回收线程");
        if (connectionsCount.get() > minConnections) {

            ThriftConnection ThriftConnection = blockingQueue.peek();
            long nowTime = System.currentTimeMillis();

            int count = connectionsCount.get();
            while (ThriftConnection != null) {
                if (connectionsCount.get() <= minConnections) {
                    break;
                }
                //遍历完出队列
                if (count < 0) {
                    break;
                }

                long keepAliveTime = keepAlive * 1000;

                long lru = ThriftConnection.getLru();
                //已经 有 keepAlive 秒没有用到了  连接关闭
                if ((nowTime - lru) > keepAliveTime) {

                    System.out.println("开始回收连接...");

                    try {
                        TProtocol protocol = ThriftConnection.getProtocol();
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

                ThriftConnection = blockingQueue.peek();
                count --;
            }

        }

    }



    //关闭连接
    public synchronized void onDestroy(){
        try {

            ThriftConnection ThriftConnection = blockingQueue.take();
            while (ThriftConnection != null) {
                if (ThriftConnection.getProtocol().getTransport().isOpen()) {
                    ThriftConnection.getProtocol().getTransport().close();
                }
                ThriftConnection = blockingQueue.take();
                connectionsCount.decrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void afterPropertiesSet() throws Exception {
        initThriftConnectionPool();

    }


    public TSocketFactory getSocketFactory() {
        return socketFactory;
    }

    public void setSocketFactory(TSocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    public TTransportFactory getTransportFactory() {
        return transportFactory;
    }

    public void setTransportFactory(TTransportFactory transportFactory) {
        this.transportFactory = transportFactory;
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }

    public void setProtocolFactory(TProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
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

    public int getRecycle_time() {
        return recycle_time;
    }

    public AtomicInteger getConnectionsCount() {
        return connectionsCount;
    }

    public void setConnectionsCount(AtomicInteger connectionsCount) {
        this.connectionsCount = connectionsCount;
    }

    public int getWaitTimeout() {
        return waitTimeout;
    }

    public void setWaitTimeout(int waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    public int getWaitQueueSeconds() {
        return waitQueueSeconds;
    }

    public void setWaitQueueSeconds(int waitQueueSeconds) {
        this.waitQueueSeconds = waitQueueSeconds;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public LinkedBlockingQueue<ThriftConnection> getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(LinkedBlockingQueue<ThriftConnection> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public Lock getThreadLock() {
        return threadLock;
    }

    public Lock getCreateLock() {
        return createLock;
    }

    public ThreadLocal<ThriftConnection> getProtocolLocal() {
        return protocolLocal;
    }

    public void setProtocolLocal(ThreadLocal<ThriftConnection> protocolLocal) {
        this.protocolLocal = protocolLocal;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
