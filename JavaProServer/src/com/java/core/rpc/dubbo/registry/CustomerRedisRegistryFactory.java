package com.java.core.rpc.dubbo.registry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/9/19
 */
@Slf4j
@SPI
public class CustomerRedisRegistryFactory implements RegistryFactory {
    @Override
    public Registry getRegistry(URL url) {
        String password = url.getParameter("password");
        String host = url.getHost();
        int port = url.getPort();
        url.getIp();
        log.info(String.format(" CustomerRedisRegistryFactory : %s " , url));
        return new CustomerRedisRegistry(url);
    }
}
