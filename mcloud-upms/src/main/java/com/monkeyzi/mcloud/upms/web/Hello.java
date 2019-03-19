package com.monkeyzi.mcloud.upms.web;

import com.monkeyzi.mcloud.rocketmq.core.producer.MQProducerTemplate;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @Autowired
    private MQProducerTemplate rocketMQTemplate;


    @RequestMapping(value = "/test")
    public void test(){
        SendResult send = rocketMQTemplate.send("hello", "key", "你好");
        System.out.println(send);
    }
}
