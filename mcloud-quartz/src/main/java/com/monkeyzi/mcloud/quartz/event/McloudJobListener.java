package com.monkeyzi.mcloud.quartz.event;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.invoke.TaskInvokExecuteRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Trigger;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@AllArgsConstructor
public class McloudJobListener {

    private TaskInvokExecuteRecord taskInvokExecuteRecord;

    @Async
    @Order
    @EventListener(McloudJobEvent.class)
    public void comSysJob(McloudJobEvent event) {
        McloudQuartzJob quartzJob = event.getMcloudQuartzJob();
        Trigger trigger = event.getTrigger();
        taskInvokExecuteRecord.invokMethod(quartzJob, trigger);
    }
}
