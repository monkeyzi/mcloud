package com.monkeyzi.mcloud.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 高yg
 * @date: 2019/4/14 22:20
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
@AllArgsConstructor
public enum McloudJobStatusEnum {

    /**
     * JOB状态：1已发布
     */
    JOB_STATUS_RELEASE("1", "已发布"),
    /**
     * JOB状态：2运行中
     */
    JOB_STATUS_RUNNING("2", "运行中"),
    /**
     * JOB状态：3暂停
     */
    JOB_STATUS_NOT_RUNNING("3", "暂停"),
    /**
     * JOB状态：4删除
     */
    JOB_STATUS_DEL("4", "删除"),

    /**
     * JOB执行状态：0执行成功
     */
    JOB_LOG_STATUS_SUCCESS("0", "执行成功"),
    /**
     * JOB执行状态：1执行失败
     */
    JOB_LOG_STATUS_FAIL("1", "执行失败");
    /**
     * 类型
     */
    private  String type;
    /**
     * 描述
     */
    private  String description;

}
