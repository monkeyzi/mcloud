package com.monkeyzi.mcloud.rocketmq.config;

import com.monkeyzi.mcloud.rocketmq.annotation.RocketMQConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnBean(MQBaseAutoConfiguration.class)
public class MQConsumerAutoConfiguration  extends MQBaseAutoConfiguration{



    @PostConstruct
    public void  initConsumer(){
        Map<String,Object> beans=applicationContext.getBeansWithAnnotation(RocketMQConsumer.class);
        if (CollectionUtils.isEmpty(beans)&&mqProperties.getTraceEnabled()){

        }
    }

}
