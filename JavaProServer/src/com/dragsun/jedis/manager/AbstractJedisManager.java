package com.dragsun.jedis.manager;

import com.dragsun.jedis.JedisCommandManager;
import com.dragsun.jedis.JedisPoolWrapper;
import com.dragsun.jedis.RedisServerState;
import com.dragsun.jedis.constant.RedisConstant;
import com.dragsun.jedis.constant.RedisMode;
import com.dragsun.jedis.constant.RedisRole;
import com.dragsun.jedis.utils.RedisParser;
import com.dragsun.jedis.utils.RedisServerHelper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangjiesen on 2018/1/16.
 */
public abstract class AbstractJedisManager implements JedisManager , InitializingBean {
    public static String LOCALHOST_ALIAS = "127.0.0.1";

    private int timeout = 2000;

    /** 检查 master **/
    protected boolean checkMaster;
    protected String mode = "default";
    protected JedisPool jedisPool;
    protected GenericObjectPoolConfig slavePoolConfig;
    protected GenericObjectPoolConfig masterPoolConfig;

    protected volatile RedisServerState redisServerState;

    @Override
    public void init() {
        if (slavePoolConfig == null) {
            slavePoolConfig = masterPoolConfig;
        }

        RedisServerState redisState = getState(jedisPool);
        this.redisServerState = redisState;
        JedisCluster cluster = null;
    }

    @Override
    public JedisCommandManager getJedis() {
        return doGetJedis();
    }


    protected JedisCommandManager doGetJedis(){
        return null;
    }


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }


    public JedisPool getMaster (JedisPool jedisPool) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //设置LOCALHOST的值
            LOCALHOST_ALIAS = jedis.getClient().getHost();
            int port = jedis.getClient().getPort();

            String serverInfo = jedis.info("server");
            Map<String, String> serverInfoMap = RedisParser.parseInfoServer(serverInfo);
            String replicationInfo = jedis.info("Replication");
            Map<String, String> replicationInfoMap = RedisParser.parseInfoServer(replicationInfo);
            String mRole = null;
            if (checkMaster) {
                if ((mRole = replicationInfoMap.get(RedisConstant.ROLE)) != null && (!mRole.toLowerCase().equals(RedisRole.MASTER.getRole()))) {
                    if (checkMaster) {
                        throw new RuntimeException("redis : " + LOCALHOST_ALIAS + " : " + port + " is not master please recheck ");
                    }
                }
            }
            if ((mRole = replicationInfoMap.get(RedisConstant.ROLE)) != null) {
                if ((mRole.toLowerCase().equals(RedisRole.MASTER.getRole()))) {
                    return jedisPool;
                } else if ((mRole.toLowerCase().equals(RedisRole.SLAVE.getRole()))) {
                    JedisPool masterJedisPool = getMasterBySlave(jedis , jedisPool);
                    return masterJedisPool;
                } else {
                    throw new RuntimeException("redis not find role by : " + LOCALHOST_ALIAS + " : " + port);
                }
            } else {
                throw new RuntimeException("redis not find role by : " + LOCALHOST_ALIAS + " : " + port);
            }

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /*
        * 获取 redis 服务的状态
        * */
    public RedisServerState getState(JedisPool jedisPool){
        RedisServerState redisServerState = null;
        JedisPool mMasterJedisPool = getMaster(jedisPool);
        Jedis jedis = null;
        try {
            jedis = mMasterJedisPool.getResource();
            //设置LOCALHOST的值
            LOCALHOST_ALIAS = jedis.getClient().getHost();
            int port = jedis.getClient().getPort();
            RedisMode redisMode = null;
            if ((redisMode = RedisServerHelper.getRedisMode(jedis)) != null) {
                //处理服务
                if (redisMode.equals(RedisMode.SINGLE)) {
                    redisServerState = handleSingle(jedis , mMasterJedisPool);
                } else if (redisMode.equals(RedisMode.MASTERSLAVE)) {
                    redisServerState = handleMasterSlave(jedis, mMasterJedisPool);
                } else if (redisMode.equals(RedisMode.SENTINEL)) {
                    redisServerState = handleSentinel(jedis, mMasterJedisPool);
                } else if (redisMode.equals(RedisMode.CLUSTER)) {
                    redisServerState = handleCluster(jedis, mMasterJedisPool);
                }
            }
        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return redisServerState;
    }







    private JedisPool getMasterBySlave(Jedis slaveJedis , JedisPool slaveJedisPool){
        Map<String , String> replicationInfoMap = RedisParser.parseInfoServer(slaveJedis.info(RedisConstant.REPLICATION));
        String masterHost = replicationInfoMap.get("master_host");
        Integer masterPort = Integer.valueOf(replicationInfoMap.get("master_port"));
        String password = RedisServerHelper.getPassword(slaveJedis);
        JedisPool master = new JedisPool(masterPoolConfig, masterHost, masterPort, timeout , password);
        return master;
    }


    private List<JedisPool> getSlaves(Jedis master , JedisPool masterJedisPool) {
        List<JedisPool> slaves = null;
        String replicationInfo = master.info(RedisConstant.REPLICATION);
        Map<String, String> replicationInfoMap = RedisParser.parseInfoServer(replicationInfo);
        Integer connectedSlaves = Integer.valueOf(replicationInfoMap.get("connected_slaves"));
        if (connectedSlaves.intValue() > 0) {
            slaves = new ArrayList<>();
            String slaveNamePrefix = "slave";
            for (int i = 0 ; i < connectedSlaves.intValue() ; i ++) {
                String slaveName = slaveNamePrefix + i;
                Map<String , String> slaveProperty = RedisParser.getProperty(replicationInfoMap.get(slaveName) ,  ",", "=");
                String host = slaveProperty.get("ip");
                if (host.equals(RedisConstant.LOCALHOST)) {
                    host = LOCALHOST_ALIAS;
                }
                Integer port = Integer.valueOf(slaveProperty.get("port"));
                String password = RedisServerHelper.getPassword(master);
                JedisPool jedisPool = new JedisPool(slavePoolConfig, host, port, timeout , password);
                Jedis jedis = null;
                try {
                    jedis = jedisPool.getResource();
                    //监测连接
                    String pong = jedis.ping();
                    if (pong.toLowerCase().equals("pong")) {
                        slaves.add(jedisPool);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }

        }
        return slaves;
    }



    private JedisPoolWrapper getSingleWrapper(Jedis master , JedisPool masterJedisPool) {
        JedisPoolWrapper wrapper = new JedisPoolWrapper();
        wrapper.setMode(RedisMode.SINGLE.getMode());
        wrapper.setRole(RedisRole.MASTER.getRole());
        wrapper.setPoolConfig(this.masterPoolConfig);
        wrapper.setJedisPool(masterJedisPool);
        String host = master.getClient().getHost();
        if (host.equals(RedisConstant.LOCALHOST)) {
            host = LOCALHOST_ALIAS;
        }
        wrapper.setHost(host);
        Integer port = master.getClient().getPort();
        wrapper.setPort(port);
        List<String> pwds = master.configGet(RedisConstant.REQUIRE_PASS);
        if (pwds != null && pwds.size() > 1) {
            wrapper.setPassword(pwds.get(1));
        }
        return wrapper;
    }


    /*
    * 处理单机情况
    *
    * */
    private RedisServerState handleSingle(Jedis master ,  JedisPool masterJedisPool){
        RedisServerState serverState = new RedisServerState();
        serverState.setMode(RedisMode.SINGLE.getMode());
        JedisPoolWrapper wrapper = getSingleWrapper(master , masterJedisPool);
        serverState.setMaster(wrapper);
        return serverState;
    }





    private JedisPoolWrapper getMasterSlaveWrapper(Jedis master ,  JedisPool masterJedisPool){
        JedisPoolWrapper masterWrapper = getSingleWrapper(master , masterJedisPool);
        masterWrapper.setMode(RedisMode.MASTERSLAVE.getMode());

        List<JedisPool> slaves = getSlaves(master , masterJedisPool);
        if (slaves != null) {
            masterWrapper.setSlaveJedisPoolWrappers(new ArrayList<>());
            for (JedisPool slave : slaves) {
                JedisPoolWrapper slaveWrapper = new JedisPoolWrapper();
                slaveWrapper.setMode(RedisMode.MASTERSLAVE.getMode());
                slaveWrapper.setRole(RedisRole.SLAVE.getRole());
                slaveWrapper.setPoolConfig(this.slavePoolConfig);
                Jedis jedis = null;
                try {
                    jedis = slave.getResource();
                    String host = jedis.getClient().getHost();
                    if (host.equals(RedisConstant.LOCALHOST)) {
                        host = LOCALHOST_ALIAS;
                    }
                    slaveWrapper.setHost(host);
                    Integer port = jedis.getClient().getPort();
                    slaveWrapper.setPort(port);
                    slaveWrapper.setPassword(RedisServerHelper.getPassword(jedis));
                    slaveWrapper.setJedisPool(slave);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                    masterWrapper.getSlaveJedisPoolWrappers().add(slaveWrapper);
                }

            }
        }

        return masterWrapper;
    }


    /*
    * 处理 主从master / slave 情况
    *
    * */
    private RedisServerState handleMasterSlave(Jedis master ,  JedisPool masterJedisPool){
        RedisServerState serverState = new RedisServerState();
        serverState.setMode(RedisMode.MASTERSLAVE.getMode());
        JedisPoolWrapper wrapper = getMasterSlaveWrapper(master , masterJedisPool);
        serverState.setSlaves(wrapper.getSlaveJedisPoolWrappers());
        serverState.setSentinel(wrapper.getSentinelJedisPoolWrappers());
        serverState.setMaster(wrapper);
        return serverState;
    }

    /*
    * 处理 sentinel 情况
    *
    * */
    private RedisServerState handleSentinel(Jedis master ,  JedisPool masterJedisPool){
        RedisServerState serverState = null;


        return serverState;
    }


    /*
    * 处理 cluster 情况
    *
    * */
    private RedisServerState handleCluster(Jedis master ,  JedisPool masterJedisPool){
        RedisServerState serverState = null;


        return serverState;
    }


    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isCheckMaster() {
        return checkMaster;
    }

    public void setCheckMaster(boolean checkMaster) {
        this.checkMaster = checkMaster;
    }


    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public GenericObjectPoolConfig getSlavePoolConfig() {
        return slavePoolConfig;
    }

    public void setSlavePoolConfig(GenericObjectPoolConfig slavePoolConfig) {
        this.slavePoolConfig = slavePoolConfig;
    }

    public GenericObjectPoolConfig getMasterPoolConfig() {
        return masterPoolConfig;
    }

    public void setMasterPoolConfig(GenericObjectPoolConfig masterPoolConfig) {
        this.masterPoolConfig = masterPoolConfig;
    }

    @Override
    public RedisServerState getCurRedisServerState() {
        return this.redisServerState;
    }


    public void setRedisServerState(RedisServerState redisServerState) {
        this.redisServerState = redisServerState;
    }

    @Override
    public void reset() {

    }

    @Override
    public RedisServerState addSlave() {
        RedisServerState serverState = getCurRedisServerState();
        RedisServerState newServerState = getState(serverState.getMaster().getJedisPool());
        return newServerState;
    }





}
