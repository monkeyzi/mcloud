package com.monkeyzi.mcloud.quartz.event;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@AllArgsConstructor
public class McloudJobLogListener {

    private McloudQuartzJobLogService mcloudQuartzJobLogService;

    @Async
    @Order
    @EventListener(McloudJobLogEvent.class)
    public void comSysJob(McloudJobLogEvent event) {
        McloudQuartzJobLog quartzJobLog = event.getMcloudQuartzJobLog();
        mcloudQuartzJobLogService.save(quartzJobLog);
        log.info("执行保存定时任务执行日志");
    }
}
