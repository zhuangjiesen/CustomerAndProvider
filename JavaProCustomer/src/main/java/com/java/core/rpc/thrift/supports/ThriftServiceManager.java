package com.java.core.rpc.thrift.supports;

import org.apache.thrift.protocol.TProtocol;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhuangjiesen on 2017/3/17.
 */
public class ThriftServiceManager {

    private ThriftConnectionPool thriftConnectionPool;
    private IThriftExceptionResolver thriftExceptionResolver;




    public <T> T getThriftClient(Class<T> serviceIfaceClass){
        if (!serviceIfaceClass.isInterface()) {
            throw new RuntimeException("类型错误");
        }
        T client =null;
        ThriftServiceProxyInvocation proxyInvocation =new ThriftServiceProxyInvocation();
        proxyInvocation.setThriftExceptionResolver(thriftExceptionResolver);
        // 代理接口类
        proxyInvocation.setIfaceClazz(serviceIfaceClass);
        // 设置连接池
        proxyInvocation.setThriftConnectionPool(thriftConnectionPool);

        client = (T) Proxy.newProxyInstance(serviceIfaceClass.getClassLoader(), new Class[]{ serviceIfaceClass }, proxyInvocation);



        return client;
    }


    public ThriftConnectionPool getThriftConnectionPool() {
        return thriftConnectionPool;
    }

    public void setThriftConnectionPool(ThriftConnectionPool thriftConnectionPool) {
        this.thriftConnectionPool = thriftConnectionPool;
    }
}
