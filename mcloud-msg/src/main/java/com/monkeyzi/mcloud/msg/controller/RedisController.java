package com.monkeyzi.mcloud.msg.controller;

import com.monkeyzi.mcloud.common.redis.template.McloudRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 高yg
 * @date: 2019/6/12 22:56
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private McloudRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String,Object> objectRedisTemplate;

    @GetMapping(value = "redis")
    public String  tt(){
        String value="ewrwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
        stringRedisTemplate.opsForValue().set("gg",value);
        objectRedisTemplate.opsForValue().set("oo",value);
        objectRedisTemplate.opsForValue().set("co",4);
        redisTemplate.set("mm",value);
        String gg=stringRedisTemplate.opsForValue().get("gg");
        System.out.println(gg);
        System.out.println(gg.equals(value));
        Object mm = redisTemplate.get("mm");
        System.out.println(mm.equals(value));
        System.out.println(mm);
        return "ok";
    }
}
