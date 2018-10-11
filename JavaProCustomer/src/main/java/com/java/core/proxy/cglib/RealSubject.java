package com.java.core.proxy.cglib;

public class RealSubject implements Subject {

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("我是  RealSubject 的show 方法！！！");

	}
	
	
	
	public void showSelf(){
		
		System.out.println("我是  RealSubject 的showSelf 方法！！！");
		
	}
	
	
	

}
