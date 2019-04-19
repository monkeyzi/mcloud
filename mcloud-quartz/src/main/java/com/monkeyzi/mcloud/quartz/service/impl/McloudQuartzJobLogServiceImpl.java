package com.monkeyzi.mcloud.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mcloud.common.utils.PublicUtil;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import com.monkeyzi.mcloud.quartz.mapper.McloudQuartzJobLogMapper;
import com.monkeyzi.mcloud.quartz.mapper.McloudQuartzJobMapper;
import com.monkeyzi.mcloud.quartz.protocal.req.JobLogPageReq;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobLogService;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class McloudQuartzJobLogServiceImpl extends ServiceImpl<McloudQuartzJobLogMapper,McloudQuartzJobLog> implements McloudQuartzJobLogService {
    @Autowired
    private McloudQuartzJobLogMapper mcloudQuartzJobLogMapper;



    @Override
    public PageInfo getJobLogByPage(JobLogPageReq logPageReq) {
        PageHelper.startPage(logPageReq.getPageNum(),logPageReq.getPageSize());
        if (PublicUtil.isNotEmpty(logPageReq.getEndTime())){
            logPageReq.setEndTime(logPageReq.getEndTime()+" 23:59:59");
        }
        List<McloudQuartzJobLog> list=mcloudQuartzJobLogMapper.selectJobByCondition(logPageReq);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
}
