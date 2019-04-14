package com.monkeyzi.mcloud.quartz.controller;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/4/13 20:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class HomeController {

    @Autowired
    private McloudQuartzJobService mcloudQuartzJobService;
    @GetMapping(value = "/test")
    public List<McloudQuartzJob> test(){
        return mcloudQuartzJobService.list();
    }
}
