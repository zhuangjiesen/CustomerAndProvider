package com.java.core.canal.common;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.java.core.canal.adapter.InsertEntityListener;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by zhuangjiesen on 2018/2/21.
 */
public class DefaultInsertEntityListener implements InsertEntityListener {
    @Override
    public void onEntityInserted(CanalEntry.Entry entry  , CanalEntry.RowChange rowChage , Class entityClazz) {
        System.out.println("DefaultInsertEntityListener .. onEntityInserted .. ");
        List<CanalEntry.RowData> rowDatas = rowChage.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatas) {
            printColumn(rowData.getAfterColumnsList());
        }
    }


    private static void printColumn(@NotNull List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
