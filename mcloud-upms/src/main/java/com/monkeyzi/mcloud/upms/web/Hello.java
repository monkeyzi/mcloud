package com.monkeyzi.mcloud.upms.web;

import com.monkeyzi.mcloud.rocketmq.annotation.RocketMQConsumer;
import com.monkeyzi.mcloud.rocketmq.core.consumer.AbsMQPushConsumer;
import com.monkeyzi.mcloud.rocketmq.core.producer.MQProducerTemplate;
import com.monkeyzi.mcloud.rocketmq.enums.ConsumeMode;
import com.monkeyzi.mcloud.rocketmq.message.MessageExtConst;
import com.monkeyzi.mcloud.rocketmq.message.RocketMQHeader;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RocketMQConsumer(consumerGroup = "ttt",topic = "quick_topic",tag = "*",consumeMode = ConsumeMode.ORDERLY)
public class Hello  extends AbsMQPushConsumer<String> {

    @Autowired
    private MQProducerTemplate rocketMQTemplate;


    @RequestMapping(value = "/test")
    public void test() throws Exception {
        RocketMQHeader header=new RocketMQHeader();
        header.setKeys("kkk");
        final SendResult send = rocketMQTemplate.send("hello", "order", "wowowo", header);
        System.out.println(send);
      /* String[] tags = new String[]{"创建订单", "支付", "发货", "收货", "五星好评"};

        for (int i = 5; i < 25; i++) {
            String orderId = i / 5+"";
            Message msg = new Message("hello", tags[i % tags.length], "uniqueId:" + i,
                    ("order_"+ orderId +"" +tags[i % tags.length]).getBytes(RemotingHelper.DEFAULT_CHARSET));
            rocketMQTemplate.asyncSendOrderly(msg,orderId, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(orderId+":"+sendResult);
                }

                @Override
                public void onException(Throwable throwable) {

                }
            });
        }*/


    }


    @Override
    public boolean process(String message, Map<String, Object> msgExtMap) {
        System.out.println("消费消息"+message +"------msgId"+
                msgExtMap.get(MessageExtConst.PROPERTY_EXT_MSG_ID)+"===="
                +msgExtMap.get(MessageExtConst.PROPERTY_EXT_QUEUE_ID));
        return true;
    }
}
