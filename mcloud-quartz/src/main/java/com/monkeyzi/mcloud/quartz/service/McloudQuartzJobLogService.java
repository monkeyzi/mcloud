package com.monkeyzi.mcloud.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import com.monkeyzi.mcloud.quartz.protocal.req.JobLogPageReq;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:36
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface McloudQuartzJobLogService extends IService<McloudQuartzJobLog> {
    /**
     * 查询定时任务执行日志列表
     * @param logPageReq
     * @return
     */
    PageInfo getJobLogByPage(JobLogPageReq logPageReq);
}
