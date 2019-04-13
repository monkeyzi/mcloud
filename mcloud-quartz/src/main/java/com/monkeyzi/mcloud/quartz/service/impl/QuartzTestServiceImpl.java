package com.monkeyzi.mcloud.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkeyzi.mcloud.quartz.entity.QuartzTest;
import com.monkeyzi.mcloud.quartz.mapper.QuartzTestMapper;
import com.monkeyzi.mcloud.quartz.service.QuartzTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:36
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class QuartzTestServiceImpl extends ServiceImpl<QuartzTestMapper,QuartzTest> implements QuartzTestService {
}
