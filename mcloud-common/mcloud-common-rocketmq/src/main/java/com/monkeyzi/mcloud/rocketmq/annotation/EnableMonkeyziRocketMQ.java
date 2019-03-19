package com.monkeyzi.mcloud.rocketmq.annotation;

import java.lang.annotation.*;

/**
 * @author: 高yg
 * @date: 2019/3/18 21:05
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 自定义recketmq注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableMonkeyziRocketMQ {
}
