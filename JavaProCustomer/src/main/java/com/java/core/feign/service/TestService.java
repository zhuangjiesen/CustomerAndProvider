package com.java.core.feign.service;

import com.alibaba.fastjson.JSONObject;
import feign.Param;
import feign.RequestLine;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/10
 */
public interface TestService {

    @RequestLine("GET /test/test?name={name}")
    String test(@Param("name") String name);


    @RequestLine("GET /test/testNoPage")
    String testNoPage();


    @RequestLine("GET /test/testNoJSON")
    JSONObject testNoJSON();


    @RequestLine("GET /test/testError")
    String testError();

}
