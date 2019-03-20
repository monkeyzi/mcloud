package com.monkeyzi.mcloud.rocketmq.core.consumer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbsMQPushConsumer<T> extends AbsMQConsumer<T> {


    public AbsMQPushConsumer(){

    }

    @Getter
    @Setter
    private DefaultMQPushConsumer consumer;

    /**
     * 重写这个方法实现自己业务
     * @param message
     * @param msgExtMap
     * @return
     */
    public abstract boolean process(T message, Map<String,Object> msgExtMap);

    /**
     * 多线程并发消费
     * @param list
     * @param consumeConcurrentlyContext
     * @return
     */
    public ConsumeConcurrentlyStatus dealMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext){
        for (MessageExt msg:list){
            log.info("MQ receive  msgId: {}, tags : {}" , msg.getMsgId(), msg.getTags());
            T data=parseMessage(msg);
            Map<String,Object> map=msgExtMap(msg);
            if (null!=data&&!process(data,map)){
                log.error("consume fail , ask for re-consume , msgId: {}", msg.getMsgId());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 顺序消费
     * @param list
     * @param consumeOrderlyContext
     * @return
     */
    public ConsumeOrderlyStatus dealMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext){
        for(MessageExt messageExt : list) {
            log.info("MQ receive orderly msgId: {}, tags : {}" , messageExt.getMsgId(), messageExt.getTags());
            T t = parseMessage(messageExt);
            Map<String, Object> ext = msgExtMap(messageExt);
            if( null != t && !process(t, ext)) {
                log.error("consume orderly msg fail , ask for re-consume , msgId: {}", messageExt.getMsgId());
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
        }
        return ConsumeOrderlyStatus.SUCCESS;

    }




}
