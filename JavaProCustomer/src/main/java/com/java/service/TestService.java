package com.java.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


import com.java.core.activemq.ProducerService;
import com.java.core.netty.thrift.AppThriftNettyConsumer;
import com.java.core.rpc.dubbo.service.IDubboInfoTestService;
import com.java.core.rpc.dubbo.service.IDubboTestService;
import com.java.core.rpc.thrift.service.IThriftTestService;
import com.java.helper.BeanHelper;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.thrift.TException;

import com.java.core.rpc.thrift.service.IThriftInfoTestService;
import sun.misc.Unsafe;

import javax.jms.Destination;


public class TestService  {

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

		StringBuilder msgSb = new StringBuilder();
		msgSb.append("byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];");
		msgSb.append("byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];");
		msgSb.append("byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];");
		msgSb.append("byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];");
		msgSb.append("byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];byte[] buf = new byte[1 * 1024 * 1024];");
		map.put("data", msgSb.toString());


//		byte[] buf = new byte[1 * 1024 * 1024];
//		map.put("data", new String(buf));
		String name = String.valueOf(System.currentTimeMillis());
		try {
			String result = client.showInfoData(name, true, map);
			if (!name.equals(result)) {

				System.out.println("返回结果串了！！！！！============================================");
			} else {
				System.out.println("正确的返回结果============================================");

			}
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



	/*
	*dubbo 下执行 thrift
	*
	* */
	public void doDubboThriftTest(){

		//运用动态代理 使thrift 接口透明化调用
		// 与普通的spring bean 一样调用
		IThriftInfoTestService.Iface thriftInfoTestService = BeanHelper.getContext().getBean(IThriftInfoTestService.Iface.class);
		Map<String, String> map =new HashMap<String, String>();
		map.put("name", "庄杰森");
		map.put("IThriftInfoTestService.Iface", "client");
		map.put("content", "thrift 的 rpc 调用");
		String name = "zhuangjiesen ...IThriftInfoTestService.Iface doing...";


		try {
			String result = thriftInfoTestService.showInfoData( name,  true  , map);
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
		String name = " 33333333333 zhuangjiesen ...IThriftInfoTestService doing...";


		try {
			String result = dubboInfoTestService.showDubboInfoData(map ,name);
			System.out.println( " 333333333333 result : " + result );

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	public void doActiveMqTest(){

		ProducerService producerService = BeanHelper.getContext().getBean(ProducerService.class);
		ActiveMQQueue queue = new ActiveMQQueue();

		Destination destination = (Destination)BeanHelper.getContext().getBean("queueDestination_2");


		producerService.sendMessage(destination ,"我是个消息队列");


	}


	public void doNettyThriftTest(){

		System.out.println("doNettyThriftTest.....");

	}


	public void doNettyRequestTest(){

		System.out.println("doNettyRequestTest.....");

		AppThriftNettyConsumer appThriftNettyConsumer = BeanHelper.getContext().getBean(AppThriftNettyConsumer.class);
//		appThriftNettyConsumer.connect("10.11.165.101" , 38888 , "/chat.do");



	}



}
