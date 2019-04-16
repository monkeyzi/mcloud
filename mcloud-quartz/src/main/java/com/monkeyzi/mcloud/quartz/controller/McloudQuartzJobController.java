package com.monkeyzi.mcloud.quartz.controller;

import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/job")
public class McloudQuartzJobController {

    @Autowired
    private McloudQuartzJobService mcloudQuartzJobService;

    /**
     * 分页查询定时任务信息
     * @return
     */
    @GetMapping(value = "/page")
    public R  getJobByPage(){
        return R.ok("ok",null);
    }

}
