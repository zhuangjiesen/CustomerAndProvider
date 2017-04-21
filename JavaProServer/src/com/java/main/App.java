package com.java.main;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.java.core.rpc.thrift.supports.TProcessorPorxy;

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
        
        
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(" server is running ....");
				while (true) {
					
				}
				
			}
		}).start();
        
    }
    
    
    
    public static void init(){

    	applicationContext = new FileSystemXmlApplicationContext("/resources/applicationContext.xml"); 
    	
    }
}
