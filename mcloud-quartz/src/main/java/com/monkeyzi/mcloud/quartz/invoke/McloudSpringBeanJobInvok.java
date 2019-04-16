package com.monkeyzi.mcloud.quartz.invoke;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mcloud.common.utils.SpringContextHolder;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.exception.McloudJobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.JOB_LOG_STATUS_FAIL;

/**
 * @author 高艳国
 * @date 2019/4/16 11:21
 * @description 定时任务springbean反射实现
 **/
@Slf4j
@Component(value = "mcloudSpringBeanJobInvok")
public class McloudSpringBeanJobInvok implements IMcloudTaskInvok {


    @Override
    public void invokeMethod(McloudQuartzJob quartzJob) throws McloudJobException {
        Object target;
        Method method;
        Object returnValue;
        target=SpringContextHolder.getBean(quartzJob.getJobClassName());
        try {
            if (StrUtil.isNotEmpty(quartzJob.getJobMethodParam())){
                method=target.getClass().getDeclaredMethod(quartzJob.getJobMethodName(),String.class);
                ReflectionUtils.makeAccessible(method);
                returnValue=method.invoke(target,quartzJob.getJobMethodParam());
            }else {
                method=target.getClass().getDeclaredMethod(quartzJob.getJobMethodName());
                ReflectionUtils.makeAccessible(method);
                returnValue=method.invoke(target);
            }
            if (StrUtil.isEmpty(returnValue.toString())||returnValue.toString().equals(JOB_LOG_STATUS_FAIL.getType())){
                log.error("定时任务springBeanTaskInvok异常,执行任务：{}", quartzJob.getJobClassName());
                throw new McloudJobException("定时任务springBeanTaskInvok业务执行失败,任务：" + quartzJob.getJobClassName());
            }
        } catch (NoSuchMethodException e) {
            log.error("定时任务spring bean反射异常方法未找到,执行任务：{}", quartzJob.getJobClassName());
            throw new McloudJobException("定时任务spring bean反射异常方法未找到,执行任务：" + quartzJob.getJobClassName());
        } catch (IllegalAccessException e) {
            log.error("定时任务spring bean反射异常,执行任务：{}", quartzJob.getJobClassName());
            throw new McloudJobException("定时任务spring bean反射异常,执行任务：" + quartzJob.getJobClassName());
        } catch (InvocationTargetException e) {
            log.error("定时任务spring bean反射执行异常,执行任务：{}", quartzJob.getJobClassName());
            throw new McloudJobException("定时任务spring bean反射执行异常,执行任务：" + quartzJob.getJobClassName());
        }

    }
}
