package com.java.main;

import com.java.helper.BeanHelper;
import com.java.helper.ThreadHelper;
import com.java.service.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static ApplicationContext applicationContext;

    private static CountDownLatch countDownLatch;
	
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        applicationContext = SpringInit.init();
		final TestService testService=applicationContext.getBean(TestService.class);

        ITestListener testListener = new ITestListener() {
            public void doTest() {
                testService.doThriftInfoTest();
            }
        };

        ITestListener dubboTestListener = new ITestListener() {
            public void doTest() {
                testService.doDubboTest();
            }
        };
        ITestListener dubboTestInfoListener = new ITestListener() {
            public void doTest() {
                testService.doDubboInfoTest();
            }
        };
        ITestListener dubboThriftTestListener = new ITestListener() {
            public void doTest() {
                testService.doDubboThriftTest();
            }
        };

        ITestListener dubboInfoTestListener = new ITestListener() {
            public void doTest() {
                testService.doDubboInfoTest();
            }
        };
        ITestListener activemqTestListener = new ITestListener() {
            public void doTest() {
                testService.doActiveMqTest();
            }
        };


        ITestListener nettyThriftListener = new ITestListener() {
            public void doTest() {
                testService.doNettyThriftTest();
            }
        };

        ITestListener nettyTestListener = new ITestListener() {
            public void doTest() {
                applicationContext.getBean(TestNettyService.class).doNettyCommonTest();

            }
        };





//        registry.removeBeanDefinition("dragsunTestService");
//        dragsunTestService = (DragsunTestService)applicationContext.getBean("dragsunTestService");
//        dragsunTestService.doTestShow();
//        System.out.println("dragsunTestService....");

//        doConcurentTest(1,activemqTestListener);
//        doConcurentTest(1,nettyTestListener);
//        doConcurentTest(1,dubboTestInfoListener);

//        try {
//            Thread.currentThread().sleep(10000);
//        } catch (Interrupte\dException e) {
//            e.printStackTrace();
//        }
        System.out.println(" 开始执行....");
//        long start = System.currentTimeMillis();
//        doConcurentTest(1 , testListener);
//        try {
//            countDownLatch.await();
//            System.out.println(" 执行时间 ： " + (System.currentTimeMillis() - start));
//
//            System.out.println(" 执行成功....");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        doConcurentTest(1,dubboTestListener);
//        doConcurentTest(1,dubboThriftTestListener);
//        doThriftTest(3000,dubboInfoTestListener);
//        doThriftTest(1,activemqTestListener);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {

                }
            }
        });

    }
    

    //开启并发线程测试
    public static void doConcurentTest (int threadCounts,final ITestListener testListener){
        final Object waitObj = new Object();
        countDownLatch = new CountDownLatch(threadCounts);
        for (int i=0 ;i< threadCounts ;i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    synchronized (waitObj) {
                        try {
                            waitObj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    if (testListener != null) {
                        testListener.doTest();
                    }


                    countDownLatch.countDown();
                }
            }).start();
        }


        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (waitObj) {
            waitObj.notifyAll();
        }

    }


    //测试方法回调
    public interface ITestListener {
        public void doTest();
    }

}
