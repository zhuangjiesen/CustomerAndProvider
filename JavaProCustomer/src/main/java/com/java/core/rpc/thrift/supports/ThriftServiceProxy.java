package com.java.core.rpc.thrift.supports;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

public class ThriftServiceProxy {
	
	private static ConcurrentHashMap<String,Object> thriftClientCache;
	
	
	public static <T> T getThriftClient(Class<T> clazz){
		if (!clazz.isInterface()) {
			throw new RuntimeException("类型错误");
		}
		if (thriftClientCache == null) {
			thriftClientCache = new ConcurrentHashMap<String,Object>();
		}
		T client =null;
		String cacheKey = clazz.getName();
		if (thriftClientCache.containsKey(cacheKey)) {
			client=(T)thriftClientCache.get(cacheKey);
		} else {
			ThriftServiceProxyInvocation proxyInvocation =new ThriftServiceProxyInvocation();
			proxyInvocation.setIfaceClazz(clazz);
			client = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{ clazz }, proxyInvocation);
			if (client != null) {
				thriftClientCache.put(cacheKey, client);
			}
		}
		return client;	
	}

}
