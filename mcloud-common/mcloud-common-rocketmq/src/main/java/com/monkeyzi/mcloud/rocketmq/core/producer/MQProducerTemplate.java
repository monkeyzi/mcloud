package com.monkeyzi.mcloud.rocketmq.core.producer;

import com.alibaba.fastjson.JSON;
import com.monkeyzi.mcloud.rocketmq.exeption.MQException;
import com.monkeyzi.mcloud.rocketmq.message.RocketMQHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

@Slf4j
public class MQProducerTemplate {

    public MQProducerTemplate(){

    }

    /** 生产者*/
    @Autowired
    private DefaultMQProducer producer;


    /** 消息默认字符集 */
    private String charset = "UTF-8";

    /** 默认队列选择器 */
    private MessageQueueSelector defalutMessageQueueSelector = new SelectMessageQueueByHash();

    /**
     * 发送同步消息
     * @param topic 主题
     * @param tag   tag
     * @param message  消息体
     * @return
     */
    public SendResult send(String topic,String tag,Object message){
        return send(topic, tag, message, producer.getSendMsgTimeout());
    }

    /**
     * 发送同步消息 带超时时间
     * @param topic
     * @param tag
     * @param message
     * @param timeout
     * @return
     */
    private SendResult send(String topic, String tag, Object message, int timeout) {
        Message msg = convertMessage(topic, tag, message, null);
        return send(topic,tag,msg,timeout);
    }

    /**
     * 发送消息
     * @param topic
     * @param tag
     * @param msg
     * @param timeout
     * @return
     */
    private SendResult send(String topic, String tag, Message msg, int timeout) {
        if(topic==null || "".equals(topic)){
            throw new IllegalArgumentException("'topic' cannot be null");
        }
        if(msg==null || msg.getBody()==null || msg.getBody().length<=0){
            throw new IllegalArgumentException("'message' and 'message.body' cannot be null");
        }
        try {
            long now = System.currentTimeMillis();
            SendResult sendResult = producer.send(msg, timeout);
            long costTime = System.currentTimeMillis() - now;
            log.debug("send message cost: {} ms, msgId:{}", costTime, sendResult.getMsgId());
            return sendResult;
        }
        catch (Exception e) {
            log.error("syncSend failed. topic:{}, tag:{}, messageBody:{} ", topic, tag, msg.getBody());
            throw new MQException(e.getMessage(), e);
        }
    }

    /**
     * 转换成RocketMQ消息
     * @param topic
     * @param tag
     * @param message
     * @param header
     * @return
     */
    private Message convertMessage(String topic, String tag, Object message, RocketMQHeader header){
        String result;
        if (message instanceof String){
              result= (String) message;
        }else{
              result=JSON.toJSONString(message);
        }
        byte[] messageBody = result.getBytes(Charset.forName(charset));
        Message msg = new Message(topic, tag, messageBody);
        //如果RocketMQHeader不为空
        if(header != null){
            //设置业务keys
            String keys = header.getKeys();
            if(keys!=null && !"".equals(keys)){
                msg.setKeys(keys);
            }
            //设置waitStoreMsgOK，默认值true
            boolean waitStoreMsgOK = header.isWaitStoreMsgOK();
            msg.setWaitStoreMsgOK(waitStoreMsgOK);
        }
        return msg;

    }


}
