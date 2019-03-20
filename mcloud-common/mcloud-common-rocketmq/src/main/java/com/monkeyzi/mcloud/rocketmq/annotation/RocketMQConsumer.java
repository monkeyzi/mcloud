package com.monkeyzi.mcloud.rocketmq.annotation;

import com.monkeyzi.mcloud.rocketmq.enums.ConsumeMode;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RocketMQConsumer {
    /**
     * 消息消费组
     * @return
     */
    String consumerGroup();

    /**
     * 主题
     * @return
     */
    String topic();

    /**
     * 从哪儿开始消费，默认CONSUME_FROM_LAST_OFFSET
     */
    ConsumeFromWhere consumeFromWhere()  default ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

    /**
     * tag  默认*
     * @return
     */
    String[] tag() default {"*"};

    /**
     * 消费模式，默认是并发消费CONCURRENTLY
     */
    ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;

    /**
     * 消息模式，默认集群模式，还有BROADCASTING广播模式
     */
    MessageModel messageModel() default MessageModel.CLUSTERING;
    /**
     * 最小消费线程数，默认20
     */
    int consumeThreadMin()  default  20;

    /**
     * 最大消费线程数，默认64
     */
    int consumeThreadMax() default 64;


    /**
     * 最大重复消费次数，默认2
     */
    int maxReconsumeTime() default 2;
}
