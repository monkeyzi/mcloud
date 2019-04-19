package com.monkeyzi.mcloud.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.common.utils.PublicUtil;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum;
import com.monkeyzi.mcloud.quartz.mapper.McloudQuartzJobMapper;
import com.monkeyzi.mcloud.quartz.protocal.req.JobPageReq;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import com.monkeyzi.mcloud.quartz.util.McloudTaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.*;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:36
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class McloudQuartzJobServiceImpl extends ServiceImpl<McloudQuartzJobMapper,McloudQuartzJob> implements McloudQuartzJobService {

    @Autowired
    private McloudQuartzJobMapper mcloudQuartzJobMapper;

    @Autowired
    private McloudTaskUtils mcloudTaskUtils;

    @Autowired
    private Scheduler scheduler;


    @Override
    public PageInfo getJobByPage(JobPageReq jobPageReq) {
        PageHelper.startPage(jobPageReq.getPageNum(),jobPageReq.getPageSize());
        if (PublicUtil.isNotEmpty(jobPageReq.getEndTime())){
            jobPageReq.setEndTime(jobPageReq.getEndTime()+" 23:59:59");
        }
        List<McloudQuartzJob>  list=mcloudQuartzJobMapper.selectJobByCondition(jobPageReq);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int isValidJobName(String jobName, String jobGroup) {
        return super.count(Wrappers.query(
                McloudQuartzJob
                        .builder()
                        .jobName(jobName)
                        .jobGroup(jobGroup)
                        .build()));
    }

    @Override
    public R saveJob(McloudQuartzJob quartzJob) {
        //校验jobName和jobGroup 组合是否为一
        int count =isValidJobName(quartzJob.getJobName(),quartzJob.getJobGroup());
        if (count>0){
            return R.errorMsg("任务名和任务组组合已存在");
        }
        quartzJob.setJobStatus(McloudJobStatusEnum.JOB_STATUS_RELEASE.getType());
        quartzJob.setCreateBy("");
        quartzJob.setUpdateBy("");
        quartzJob.setCreateTime(LocalDateTime.now());
        quartzJob.setUpdateTime(LocalDateTime.now());
        boolean flag=super.save(quartzJob);
        if (!flag){
            return R.errorMsg("任务新增失败");
        }
        return R.okMsg("新增成功");
    }

    @Override
    public R updateJobById(McloudQuartzJob quartzJob) {
        if (PublicUtil.isEmpty(quartzJob.getId())){
            return R.errorMsg("任务Id不能为空");
        }
        McloudQuartzJob  job=super.getById(quartzJob.getId());
        if (job==null){
            return R.errorMsg("任务不存在");
        }
        if (job.getJobStatus().equals(JOB_STATUS_RUNNING)){
            return R.errorMsg("运行中任务不能修改");
        }
        if (job.getJobStatus().equals(JOB_STATUS_NOT_RUNNING.getType())){
            this.mcloudTaskUtils.addOrUpdateJob(quartzJob,scheduler);
            super.updateById(quartzJob);
        }else if (job.getJobStatus().equals(JOB_STATUS_RELEASE.getType())){
            super.updateById(quartzJob);
        }
        return R.okMsg("任务修改成功");
    }

    @Override
    public R removeJobById(Long id) {
        McloudQuartzJob quartzJob=super.getById(id);
        if (quartzJob==null){
            return R.errorMsg("任务不存在");
        }
        if (quartzJob.getJobStatus().equals(JOB_STATUS_RUNNING.getType())){
            return R.errorMsg("任务正在运行,请先暂停再删除！");
        }
        if (quartzJob.getJobStatus().equals(JOB_STATUS_NOT_RUNNING.getType())){
            this.mcloudTaskUtils.removeJob(quartzJob,scheduler);
            super.removeById(id);
        }else if(quartzJob.getJobStatus().equals(JOB_STATUS_RELEASE)){
            super.removeById(id);
        }
        return R.okMsg("任务删除成功！");
    }

    @Override
    public R pauseAllJobs() {
        int count=super.count(new LambdaQueryWrapper<McloudQuartzJob>()
                .eq(McloudQuartzJob::getJobStatus,JOB_STATUS_RUNNING.getType()));
        if (count>0){
            this.mcloudTaskUtils.pauseJobs(scheduler);
            super.update(McloudQuartzJob.builder().jobStatus(JOB_STATUS_NOT_RUNNING.getType()).build(),
                   new UpdateWrapper<McloudQuartzJob>().lambda().eq(McloudQuartzJob::getJobStatus,JOB_STATUS_RUNNING.getType()));
            return R.okMsg("任务暂停成功");
        }else {
            return R.errorMsg("没有正在运行的任务");
        }

    }

    @Override
    public R startAllJobs() {
        int count=super.count();
        if (count<=0){
            return R.errorMsg("系统中没有配置任务");
        }
        super.update(McloudQuartzJob.builder().jobStatus(JOB_STATUS_RUNNING.getType()).build(),
                new UpdateWrapper<McloudQuartzJob>().lambda().eq(McloudQuartzJob::getJobStatus,JOB_STATUS_NOT_RUNNING.getType()));
        this.mcloudTaskUtils.startJobs(scheduler);
        return R.okMsg("任务启动成功");
    }

    @Override
    public R refreshAllJobs() {
        super.list().forEach(a->{
            if (JOB_STATUS_RELEASE.getType().equals(a.getJobStatus())||JOB_STATUS_DEL.getType().equals(a.getJobStatus())){
                mcloudTaskUtils.removeJob(a,scheduler);
            }else if(JOB_STATUS_RUNNING.getType().equals(a.getJobStatus())||JOB_STATUS_NOT_RUNNING.getType().equals(a.getJobStatus())){
                mcloudTaskUtils.addOrUpdateJob(a,scheduler);
            }else {
                mcloudTaskUtils.resumeJob(a,scheduler);
            }
        });
        return R.okMsg("操作成功");
    }

    @Override
    public R startJob(Long id) {
        McloudQuartzJob quartzJob=super.getById(id);
        if (quartzJob==null){
            return R.errorMsg("任务不存在");
        }
        if (quartzJob.getJobStatus().equals(JOB_STATUS_RUNNING.getType())){
            return R.errorMsg("任务已经是运行状态");
        }
        if (JOB_STATUS_RELEASE.getType().equals(quartzJob.getJobStatus())){
            this.mcloudTaskUtils.addOrUpdateJob(quartzJob,scheduler);
        }else {
            this.mcloudTaskUtils.resumeJob(quartzJob,scheduler);
        }
        super.updateById(McloudQuartzJob.builder().id(id).jobStatus(JOB_STATUS_RUNNING.getType()).build());
        return R.okMsg("任务启动成功！");
    }

    @Override
    public R pauseJob(Long id) {
        McloudQuartzJob quartzJob=super.getById(id);
        if (quartzJob==null){
            return R.errorMsg("任务不存在！");
        }
        //将任务的状态修改
        super.updateById(quartzJob.builder().id(id)
                .jobStatus(JOB_STATUS_NOT_RUNNING.getType()).build());
        this.mcloudTaskUtils.pauseJob(quartzJob, scheduler);
        return R.okMsg("任务暂停成功！");
    }
}
