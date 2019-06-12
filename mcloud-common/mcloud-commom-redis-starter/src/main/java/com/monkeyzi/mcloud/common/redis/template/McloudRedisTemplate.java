package com.monkeyzi.mcloud.common.redis.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private static final GenericJackson2JsonRedisSerializer OBJECT_SERIALIZER=new GenericJackson2JsonRedisSerializer();


    /**
     * Spring Redis Template
     */
    private RedisTemplate<String, Object> redisTemplate;


    public McloudRedisTemplate(RedisTemplate<String,Object> redisTemplate){
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
     * 获取字符串序列化方式
     * @return
     */
    protected RedisSerializer<String> getRedisSerializer(){
        return redisTemplate.getStringSerializer();
    }
    /**
     * 清空redis节点数据
     * @param node
     */
    public void flushDB(RedisClusterNode node){
         this.redisTemplate.opsForCluster().flushDb(node);
    }

    /**
     * 添加过期时间的缓存
     * @param key 缓存key
     * @param value 缓存value
     * @param time  过期时间 单位为妙
     */
    public void setExpire(final byte[] key,final byte[] value,final  long time){
        this.redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.setEx(key, time, value);
            log.debug("[redisTemplate controller]放入 缓存  key:{} ========缓存时间为{}秒", key, time);
            return 1L;
        });
    }

    /**
     * 添加缓存  包含过期时间
     * @param key redis缓存key
     * @param value value
     * @param time  过去时间-单位秒
     */
    public void  setExpire(final String key,final Object value,final long time){
        this.redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisSerializer<String> serialize=getRedisSerializer();
            byte[] keys=serialize.serialize(key);
            byte[] values=OBJECT_SERIALIZER.serialize(value);
            redisConnection.setEx(keys,time,values);
            return 1L;
        });
    }
    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     * @param keys   controller key数组
     * @param values 值数组
     * @param time   过期时间(单位秒)
     */
   public void serExpire(final String[] keys,final Object[] values,final long time){
        this.redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisSerializer<String> serializer=getRedisSerializer();
            int length=keys.length;
            for (int i=0;i<length;i++){
                byte[] mKey=serializer.serialize(keys[i]);
                byte[] mValue=OBJECT_SERIALIZER.serialize(values[i]);
                redisConnection.setEx(mKey,time,mValue);
            }
            return 1L;
        });
   }

    /**
     * 一次性添加数组到缓存中，不用多次连接，节省开销 --没有过期时间
     * @param keys   controller key数组
     * @param values 值数组
     */
   public void set(final String[] keys,final Object[] values){
       this.redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
           RedisSerializer<String> serializer=getRedisSerializer();
           int length=keys.length;
           for (int i=0;i<length;i++){
               byte[] key=serializer.serialize(keys[i]);
               byte[] value=OBJECT_SERIALIZER.serialize(values[i]);
               redisConnection.set(key,value);
           }
           return 1L;
       });
   }


    /**
     * 添加指定key到redis中 没有过期时间
     * @param key
     * @param value
     */
  public void set(final String key,final Object value){
        this.redisTemplate.execute((RedisCallback<Long>) redisConnection ->{
            RedisSerializer<String> serializer=getRedisSerializer();
            byte[] keys=serializer.serialize(key);
            byte[] values=OBJECT_SERIALIZER.serialize(value);
            redisConnection.set(keys,values);
            return 1L;
        });
  }

    /**
     * 查询在指定时间内的即将过期的key
     * @param key
     * @param time
     * @return
     */
  public List<String> willExpireKeys(final String key,final long time){
       final List<String> keysList=new ArrayList<>();
       this.redisTemplate.execute((RedisCallback<List<String>>) redisConnection -> {
           Set<String> keys = redisTemplate.keys(key + "*");
           keys.forEach(ks->{
               Long ttl=redisConnection.ttl(ks.getBytes(DEFAULT_CHARSET));
               if (ttl>=0&&ttl<=time){
                   keysList.add(ks);
               }
           });
           return keysList;
       });
       return keysList;
  }



    /**
     * 查询在以keyPatten的所有  key
     *
     * @param keyPatten the key patten
     * @return the set
     */
  public Set<String> keys(final String keyPatten) {
        return redisTemplate.execute((RedisCallback<Set<String>>) redisConnection -> redisTemplate.keys(keyPatten + "*"));
  }

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the byte [ ]
     */
    public byte[] get(final byte[] key) {
        byte[] result = redisTemplate.execute((RedisCallback<byte[]>) redisConnection -> redisConnection.get(key));
        log.debug("[redisTemplate controller]取出 缓存  key:{} ", key);
        return result;
    }


    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the string
     */
    public Object get(final String key) {
        Object resultStr = redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keys = serializer.serialize(key);
            byte[] values = redisConnection.get(keys);
            return OBJECT_SERIALIZER.deserialize(values);
        });
        log.debug("[redisTemplate controller]取出 缓存  key:{},value={} ", key,resultStr);
        return resultStr;
    }

    /**
     * 根据key获取对象
     *
     * @param keyPatten the key patten
     * @return the keys values
     */
    public Map<String, Object> getKeysValues(final String keyPatten) {
        log.debug("[redisTemplate controller]  getValues()  patten={} ", keyPatten);
        return redisTemplate.execute((RedisCallback<Map<String, Object>>) connection -> {
            RedisSerializer<String> serializer = getRedisSerializer();
            Map<String, Object> maps = new HashMap<>(16);
            Set<String> keys = redisTemplate.keys(keyPatten + "*");
            if (CollectionUtils.isNotEmpty(keys)) {
                for (String key : keys) {
                    byte[] bKeys = serializer.serialize(key);
                    byte[] bValues = connection.get(bKeys);
                    Object value = OBJECT_SERIALIZER.deserialize(bValues);
                    maps.put(key, value);
                }
            }
            return maps;
        });
    }






}
