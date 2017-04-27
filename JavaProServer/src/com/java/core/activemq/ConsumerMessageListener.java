package com.java.core.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			Thread.currentThread().sleep(5000);
			System.out.println("time : "+System.currentTimeMillis()+"  threadName : "+Thread.currentThread().getName());
			
			if(message instanceof TextMessage){
				String className=message.getClass().getName();
				String msgType=message.getJMSType();
				System.out.println("className : "+className);  
				System.out.println("msgType : "+msgType);  
				TextMessage textMsg = (TextMessage) message;  
				System.out.println("消息到达： " + textMsg.getText());

				
			}
			
			
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
