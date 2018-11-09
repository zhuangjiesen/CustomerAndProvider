package com.java.core.future;

import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/11/6
 */
public class FutureMain {

    public static void main(String[] args) throws Exception {
        SettableListenableFuture<String> listenableFuture = new SettableListenableFuture<>();
        listenableFuture.set("1");
        String result = null;
        result = listenableFuture.get();
        System.out.println("result : " + result);
        listenableFuture.cancel(true);
        result = listenableFuture.get();
        System.out.println("result : " + result);
    }


    public class SettableFuture implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "hello-SettableFuture";
        }
    }

}
