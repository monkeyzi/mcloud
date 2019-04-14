package com.monkeyzi.mcloud.quartz.config;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.event.McloudJobEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author: 高yg
 * @date: 2019/4/14 23:18
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@Aspect
public class McloudQuartzInvokeFactory {

    @Autowired
    private  ApplicationEventPublisher publisher;

    void init(McloudQuartzJob mcloudQuartzJob, Trigger trigger) {
        publisher.publishEvent(new McloudJobEvent(mcloudQuartzJob, trigger));
    }

}
