package com.monkeyzi.mcloud.rocketmq.exeption;

/**
 * @author: 高yg
 * @date: 2019/3/18 21:09
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:MQ异常
 */
public class MQException extends RuntimeException {

    public MQException() {
        super();
    }

    public MQException(String message) {
        super(message);
    }

    public MQException(String message, Throwable cause) {
        super(message, cause);
    }

    public MQException(Throwable cause) {
        super(cause);
    }
}
