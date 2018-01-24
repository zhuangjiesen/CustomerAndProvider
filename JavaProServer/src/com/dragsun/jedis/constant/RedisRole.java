package com.dragsun.jedis.constant;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public enum RedisRole {

    /** 主服务 **/
    MASTER("master"),
    /** 从服务 **/
    SLAVE("slave"),
    /** 哨兵 **/
    SENTINEL("sentinel"),
    ;

    private String role;

    RedisRole(String role) {
        this.role = role;
    }


    public RedisRole getRedisRole(String role){
        return RedisRole.valueOf(role);
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
