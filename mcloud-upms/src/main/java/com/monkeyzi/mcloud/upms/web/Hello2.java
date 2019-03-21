package com.monkeyzi.mcloud.upms.web;

import com.monkeyzi.mcloud.rocketmq.annotation.MQTransactionProducer;
import com.monkeyzi.mcloud.rocketmq.core.producer.AbstractMQTransactionProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@MQTransactionProducer(producerGroup = "transaction-group")
public class Hello2  extends AbstractMQTransactionProducer {

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @RequestMapping(value = "/test2")
    public void test() {
        String[] tags = new String[] {"TagA", "TagB"};
        for (int i = 0; i < 3; i++) {
            try {
                Message msg =
                        new Message("TopicTest1234", tags[i % 2], "KEY" + i,
                                ("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = sendTransactionMessage(msg,null);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object o) {
        System.out.println("执行本地事务msg = " + new String(msg.getBody()) + "   ;arg = " + o);
        String tags = msg.getTags();
        if (tags.equals("TagB")) {
            System.out.println("进行-------ROLLBACK");
            return LocalTransactionState.ROLLBACK_MESSAGE;//回滚
        }
        return LocalTransactionState.COMMIT_MESSAGE;//提交
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        try {
            System.out.println("未决事务，服务器回查客户端msg =" + new String(msg.getBody(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(i%2);
        }
    }

}
