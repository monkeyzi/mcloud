package com.monkeyzi.mcloud.quartz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.util.Assert;

/**
 * @author: 高yg
 * @date: 2019/4/14 20:49
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class AutowireCapableBeanJobFactory extends SpringBeanJobFactory {

    private final AutowireCapableBeanFactory beanFactory;

    AutowireCapableBeanJobFactory(AutowireCapableBeanFactory beanFactory) {
        Assert.notNull(beanFactory, "Bean factory must not be null");
        this.beanFactory = beanFactory;
    }
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
         Object objectInstance=super.createJobInstance(bundle);
         this.beanFactory.autowireBean(objectInstance);
         this.beanFactory.initializeBean(objectInstance, (String) null);
         return objectInstance;
    }
}
