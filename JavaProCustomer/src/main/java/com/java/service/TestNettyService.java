package com.java.service;

import com.java.core.activemq.ProducerService;
import com.java.core.netty.common.AppNettyCommonConsumer;
import com.java.core.netty.common.handler.CommonCustomerChannelHandler;
import com.java.core.netty.thrift.AppThriftNettyConsumer;
import com.java.core.rpc.dubbo.service.IDubboInfoTestService;
import com.java.core.rpc.dubbo.service.IDubboTestService;
import com.java.core.rpc.thrift.service.IThriftInfoTestService;
import com.java.core.rpc.thrift.service.IThriftTestService;
import com.java.helper.BeanHelper;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.thrift.TException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.print.attribute.standard.Destination;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class TestNettyService implements InitializingBean , ApplicationContextAware{

	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	private static ApplicationContext applicationContext;


	private static AtomicInteger incr = new AtomicInteger(0);


	public void doNettyCommonTest(){

		System.out.println("doNettyThriftTest.....");
		String message = "庄杰森...." + incr.incrementAndGet() + "/n";
		CommonCustomerChannelHandler.customerChannel.writeAndFlush(message);

	}


	public void afterPropertiesSet() throws Exception {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (applicationContext != null) {
					applicationContext.getBean(TestNettyService.class).doNettyCommonTest();
				}
			}
		} , 1 , 5 , TimeUnit.SECONDS);
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
}
