package com.monkeyzi.mcloud.quartz.protocal.req;

import com.monkeyzi.mcloud.quartz.protocal.BasePageReq;
import lombok.Data;

import java.io.Serializable;

@Data
public class JobPageReq extends BasePageReq implements Serializable {

    private static final long serialVersionUID = -5912593646511767158L;

    private String  jobName;

    private String  jobGroup;

    private String  jobStatus;

    private String  jobExeStatus;


}
