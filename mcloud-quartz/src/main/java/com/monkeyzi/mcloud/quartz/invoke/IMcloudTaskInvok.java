package com.monkeyzi.mcloud.quartz.invoke;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.exception.McloudJobException;

/**
 * @author: 高yg
 * @date: 2019/4/15 23:27
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:定时任务反射实现接口类
 */
public interface IMcloudTaskInvok {
    /**
     * 执行job反射方法
     */
    void invokeMethod(McloudQuartzJob quartzJob) throws McloudJobException;
}
