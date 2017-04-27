package com.java.core.rpc.thrift.supports;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.java.core.rpc.thrift.service.IThriftInfoTestService;

/*
* thrift 服务代理类
*
* */
public class ThriftServiceProxyInvocation implements InvocationHandler {
	
	/*thrift 服务类的iface 类*/
	private Class ifaceClazz;
	/* thrift 连接池*/
	private ThriftConnectionPool thriftConnectionPool;


	private IThriftExceptionResolver thriftExceptionResolver;


	private final ConcurrentHashMap<String,Object> thriftClientCache = new ConcurrentHashMap();


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		//doSomething Before....
//		System.out.println(" ThriftServiceProxyInvocation  invoke doing before ....");
		if (ifaceClazz == null) {
			return null;
		}
		Object result = null;
		try {
			String serviceIfaceClassName = ifaceClazz.getName();
			String serviceClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,"");
			// 连接池中选择 protocol
			TProtocol protocol = thriftConnectionPool.getProtocol(serviceClassName);

			String cacheName = serviceClassName;
			Object clientInstance = null;
			if (thriftClientCache.containsKey(cacheName)) {
					ThriftClientManager thriftClientManager = (ThriftClientManager) thriftClientCache.get(cacheName);

					//获取缓存构造函数
					Constructor constructor = thriftClientManager.getClientClazz().getConstructor(TProtocol.class);
					clientInstance = constructor.newInstance(protocol);
			} else {
					String serviceClientClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,ThriftConstant.CLIENT_NAME);
					Class clientClazz = Class.forName(serviceClientClassName);
					Constructor constructor = clientClazz.getConstructor(TProtocol.class);
					clientInstance = constructor.newInstance(protocol);

					//封装成对象
					ThriftClientManager thriftClientManager = new ThriftClientManager();
					thriftClientManager.setServiceIfaceClassName(serviceIfaceClassName);
					thriftClientManager.setServiceClassName(serviceClassName);
					thriftClientManager.setServiceClientClassName(serviceClientClassName);
					thriftClientManager.setClientClazz(clientClazz);

					thriftClientCache.put(cacheName , thriftClientManager);
			}


			long start = System.currentTimeMillis();
			result=method.invoke(clientInstance, args);

			//执行时间
			long invokeTime = System.currentTimeMillis() - start;

//			System.out.println("result : "+result);
		} catch (Exception e) {
			//异常处理
			// TODO: handle exception
			if (thriftExceptionResolver != null) {
				thriftExceptionResolver.doException(e);
			}
			e.printStackTrace();
		} finally {
			// 回收 protocol
			thriftConnectionPool.recycleProtocol();
		}
		//doSomething After....
//		System.out.println(" ThriftServiceProxyInvocation  invoke doing after ....");

		return result;
	}



	public Class getIfaceClazz() {
		return ifaceClazz;
	}


	public void setIfaceClazz(Class ifaceClazz) {
		this.ifaceClazz = ifaceClazz;
	}


	public ThriftConnectionPool getThriftConnectionPool() {
		return thriftConnectionPool;
	}

	public void setThriftConnectionPool(ThriftConnectionPool thriftConnectionPool) {
		this.thriftConnectionPool = thriftConnectionPool;
	}


	public IThriftExceptionResolver getThriftExceptionResolver() {
		return thriftExceptionResolver;
	}

	public void setThriftExceptionResolver(IThriftExceptionResolver thriftExceptionResolver) {
		this.thriftExceptionResolver = thriftExceptionResolver;
	}
}
