package com.monkeyzi.mcloud.common.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基于redisson的分布式锁
 */
@Slf4j
@Component
public class RedissonDistributedLock {


    @Autowired
    private RedissonClient redissonClient;



    public RedissonDistributedLock(RedissonClient client){
        super();
        this.redissonClient=client;
    }



    public RLock lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        return lock;
    }

    /**
     * 释放锁
     * @param key 锁的key
     */
    public void releaseLock(String key) {
        RLock lock=redissonClient.getLock(key);
        lock.unlock();
    }
}
