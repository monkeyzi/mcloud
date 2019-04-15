package com.monkeyzi.mcloud.quartz.util;

import com.monkeyzi.mcloud.quartz.config.McloudQuartzFactory;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.JOB_STATUS_NOT_RUNNING;
import static com.monkeyzi.mcloud.quartz.enums.McloudQuartzEnum.*;

/**
 * @author: 高yg
 * @date: 2019/4/14 22:21
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: mcloud任务工具类
 */
@Slf4j
@Component
public class McloudTaskUtils {

    /**
     * 根据 任务名和任务分组获取唯一key
     * @param quartzJob
     * @return
     */
    public JobKey getJobKey(McloudQuartzJob quartzJob){
        return JobKey.jobKey(quartzJob.getJobName(),quartzJob.getJobGroup());
    }

    /**
     * 获取定时任务触发器cron的唯一key
     * @param quartzJob
     * @return
     */
    public TriggerKey getTriggerKey(McloudQuartzJob quartzJob){
        return TriggerKey.triggerKey(quartzJob.getJobName(),quartzJob.getJobGroup());
    }

    /**
     * 新增或者更新job
     * @param quartzJob
     * @param scheduler
     */
    public  void addOrUpdateJob(McloudQuartzJob quartzJob, Scheduler scheduler){
        CronTrigger cronTrigger=null;
        try {
            // 获取jobkey
            JobKey jobKey=this.getJobKey(quartzJob);
            // 获取triggerKey
            TriggerKey triggerKey=this.getTriggerKey(quartzJob);
            // 获得触发器
            cronTrigger= (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果cronTrigger 存在说明之前运行过只是现在被禁用了， 不存在说明之前没有运行过
            if (cronTrigger==null){
                //新建一个任务，指定任务是串接进行的
                JobDetail jobDetail=JobBuilder.newJob(McloudQuartzFactory.class).withIdentity(jobKey).build();
                //添加任务信息
                jobDetail.getJobDataMap().put(SCHEDULE_JOB_KEY.getType(),quartzJob);
                // cron表达式进行转化
                CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(quartzJob.getJobCronExpression());
                cronScheduleBuilder=this.handleCronScheduleMisfirePolicy(quartzJob,cronScheduleBuilder);
                //创建触发器并将cron表达式塞入
                cronTrigger=TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
                //调度器中将触发器和job信息组合
                scheduler.scheduleJob(jobDetail,cronTrigger);
            }else {
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getJobCronExpression());
                cronScheduleBuilder = this.handleCronScheduleMisfirePolicy(quartzJob, cronScheduleBuilder);
                //按照新的规则进行
                cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
                //将任务信息更新到任务信息中
                cronTrigger.getJobDataMap().put(SCHEDULE_JOB_KEY.getType(), quartzJob);
                //重启任务
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
            // 如果状态为暂定，就暂定任务
            if (quartzJob.getJobStatus().equals(JOB_STATUS_NOT_RUNNING.getType())){
                this.pauseJob(quartzJob,scheduler);
            }
        }catch (SchedulerException  e){
            log.error("新增或者更新定时任务出现异常 e={}",e.getMessage());
        }
    }

    /**
     * 暂定执行job
     * @param quartzJob
     * @param scheduler
     */
    public void pauseJob(McloudQuartzJob  quartzJob, Scheduler scheduler) {
        try {
            if (scheduler != null) {
                scheduler.pauseJob(getJobKey(quartzJob));
            }
        } catch (SchedulerException e) {
            log.error("暂停任务失败，失败信息：{}", e.getMessage());
        }
    }

    /**
     * 恢复定时任务
     * @param quartzJob
     * @param scheduler
     */
    public void resumeJob(McloudQuartzJob quartzJob, Scheduler scheduler) {
        try {
            if (scheduler != null) {
                scheduler.resumeJob(getJobKey(quartzJob));
            }
        } catch (SchedulerException e) {
            log.error("恢复任务失败，失败信息：{}", e.getMessage());
        }
    }

    /**
     * 移除定时任务
     * @param quartzJob
     * @param scheduler
     */
    public void removeJob(McloudQuartzJob  quartzJob, Scheduler scheduler) {
        try {
            if (scheduler != null) {
                // 停止触发器
                scheduler.pauseTrigger(getTriggerKey(quartzJob));
                //移除触发器
                scheduler.unscheduleJob(getTriggerKey(quartzJob));
                //删除任务
                scheduler.deleteJob(getJobKey(quartzJob));
            }
        } catch (Exception e) {
            log.error("移除定时任务失败，失败信息：{}", e.getMessage());
        }
    }

    /**
     * 启动所有运行定时任务
     * @param scheduler
     */
    public void startJobs(Scheduler scheduler) {
        try {
            if (scheduler != null) {
                scheduler.resumeAll();
            }
        } catch (SchedulerException e) {
            log.error("启动所有运行定时任务失败，失败信息：{}", e.getMessage());
        }
    }

    /**
     * 停止所有运行定时任务
     * @param scheduler
     */
    public void pauseJobs(Scheduler scheduler) {
        try {
            if (scheduler != null) {
                scheduler.pauseAll();
            }
        } catch (Exception e) {
            log.error("暂停所有运行定时任务失败，失败信息：{}", e.getMessage());
        }
    }

    /**
     * 判断cron表达式是否正确
     *
     * @param cronExpression
     * @return
     */
    public boolean isValidCron(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }


    /**
     * 获取任务错过执行策略
     * @param quartzJob
     * @param cronScheduleBuilder
     * @return
     */
    private CronScheduleBuilder handleCronScheduleMisfirePolicy(McloudQuartzJob quartzJob,
                                                                CronScheduleBuilder cronScheduleBuilder){
          // 默认策略
          if (MISFIRE_DEFAULT.getType().equals(quartzJob.getJobMisfirePolicy())){
              return cronScheduleBuilder;
          //立即执行错失任务
          }else if(MISFIRE_IGNORE_MISFIRES.getType().equals(quartzJob.getJobMisfirePolicy())){
              return cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
          //马上再次触发,然后周期执行
          }else if(MISFIRE_FIRE_AND_PROCEED.getType().equals(quartzJob.getJobMisfirePolicy())){
              return cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
          //Quartz下一次执行时间到来时再执行，并不想马上执行
          }else if (MISFIRE_DO_NOTHING.getType().equals(quartzJob.getJobMisfirePolicy())){
              return cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
          }else {
              return cronScheduleBuilder;
          }
    }
}
