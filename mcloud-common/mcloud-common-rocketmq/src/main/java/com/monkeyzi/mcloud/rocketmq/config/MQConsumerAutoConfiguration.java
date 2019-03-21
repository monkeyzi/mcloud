package com.monkeyzi.mcloud.rocketmq.config;

import com.monkeyzi.mcloud.rocketmq.annotation.RocketMQConsumer;
import com.monkeyzi.mcloud.rocketmq.core.consumer.AbsMQPushConsumer;
import com.monkeyzi.mcloud.rocketmq.enums.ConsumeMode;
import com.monkeyzi.mcloud.rocketmq.exeption.MQException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
@ConditionalOnBean(MQBaseAutoConfiguration.class)
public class MQConsumerAutoConfiguration  extends MQBaseAutoConfiguration{
    /**
     * 维护一份map用于检测是否用同样的consumerGroup订阅了不同的topic+tag
     */
    private Map<String,String> validConsumerMap;

    @PostConstruct
    public void  initConsumer() throws Exception {
        Map<String,Object> beans=applicationContext.getBeansWithAnnotation(RocketMQConsumer.class);
        if (!CollectionUtils.isEmpty(beans)&&mqProperties.getTraceEnabled()){
           //TODO
        }
        validConsumerMap=new HashMap<>(16);
        for (Map.Entry<String,Object> entry:beans.entrySet()){
             publishConsumer(entry.getKey(),entry.getValue());
        }
        //清空map
        validConsumerMap=null;

    }


    private void publishConsumer(String beanName,Object bean) throws Exception {
         RocketMQConsumer mqConsumer=applicationContext.findAnnotationOnBean(beanName,RocketMQConsumer.class);
         if (StringUtils.isEmpty(mqProperties.getNameServerAddress())){
             throw new MQException("name server addr cannot be null");
         }
         Assert.notNull(mqConsumer.consumerGroup(), "consumer's consumerGroup cannot be null");
         Assert.notNull(mqConsumer.topic(), "consumer's topic cannot be null");

         if (!AbsMQPushConsumer.class.isAssignableFrom(bean.getClass())){
             throw new MQException(bean.getClass().getName()+"--consumer未实现Consumer消费类");
         }

         Environment environment = applicationContext.getEnvironment();

         String consumerGroup = environment.resolvePlaceholders(mqConsumer.consumerGroup());
         String topic = environment.resolvePlaceholders(mqConsumer.topic());
         String tags = "*";
         if(mqConsumer.tag().length == 1) {
            tags = environment.resolvePlaceholders(mqConsumer.tag()[0]);
         } else if(mqConsumer.tag().length > 1) {
            tags = StringUtils.join(mqConsumer.tag(), "||");
         }
         // 检查consumerGroup
         if(!StringUtils.isEmpty(validConsumerMap.get(consumerGroup))) {
            String exist = validConsumerMap.get(consumerGroup);
            throw new RuntimeException("消费组重复订阅，请新增消费组用于新的topic和tag组合: " + consumerGroup + "已经订阅了" + exist);
         } else {
            validConsumerMap.put(consumerGroup+topic, topic + "-" + tags);
         }
         //配置push consumer
         if (AbsMQPushConsumer.class.isAssignableFrom(bean.getClass())){
             DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
             consumer.setNamesrvAddr(mqProperties.getNameServerAddress());
             consumer.setMessageModel(mqConsumer.messageModel());
             consumer.subscribe(topic, tags);
             consumer.setInstanceName(UUID.randomUUID().toString());
             consumer.setVipChannelEnabled(mqProperties.getProducer().isVipChannelEnabled());
             consumer.setConsumeThreadMin(mqConsumer.consumeThreadMin());
             consumer.setConsumeThreadMax(mqConsumer.consumeThreadMax());
             consumer.setConsumeFromWhere(mqConsumer.consumeFromWhere());
             consumer.setMaxReconsumeTimes(mqConsumer.maxReconsumeTime());
             AbsMQPushConsumer absMQPushConsumer= (AbsMQPushConsumer) bean;
             //多线程并发消费
             if (ConsumeMode.CONCURRENTLY.getMode().equals(mqConsumer.consumeMode().getMode())){
                 consumer.registerMessageListener((List<MessageExt> list,
                                                   ConsumeConcurrentlyContext consumeConcurrentlyContext) ->
                         absMQPushConsumer.dealMessage(list, consumeConcurrentlyContext));
             //顺序
             }else if (ConsumeMode.ORDERLY.getMode().equals(mqConsumer.consumeMode().getMode())){
                 consumer.registerMessageListener((List<MessageExt> list,
                                                   ConsumeOrderlyContext consumeOrderlyContext)->
                         absMQPushConsumer.dealMessage(list,consumeOrderlyContext));
             }else {
                 throw new MQException("未知的消息消费方式,支持CONCURRENTLY和ORDERLY两种 ");
             }
             absMQPushConsumer.setConsumer(consumer);
             consumer.start();
         }
         log.info(String.format("%s is ready to subscribe message", bean.getClass().getName()));

    }

}
