package com.monkeyzi.mcloud.rocketmq.core.producer;

import com.alibaba.fastjson.JSON;
import com.monkeyzi.mcloud.rocketmq.exeption.MQException;
import com.monkeyzi.mcloud.rocketmq.message.RocketMQHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
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
     * @param topic   主题
     * @param tag     tag
     * @param message 消息
     * @param timeout
     * @return
     */
    private SendResult send(String topic, String tag, Object message, long timeout) {
        Message msg = convertMessage(topic, tag, message, null);
        return send(topic,tag,msg,timeout);
    }


    /**
     * 同步发送消息
     * @param topic    主题
     * @param tag      标签
     * @param message  消息体，Object类型
     * @param header   消息头
     * @return
     */
    public SendResult send(String topic, String tag, Object message, RocketMQHeader header) {
        return send(topic, tag, message, header, producer.getSendMsgTimeout());
    }


    /**
     * 同步发送消息
     * @param topic     主题
     * @param tag       标签
     * @param message   消息体，Object类型
     * @param header    消息头
     * @param timeout   消息发送超时时间，单位毫秒
     * @return
     */
    public SendResult send(String topic, String tag, Object message, RocketMQHeader header, long timeout) {
        Message msg = convertMessage(topic, tag, message, header);
        return send(topic,tag,msg,timeout);
    }

    /**
     * 同步发送顺序消息， 使用默认的MessageQueueSelector
     * @param topic     主题
     * @param tag       标签
     * @param message   消息体，Object类型
     * @param hashKey   用于选择队列的key，使用SelectMessageQueueByHash
     * @return
     */
    public SendResult sendOrderly(String topic, String tag, Object message, String hashKey) {
        return sendOrderly(topic, tag, message, hashKey, producer.getSendMsgTimeout());
    }

    /**
     * 同步发送顺序消息，  使用默认的MessageQueueSelector
     * @param message            消息体，
     * @param hashKey    用于选择队列的key，使用SelectMessageQueueByHash
     * @return
     */
    public SendResult sendOrderly(Message message, String hashKey) {
        if (message==null||message.getBody()==null||message.getBody().length<=0){
            throw new IllegalArgumentException("message || message body cannot be null");
        }
        try {
            SendResult sendResult=producer.send(message,defalutMessageQueueSelector,hashKey,producer.getSendMsgTimeout());
            return sendResult;
        }catch (Exception e){
            log.error("message send fail msgBody={}",message.getBody());
            throw new MQException(e.getMessage(),e);
        }
    }

    /**
     * 同步发送顺序消息，使用默认的MessageQueueSelector
     * @param topic              主题
     * @param tag                 标签
     * @param message            消息体，Object类型
     * @param hashKey    用于选择队列的key，使用SelectMessageQueueByHash
     * @param timeout            消息发送超时时间，单位毫秒
     * @return
     */
    public SendResult sendOrderly(String topic, String tag, Object message, String hashKey, long timeout) {
        Message msg = convertMessage(topic, tag, message, null);
        return sendOrderly(topic, tag, msg, null, hashKey, timeout);
    }

    /**
     * 同步发送顺序消息，自定义队列选择器
     * @param topic    主题
     * @param tag       标签
     * @param message  消息体，Object类型
     * @param customMessageQueueSelector  自定义队列选择器
     * @param hashKey  用于选择队列的key
     * @return
     */
    public SendResult sendOrderly(String topic, String tag, Object message,
                                  MessageQueueSelector customMessageQueueSelector, String hashKey) {
        return sendOrderly(topic, tag, message, customMessageQueueSelector, hashKey, producer.getSendMsgTimeout());
    }

    /**
     * 同步发送顺序消息
     * @param topic              主题
     * @param tag                 标签
     * @param message            消息体，Object类型
     * @param customMessageQueueSelector  自定义队列选择器
     * @param hashKey    用于选择队列的key
     * @param timeout            消息发送超时时间，单位毫秒
     * @return
     */
    public SendResult sendOrderly(String topic, String tag, Object message,
                                  MessageQueueSelector customMessageQueueSelector, String hashKey, long timeout) {
        Message rocketmqMsg = convertMessage(topic, tag, message, null);
        return sendOrderly(topic, tag, rocketmqMsg, customMessageQueueSelector, hashKey, timeout);
    }

    /**
     * 发送异步消息
     * @param topic          主题
     * @param tag            标签
     * @param message        消息体，Object类型
     * @param sendCallback   发送结束后的回调方法
     */
    public void asyncSend(String topic, String tag, Object message, SendCallback sendCallback) {
        asyncSend(topic, tag, message, sendCallback, producer.getSendMsgTimeout());
    }

    /**
     * 发送异步消息
     * @param message rocketMq消息
     * @param sendCallback 回调
     */
    public void asyncSend(Message message, SendCallback sendCallback) {
        if (message==null||message.getBody()==null){
            throw new IllegalArgumentException("message cannot be null");
        }
        try {
            producer.send(message,sendCallback,producer.getSendMsgTimeout());
        }catch (Exception e){
            log.error("asyncSend failed. messageBody:{} ", message.getBody());
            throw new MQException(e.getMessage(), e);
        }

    }

    /**
     * 发送异步消息
     * @param topic          主题
     * @param tag            标签
     * @param message        消息体，Object类型
     * @param sendCallback   发送结束后的回调方法
     * @param timeout         消息发送超时时间，单位毫秒
     */
    public void asyncSend(String topic, String tag, Object message, SendCallback sendCallback, long timeout) {
        Message rocketmqMsg = convertMessage(topic, tag, message, null);
        asyncSend(topic, tag, rocketmqMsg, sendCallback, timeout);
    }


    /**
     * 异步发送顺序消息，使用默认的MessageQueueSelector
     * @param topic    主题
     * @param tag       标签
     * @param message  消息体，Object类型
     * @param hashKey   用于选择队列的key，使用SelectMessageQueueByHash
     * @param sendCallback     发送完后的回调方法
     * @return
     */
    public void asyncSendOrderly(String topic, String tag, Object message, String hashKey, SendCallback sendCallback) {
        asyncSendOrderly(topic, tag, message, hashKey, sendCallback, producer.getSendMsgTimeout());
    }

    /**
     * 异步发送顺序消息，使用默认的MessageQueueSelector
     * @param topic              主题
     * @param tag                 标签
     * @param message            消息体，Object类型
     * @param hashKey    用于选择队列的key，使用SelectMessageQueueByHash
     * @param sendCallback       发送完后的回调方法
     * @param timeout            消息发送超时时间，单位毫秒
     * @return
     */
    public void asyncSendOrderly(String topic, String tag, Object message, String hashKey, SendCallback sendCallback, long timeout) {
        Message rocketmqMsg = convertMessage(topic, tag, message, null);
        asyncSendOrderly(topic, tag, rocketmqMsg, null, hashKey, sendCallback, timeout);
    }

    /**
     * 异步发送顺序消息
     * @param message rocketMq 消息
     * @param hashKey
     * @param sendCallback 回调
     */
    public void asyncSendOrderly(Message message, String hashKey, SendCallback sendCallback) {
        if (message==null||message.getBody()==null){
            throw new IllegalArgumentException("message cannot be null");
        }
        if (StringUtils.isEmpty(hashKey)){
            throw new IllegalArgumentException("hashKey cannot be null");
        }
        if (sendCallback==null){
            throw new IllegalArgumentException("sendCallback cannot be null");
        }
        try {
           producer.send(message,defalutMessageQueueSelector,hashKey,sendCallback,producer.getSendMsgTimeout());
        }catch (Exception e){
           log.error("asyncSendOrderly failed. messageBody:{} ", message.getBody());
           throw new MQException(e.getMessage(), e);
        }
    }
    /**
     * 异步发送顺序消息，自定义队列选择器
     * @param topic    主题
     * @param tag       标签
     * @param message  消息体，Object类型
     * @param customMessageQueueSelector  自定义队列选择器
     * @param hashKey  用于选择队列的key
     * @param sendCallback     发送完后的回调方法
     * @return
     */
    public void asyncSendOrderly(String topic, String tag, Object message,
                                 MessageQueueSelector customMessageQueueSelector,
                                 String hashKey, SendCallback sendCallback) {
        asyncSendOrderly(topic, tag, message, customMessageQueueSelector, hashKey, sendCallback, producer.getSendMsgTimeout());
    }


    /**
     * 异步发送顺序消息
     * @param topic              主题
     * @param tag                 标签
     * @param message            消息体，Object类型
     * @param customMessageQueueSelector  自定义队列选择器
     * @param hashKey    用于选择队列的key
     * @param sendCallback       发送完后的回调方法
     * @param timeout            消息发送超时时间，单位毫秒
     * @return
     */
    public void asyncSendOrderly(String topic, String tag, Object message,
                                 MessageQueueSelector customMessageQueueSelector,
                                 String hashKey, SendCallback sendCallback,
                                 long timeout) {
        Message rocketmqMsg = convertMessage(topic, tag, message, null);
        asyncSendOrderly(topic, tag, rocketmqMsg, customMessageQueueSelector, hashKey, sendCallback, timeout);
    }
    /**
     *
     * 调用同步发送顺序消息
     * @param topic 主题
     * @param tag   标签
     * @param message mq消息
     * @param messageQueueSelector 队列选择器
     * @param hashKey  用于选择队列的key
     * @param timeout  超时时长
     * @return
     */
    private SendResult sendOrderly(String topic,String tag,Message message,
                                   MessageQueueSelector messageQueueSelector,String hashKey,long timeout){
        if (StringUtils.isEmpty(topic)){
            throw new IllegalArgumentException("topic cannot be null");
        }
        if (message==null||message.getBody()==null||message.getBody().length<=0){
            throw new IllegalArgumentException("'message' and 'message.body' cannot be null");
        }
        if (StringUtils.isEmpty(hashKey)){
            throw new IllegalArgumentException("hashKey cannot be null");
        }
        if (messageQueueSelector==null){
            messageQueueSelector=this.defalutMessageQueueSelector;
        }
        try {
            long now=System.currentTimeMillis();
            SendResult sendResult = producer.send(message, messageQueueSelector, hashKey, timeout);
            long costTime = System.currentTimeMillis() - now;
            log.debug("send message cost: {} ms, msgId:{}", costTime, sendResult.getMsgId());
            return sendResult;
        }catch (Exception e){
            log.error("syncSendOrderly failed. topic:{}, tag:{}, messageBody:{} ", topic, tag, message.getBody());
            throw new MQException(e.getMessage(), e);
        }
    }


    /**
     * 真正的发送异步消息，用于对响应时间敏感的业务场景，方法会立刻返回，在发送结束后会执行SendCallback
     * @param topic            主题
     * @param tag              标签
     * @param message          rocketmq message
     * @param sendCallback     发送结束后的回调方法
     * @param timeout          消息发送超时时间，单位毫秒
     */
    private void asyncSend(String topic, String tag, Message message, SendCallback sendCallback,long timeout){
        if(topic==null || "".equals(topic)){
            throw new IllegalArgumentException("'topic' cannot be null");
        }
        if(message==null || message.getBody()==null || message.getBody().length<=0){
            throw new IllegalArgumentException("'message' and 'message.body' cannot be null");
        }
        if (sendCallback==null){
            throw new IllegalArgumentException("'sendCallback' cannot be null");
        }
        try {
           producer.send(message,sendCallback,timeout);
        }catch (Exception e){
            log.error("asyncSend failed. topic:{}, tag:{}, messageBody:{} ", topic, tag, message.getBody());
            throw new MQException(e.getMessage(),e);
        }
    }
    /**
     * 调用发送消息
     * @param topic   主题
     * @param tag     tag
     * @param msg     消息
     * @param timeout 超时时间
     * @return
     */
    private SendResult send(String topic, String tag, Message msg, long timeout) {
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
     * 真正调用异步发送顺序消息
     * @param topic                 主题
     * @param tag                   标签
     * @param message           rocketmq message
     * @param messageQueueSelector  队列选择器，如果没传默认使用SelectMessageQueueByHash
     * @param hashKey               用于选择队列的key，使用SelectMessageQueueByHash
     * @param sendCallback          发送完成后的回调方法
     * @param timeout               消息发送超时时间，单位毫秒
     * @return
     */
    private void asyncSendOrderly(String topic,String tag,Message message,MessageQueueSelector messageQueueSelector,
                                  String hashKey,SendCallback sendCallback,long timeout){
        if (StringUtils.isEmpty(topic)){
            throw new IllegalArgumentException("topic cannot be null");
        }
        if (message==null||message.getBody()==null||message.getBody().length<=0){
            throw new IllegalArgumentException("'message' and 'message.body' cannot be null");
        }
        if (StringUtils.isEmpty(hashKey)){
            throw new IllegalArgumentException("hashKey cannot be null");
        }
        if (messageQueueSelector==null){
            messageQueueSelector=this.defalutMessageQueueSelector;
        }
        if(sendCallback==null){
            throw new IllegalArgumentException("'sendCallback' cannot be null");
        }
        try {
            producer.send(message, messageQueueSelector, hashKey, sendCallback, timeout);
        } catch (Exception e) {
            log.error("asyncSendOrderly failed. topic:{}, tag:{}, messageBody:{} ", topic, tag, message.getBody());
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
