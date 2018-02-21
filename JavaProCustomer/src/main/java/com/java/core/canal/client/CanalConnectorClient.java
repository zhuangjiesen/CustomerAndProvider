package com.java.core.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.java.core.canal.adapter.EntityAdapter;
import com.sun.istack.internal.NotNull;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuangjiesen on 2018/2/21.
 */
public class CanalConnectorClient {

    private String host;
    private int port;
    private int idleTime = 500;
    private String destination;
    private String username;
    private String password;

    private int batchSize;
    private String subscribeRegex = ".*\\..*";

    private EntityAdapter entityAdapter;


    private ExecutorService executorService = Executors.newFixedThreadPool(1);



    public void start() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                InetSocketAddress address = new InetSocketAddress( host ,
                        port);
                CanalConnector connector = CanalConnectors.newSingleConnector(address , destination , username , password);
                try {
                    connector.connect();
                    connector.subscribe(subscribeRegex);
                    connector.rollback();
                    while (true) {

                        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                        long batchId = message.getId();
                        int size = message.getEntries().size();
                        if (batchId == -1 || size == 0) {
                            try {
                                Thread.sleep(idleTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            entityAdapter.handleEntities(message.getEntries());
                        }
                        connector.ack(batchId); // 提交确认
                        // connector.rollback(batchId); // 处理失败, 回滚数据
                    }
                } finally {
                    connector.disconnect();
                }
            }
        });

    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getSubscribeRegex() {
        return subscribeRegex;
    }

    public void setSubscribeRegex(String subscribeRegex) {
        this.subscribeRegex = subscribeRegex;
    }

    public EntityAdapter getEntityAdapter() {
        return entityAdapter;
    }

    public void setEntityAdapter(EntityAdapter entityAdapter) {
        this.entityAdapter = entityAdapter;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
