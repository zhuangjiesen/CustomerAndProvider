package com.java.core.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by zhuangjiesen on 2017/4/1.
 */
public class ProxyMethodInterceptor implements MethodInterceptor {


    private Object target;




    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {


        System.out.println("_ProxyMethodInterceptor ....intercept !!!");
        System.out.println("_method !!! : " + method.getName());
        Object result = methodProxy.invokeSuper(target ,args);


        return result;
    }


    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
