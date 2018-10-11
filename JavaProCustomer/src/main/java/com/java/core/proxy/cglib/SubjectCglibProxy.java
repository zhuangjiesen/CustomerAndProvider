package com.java.core.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class SubjectCglibProxy implements MethodInterceptor {
	
	@Override
	public Object intercept(Object arg0, Method method, Object[] arg2, MethodProxy arg3) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("++++++before " + arg3.getSuperName() + "++++++");
        System.out.println(method.getName());  
        Object o1 = arg3.invokeSuper(arg0, arg2);  
        System.out.println("++++++before " + arg3.getSuperName() + "++++++");  
        return o1;  
	}

}
