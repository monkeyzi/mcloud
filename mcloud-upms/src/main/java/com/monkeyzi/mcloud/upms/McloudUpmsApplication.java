package com.monkeyzi.mcloud.upms;

import com.monkeyzi.mcloud.rocketmq.annotation.EnableMonkeyziRocketMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMonkeyziRocketMQ
public class McloudUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(McloudUpmsApplication.class);
    }
}
