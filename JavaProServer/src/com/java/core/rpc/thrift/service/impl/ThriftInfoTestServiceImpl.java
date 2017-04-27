package com.java.core.rpc.thrift.service.impl;



import java.util.Map;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSONObject;
import com.java.core.rpc.thrift.service.IThriftInfoTestService;
import com.java.helper.ThreadHelper;

public class ThriftInfoTestServiceImpl implements IThriftInfoTestService.Iface {

	@Override
	public String showInfoData(String name, boolean success, Map<String, String> map) throws TException {
		// TODO Auto-generated method stub
		ThreadHelper.sleep(300);
		
		
		System.out.println(" xxxxxxxxxx ThriftInfoTestServiceImpl doing ...showInfoData()... ");
		System.out.println(" map : "+ JSONObject.toJSONString(map));
		System.out.println(" success : "+ success);
		System.out.println(" name : "+ name);
		String result = name +" time : " + System.currentTimeMillis();
		
		return result;
	}

}
