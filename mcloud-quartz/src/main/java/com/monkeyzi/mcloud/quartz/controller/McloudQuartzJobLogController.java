package com.monkeyzi.mcloud.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mcloud.common.core.base.BaseController;
import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.quartz.protocal.req.JobLogPageReq;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 高艳国
 * @date 2019/4/19 10:27
 * @description quartz任务执行日志
 **/
@RestController
@Slf4j
@RequestMapping(value = "/job-log")
public class McloudQuartzJobLogController extends BaseController {

    @Autowired
    private McloudQuartzJobLogService mcloudQuartzJobLogService;
    /**
     * 分页查询定时任务执行日志信息
     * @return
     */
    @GetMapping(value = "/page")
    public R getJobByPage(@Valid JobLogPageReq logPageReq){
        log.info("查询job日志列表的参数为 param={}",logPageReq);
        PageInfo pageInfo=mcloudQuartzJobLogService.getJobLogByPage(logPageReq);
        return R.ok("ok",pageInfo);
    }

}
