package com.monkeyzi.mcloud.quartz.event;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author 高艳国
 * @date 2019/4/16 16:50
 * @description  任务日志事件
 **/
@Getter
@AllArgsConstructor
public class McloudJobLogEvent {

    private final McloudQuartzJobLog mcloudQuartzJobLog;
}
