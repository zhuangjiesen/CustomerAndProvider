package com.java.core.proxy.jdk;

import java.lang.reflect.Proxy;

public class ProxyDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("我是动态代理！！！");
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		 
		RealSubject realSubject=new RealSubject();
		Subject subject=(Subject)Proxy.newProxyInstance
				(RealSubject.class.getClassLoader(), 
						realSubject.getClass().getInterfaces(), 
						new SubjectProxyHandler(realSubject));
		subject.show();
		Subject subject01=new RealSubject();
		
		System.out.println("------------------");

		
		subject01.show();
		
	}
	
	
	
	

}
