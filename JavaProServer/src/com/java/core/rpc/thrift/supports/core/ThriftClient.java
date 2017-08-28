package com.java.core.rpc.thrift.supports.core;

/**
 * Created by zhuangjiesen on 2017/4/30.
 */
public class ThriftClient {



    private String serviceIfaceClassName;
    private String serviceClassName;
    private String serviceClientClassName;
    private Class clientClazz;


    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getServiceClientClassName() {
        return serviceClientClassName;
    }

    public void setServiceClientClassName(String serviceClientClassName) {
        this.serviceClientClassName = serviceClientClassName;
    }

    public Class getClientClazz() {
        return clientClazz;
    }

    public void setClientClazz(Class clientClazz) {
        this.clientClazz = clientClazz;
    }



    public String getServiceIfaceClassName() {
        return serviceIfaceClassName;
    }

    public void setServiceIfaceClassName(String serviceIfaceClassName) {
        this.serviceIfaceClassName = serviceIfaceClassName;
    }

}
