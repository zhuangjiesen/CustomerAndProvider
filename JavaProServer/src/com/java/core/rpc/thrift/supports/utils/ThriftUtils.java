package com.java.core.rpc.thrift.supports.utils;

import com.java.core.rpc.thrift.supports.constant.ThriftConstant;

/**
 * Created by zhuangjiesen on 2017/4/30.
 */
public class ThriftUtils {


    public static String getServiceName (Class ifaceClazz){
        String serviceIfaceClassName = ifaceClazz.getName();
        String serviceClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,"");
        return serviceClassName;
    }

    public static Class getIfaceClazz(String serviceName){
        String serviceIfaceClassName = serviceName + ThriftConstant.IFACE_NAME;
        Class clazz = null;
        try {
            clazz = Class.forName(serviceIfaceClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz ;
    }

}
