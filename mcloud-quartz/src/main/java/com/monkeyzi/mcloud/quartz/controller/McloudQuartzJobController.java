package com.monkeyzi.mcloud.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mcloud.common.core.base.BaseController;
import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.protocal.req.JobPageReq;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "/job")
public class McloudQuartzJobController  extends BaseController {

    @Autowired
    private McloudQuartzJobService mcloudQuartzJobService;

    /**
     * 分页查询定时任务信息
     * @return
     */
    @GetMapping(value = "/page")
    public R  getJobByPage(@Valid @RequestBody JobPageReq jobPageReq){
        log.info("查询job列表的参数为 param={}",jobPageReq);
        PageInfo pageInfo=mcloudQuartzJobService.getJobByPage(jobPageReq);
        return R.ok("ok",pageInfo);
    }

    /**
     * 查询任务详细信息
     * @param id
     * @return
     */
    @GetMapping(value = "/jobInfo/{id}")
    public R getJobById(@PathVariable Long id){
        log.info("通过任务Id查询任务详情的参数为 param={}",id);
        McloudQuartzJob quartzJob=mcloudQuartzJobService.getById(id);
        return R.ok("ok",quartzJob);
    }

    @PostMapping(value = "/saveJob")
    public R saveJob(@Valid @RequestBody McloudQuartzJob quartzJob){
        log.info("新增任务的参数为 param={}",quartzJob);
        return mcloudQuartzJobService.saveJob(quartzJob);
    }


    @PostMapping(value = "updateJob")
    public R updateJobById(@Valid @RequestBody McloudQuartzJob quartzJob){
        log.info("修改定时任务的参数为 param={}",quartzJob);
        return R.ok();
    }



    @GetMapping("/isValidJobName")
    public R isValidJobName(@RequestParam String jobName,
                            @RequestParam String jobGroup) {
        log.info("通过任务名和任务分组查询该任务是否是唯一的参数 jobName={},jobGroup={}",jobName,jobGroup);
        int count=mcloudQuartzJobService.isValidJobName(jobName,jobGroup);
        if (count>0){
            return R.okMsg("success");
        }else {
            return R.errorMsg("任务名已存在");
        }
    }




}
