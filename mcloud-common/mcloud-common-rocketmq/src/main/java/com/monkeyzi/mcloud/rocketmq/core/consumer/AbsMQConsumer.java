package com.monkeyzi.mcloud.rocketmq.core.consumer;

import com.google.gson.Gson;
import com.monkeyzi.mcloud.rocketmq.message.MessageExtConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbsMQConsumer<T> {

    private static Gson gson=new Gson();


    /**
     * 解析消息 body
     * @param msg
     * @return
     */
    protected  T parseMessage(MessageExt msg){

        if (msg==null||msg.getBody()==null){
            return null;
        }
        final Type type=getMessageType();

        try {
            T data;
            //解决字符串 空格报错
            if (type.getTypeName().equals("java.lang.String")){
                data= (T) new String(msg.getBody(),"UTF-8");
            }else {
                data=gson.fromJson(new String(msg.getBody(),"UTF-8"),type);
            }
            return data;
        }catch (Exception e){
            log.error("parse message json fail : {}", e.getMessage());
        }
        return null;
    }

    /**
     * 封装消息其他属性
     * @param message
     * @return
     */
    protected Map<String,Object> msgExtMap(MessageExt message){
        Map<String,Object> extMap=new HashMap<>(32);
        // parse message property
        extMap.put(MessageExtConst.PROPERTY_TOPIC, message.getTopic());
        extMap.putAll(message.getProperties());

        // parse messageExt property
        extMap.put(MessageExtConst.PROPERTY_EXT_BORN_HOST, message.getBornHost());
        extMap.put(MessageExtConst.PROPERTY_EXT_BORN_TIMESTAMP, message.getBornTimestamp());
        extMap.put(MessageExtConst.PROPERTY_EXT_COMMIT_LOG_OFFSET, message.getCommitLogOffset());
        extMap.put(MessageExtConst.PROPERTY_EXT_MSG_ID, message.getMsgId());
        extMap.put(MessageExtConst.PROPERTY_EXT_PREPARED_TRANSACTION_OFFSET, message.getPreparedTransactionOffset());
        extMap.put(MessageExtConst.PROPERTY_EXT_QUEUE_ID, message.getQueueId());
        extMap.put(MessageExtConst.PROPERTY_EXT_QUEUE_OFFSET, message.getQueueOffset());
        extMap.put(MessageExtConst.PROPERTY_EXT_RECONSUME_TIMES, message.getReconsumeTimes());
        extMap.put(MessageExtConst.PROPERTY_EXT_STORE_HOST, message.getStoreHost());
        extMap.put(MessageExtConst.PROPERTY_EXT_STORE_SIZE, message.getStoreSize());
        extMap.put(MessageExtConst.PROPERTY_EXT_STORE_TIMESTAMP, message.getStoreTimestamp());
        extMap.put(MessageExtConst.PROPERTY_EXT_SYS_FLAG, message.getSysFlag());
        extMap.put(MessageExtConst.PROPERTY_EXT_BODY_CRC, message.getBodyCRC());
        return extMap;
    }

    /**
     * 获取消息类型
     * @return
     */
    protected Type getMessageType(){
        Type superType=this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
            return actualTypeArguments[0];
        } else {
            // 如果没有定义泛型，解析为Object
            return Object.class;
        }
    }
}
