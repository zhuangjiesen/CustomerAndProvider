package com.dragsun.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by zhuangjiesen on 2018/1/18.
 */
public class HelloWorldHystrixCommand extends HystrixCommand {
    private final String name;
    public HelloWorldHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }
    // 如果继承的是HystrixObservableCommand，要重写Observable construct()
    @Override
    protected String run() {
        return "Hello " + name;
    }



}

