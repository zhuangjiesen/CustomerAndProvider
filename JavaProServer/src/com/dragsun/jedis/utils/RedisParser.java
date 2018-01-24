package com.dragsun.jedis.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public class RedisParser {


    public static Map<String ,String > parseInfoServer(String serverInfo){
        Map<String ,String > info = null;

        String[] serverInfoArr = serverInfo.split("\r\n");
        if (serverInfoArr != null && serverInfoArr.length > 0) {
            info = new HashMap<>();
            for (String serverInfoItem : serverInfoArr) {
                if (serverInfoItem.startsWith("#")) {
                    continue;
                }
                String[] serverInfoItemArr = serverInfoItem.split(":");
                if (serverInfoItemArr != null && serverInfoItemArr.length > 1) {
                    info.put(serverInfoItemArr[0] , serverInfoItemArr[1]);
                }
            }
        }
        return info;
    }


    public static Map<String , String> getProperty(String content , String splitStr , String subSplitStr){
        Map<String , String> map = null;
        String[] splitArr = content.split(splitStr);
        if (splitArr.length > 0) {
            map = new HashMap<>();
            for (String item : splitArr) {
                String[] subSplitArr = item.split(subSplitStr);
                if (subSplitArr.length == 2) {
                    map.put(subSplitArr[0] , subSplitArr[1]);
                }
            }
        }
        return map;
    }

}
