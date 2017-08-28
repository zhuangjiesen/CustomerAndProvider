package com.java.core.rpc.dubbo.exporter;

import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.ExporterListener;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * Created by zhuangjiesen on 2017/8/1.
 */
public class DragsunDubboExporterListener implements ExporterListener {
    public void exported(Exporter<?> exporter) throws RpcException {

        System.out.println(" DragsunDubboExporterListener : exported ========= ");


    }

    public void unexported(Exporter<?> exporter) {

        System.out.println(" DragsunDubboExporterListener : unexported ========= ");

    }
}
