package com.java.core.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectProxyHandler implements InvocationHandler {
	private Object proxySubject;
	
	
	

	public SubjectProxyHandler(Object proxySubject) {
		super();
		this.proxySubject = proxySubject;
	}




	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		System.out.println("dosomething___before");
		method.invoke(proxySubject, args);
		
		
		System.out.println("dosomething___after");

		
		
		return null;
	}

}
