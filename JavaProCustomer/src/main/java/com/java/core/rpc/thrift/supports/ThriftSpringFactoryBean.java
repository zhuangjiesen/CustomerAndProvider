package com.java.core.rpc.thrift.supports;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by zhuangjiesen on 2017/3/17.
 */
public class ThriftSpringFactoryBean<T> implements FactoryBean<T> {

    private ThriftServiceManager thriftServiceManager;

    private Class serviceIfaceClass;

    public T getObject() throws Exception {
        return (T) thriftServiceManager.getThriftClient(serviceIfaceClass);
    }

    public Class<T> getObjectType() {
        return serviceIfaceClass;
    }

    public boolean isSingleton() {
        return true;
    }


    public ThriftServiceManager getThriftServiceManager() {
        return thriftServiceManager;
    }

    public void setThriftServiceManager(ThriftServiceManager thriftServiceManager) {
        this.thriftServiceManager = thriftServiceManager;
    }

    public Class getServiceIfaceClass() {
        return serviceIfaceClass;
    }

    public void setServiceIfaceClass(Class serviceIfaceClass) {
        this.serviceIfaceClass = serviceIfaceClass;
    }
}
