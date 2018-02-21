package com.java.core.canal.adapter;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

/**
 * Created by zhuangjiesen on 2018/2/21.
 */
public interface EntityAdapter {

    public void handleEntities(List<CanalEntry.Entry> entries);

}
