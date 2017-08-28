package com.java.core.rpc.thrift.provider;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhuangjiesen
 *封装 TThreadedSelectorServer.Args 类的属性 使得能在spring 配置
 */
public class TServerArgsFactory implements FactoryBean<Args> {
	
	private TServerTransport transport;
	private int selectorThreads;
	private int workerThreads;
	private TTransportFactory transportFactory;
	private TProtocolFactory protocolFactory;
	private TProcessor processor;


	private long maxReadBufferBytes = Long.MAX_VALUE;

	@Override
	public Args getObject() throws Exception {
		
		TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args((TNonblockingServerSocket)transport);
		args.processor(processor);
		args.protocolFactory(protocolFactory);
		args.transportFactory(transportFactory);
//		args.maxReadBufferBytes = maxReadBufferBytes;
		if (selectorThreads > 0) {
			args.selectorThreads = selectorThreads;
		}
		if (workerThreads > 0) {
			args.workerThreads(workerThreads);
		}
		// TODO Auto-generated method stub
		return args;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return TThreadedSelectorServer.Args.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	public TServerTransport getTransport() {
		return transport;
	}

	public void setTransport(TServerTransport transport) {
		this.transport = transport;
	}

	public int getSelectorThreads() {
		return selectorThreads;
	}

	public void setSelectorThreads(int selectorThreads) {
		this.selectorThreads = selectorThreads;
	}

	public int getWorkerThreads() {
		return workerThreads;
	}

	public void setWorkerThreads(int workerThreads) {
		this.workerThreads = workerThreads;
	}

	public TTransportFactory getTransportFactory() {
		return transportFactory;
	}

	public void setTransportFactory(TTransportFactory transportFactory) {
		this.transportFactory = transportFactory;
	}

	public TProtocolFactory getProtocolFactory() {
		return protocolFactory;
	}

	public void setProtocolFactory(TProtocolFactory protocolFactory) {
		this.protocolFactory = protocolFactory;
	}

	public TProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(TProcessor processor) {
		this.processor = processor;
	}

	
	
	
	
}
