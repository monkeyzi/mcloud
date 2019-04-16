package com.monkeyzi.mcloud.quartz.invoke;

import com.monkeyzi.mcloud.common.result.R;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.exception.McloudJobException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
/**
 * @author 高艳国
 * @date 2019/4/16 11:30
 * @description  定时任务 rest反射实现
 **/
@Slf4j
@Component(value = "mcloudRestJobInvok")
@AllArgsConstructor
public class McloudRestJobInvok implements IMcloudTaskInvok {

    private RestTemplate restTemplate;

    @Override
    public void invokeMethod(McloudQuartzJob quartzJob) throws McloudJobException {
        R r = restTemplate.getForObject(quartzJob.getJobExecutePath(), R.class);
        if (!r.isSuccess()){
            log.error("定时任务restTaskInvok异常,执行任务：{}", quartzJob.getJobExecutePath());
            throw new McloudJobException("定时任务restTaskInvok业务执行失败,任务：" + quartzJob.getJobExecutePath());
        }
    }
}
