package com.monkeyzi.mcloud.eureka;

import com.monkeyzi.mcloud.rocketmq.annotation.EnableMonkeyziRocketMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMonkeyziRocketMQ
public class UpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class);
    }
}
