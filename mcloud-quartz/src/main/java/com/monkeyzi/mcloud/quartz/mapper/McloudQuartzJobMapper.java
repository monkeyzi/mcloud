package com.monkeyzi.mcloud.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.protocal.req.JobPageReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:33
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface McloudQuartzJobMapper extends BaseMapper<McloudQuartzJob> {
    /**
     * 条件查询任务信息
     * @param jobPageReq
     * @return
     */
    List<McloudQuartzJob> selectJobByCondition(JobPageReq jobPageReq);
}
