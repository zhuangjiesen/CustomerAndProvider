package com.java.core.rpc.dubbo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.core.rpc.dubbo.service.IDubboInfoTestService;

import java.util.Map;

/**
 * Created by zhuangjiesen on 2017/4/22.
 */
public class DubboInfoTestServiceImpl implements IDubboInfoTestService {


    public String showDubboInfoData(Map<String, Object> map, String name) {
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(" DubboInfoTestServiceImpl _ showDubboInfoData().....");

        System.out.println("  JSON map : " + JSONObject.toJSONString(map));
        System.out.println("  name : " + name);


        String result = " showDubboInfoData dubbo 返回结果！！！";


        return result;
    }


}
