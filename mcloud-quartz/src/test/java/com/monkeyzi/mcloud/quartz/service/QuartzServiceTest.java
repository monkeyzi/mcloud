package com.monkeyzi.mcloud.quartz.service;

import com.monkeyzi.mcloud.quartz.McloudQuartzTests;
import com.monkeyzi.mcloud.quartz.entity.QuartzTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:41
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class QuartzServiceTest extends McloudQuartzTests {

    @Autowired
    private QuartzTestService quartzTestService;

    @Test
    public void test1(){
        QuartzTest quartzTest=new QuartzTest();
        quartzTest.setId("00");
        quartzTest.setName(1);
        final boolean save = quartzTestService.save(quartzTest);
        System.out.println("新增数据"+save);
    }


    @Test
    public void test2(){
        QuartzTest quartzTest=new QuartzTest();
        quartzTest.setId("00");
        final boolean save =quartzTest.deleteById();
        System.out.println("新增数据"+save);
    }
}
