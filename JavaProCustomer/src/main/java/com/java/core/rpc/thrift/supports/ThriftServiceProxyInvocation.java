package com.java.core.rpc.thrift.supports;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(" ThriftServiceProxyInvocation  invoke doing before ....");
		if (ifaceClazz == null) {
			return null;
		}
		Object result = null;
		try {
			String serviceIfaceClassName = ifaceClazz.getName();
			String serviceClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,"");
			String serviceClientClassName = serviceIfaceClassName.replace(ThriftConstant.IFACE_NAME,ThriftConstant.CLIENT_NAME);
			Class clientClazz = Class.forName(serviceClientClassName);
			// 连接池中选择 protocol
			TProtocol protocol = thriftConnectionPool.getProtocol(serviceClassName);
			Object clientInstance= clientClazz.getConstructor(TProtocol.class).newInstance(protocol);
			result=method.invoke(clientInstance, args);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 回收 protocol
			thriftConnectionPool.recycleProtocol();
		}
		
		System.out.println(" ThriftServiceProxyInvocation  invoke doing after ....");
		
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

}
