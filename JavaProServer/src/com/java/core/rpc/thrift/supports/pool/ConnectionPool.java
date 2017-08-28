package com.java.core.rpc.thrift.supports.pool;

import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zhuangjiesen on 2017/5/1.
 */
public interface ConnectionPool {


    public TProtocol createNewProtocol();
    public TProtocol getProtocol(String serviceName);
    public void recycleProtocol();
    public void onDestroy();

}
