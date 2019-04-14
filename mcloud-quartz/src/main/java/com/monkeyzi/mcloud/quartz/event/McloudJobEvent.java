package com.monkeyzi.mcloud.quartz.event;

import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import lombok.Getter;
import org.quartz.Trigger;

/**
 * @author: 高yg
 * @date: 2019/4/14 23:23
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
public class McloudJobEvent {

    private  McloudQuartzJob mcloudQuartzJob;

    private  Trigger trigger;

    public McloudJobEvent(McloudQuartzJob mcloudQuartzJob,Trigger trigger){
           this.mcloudQuartzJob=mcloudQuartzJob;
           this.trigger=trigger;
    }
}
