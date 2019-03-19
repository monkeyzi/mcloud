package com.monkeyzi.mcloud.rocketmq.config;

import com.monkeyzi.mcloud.rocketmq.annotation.EnableMonkeyziRocketMQ;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * ApplicationContext中有Bean使用@EnableMonkeyziRocketMQ，配置生效
 */

@ConditionalOnBean(annotation = EnableMonkeyziRocketMQ.class)
@EnableConfigurationProperties(RocketMQProperties.class)
public class MQBaseAutoConfiguration implements ApplicationContextAware {

    protected RocketMQProperties mqProperties;

    @Autowired
    public void setMqProperties(RocketMQProperties mqProperties) {
        this.mqProperties = mqProperties;
    }

    protected ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
