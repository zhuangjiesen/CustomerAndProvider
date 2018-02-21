package com.java.core.canal.adapter;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 数据更新事件监听
 * Created by zhuangjiesen on 2018/2/21.
 */
public interface InsertEntityListener {

    public void onEntityInserted(CanalEntry.Entry entry , CanalEntry.RowChange rowChage , Class entityClazz);

}
