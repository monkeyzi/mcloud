package com.monkeyzi.mcloud.common.core.lock;

/**
 * 分布式锁接口
 * 说明： RETRY_TIMES=50，SLEEP_MILLIS=100
 *       RETRY_TIMES * SLEEP_MILLIS = 5000 意味着如果一直获取不了锁，最长会等待5秒后抛超时异常
 */
public interface DistributedLock {

    /**
     * 默认超时时间
     */
    long TIMEOUT_MILLIS = 5000;

    /**
     * 重试次数
     */
    int RETRY_TIMES = 100;

    /**
     * 每次重试后等待的时间
     */
    long SLEEP_MILLIS = 100;

    /**
     * 获取锁
     * @param key key
     * @return 获取锁成功或者失败
     */
    boolean lock(String key);

    /**
     * 获取锁
     * @param key key
     * @param retryTimes 重试次数
     * @return 成功或者失败
     */
    boolean lock(String key,int retryTimes);

    /**
     * 获取锁
     * @param key key
     * @param retryTimes 重试次数
     * @param sleepMillis 每次重试间隔时间
     * @return
     */
    boolean lock(String key,int retryTimes,long sleepMillis);

    /**
     * 获取锁
     * @param key key
     * @param expire 获取锁的超时时间
     * @return 成功/失败
     */
    boolean lock(String key,long expire);

    /**
     * 获取锁
     * @param key key
     * @param expire 获取锁的超时时间
     * @param retryTimes 重试次数
     * @return 成功、失败
     */
    boolean lock(String key, long expire, int retryTimes );

    /**
     * 获取锁
     * @param key key
     * @param expire 获取锁的超时时间
     * @param retryTimes 重试次数
     * @param sleepMillis 每次重试时间间隔
     * @return 成功、失败
     */
    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    /**
     * 释放锁
     * @param key key
     * @return 释放锁的结果  成功、失败
     */
    boolean releaseLock(String key);
}
