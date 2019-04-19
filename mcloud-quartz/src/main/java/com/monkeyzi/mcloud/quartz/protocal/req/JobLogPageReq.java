package com.monkeyzi.mcloud.quartz.protocal.req;

import com.monkeyzi.mcloud.quartz.protocal.BasePageReq;
import lombok.Data;

import java.io.Serializable;

/**
 * quartz定时任务执行任务日志查询参数
 */
@Data
public class JobLogPageReq extends BasePageReq implements Serializable {

    private static final long serialVersionUID = -5912593646511767158L;

    private String  jobName;

    private String  jobGroup;

    private String  jobStatus;

    private String  startTime;

    private String  endTime;

    private Long    jobId;
}
