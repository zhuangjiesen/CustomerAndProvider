package com.java.service;

import java.util.HashMap;
import java.util.Map;

import com.java.core.rpc.thrift.service.IThriftTestService;
import com.java.helper.BeanHelper;
import org.apache.thrift.TException;

import com.java.core.rpc.thrift.service.IThriftInfoTestService;

public class TestService {

	public void doThriftTest(){

		//运用动态代理 使thrift 接口透明化调用
		// 与普通的spring bean 一样调用
		IThriftTestService.Iface client = BeanHelper.getContext().getBean(IThriftTestService.Iface.class);
		Map<String, String> map =new HashMap<String, String>();
		map.put("name", "庄杰森");
		map.put("IThriftTestService", "client");
		map.put("content", "thrift 的 rpc 调用");
		String name = "zhuangjiesen ...IThriftTestService doing...";
		try {
			client.showThriftResult(name, true, map);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doThriftInfoTest(){

		//运用动态代理 使thrift 接口透明化调用
		// 与普通的spring bean 一样调用
		IThriftInfoTestService.Iface client = BeanHelper.getContext().getBean(IThriftInfoTestService.Iface.class);
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
