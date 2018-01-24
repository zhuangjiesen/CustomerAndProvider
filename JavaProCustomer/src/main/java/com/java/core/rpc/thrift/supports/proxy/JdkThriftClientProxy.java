package com.java.core.rpc.thrift.supports.proxy;

import com.java.core.rpc.thrift.supports.pool.AppThriftServiceClientPool;
import com.java.core.rpc.thrift.supports.pool.ServiceClientPool;
import com.java.core.rpc.thrift.supports.utils.ThriftUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * Created by zhuangjiesen on 2017/4/30.
 */
public class JdkThriftClientProxy implements InvocationHandler {

    /*thrift 服务类的iface 类*/
    private Class ifaceClazz;

    private ServiceClientPool serviceClientPool;


    public JdkThriftClientProxy() {

        System.out.println(" public JdkThriftClientProxy ....");

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {



        Object result = null;


        String serviceName = ThriftUtils.getServiceName(ifaceClazz);

        try {
            Object clientInstance = null;


            clientInstance = serviceClientPool.getClientInstance(serviceName);





            long start = System.currentTimeMillis();

            //方法执行
            result = method.invoke(clientInstance, args);

            long invokeTime = System.currentTimeMillis() - start;

            System.out.println("result : "+result + "  invokeTime : "+ invokeTime);

            //执行时间

        } catch (Exception e) {
            //异常处理
            // TODO: handle exception

            e.printStackTrace();
        } finally {
            // 回收
            serviceClientPool.recycleClient();
        }


        return result;
    }


    public Class getIfaceClazz() {
        return ifaceClazz;
    }

    public void setIfaceClazz(Class ifaceClazz) {
        this.ifaceClazz = ifaceClazz;
    }


    public ServiceClientPool getServiceClientPool() {
        return serviceClientPool;
    }

    public void setServiceClientPool(ServiceClientPool serviceClientPool) {
        this.serviceClientPool = serviceClientPool;
    }
}
