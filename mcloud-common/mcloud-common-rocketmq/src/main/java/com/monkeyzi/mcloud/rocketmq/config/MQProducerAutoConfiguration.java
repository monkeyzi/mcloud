package com.monkeyzi.mcloud.rocketmq.config;

import com.monkeyzi.mcloud.rocketmq.core.producer.MQProducerTemplate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnBean(MQBaseAutoConfiguration.class)
public class MQProducerAutoConfiguration  extends MQBaseAutoConfiguration{

    @Setter
    private static DefaultMQProducer producer;



    @Bean
    public DefaultMQProducer mqProducer() throws Exception {
        Map<String,MQProducerTemplate>  beans=applicationContext.getBeansOfType(MQProducerTemplate.class);
        //对于只需要消费者的项目不需要构建生产者
        if (CollectionUtils.isEmpty(beans)){
            return null;
        }
        if (producer==null){
            Assert.notNull(mqProperties.getProducer().getProducerGroup(),"producer group can not be null");
            Assert.notNull(mqProperties.getNameServerAddress(),"name server address can not be null");

            producer=new DefaultMQProducer(mqProperties.getProducer().getProducerGroup());
            producer.setNamesrvAddr(mqProperties.getNameServerAddress());
            producer.setVipChannelEnabled(mqProperties.getProducer().isVipChannelEnabled());
            producer.setSendMsgTimeout(mqProperties.getProducer().getSendMsgTimeout());
            producer.setMaxMessageSize(mqProperties.getProducer().getMaxMessageSize());
            producer.setRetryTimesWhenSendFailed(mqProperties.getProducer().getRetryTimesWhenSendFailed());
            producer.setRetryTimesWhenSendAsyncFailed(mqProperties.getProducer().getRetryTimesWhenSendAsyncFailed());
            producer.setRetryAnotherBrokerWhenNotStoreOK(mqProperties.getProducer().isRetryAnotherBrokerWhenNotStoreOk());
            producer.setCompressMsgBodyOverHowmuch(mqProperties.getProducer().getCompressMsgBodyOverHowmuch());
            log.info("DefaultMQProducer初始化完成 " +producer);
            producer.start();
        }
        return producer;
    }



    @Bean
    @ConditionalOnBean(DefaultMQProducer.class)
    @ConditionalOnMissingBean(MQProducerTemplate.class)
    public MQProducerTemplate mqProducerTemplate(){
        MQProducerTemplate mqProducerTemplate = new MQProducerTemplate();
        return mqProducerTemplate;
    }




}
