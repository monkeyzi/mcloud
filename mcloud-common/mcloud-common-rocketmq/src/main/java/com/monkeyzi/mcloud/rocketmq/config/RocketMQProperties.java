package com.monkeyzi.mcloud.rocketmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: 高yg
 * @date: 2019/3/18 21:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ConfigurationProperties(prefix = "spring.rocketmq")
public class RocketMQProperties {
    /**
     * nameServer
     * 地址：端口号; 多个分号分割 127.0.0.1:9876;192.168.10.1:9876
     */
    private String nameServerAddress;
    /**
     * producer
     */
    private Producer producer=new Producer();

    /**
     * 跟踪消息使用者的切换：将消息使用者信息发送到主题：rmq_comm_TRACE_DATA
     */
    private Boolean traceEnabled = Boolean.TRUE;

    @Data
    public static   class Producer{
        /**
         * 生产组
         */
        private String producerGroup;
        /**
         * 发送消息超时时间，单位毫秒，默认值3000
         */
        private int sendMsgTimeout = 3000;

        /** 是否vip通道，默认值false */
        private boolean vipChannelEnabled = false;

        /**
         * 压缩消息体的阀值，默认1024 * 4，4k，即默认大于4k的消息体将开启压缩
         */
        private int compressMsgBodyOverHowmuch = 1024 * 4;

        /**
         * 在同步模式下，声明发送失败之前内部执行的最大重试次数
         * 这可能会导致消息重复，应用程序开发人员需要解决此问题
         */
        private int retryTimesWhenSendFailed = 2;

        /**
         * 在异步模式下，声明发送失败之前内部执行的最大重试次数
         * 这可能会导致消息重复，应用程序开发人员需要解决此问题
         */
        private int retryTimesWhenSendAsyncFailed = 2;

        /**
         * 内部发送失败时是否重试另一个broker
         */
        private boolean retryAnotherBrokerWhenNotStoreOk = false;

        /**
         * 消息体最大值，单位byte，默认4Mb
         */
        private int maxMessageSize = 1024 * 1024 * 4;


    }


}
