package com.java.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanHelper implements ApplicationContextAware   , BeanPostProcessor , BeanFactoryAware , BeanDefinitionRegistryPostProcessor{
	
	
	private static ApplicationContext context;

	public static BeanFactory beanFactory;

	public static BeanDefinitionRegistry beanDefinitionRegistry;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		context=applicationContext;
	}
	
	
	public static ApplicationContext getContext(){
		return context;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {


		if ("dragsunTestService".equals(beanName)) {
//			boolean contains = beanFactory.containsBean(beanName);
//			beanDefinitionRegistry.removeBeanDefinition(beanName);
//			contains = beanFactory.containsBean(beanName);
			return bean;
		}
		System.out.println("-----BeanHelper------postProcessBeforeInitialization beanName : " + beanName);

		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("------BeanHelper-------postProcessAfterInitialization beanName : " + beanName);
		if ("dragsunTestService".equals(beanName)) {

			return bean;
		}

		return bean;
	}

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		beanDefinitionRegistry = registry;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}
}
