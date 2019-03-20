package com.monkeyzi.mcloud.upms.web;

import com.monkeyzi.mcloud.rocketmq.annotation.RocketMQConsumer;
import com.monkeyzi.mcloud.rocketmq.core.consumer.AbsMQPushConsumer;
import com.monkeyzi.mcloud.rocketmq.core.producer.MQProducerTemplate;
import com.monkeyzi.mcloud.rocketmq.enums.ConsumeMode;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RocketMQConsumer(consumerGroup = "my-producer-group",topic = "hello",tag = "key",consumeMode = ConsumeMode.ORDERLY)
public class Hello  extends AbsMQPushConsumer<String> {

    @Autowired
    private MQProducerTemplate rocketMQTemplate;


    @RequestMapping(value = "/test")
    public void test(){
        for (int i=0;i<10;i++){
            SendResult send = rocketMQTemplate.send("hello", "key", "你好"+i);
            System.out.println(send);
        }

    }

    @Override
    public boolean process(String message, Map msgExtMap) {
        System.out.println("消费消息："+message);
        return true;
    }
}
