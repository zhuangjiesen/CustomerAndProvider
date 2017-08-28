package com.java.core.rpc.thrift.supports.pool;

/**
 * Created by zhuangjiesen on 2017/5/1.
 */
public interface ServiceClientPool {


    public Object getClientInstance(String serviceName);

    public void recycleClient();


}
