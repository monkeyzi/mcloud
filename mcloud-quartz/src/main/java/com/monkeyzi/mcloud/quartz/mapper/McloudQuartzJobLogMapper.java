package com.monkeyzi.mcloud.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import com.monkeyzi.mcloud.quartz.protocal.req.JobLogPageReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:33
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface McloudQuartzJobLogMapper extends BaseMapper<McloudQuartzJobLog> {
    /**
     * 条件查询定时任务日志
     * @param logPageReq
     * @return
     */
    List<McloudQuartzJobLog> selectJobByCondition(JobLogPageReq logPageReq);
}
