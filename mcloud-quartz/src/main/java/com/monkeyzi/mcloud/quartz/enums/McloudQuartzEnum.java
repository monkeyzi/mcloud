package com.monkeyzi.mcloud.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 高yg
 * @date: 2019/4/14 23:28
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
@AllArgsConstructor
public enum  McloudQuartzEnum {

    /**
     * 任务详细信息的key
     */
    SCHEDULE_JOB_KEY("scheduleJob", "获取任务详细信息的key");

    /**
     * 类型
     */
    private  String type;
    /**
     * 描述
     */
    private  String description;
}
