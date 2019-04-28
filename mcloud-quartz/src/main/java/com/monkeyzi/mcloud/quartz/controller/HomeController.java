package com.monkeyzi.mcloud.quartz.controller;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJobLog;
import com.monkeyzi.mcloud.quartz.entity.User;
import com.monkeyzi.mcloud.quartz.service.McloudQuartzJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/4/13 20:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
@Validated
public class HomeController {

    @Autowired
    private McloudQuartzJobLogService mcloudQuartzJobLogService;

    @GetMapping(value = "/test")
    public List<McloudQuartzJobLog> test(
            @NotBlank(message = "姓名不能为空")
            @RequestParam String name
         ){
        return mcloudQuartzJobLogService.list();
    }


    @GetMapping(value = "/test1")
    public List<McloudQuartzJobLog> test1(
            @NotBlank(message = "姓名不能为空")
            String name
    ){
        return mcloudQuartzJobLogService.list();
    }

    @GetMapping(value = "/test2/{name}")
    public List<McloudQuartzJobLog> test2(
            @NotBlank(message = "姓名不能为空")
                    @PathVariable  String name
    ){
        return mcloudQuartzJobLogService.list();
    }

    @PostMapping(value = "/test3")
    public List<McloudQuartzJobLog> test3(
            @Valid
            @RequestBody User user
            ){
        return mcloudQuartzJobLogService.list();
    }

    @PostMapping(value = "/test4")
    public List<McloudQuartzJobLog> test4(
            @Valid
            User user
    ){
        return mcloudQuartzJobLogService.list();
    }

    @PutMapping(value = "/test5")
    public List<McloudQuartzJobLog> test5(
            @Valid
                    User user
    ){
        return mcloudQuartzJobLogService.list();
    }

    @PutMapping(value = "/test6")
    public List<McloudQuartzJobLog> test5(

    ){
        System.out.println(1/0);
       return  null;
    }
}
