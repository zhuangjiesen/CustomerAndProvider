package com.java.core.common;

import com.sun.tools.javac.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/17
 */
public class CollectionMain {

    public static void main(String[] args) {

        Map<String, String> map = null;
        int count = 20 * 10000;
        {
            map = new HashMap<>();
            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                String key = "key_" + i;
                String value = "key_" + i;
                map.put(key, value);
            }
            long time = System.currentTimeMillis() - start;
            System.out.println(" time : " + time);

        }

        {
            map = new HashMap<>(count);
            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                String key = "key_" + i;
                String value = "key_" + i;
                map.put(key, value);
            }
            long time = System.currentTimeMillis() - start;
            System.out.println(" time : " + time);
        }

    }


}
