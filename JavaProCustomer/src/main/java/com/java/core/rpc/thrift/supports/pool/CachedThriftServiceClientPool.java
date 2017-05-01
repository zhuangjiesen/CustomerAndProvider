package com.java.core.rpc.thrift.supports.pool;

import com.java.core.rpc.thrift.supports.constant.ThriftConstant;
import com.java.core.rpc.thrift.supports.core.ThriftClient;
import com.java.core.rpc.thrift.supports.utils.ThriftUtils;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 实例池，实例复用
 * Created by zhuangjiesen on 2017/4/30.
 */
public class CachedThriftServiceClientPool implements ServiceClientPool ,InitializingBean {



    private ConnectionPool connectionPool;


    private List<String> serviceNames ;


    private int minObjectCounts = 5;
    private ThreadLocal<Object> objectLocal;
    private LinkedBlockingQueue<Object> objectQueue;


    private volatile AtomicInteger objectCount = new AtomicInteger(0);


    private ConcurrentHashMap<String , ThriftClient> thriftClientCache = new ConcurrentHashMap<String, ThriftClient>();

    public Object getClientInstance(String serviceName){

        ThriftClient thriftClient = thriftClientCache.get(serviceName);

        objectLocal.remove();

        Object clientInstance = null;

        try {
            clientInstance = objectQueue.poll();
            if (clientInstance != null) {

                TProtocol protocol = connectionPool.getProtocol(serviceName) ;


                Class clientClazz = thriftClient.getClientClazz();
                Field iprot_ = clientClazz.getSuperclass().getDeclaredField("iprot_");
                iprot_.setAccessible(true);
                iprot_.set(clientInstance , protocol);

                Field oprot_ = clientClazz.getDeclaredField("oprot_");
                oprot_.setAccessible(true);
                oprot_.set(clientInstance , protocol);
            } else {
                //空
                clientInstance = createNewClientInstance();
                if (clientInstance == null) {
                    clientInstance = objectQueue.take();
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        objectLocal.set(clientInstance);
        return clientInstance;
    }


    public void recycleClient(){
        Object recycleClient = objectLocal.get();
        try {
            if (recycleClient != null) {
                objectQueue.add(recycleClient);
            }
        } finally {
            objectLocal.remove();

        }


        connectionPool.recycleProtocol();
    }


    public void onDestroy(){


    }

    public Object createNewClientInstance(String serviceName ){
        Object newClient = null;
        if (objectCount.get() < minObjectCounts) {
            try {

                ThriftClient thriftClient = thriftClientCache.get(serviceName);
                Constructor constructor = thriftClient.getClientClazz().getConstructor(TProtocol.class);

                //  protocol
                TProtocol protocol = connectionPool.getProtocol(serviceName) ;

                // 初始化服务实例 client
                newClient = constructor.newInstance(protocol);
                objectQueue.add(newClient);
            } catch (Exception e) {
                //异常处理
                // TODO: handle exception
                e.printStackTrace();
            } finally {
            }
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
