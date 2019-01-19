package com.java.core.zookeeper.curator;

import com.alibaba.fastjson.JSONObject;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2019/1/16
 */
public class CuratorMain {

    public static void main(String[] args) throws Exception {


        RetryPolicy retryPolicy = new RetryNTimes(10, 5000);
        String zkAddress = "";
        String root = "ysf";

        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(zkAddress)
                .namespace(root).retryPolicy(retryPolicy).connectionTimeoutMs(10).sessionTimeoutMs(5000).build();
        curator.blockUntilConnected(10, TimeUnit.SECONDS);

        curator.start();
        String name = curator.getState().name();

        List<String> list = curator.getChildren().forPath("/config/device");
        System.out.println("1. list : " + JSONObject.toJSONString(list));
        list = curator.getChildren().forPath("/");
        System.out.println("2. list : " + JSONObject.toJSONString(list));
        System.out.println("name : " + name);

    }

}
