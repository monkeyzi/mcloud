package com.monkeyzi.mcloud.common.redis.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * redis基本操作封装
 */
@Slf4j
public class McloudRedisTemplate {
    /**
     * 编码字符集
     */
    private static final Charset DEFAULT_CHARSET=StandardCharsets.UTF_8;

    /**
     * String序列化
     */
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
    /**
     * jdk序列化
     */
    private static final JdkSerializationRedisSerializer OBJECT_SERIALIZER = new JdkSerializationRedisSerializer();


    /**
     * Spring Redis Template
     */
    private RedisTemplate<String, Object> redisTemplate;


    private McloudRedisTemplate(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate=redisTemplate;
        this.redisTemplate.setKeySerializer(STRING_SERIALIZER);
        this.redisTemplate.setValueSerializer(OBJECT_SERIALIZER);
    }

    /**
     * redis连接工厂
     * @return
     */
    public RedisConnectionFactory getConnectionFactory(){
        return this.redisTemplate.getConnectionFactory();
    }

    /**
     * 获取redis template
     * @return
     */
    public RedisTemplate<String,Object> getRedisTemplate(){
        return this.redisTemplate;
    }


    /**
     * 清空redis节点数据
     * @param node
     */
    public void flushDB(RedisClusterNode node){
         this.redisTemplate.opsForCluster().flushDb(node);
    }








}
