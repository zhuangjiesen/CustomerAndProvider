package com.java.core.rpc.thrift.provider;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.java.core.rpc.thrift.supports.ThriftProcessorFactory;

public class AppThriftServer  implements ApplicationContextAware,InitializingBean {
	

	/**线程池**/
	private static ExecutorService executorService;
	// ApplicationContextAware 可以调用spring 生命周期获取上下文
	private static ApplicationContext context;

	
	private TServerTransport transport;
	private TThreadedSelectorServer.Args args;
	private TServer server;
	

	public AppThriftServer() {
		super();
		executorService = Executors.newSingleThreadExecutor();
	}
	
	
	public void initThriftServer(){
		//初始化设置
		initConfig();	
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(" ThriftServer start ing ....");
				try { 
					if (!server.isServing()) {
						server.serve();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (transport != null) {
						transport.close();
					}
					
				}
			}
		});
		
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		context=applicationContext;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}
	

	
	/**
	 * 初始化参数
	 */
	private void initConfig(){
	}


	public TServerTransport getTransport() {
		return transport;
	}


	public void setTransport(TServerTransport transport) {
		this.transport = transport;
	}


	public TThreadedSelectorServer.Args getArgs() {
		return args;
	}


	public void setArgs(TThreadedSelectorServer.Args args) {
		this.args = args;
	}


	public TServer getServer() {
		return server;
	}


	public void setServer(TServer server) {
		this.server = server;
	}
	
	
	

}
