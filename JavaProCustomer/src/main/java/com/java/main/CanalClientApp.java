package com.java.main;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.client.*;
import com.java.core.canal.client.CanalConnectorClient;
import com.java.core.canal.entity.CanalEntity;
import com.java.core.canal.entity.Goods;
import com.sun.istack.internal.NotNull;
import org.springframework.context.ApplicationContext;

import java.net.InetSocketAddress;
import java.util.List;

/**
 *
 * canal 客户端程序
 * Created by zhuangjiesen on 2018/2/15.
 */
public class CanalClientApp {

    private static ApplicationContext applicationContext;

    public static void main(String args[]) {
        applicationContext = SpringInit.init();
        CanalConnectorClient client = applicationContext.getBean(CanalConnectorClient.class);
        client.start();

        System.out.println(" canal client starting ....");

        // 创建链接
//        InetSocketAddress address = new InetSocketAddress(AddressUtils.getHostIp(),
//                11111);

    }


}
