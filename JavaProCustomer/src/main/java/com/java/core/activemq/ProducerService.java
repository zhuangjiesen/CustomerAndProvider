package com.java.core.activemq;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ProducerService {

	   private JmsTemplate jmsTemplate;  
	      
	    public void sendMessage(Destination destination, final String message) {
	        System.out.println("--------------sendMessage-----------------");  
	        System.out.println("-------------sendMessage : " + message);
	        System.out.println("-------------sendMessage  time : " + System.currentTimeMillis());
	        
//	        jmsTemplate.send(destination, new MessageCreator() {
//
//	            public Message createMessage(Session session) throws JMSException {
//
//	            	Message msg =  session.createTextMessage(message);
//
//	                return msg;
//	            }
//	        });
	    }   
	  
	    public JmsTemplate getJmsTemplate() {  
	        return jmsTemplate;  
	    }   
	  
	    @Resource  
	    public void setJmsTemplate(JmsTemplate jmsTemplate) {  
	        this.jmsTemplate = jmsTemplate;  
	    }  
	
}
