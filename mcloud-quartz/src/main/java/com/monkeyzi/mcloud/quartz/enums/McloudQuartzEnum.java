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
    SCHEDULE_JOB_KEY("scheduleJob", "获取任务详细信息的key"),
    /**
     * 错失执行策略默认
     */
    MISFIRE_DEFAULT("0", "默认"),

    /**
     * 错失执行策略-立即执行错失任务
     */
    MISFIRE_IGNORE_MISFIRES("1", "立即执行错失任务"),

    /**
     * 错失执行策略-触发一次执行周期执行
     */
    MISFIRE_FIRE_AND_PROCEED("2", "触发一次执行周期执行"),

    /**
     * 错失执行策略-不触发执行周期执行
     */
    MISFIRE_DO_NOTHING("3", "不触发周期执行");

    /**
     * 类型
     */
    private  String type;
    /**
     * 描述
     */
    private  String description;
}
