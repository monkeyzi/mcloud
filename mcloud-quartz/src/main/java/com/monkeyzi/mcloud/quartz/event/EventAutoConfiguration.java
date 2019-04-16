package com.monkeyzi.mcloud.quartz.event;

import com.monkeyzi.mcloud.quartz.config.McloudQuartzInvokeFactory;
import com.monkeyzi.mcloud.quartz.invoke.TaskInvokExecuteRecord;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

/**
 * @author 高艳国
 * @date 2019/4/16 17:06
 * @description  自动装配
 **/
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class EventAutoConfiguration  {

    @Resource
    private TaskInvokExecuteRecord taskInvokExecuteRecord;
    @Autowired
    private McloudQuartzJobLogService mcloudQuartzJobLogService;

    /**
     * 配置任务监听
     * @return
     */
    @Bean
    public McloudJobListener mcloudJobListener() {
        return new McloudJobListener(taskInvokExecuteRecord);
    }

    /**
     * 任务日志监听
     * @return
     */
    @Bean
    public McloudJobLogListener mcloudJobLogListener() {
        return new McloudJobLogListener(mcloudQuartzJobLogService);
    }

    /**
     * 定时任务反射工具类
     * @param publisher
     * @return
     */
    @Bean
    public TaskInvokExecuteRecord taskInvokUtil(ApplicationEventPublisher publisher) {
        return new TaskInvokExecuteRecord(publisher);
    }

    @Bean
    public McloudQuartzInvokeFactory mcloudQuartzInvokeFactory(ApplicationEventPublisher publisher) {
        return new McloudQuartzInvokeFactory(publisher);
    }

}
