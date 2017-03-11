package com.java.core.rpc.thrift.supports;

import java.lang.reflect.Proxy;

public class ThriftServiceProxy {
	
	
	public static <T> T getThriftClient(Class<T> clazz){
		T client =null;
		ThriftServiceProxyInvocation proxyInvocation =new ThriftServiceProxyInvocation();
		proxyInvocation.setIfaceClazz(clazz);
		client = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{ clazz }, proxyInvocation);
		return client;	
	}

}
