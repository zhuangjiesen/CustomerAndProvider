package com.java.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.beans.PropertyDescriptor;

public class AppApplicationContext implements  ApplicationContextAware  ,   BeanFactoryAware , BeanDefinitionRegistryPostProcessor{
	
	
	private static ApplicationContext context;

	private BeanFactory beanFactory;

	private BeanDefinitionRegistry beanDefinitionRegistry;

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
			return "庄杰森22222--postProcessBeforeInitialization --";
		}
		System.out.println("-----AppApplicationContext -----postProcessBeforeInitialization beanName : " + beanName);

		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("--------AppApplicationContext --------postProcessAfterInitialization beanName : " + beanName);
		if ("dragsunTestService".equals(beanName)) {

			return "庄杰森2222";
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

	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

		System.out.println("--------AppApplicationContext --------postProcessBeforeInstantiation beanName : " + beanName);
		if ("dragsunTestService".equals(beanName)) {

			return null;
		}

		return null;
	}

	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("--------AppApplicationContext --------postProcessAfterInstantiation beanName : " + beanName);


		return false;
	}

	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {

		System.out.println("--------AppApplicationContext --------postProcessPropertyValues beanName : " + beanName);


		return null;
	}
}
