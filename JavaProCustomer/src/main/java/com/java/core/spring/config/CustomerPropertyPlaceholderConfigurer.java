package com.java.core.spring.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 *
 * 自定义添加配置
 * @Date: Created in 2019/1/11
 */
public class CustomerPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = new Properties();
        properties.put("placeHolder-config" , "zhuangjiesen");
        this.setProperties(properties);

    }
}
