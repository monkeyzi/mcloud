package com.monkeyzi.mcloud.quartz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 高yg
 * @date: 2019/4/13 20:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class HomeController {

    @GetMapping(value = "/test")
    public String test(){
        return "hello world";
    }
}
