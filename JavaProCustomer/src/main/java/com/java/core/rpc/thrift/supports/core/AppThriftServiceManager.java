package com.java.core.rpc.thrift.supports.core;

import com.java.core.rpc.thrift.supports.pool.AppThriftServiceClientPool;
import com.java.core.rpc.thrift.supports.pool.ServiceClientPool;
import com.java.core.rpc.thrift.supports.proxy.JdkThriftClientProxy;

import java.lang.reflect.Proxy;

/**
 * Created by zhuangjiesen on 2017/4/30.
 */
public class AppThriftServiceManager {



    private ServiceClientPool serviceClientPool;




    public <T> T getClient(Class<T> serviceIfaceClass){
        if (!serviceIfaceClass.isInterface()) {
            throw new RuntimeException("类型错误");
        }
        T client =null;
        JdkThriftClientProxy proxyInvocation =new JdkThriftClientProxy();
        // 代理接口类
        proxyInvocation.setIfaceClazz(serviceIfaceClass);
        proxyInvocation.setServiceClientPool(serviceClientPool);
        client = (T) Proxy.newProxyInstance(serviceIfaceClass.getClassLoader(), new Class[]{ serviceIfaceClass }, proxyInvocation);
        return client;
    }


    public ServiceClientPool getServiceClientPool() {
        return serviceClientPool;
    }

    public void setServiceClientPool(ServiceClientPool serviceClientPool) {
        this.serviceClientPool = serviceClientPool;
    }
}
