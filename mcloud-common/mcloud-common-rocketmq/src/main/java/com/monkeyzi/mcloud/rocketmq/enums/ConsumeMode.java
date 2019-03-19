package com.monkeyzi.mcloud.rocketmq.enums;

import lombok.Getter;

public enum ConsumeMode {

    /**
     * CONCURRENTLY
     * 使用线程池并发消费
     */
    CONCURRENTLY("CONCURRENTLY"),
    /**
     * ORDERLY
     * 单线程消费
     */
    ORDERLY("ORDERLY");

    @Getter
    private String mode;

    ConsumeMode(String mode) {
        this.mode = mode;
    }
}
