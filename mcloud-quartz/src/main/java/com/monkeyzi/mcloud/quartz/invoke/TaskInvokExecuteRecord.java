package com.monkeyzi.mcloud.quartz.invoke;

import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mcloud.common.utils.IdGenUtils;
import com.monkeyzi.mcloud.common.utils.SpringContextHolder;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum;
import com.monkeyzi.mcloud.quartz.enums.McloudJobTypeEnum;
import com.monkeyzi.mcloud.quartz.event.McloudJobLogEvent;
import com.monkeyzi.mcloud.quartz.exception.McloudJobException;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class TaskInvokExecuteRecord {


    private final ApplicationEventPublisher publisher;
    @Autowired
    private McloudQuartzJobService mcloudQuartzJobService;


    @SneakyThrows
    public void invokMethod(McloudQuartzJob quartzJob, Trigger trigger) {
         IMcloudTaskInvok iMcloudTaskInvok;
         long startTime=System.currentTimeMillis();
         long endTime;

         //更新任务
         McloudQuartzJob mcloudQuartzJob=new McloudQuartzJob();
         mcloudQuartzJob.setId(quartzJob.getId());
         //任务执行日志
         McloudQuartzJobLog quartzJobLog=new McloudQuartzJobLog();
         quartzJobLog.setId(IdGenUtils.nextId());
         quartzJobLog.setJobClassName(quartzJob.getJobClassName());
         quartzJobLog.setJobCronExpression(quartzJob.getJobCronExpression());
         quartzJobLog.setJobExecutePath(quartzJob.getJobExecutePath());
         quartzJobLog.setJobGroup(quartzJob.getJobGroup());
         quartzJobLog.setJobName(quartzJob.getJobName());
         quartzJobLog.setJobOrder(quartzJob.getJobOrder());
         quartzJobLog.setJobType(quartzJob.getJobType());
         quartzJobLog.setJobMethodParam(quartzJob.getJobMethodParam());
         quartzJobLog.setJobMethodName(quartzJob.getJobMethodName());
         quartzJobLog.setJobId(quartzJob.getId());
         try {
             if (StrUtil.isEmpty(quartzJob.getJobType())){
                 log.info("定时任务类型无对应反射方式，反射类型为空");
                 throw new McloudJobException("定时任务类型无对应反射方式，反射类型为空");
             }else if (StrUtil.isNotEmpty(quartzJob.getJobType())&&
                     McloudJobTypeEnum.JAVA.getType().equals(quartzJob.getJobType())){
                 iMcloudTaskInvok=SpringContextHolder.getBean("mcloudJavaClassJobInvok");
                 iMcloudTaskInvok.invokeMethod(quartzJob);
             }else if (StrUtil.isNotEmpty(quartzJob.getJobType())&&
                     McloudJobTypeEnum.SPRING_BEAN.getType().equals(quartzJob.getJobType())){
                 iMcloudTaskInvok=SpringContextHolder.getBean("mcloudSpringBeanJobInvok");
                 iMcloudTaskInvok.invokeMethod(quartzJob);
             }else if (StrUtil.isNotEmpty(quartzJob.getJobType())&&McloudJobTypeEnum.REST.getType().equals(quartzJob.getJobType())){
                 iMcloudTaskInvok=SpringContextHolder.getBean("mcloudRestJobInvok");
                 iMcloudTaskInvok.invokeMethod(quartzJob);
             }else if (StrUtil.isNotEmpty(quartzJob.getJobType())&&McloudJobTypeEnum.JAR.getType().equals(quartzJob.getJobType())){
                 iMcloudTaskInvok=SpringContextHolder.getBean("mcloudJarTaskInvok");
                 iMcloudTaskInvok.invokeMethod(quartzJob);
             }else {
                 log.info("定时任务类型无对应反射方式，反射类型={}",quartzJob.getJobType());
                 throw new McloudJobException("定时任务类型无对应反射方式");
             }
             //获取执行结束时间
             endTime = System.currentTimeMillis();
             quartzJobLog.setJobMessage(McloudJobStatusEnum.JOB_LOG_STATUS_SUCCESS.getDescription());
             quartzJobLog.setJobLogStatus(McloudJobStatusEnum.JOB_LOG_STATUS_SUCCESS.getType());
             quartzJobLog.setJobExecuteTime(String.valueOf(endTime - startTime));
             //任务表信息更新
             mcloudQuartzJob.setJobExecuteStatus(McloudJobStatusEnum.JOB_LOG_STATUS_SUCCESS.getType());
             mcloudQuartzJob.setJobStartTime(trigger.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
             mcloudQuartzJob.setJobPreviousTime(trigger.getPreviousFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
             mcloudQuartzJob.setJobNextTime(trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
         }catch (Throwable e){
             //获取执行结束时间
             endTime = System.currentTimeMillis();
             log.error("定时任务执行失败，任务名称：{}；任务组名：{}，cron执行表达式：{}，执行时间：{}", quartzJob.getJobName(), quartzJob.getJobGroup(),
                     quartzJob.getJobCronExpression(), new Date());
             quartzJobLog.setJobMessage(McloudJobStatusEnum.JOB_LOG_STATUS_FAIL.getDescription());
             quartzJobLog.setJobLogStatus(McloudJobStatusEnum.JOB_LOG_STATUS_FAIL.getType());
             quartzJobLog.setJobExecuteTime(String.valueOf(endTime - startTime));
             quartzJobLog.setJobExceptionInfo(StrUtil.sub(e.getMessage(), 0, 2000));
             //任务表信息更新
             mcloudQuartzJob.setJobExecuteStatus(McloudJobStatusEnum.JOB_LOG_STATUS_FAIL.getType());
             mcloudQuartzJob.setJobStartTime(trigger.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
             mcloudQuartzJob.setJobPreviousTime(trigger.getPreviousFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
             mcloudQuartzJob.setJobNextTime(trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
         }finally {
             publisher.publishEvent(new McloudJobLogEvent(quartzJobLog));
             mcloudQuartzJobService.updateById(mcloudQuartzJob);
         }

    }

}
