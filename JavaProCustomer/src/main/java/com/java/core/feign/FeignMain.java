package com.java.core.feign;

import com.alibaba.fastjson.JSONObject;
import com.java.core.feign.service.TestService;
import feign.*;
import feign.codec.ErrorDecoder;

import java.io.BufferedReader;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/10
 */
public class FeignMain {


    public static void main(String[] args) {
        TestService testService = Feign.builder()
                //访问404页面直接可以返回null
                .decode404()
                //请求拦截
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        //请求发起前触发的拦截器
                        System.out.println("i am before request .....");
                        //添加自定义全局的header
//                        template.header("xxx" , "headervalue");
                    }
                })
                .errorDecoder(new ErrorDecoder() {
                    @Override
                    public Exception decode(String methodKey, Response response) {
                        System.out.println(String.format("methodKey : %s" , methodKey));

                        return new RuntimeException("500 报错");
                    }
                })
                .retryer(new Retryer.Default())
                .target(TestService.class, "http://localhost:7475");
        String res = null;
        res = testService.test("hahaha");
        System.out.println(String.format("test result:%s " , res));

        res = testService.testNoPage();
        System.out.println(String.format("testNoPage result:%s " , res));

        JSONObject json = testService.testNoJSON();
        System.out.println(String.format("testNoJSON result:%s " , json));


        res = testService.testError();
        System.out.println(String.format("testError result:%s " , res));


        System.out.println("result : " + res);

    }

}
