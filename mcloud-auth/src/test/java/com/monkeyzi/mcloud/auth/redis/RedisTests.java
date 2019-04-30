package com.monkeyzi.mcloud.auth.redis;

import com.monkeyzi.mcloud.auth.McloudQuartzTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTests extends McloudQuartzTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Test
    public void test1(){
        redisTemplate.opsForValue().set("name","guoguo");

    }
}
