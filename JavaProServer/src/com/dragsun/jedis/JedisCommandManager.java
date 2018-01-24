package com.dragsun.jedis;

import redis.clients.jedis.*;

/**
 *
 * 默认的jedis 调用类
 * 通过实现所有的命令接口，之后再对这个类进行动态代理，开发端只需要获取这个类的bean就可以直接调用，
 * 具体命令的读写处理通过动态代理拦截
 * @JedisProxy
 * 框架通过动态代理实现拦截处理
 * 实现读写分离
 * cluster
 * redis 状态监测
 * Created by zhuangjiesen on 2018/1/16.
 */
public abstract class JedisCommandManager implements
        /* jedis 的实现 */
        JedisCommands,
        MultiKeyCommands, AdvancedJedisCommands, ScriptingCommands, BasicCommands, ClusterCommands, SentinelCommands
        /* jedisCluster 的实现  */
        , MultiKeyJedisClusterCommands, JedisClusterScriptingCommands
{


}
