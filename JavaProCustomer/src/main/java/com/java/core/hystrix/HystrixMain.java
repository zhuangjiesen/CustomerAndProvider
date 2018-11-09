package com.java.core.hystrix;

import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/10/31
 */
public class HystrixMain {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {

                System.out.println("call...");

                return null;
            }
        });

        String s1 = new CommandHelloWorld("Bob1").execute();
        Future<String> s2 = new CommandHelloWorld("Bob2").queue();
        Observable<String> s3 = new CommandHelloWorld("Bob3").observe();
        s3.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("hotObservable of ObservableCommand completed");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("hotObservable of ObservableCommand error");
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("hotObservable of ObservableCommand onNext: " + s);
            }
        });
        Observable<String> s4 = new CommandHelloWorld("Bob4").toObservable();

        System.out.println("s1 : " + s1);
        System.out.println("s2 : " + s2.get());
//        System.out.println("s3 : " + s3.toBlocking().first());
        System.out.println("s4 : " + s4.toBlocking().first());


    }


    public static void toObservable() {

    }
    public static void observe() {

    }
    public static void queue() {

    }
    public static void execute() {

    }


    public static class CommandHelloWorld extends HystrixCommand<String> {

        private final String name;
        public CommandHelloWorld(String name) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")); //必须
            this.name = name;
        }

        @Override
        protected String run() {
                         /*
              网络调用 或者其他一些业务逻辑，可能会超时或者抛异常
             */
            return "Hello " + name + "!";
        }
    }

}
