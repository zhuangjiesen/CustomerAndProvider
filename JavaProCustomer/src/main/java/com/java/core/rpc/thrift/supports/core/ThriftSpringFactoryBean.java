package com.java.core.rpc.thrift.supports.core;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by zhuangjiesen on 2017/3/17.
 */
public class ThriftSpringFactoryBean<T> implements FactoryBean<T> {

    //客户端获取实例
    private AppThriftServiceManager appThriftClientManager;


    //服务 Iface 接口类
    private Class serviceIfaceClass;

    // 是否单例
    private boolean isSingleton = true;

    public T getObject() throws Exception {
        return (T) appThriftClientManager.getClient(serviceIfaceClass);
    }

    public Class<T> getObjectType() {
        return serviceIfaceClass;
    }

    public boolean isSingleton() {
        return isSingleton;
    }


    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public AppThriftServiceManager getAppThriftClientManager() {
        return appThriftClientManager;
    }

    public void setAppThriftClientManager(AppThriftServiceManager appThriftClientManager) {
        this.appThriftClientManager = appThriftClientManager;
    }

    public Class getServiceIfaceClass() {
        return serviceIfaceClass;
    }

    public void setServiceIfaceClass(Class serviceIfaceClass) {
        this.serviceIfaceClass = serviceIfaceClass;
    }
}
