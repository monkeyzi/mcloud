package com.monkeyzi.mcloud.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.protocal.req.JobPageReq;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:36
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface McloudQuartzJobService extends IService<McloudQuartzJob> {
    /**
     * 分页查询job信息
     * @param jobPageReq
     * @return
     */
    PageInfo getJobByPage(JobPageReq jobPageReq);

    /**
     * 查询是否是唯一的job
     * @param jobName
     * @param jobGroup
     * @return
     */
    int isValidJobName(String jobName, String jobGroup);

    /**
     * 新增任务  状态为 已发布状态
     * @param quartzJob
     */
    R saveJob(McloudQuartzJob quartzJob);
}
