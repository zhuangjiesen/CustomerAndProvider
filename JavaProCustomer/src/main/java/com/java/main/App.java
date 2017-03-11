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

        for (int i=0 ;i<10 ;i++) {
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					testService.doThriftTest();
					
					
				}
			}).start();
        }
        
    }
    
    
    
    public static void init(){

    	applicationContext = new FileSystemXmlApplicationContext("/resources/applicationContext.xml"); 
    	
    }
}
