package com.monkeyzi.mcloud.rocketmq.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class RocketMQHeader implements Serializable {

    private String keys = "";


    /** 默认值true */
    private boolean waitStoreMsgOK = true;
}
