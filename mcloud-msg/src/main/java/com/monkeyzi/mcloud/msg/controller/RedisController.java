package com.monkeyzi.mcloud.msg.controller;

import com.monkeyzi.mcloud.common.redis.template.McloudRedisTemplate;
import com.monkeyzi.mcloud.msg.entity.User;
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


    @GetMapping(value = "/kk")
    public String kk(){
        User user=new User();
        user.setUsername("hhhhhhhhhh");
        user.setPassword("sdsdddddd");
        objectRedisTemplate.opsForValue().set("ot",user);
        stringRedisTemplate.opsForValue().set("st",user.toString());
        redisTemplate.set("rt",user);
        final Object ot = objectRedisTemplate.opsForValue().get("ot");
        User user1= (User) objectRedisTemplate.opsForValue().get("ot");
        System.out.println(user1);
        System.out.println(ot);
        final String st = stringRedisTemplate.opsForValue().get("st");
        System.out.println(st);
        final Object rt = redisTemplate.get("rt");
        System.out.println(rt);
        return "ok";
    }
}
