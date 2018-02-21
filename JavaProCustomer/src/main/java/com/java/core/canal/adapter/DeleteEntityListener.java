package com.java.core.canal.adapter;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 *
 * 数据删除事件触发
 * Created by zhuangjiesen on 2018/2/21.
 */
public interface DeleteEntityListener {

    public void onEntityDeleted(CanalEntry.Entry entry , CanalEntry.RowChange rowChage , Class entityClazz);

}
