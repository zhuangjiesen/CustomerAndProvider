package com.java.core.rpc.thrift.provider;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.apple.eawt.Application;
import com.java.core.rpc.thrift.supports.ThriftProcessorFactory;

public class AppThriftServer  implements ApplicationContextAware {
	
	/**线程池**/
	private static ExecutorService executorService;
	// ApplicationContextAware 可以调用spring 生命周期获取上下文
	private static ApplicationContext context;

	public AppThriftServer() {
		super();
		// TODO Auto-generated constructor stub
		executorService = Executors.newSingleThreadExecutor();
	}
	
	
	public void initThriftServer(){
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(" ThriftServer start ing ....");
				TNonblockingServerSocket transport = null;
				try { 
					//非阻塞 ServerSocket
					transport =new TNonblockingServerSocket(new InetSocketAddress(29999));	
					// 获取 TProcessor 
					ThriftProcessorFactory thriftProcessorFactory=context.getBean(ThriftProcessorFactory.class);
					TProcessor processor =thriftProcessorFactory.getProcessor();
					//  nio selectorThreads处理io，  workerThreads 处理服务调用过程
					TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(transport);
					args.processor(processor);
					// customer也要TFramedTransport对应 否则报错
					args.transportFactory(new TFramedTransport.Factory());  
				    //二进制协议  
					args.protocolFactory(new TBinaryProtocol.Factory());  
					TThreadedSelectorServer server =new TThreadedSelectorServer(args);
					server.serve();
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

}
