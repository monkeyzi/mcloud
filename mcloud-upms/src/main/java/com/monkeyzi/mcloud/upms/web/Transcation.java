package com.monkeyzi.mcloud.upms.web;

import com.monkeyzi.mcloud.rocketmq.annotation.RocketMQConsumer;
import com.monkeyzi.mcloud.rocketmq.core.consumer.AbsMQPushConsumer;
import com.monkeyzi.mcloud.rocketmq.enums.ConsumeMode;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RocketMQConsumer(consumerGroup = "transcation-group",topic = "TopicTest1234",consumeMode = ConsumeMode.ORDERLY)
public class Transcation extends AbsMQPushConsumer<String> {
    @Override
    public boolean process(String message, Map<String, Object> msgExtMap) {
        System.out.println("事务消息:"+message);
        return true;
    }
}
