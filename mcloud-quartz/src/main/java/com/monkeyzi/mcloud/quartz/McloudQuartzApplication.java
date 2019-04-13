package com.monkeyzi.mcloud.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 高yg
 * @date: 2019/4/13 20:20
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:定时任务quartz
 */
@MapperScan("com.monkeyzi.mcloud.quartz.mapper")
@SpringBootApplication
public class McloudQuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(McloudQuartzApplication.class);
    }
}
