package com.java.core.rpc.thrift.supports.pool;

import com.java.core.rpc.thrift.supports.constant.ThriftConstant;
import com.java.core.rpc.thrift.supports.core.ThriftClient;
import com.java.core.rpc.thrift.supports.utils.ThriftUtils;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 实例池，实例复用
 * Created by zhuangjiesen on 2017/4/30.
 */
public class CachedThriftServiceClientPool implements ServiceClientPool ,InitializingBean {



    private ConnectionPool connectionPool;


    private List<String> serviceNames ;


    private final int minObjectCounts = 100;
    // 只能 minObjectCounts 个线程同时访问
    private final Semaphore createLock = new Semaphore(minObjectCounts);

    private ThreadLocal<Object> objectLocal;
    private LinkedBlockingQueue<Object> objectQueue;


    private volatile AtomicInteger atomicSeqid_ = new AtomicInteger(0);


    private volatile AtomicInteger objectCount = new AtomicInteger(0);


    private ConcurrentHashMap<String , ThriftClient> thriftClientCache = new ConcurrentHashMap<String, ThriftClient>();

    //公平锁 排队处理
    private final Lock threadLock = new ReentrantLock(true);


    public Object getClientInstance(String serviceName){
        objectLocal.remove();

        Object instance = null;
        try {



            instance = objectQueue.poll();
            boolean isCreateNew = false;
            if (instance != null) {
            } else {
                //空

                instance = createNewClientInstance(serviceName);
                if (instance == null) {
                    instance = objectQueue.take();
                } else {
                    isCreateNew = true;
                }

            }


            if (!isCreateNew) {
                TProtocol protocol = connectionPool.getProtocol(serviceName) ;
                while (protocol == null) {
                    protocol = connectionPool.getProtocol(serviceName) ;
                }

                ThriftClient thriftClient = thriftClientCache.get(serviceName);
                Class clientClazz = thriftClient.getClientClazz();

                Field iprot_ = clientClazz.getSuperclass().getDeclaredField("iprot_");
                iprot_.setAccessible(true);
                iprot_.set(instance , protocol);

                Field seqid_ = clientClazz.getSuperclass().getDeclaredField("seqid_");
                seqid_.setAccessible(true);
                seqid_.set(instance , atomicSeqid_.incrementAndGet());

                Field oprot_ = clientClazz.getSuperclass().getDeclaredField("oprot_");
                oprot_.setAccessible(true);
                oprot_.set(instance , protocol);
            }


//            this.setTProtocol(protocol , clientClazz , clientInstance);


        } catch (Exception e) {
            e.printStackTrace();
        }

        objectLocal.set(instance);
        return instance;
    }



    private void setTProtocol(TProtocol protocol ,Class clientClazz ,Object clientInstance){
        if (clientInstance == null) {
            return ;
        }
        try {
            Field iprot_ = clientClazz.getSuperclass().getDeclaredField("iprot_");
            iprot_.setAccessible(true);
            iprot_.set(clientInstance , protocol);
            Field seqid_ = clientClazz.getSuperclass().getDeclaredField("seqid_");
            seqid_.setAccessible(true);
            seqid_.set(clientInstance , 0);



            Field oprot_ = clientClazz.getSuperclass().getDeclaredField("oprot_");
            oprot_.setAccessible(true);
            oprot_.set(clientInstance , protocol);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  void recycleClient(){
        Object recycleClient = objectLocal.get();
        try {
            if (recycleClient != null) {
                Class clientClazz = recycleClient.getClass();

                Field iprot_ = clientClazz.getSuperclass().getDeclaredField("iprot_");
                iprot_.setAccessible(true);
                iprot_.set(recycleClient , null);
                Field seqid_ = clientClazz.getSuperclass().getDeclaredField("seqid_");
                seqid_.setAccessible(true);
                seqid_.set(recycleClient , -1);



                Field oprot_ = clientClazz.getSuperclass().getDeclaredField("oprot_");
                oprot_.setAccessible(true);
                oprot_.set(recycleClient , null);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            objectLocal.remove();

        }


        connectionPool.recycleProtocol();
        objectQueue.add(recycleClient);
    }


    public void onDestroy(){


    }

    public Object createNewClientInstance(String serviceName ){
        Object newClient = null;
        int objectCountTemp = 0;

            try {
                createLock.acquire();
                if ((objectCountTemp = objectCount.get()) < minObjectCounts) {


                    ThriftClient thriftClient = thriftClientCache.get(serviceName);
                    Constructor constructor = thriftClient.getClientClazz().getConstructor(TProtocol.class);

                    //  protocol
                    System.out.println("==wait protocol==");

                    TProtocol protocol = connectionPool.getProtocol(serviceName) ;
                    System.out.println("==protocol==");

                    // 初始化服务实例 client
                    newClient = constructor.newInstance(protocol);
                }
            } catch (Exception e) {
                //异常处理
                // TODO: handle exception
                e.printStackTrace();

            } finally {
                if (newClient != null) {
                    objectCount.incrementAndGet();
                }
                createLock.release();
            }

        return  newClient;
    }


    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public void afterPropertiesSet() throws Exception {
        init();
    }



    public void init(){
        objectLocal = new ThreadLocal<Object>();
        objectQueue = new LinkedBlockingQueue<Object>();




        try {

            String serviceIfaceClassName = ThriftUtils.getIfaceClazz(serviceNames.get(0)).getName();

            String serviceClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,"");



            String serviceClientClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,ThriftConstant.CLIENT_NAME);




            Class clientClazz = Class.forName(serviceClientClassName);


            Constructor constructor = clientClazz.getConstructor(TProtocol.class);


            //封装成对象
            ThriftClient thriftClient = new ThriftClient();
            thriftClient.setServiceIfaceClassName(serviceIfaceClassName);
            thriftClient.setServiceClassName(serviceClassName);
            thriftClient.setServiceClientClassName(serviceClientClassName);
            thriftClient.setClientClazz(clientClazz);

            thriftClientCache.put(serviceClassName , thriftClient);
        } catch (Exception e) {
            //异常处理
            // TODO: handle exception
            e.printStackTrace();
        } finally {
        }




    }


    public List<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(List<String> serviceNames) {
        this.serviceNames = serviceNames;
    }
}
