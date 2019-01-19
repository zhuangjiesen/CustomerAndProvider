package com.java.main;

import com.java.helper.DragsunAopHelper;
import org.springframework.context.ApplicationContext;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2019/1/11
 */
public class CommonSpringApp {

    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringInit.init();

        DragsunAopHelper dragsunAopHelper = applicationContext.getBean(DragsunAopHelper.class);
        System.out.println("configvalue : " + dragsunAopHelper.getConfigValue());

    }

}
