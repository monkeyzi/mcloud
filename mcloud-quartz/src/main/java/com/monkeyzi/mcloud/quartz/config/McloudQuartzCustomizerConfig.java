package com.monkeyzi.mcloud.quartz.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author: 高yg
 * @date: 2019/4/15 23:41
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:实现该接口后并且将实现类使用Spring IOC托管，
 * 可以完成SchedulerFactoryBean的个性化设置，
 * 这里的设置完全可以对SchedulerFactoryBean做出全部的设置变更
 */
@Configuration
public class McloudQuartzCustomizerConfig implements SchedulerFactoryBeanCustomizer {
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
           schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
    }
}
