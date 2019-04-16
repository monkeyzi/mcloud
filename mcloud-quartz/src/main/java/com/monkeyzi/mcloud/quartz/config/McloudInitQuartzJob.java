package com.monkeyzi.mcloud.quartz.config;

import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import com.monkeyzi.mcloud.quartz.util.McloudTaskUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.JOB_STATUS_NOT_RUNNING;
import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.JOB_STATUS_RELEASE;
import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.JOB_STATUS_RUNNING;

/**
 * @author: 高yg
 * @date: 2019/4/15 23:32
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 初始化加载定时任务
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class McloudInitQuartzJob {

    private final McloudQuartzJobService mcloudQuartzJobService;
    private final McloudTaskUtils mcloudTaskUtils;
    private final Scheduler scheduler;

    @Bean
    public String customize(){
        log.info("初始化加载定时任务--------------");
        mcloudQuartzJobService.list().forEach(a->{
              if (JOB_STATUS_RELEASE.getType().equals(a.getJobStatus())){
                  mcloudTaskUtils.removeJob(a,scheduler);
              }else if(JOB_STATUS_RUNNING.getType().equals(a.getJobStatus())){
                  mcloudTaskUtils.resumeJob(a,scheduler);
              }else if(JOB_STATUS_NOT_RUNNING.getType().equals(a.getJobStatus())){
                  mcloudTaskUtils.pauseJob(a,scheduler);
              }else {
                  mcloudTaskUtils.resumeJob(a,scheduler);
              }
        });
        return "ok";
    }
}
