package com.java.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.java.service.TestService;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static ApplicationContext applicationContext;
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        init();
		final TestService testService=applicationContext.getBean(TestService.class);


        ITestListener testListener = new ITestListener() {
            public void doTest() {
                testService.doThriftInfoTest();
            }
        };

        doThriftTest(2000,testListener);
    }
    


    public static void doThriftTest (int threadCounts,final ITestListener testListener){
        final Object waitObj = new Object();

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



                }
            }).start();
        }


        try {
            Thread.currentThread().sleep(1000);
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
    
    public static void init(){

    	applicationContext = new FileSystemXmlApplicationContext("/resources/applicationContext.xml"); 
    	
    }
}
