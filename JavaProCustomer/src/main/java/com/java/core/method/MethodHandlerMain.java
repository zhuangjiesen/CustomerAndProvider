package com.java.core.method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/19
 */
public class MethodHandlerMain {

    public static void main(String[] args) throws Throwable {
        HandlerDemo handlerDemo = new HandlerDemo();




        int times = 1000000;
        long start = 0;
        //methodHandle
        MethodType methodType = MethodType.methodType(void.class , String.class);
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(HandlerDemo.class , "doShow" , methodType);
        start = System.currentTimeMillis();
        for (int i = 0 ; i < times ; i ++) {
            methodHandle.invokeExact(handlerDemo , "zhuangjiesen_methodHandle...");
        }
        System.out.println("methodHandle finish .... time : " + (System.currentTimeMillis() - start));

        //反射
        Method method = HandlerDemo.class.getMethod("doShow" , String.class);
        start = System.currentTimeMillis();
        for (int i = 0 ; i < times ; i ++) {
            method.invoke(handlerDemo , "zhuangjiesen_reflect...");
        }
        System.out.println("reflect finish .... time : " + (System.currentTimeMillis() - start));

        //cglib代理
        Enhancer enhancer = new Enhancer();
        CglibProxy cglibProxy = new CglibProxy(handlerDemo);
        enhancer.setCallback(cglibProxy);
        enhancer.setSuperclass(HandlerDemo.class);
        HandlerDemo proxy = (HandlerDemo) enhancer.create();
        start = System.currentTimeMillis();
        for (int i = 0 ; i < times ; i ++) {
            proxy.doShow("zhuangjiesen_proxy...");
        }
        System.out.println("proxy finish .... time : " + (System.currentTimeMillis() - start));





    }



    public static class HandlerDemo {

        public void doShow(String name) {
            name += "abc ____";
//            System.out.println(" name : " + name + " - time : " + System.currentTimeMillis());
//            return "我是返回值_" + System.currentTimeMillis();
        }

    }


    public static class CglibProxy implements MethodInterceptor {

        private Object target;

        public CglibProxy(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return method.invoke(target , objects);
        }
    }

}
