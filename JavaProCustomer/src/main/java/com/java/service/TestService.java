package com.java.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.java.core.rpc.thrift.service.IThriftInfoTestService;
import com.java.core.rpc.thrift.supports.ThriftServiceProxy;

public class TestService {
	
	public void doThriftTest(){
		
		//运用动态代理 使thrift 接口透明化调用
		IThriftInfoTestService.Iface client =ThriftServiceProxy.getThriftClient(IThriftInfoTestService.Iface.class);
        Map<String, String> map =new HashMap<String, String>();
        map.put("name", "庄杰森");
        map.put("IThriftInfoTestService", "client");
        map.put("content", "thrift 的 rpc 调用");
        String name = "zhuangjiesen ...IThriftInfoTestService doing...";
        try {
			client.showInfoData(name, true, map);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
