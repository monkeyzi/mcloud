package com.monkeyzi.mcloud.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: 高yg
 * @date: 2019/4/14 21:35
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class McloudQuartzJobLog implements Serializable {
    private static final long serialVersionUID = -728256926320891558L;
    /**
     * 任务日志ID
     */
    @TableId
    private Long id;
    /**
     * 任务id
     */
    private Long jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组名
     */
    private String jobGroup;
    /**
     * 组内执行顺利，值越大执行优先级越高，最大值9，最小值1
     */
    private String jobOrder;
    /**
     * 1、java类;2、spring bean名称;3、rest调用;4、jar调用;
     */
    private String jobType;
    /**
     * job_type=3时，rest调用地址，仅支持post协议;job_type=4时，jar路径;其它值为空
     */
    private String jobExecutePath;
    /**
     * job_type=1时，类完整路径;job_type=2时，spring bean名称;其它值为空
     */
    private String jobClassName;
    /**
     * 任务方法
     */
    private String jobMethodName;
    /**
     * 参数值
     */
    private String jobMethodParam;
    /**
     * cron执行表达式
     */
    private String jobCronExpression;
    /**
     * 日志信息
     */
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    private String jobLogStatus;
    /**
     * 执行时间
     */
    private String jobExecuteTime;
    /**
     * 异常信息
     */
    private String jobExceptionInfo;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
