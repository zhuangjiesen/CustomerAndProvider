package com.java.helper;

import com.java.core.proxy.ProxyMethodInterceptor;
import lombok.Data;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by zhuangjiesen on 2017/8/21.
 */
@Data
public class DragsunAopHelper implements BeanPostProcessor , InitializingBean{

    private String configValue;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if ("dragsunTestService".equals(beanName)) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            ProxyMethodInterceptor proxyMethodInterceptor = new ProxyMethodInterceptor();
            enhancer.setCallback(proxyMethodInterceptor);
            return enhancer.create();
        }


        System.out.println("-----DragsunAopHelper ...postProcessAfterInitialization....");
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("configValue: " + configValue);
    }

}
