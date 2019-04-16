package com.monkeyzi.mcloud.quartz.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * @author 高艳国
 * @date 2019/4/16 16:08
 * @description  配置RestTemplate  在项目中可以使用@Autowired注入
 **/
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
