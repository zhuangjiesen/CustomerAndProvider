package com.java.core.rpc.thrift.supports.pool.bo;

import org.apache.thrift.transport.TSocket;

/**
 * Created by zhuangjiesen on 2017/4/30.
 */
public class TSocketFactory {

    private String host;
    private int port;
    private int connectTimeout;
    private int socketTimeout;
    private int timeout;
    public TSocket newTSocket(){
        TSocket socket = new TSocket(host,port);



        socket.setConnectTimeout(connectTimeout);
        socket.setSocketTimeout(socketTimeout);
        socket.setTimeout(timeout);



        return socket;
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

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
