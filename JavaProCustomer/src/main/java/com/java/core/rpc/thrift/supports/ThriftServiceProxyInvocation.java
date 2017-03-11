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

public class ThriftServiceProxyInvocation implements InvocationHandler {
	
	private final static String IFACE_NAME="$Iface";
	private final static String CLIENT_NAME="$Client";
	
	private Class ifaceClazz;
	

	public Class getIfaceClazz() {
		return ifaceClazz;
	}


	public void setIfaceClazz(Class ifaceClazz) {
		this.ifaceClazz = ifaceClazz;
	}







	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(" ThriftServiceProxyInvocation  invoke doing before ....");
		if (ifaceClazz == null) {
			return null;
		}
		TTransport transport = null;
		Object result = null;
		try {

			 // 设置调用的服务地址为本地，端口 
	        transport = new TSocket("127.0.0.1", 29999); 
	        TFramedTransport framedTransport =new TFramedTransport(transport);
	        TBinaryProtocol binaryProtocol =new TBinaryProtocol(framedTransport);
	        TMultiplexedProtocol multiplexedProtocol =new TMultiplexedProtocol(binaryProtocol, IThriftInfoTestService.class.getName());
	        transport.open();
	        
			String ifaceName =ifaceClazz.getName();
			String className = ifaceName.substring(0, ifaceName.lastIndexOf(IFACE_NAME));
			String clientName = className.concat(CLIENT_NAME);
			Class clientClazz = Class.forName(clientName);
	        
			Object clientInstance= clientClazz.getConstructor(TProtocol.class).newInstance(multiplexedProtocol);
			result=method.invoke(clientInstance, args);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (transport != null) {
				transport.close();
			}
		}
		
		System.out.println(" ThriftServiceProxyInvocation  invoke doing after ....");
		
		return result;
	}

}
