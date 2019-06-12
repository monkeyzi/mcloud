package com.monkeyzi.mcloud.common.redis.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * cache属性配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "mcloud.controller-cache")
public class RedisCacheProperties {

    private List<RedisCacheConfig> redisCacheConfigs;


    @Getter
    @Setter
    public static class RedisCacheConfig{
        /**
         * 缓存key
         */
        private String key;
        /**
         * 过期时间 默认60s
         */
        private long second=60;
    }
}
