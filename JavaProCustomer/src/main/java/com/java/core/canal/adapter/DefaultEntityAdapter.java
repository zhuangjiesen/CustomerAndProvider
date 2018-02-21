package com.java.core.canal.adapter;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.java.core.canal.annotation.CanalTable;
import com.java.core.canal.entity.CanalEntity;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhuangjiesen on 2018/2/21.
 */
public class DefaultEntityAdapter implements EntityAdapter , ApplicationContextAware , InitializingBean {

    private ApplicationContext applicationContext;

    private Map<String , Class> tableEntityMap = new ConcurrentHashMap<>();

    private List<DeleteEntityListener> deleteEntityListeners;
    private List<InsertEntityListener> insertEntityListeners;


    public void init() {
        String[] canalEntityBeanNames = applicationContext.getBeanNamesForType(CanalEntity.class);

        if (canalEntityBeanNames != null) {
            for (String name : canalEntityBeanNames) {
                Object bean = applicationContext.getBean(name);
                Class clazz = bean.getClass();
                CanalTable table = (CanalTable)clazz.getAnnotation(CanalTable.class);
                String tableName = table.value();
                tableEntityMap.put(tableName , clazz);
            }

        }

        String[] insertNames = applicationContext.getBeanNamesForType(InsertEntityListener.class);
        if (insertNames != null) {
            for (String name : insertNames) {
                InsertEntityListener listener = applicationContext.getBean(name , InsertEntityListener.class);
                if (listener != null) {
                    if (insertEntityListeners == null) {
                        insertEntityListeners = new ArrayList<>();
                    }
                    insertEntityListeners.add(listener);
                }
            }
        }
        String[] deleteNames = applicationContext.getBeanNamesForType(DeleteEntityListener.class);
        if (deleteNames != null) {
            for (String name : deleteNames) {
                DeleteEntityListener listener = applicationContext.getBean(name , DeleteEntityListener.class);
                if (listener != null) {
                    if (deleteEntityListeners == null) {
                        deleteEntityListeners = new ArrayList<>();
                    }
                    deleteEntityListeners.add(listener);
                }
            }
        }

    }


    @Override
    public void handleEntities(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            CanalEntry.EventType eventType = rowChage.getEventType();

            // 查询映射
            String tableName = entry.getHeader().getTableName();
            Class canalEntity = tableEntityMap.get(tableName);

            if (eventType == CanalEntry.EventType.DELETE) {
                if (deleteEntityListeners != null) {
                    for (DeleteEntityListener listener : deleteEntityListeners) {
                        listener.onEntityDeleted(entry , rowChage , canalEntity) ;
                    }
                }
            } else if (eventType == CanalEntry.EventType.INSERT) {
                if (insertEntityListeners != null) {
                    for (InsertEntityListener listener : insertEntityListeners) {
                        listener.onEntityInserted(entry , rowChage , canalEntity);
                    }
                }
            } else {

                System.out.println(" not delete and insert ...");
            }

            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

        }
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }



}
