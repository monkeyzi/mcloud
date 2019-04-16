package com.monkeyzi.mcloud.quartz.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
