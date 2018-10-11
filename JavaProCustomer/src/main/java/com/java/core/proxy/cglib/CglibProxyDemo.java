package com.java.core.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/zhuangjiesen/develop/my_github/CustomerAndProvider/JavaProCustomer/src/main");
		SubjectCglibProxy cglibProxy = new SubjectCglibProxy();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(RealSubject.class);
		enhancer.setCallback(cglibProxy);
		RealSubject realSubject=(RealSubject)enhancer.create();
		realSubject.show();
		realSubject.showSelf();
		
	}

}
