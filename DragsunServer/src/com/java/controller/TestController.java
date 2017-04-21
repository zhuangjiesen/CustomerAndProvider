package com.java.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuangjiesen on 2017/4/15.
 */
@Controller
public class TestController {


    private static final Logger log= LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "toIndex.do")
    public String toIndex(){

        log.debug("========================");

        return "index";
    }

}
