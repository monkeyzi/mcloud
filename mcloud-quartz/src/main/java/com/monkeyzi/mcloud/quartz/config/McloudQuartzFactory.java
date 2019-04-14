package com.monkeyzi.mcloud.quartz.config;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.enums.McloudQuartzEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: 高yg
 * @date: 2019/4/14 23:17
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@DisallowConcurrentExecution
public class McloudQuartzFactory implements Job {

    @Autowired
    private McloudQuartzInvokeFactory mcloudQuartzInvokeFactory;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始了");
        McloudQuartzJob mcloudQuartzJob = (McloudQuartzJob) jobExecutionContext.getMergedJobDataMap()
                .get(McloudQuartzEnum.SCHEDULE_JOB_KEY.getType());
        mcloudQuartzInvokeFactory.init(mcloudQuartzJob, jobExecutionContext.getTrigger());
    }
}
