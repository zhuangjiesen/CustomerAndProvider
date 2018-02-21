package com.java.core.canal.entity;

import com.java.core.canal.annotation.CanalField;
import com.java.core.canal.annotation.CanalTable;

import java.sql.Timestamp;

/**
 * Created by zhuangjiesen on 2018/2/21.
 */

@CanalTable(value = "goods")
public class Goods implements CanalEntity {

    @CanalField(value = "i_id")
    private Long id;
    @CanalField(value = "c_name")
    private String name;
    @CanalField(value = "goods_type")
    private String type;
    @CanalField(value = "i_qty")
    private Integer qty;
    @CanalField(value = "create_time")
    private Timestamp createTime;
    @CanalField(value = "end_time")
    private Timestamp endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
