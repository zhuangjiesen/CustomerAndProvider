package com.dragsun.jedis.constant;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public enum RedisMode {

    /** 单机 **/
    SINGLE ("single" ),
    /** 主从结构 **/
    MASTERSLAVE ("masterslave"),
    /** 哨兵模式 默认带主从 **/
    SENTINEL ("sentinel" ),
    /** 集群 **/
    CLUSTER ("cluster" ),
    ;

    private String mode;


    RedisMode(String mode) {
        this.mode = mode;
    }

    public static RedisMode getMode(String mode) {
        if (mode != null && mode.length() > 0) {
            RedisMode[] redisModes = RedisMode.values();
            if (redisModes.length > 0) {
                for (RedisMode item : redisModes) {
                    if (item.getMode().equals(mode)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }



}
