package com.java.core.rpc.dubbo.filter;

import com.alibaba.dubbo.rpc.*;

/**
 * Created by zhuangjiesen on 2017/8/1.
 */
public class DragsunDubboFilter implements Filter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {


        System.out.println(" DragsunDubboFilter ....invoke ");

        return invoker.invoke(invocation);
    }
}
