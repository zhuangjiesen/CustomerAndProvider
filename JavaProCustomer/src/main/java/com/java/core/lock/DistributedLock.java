package com.java.core.lock;

/**
 *
 * 分布式锁接口
 * Created by zhuangjiesen on 2017/12/8.
 */
public interface DistributedLock {
    public final static String DEFAULT_LOCK_KEY = "DEFAULT_LOCK_KEY_OF_DISTRIBUTEDLOCK";
    public void lock(String key) ;
    public boolean tryLock(String key) ;
    public void unLock() ;
    public void unLock(String key) ;
    /*
    * 用来执行分布式锁的一些问题，比如lock 之后，服务挂了，重启之后其他线程都拿不到锁了
    * 为了解决这个情况，可以使用一个定时任务，检测锁状态
    * */
    public void scheduleTask();
}
