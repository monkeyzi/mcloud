package com.monkeyzi.mcloud.common.redis.configuration;

import com.monkeyzi.mcloud.common.redis.lock.RedissonDistributedLock;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author: 高yg
 * @date: 2019/6/16 16:55
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties({RedisProperties.class})
public class RedissonAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    RedissonClient redissonSingle() {
        log.info("redisson 初始化----------------------");
        Config config = new Config();
        String node = redisProperties.getHost();
        int  port=redisProperties.getPort();
        node = node.startsWith("redis://") ? node+":"+port: "redis://" + node+":"+port;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(node);
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }


    @Bean
    @ConditionalOnMissingBean
    public RedissonDistributedLock redissonDistributedLock(RedissonClient client){
        return new RedissonDistributedLock(client);
    }

}
