package com.java.core.rpc.thrift.supports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;

import com.java.core.rpc.thrift.service.IThriftInfoTestService;

public class ThriftProcessorFactory {
	
	private final static String IFACE_NAME="$Iface";
	private final static String PROCESS_NAME="$Processor";
	
	private List<Object> targets;
	
	
	private Map<String, TProcessor> processors;
	
	
	private TMultiplexedProcessor multiplexedProcessor;
	
	
	

	public TMultiplexedProcessor getMultiplexedProcessor() {
		return multiplexedProcessor;
	}


	public void setMultiplexedProcessor(TMultiplexedProcessor multiplexedProcessor) {
		this.multiplexedProcessor = multiplexedProcessor;
	}


	public ThriftProcessorFactory() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Map<String, TProcessor> getProcessors() {
		return processors;
	}


	public void setProcessors(Map<String, TProcessor> processors) {
		this.processors = processors;
	}





	public List<Object> getTargets() {
		return targets;
	}

	public void setTargets(List<Object> targets) {
		this.targets = targets;
	}
	
	
	public void convertTargetToTProcessor(){
		if (targets.isEmpty()) {
			return ;
		}
		processors = new HashMap<String, TProcessor>();
		try {
			for (Object target : targets ) {
				Class iface= target.getClass().getInterfaces()[0];
				String ifaceName =iface.getName();
				String serviceName = ifaceName.substring(0, ifaceName.lastIndexOf(IFACE_NAME));
				
				Class processorClazz = Class.forName(serviceName.concat(PROCESS_NAME));
				Object processorObj = processorClazz.getConstructor(iface).newInstance(iface.cast(target));
				if (processorObj instanceof TProcessor) {
					TProcessor processor = (TProcessor) processorObj;
					processors.put(serviceName, processor);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initMultiplexedProcessor();
	}
	
	private void initMultiplexedProcessor(){
		if (processors.isEmpty()) {
			return ;
		}
		multiplexedProcessor = new TMultiplexedProcessor();
		Set<String> serviceNames = processors.keySet();
		for (String serviceName : serviceNames) {
			if (!processors.containsKey(serviceName)) {
				continue;
			}
			multiplexedProcessor.registerProcessor(serviceName, processors.get(serviceName));
		}
	}
	
	
	
	
	

}
