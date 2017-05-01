package com.java.service;

import java.util.HashMap;
import java.util.Map;

import com.java.core.activemq.ProducerService;
import com.java.core.rpc.dubbo.service.IDubboInfoTestService;
import com.java.core.rpc.dubbo.service.IDubboTestService;
import com.java.core.rpc.thrift.service.IThriftTestService;
import com.java.helper.BeanHelper;
import org.apache.thrift.TException;

import com.java.core.rpc.thrift.service.IThriftInfoTestService;

import javax.print.attribute.standard.Destination;


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




	public void doDubboTest(){

		//运用动态代理 使thrift 接口透明化调用
		// 与普通的spring bean 一样调用
		IDubboTestService dubboTestService = BeanHelper.getContext().getBean(IDubboTestService.class);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("name", "庄杰森");
		map.put("IDubboTestService", "client");
		map.put("content", "dubbo 的 rpc 调用");
		String name = "zhuangjiesen ...IDubboTestService doing...";


		try {
			String result = dubboTestService.showDubboData(map ,name);
			System.out.println( "result : " + result );

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void doDubboInfoTest(){

		//运用动态代理 使thrift 接口透明化调用
		// 与普通的spring bean 一样调用
		IDubboInfoTestService dubboInfoTestService = BeanHelper.getContext().getBean(IDubboInfoTestService.class);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("name", "庄杰森");
		map.put("IThriftInfoTestService", "client");
		map.put("content", "dubbo 的 rpc 调用");
		String name = "zhuangjiesen ...IThriftInfoTestService doing...";


		try {
			String result = dubboInfoTestService.showDubboInfoData(map ,name);
			System.out.println( "result : " + result );

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	public void doActiveMqTest(){

		ProducerService producerService = BeanHelper.getContext().getBean(ProducerService.class);
		Destination destination = (Destination)BeanHelper.getContext().getBean("queueDestination_2");


		producerService.sendMessage(destination ,"我是个消息队列");


	}

}
