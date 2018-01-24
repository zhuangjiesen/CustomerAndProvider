package com.java.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by zhuangjiesen on 2017/12/8.
 */
public class SpringInit {


    public static ApplicationContext init(){
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("/resources/applicationContext.xml");
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while (true) {}
            }
        });
        t1.start();
        return applicationContext;
    }

}
