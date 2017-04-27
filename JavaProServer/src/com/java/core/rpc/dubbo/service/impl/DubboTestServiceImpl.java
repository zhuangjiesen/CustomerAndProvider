package com.java.core.rpc.dubbo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.core.rpc.dubbo.service.IDubboTestService;

import java.util.Map;

/**
 * Created by zhuangjiesen on 2017/4/22.
 */
public class DubboTestServiceImpl implements IDubboTestService {


    public String showDubboData(Map<String, Object> map, String name) {
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(" DubboTestServiceImpl _ showDubboData().....");

        System.out.println("  JSON map : " + JSONObject.toJSONString(map));
        System.out.println("  name : " + name);


        String result = " showDubboData dubbo 返回结果！！！";

        return result;
    }
}
