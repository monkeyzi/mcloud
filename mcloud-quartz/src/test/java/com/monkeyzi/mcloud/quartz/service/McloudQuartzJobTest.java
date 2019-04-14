package com.monkeyzi.mcloud.quartz.service;

import com.monkeyzi.mcloud.quartz.McloudQuartzTests;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/4/14 21:44
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class McloudQuartzJobTest extends McloudQuartzTests {

    @Autowired
    private McloudQuartzJobService mcloudQuartzJobService;

    @Test
    public void test1(){
        List<McloudQuartzJob> list=mcloudQuartzJobService.list();
        System.out.println("查询出结果为 list:"+list.size());
    }
}
