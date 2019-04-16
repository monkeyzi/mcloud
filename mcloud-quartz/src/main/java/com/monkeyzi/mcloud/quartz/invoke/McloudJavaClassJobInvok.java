package com.monkeyzi.mcloud.quartz.invoke;

import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.exception.McloudJobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.monkeyzi.mcloud.quartz.enums.McloudJobStatusEnum.JOB_LOG_STATUS_FAIL;
/**
 * @author 高艳国
 * @date 2019/4/16 11:21
 * @description 定时任务java class反射实现  javaClass 类型的任务需要增加 String类型的返回值, 0代表成功，1代表失败
 **/
@Slf4j
@Component(value = "mcloudJavaClassJobInvok")
public class McloudJavaClassJobInvok implements IMcloudTaskInvok{

    @Override
    public void invokeMethod(McloudQuartzJob quartzJob) throws McloudJobException {
      Object obj;
      Class clazz;
      Method method;
      Object returnValue;
      try {
          if (StrUtil.isNotBlank(quartzJob.getJobMethodParam())){
              clazz=Class.forName(quartzJob.getJobClassName());
              obj=clazz.newInstance();
              method=clazz.getDeclaredMethod(quartzJob.getJobMethodName(),String.class);
              returnValue=method.invoke(obj,quartzJob.getJobMethodParam());
          }else {
              clazz = Class.forName(quartzJob.getJobClassName());
              obj = clazz.newInstance();
              method = clazz.getDeclaredMethod(quartzJob.getJobMethodName());
              returnValue = method.invoke(obj);
          }
          if (StrUtil.isEmpty(returnValue.toString()) || JOB_LOG_STATUS_FAIL.
                  getType().equals(returnValue.toString())) {
              log.error("定时任务mcloudJavaClassJobInvok异常,执行任务：{}", quartzJob.getJobClassName());
              throw new McloudJobException("定时任务javaClassTaskInvok业务执行失败,任务：" + quartzJob.getJobClassName());
          }
      }catch (ClassNotFoundException e){
          log.error("定时任务java反射类没有找到,执行任务：{}", quartzJob.getJobClassName());
          throw new McloudJobException("定时任务java反射类没有找到,执行任务：" + quartzJob.getJobClassName());
      } catch (IllegalAccessException e) {
          log.error("定时任务java反射类异常,执行任务：{}", quartzJob.getJobClassName());
          throw new McloudJobException("定时任务java反射类异常,执行任务：" + quartzJob.getJobClassName());
      } catch (InstantiationException e) {
          log.error("定时任务java反射类异常,执行任务：{}", quartzJob.getJobClassName());
          throw new McloudJobException("定时任务java反射类异常,执行任务：" + quartzJob.getJobClassName());
      } catch (NoSuchMethodException e) {
          log.error("定时任务java反射执行方法名异常,执行任务：{}", quartzJob.getJobClassName());
          throw new McloudJobException("定时任务java反射执行方法名异常,执行任务：" + quartzJob.getJobClassName());
      } catch (InvocationTargetException e) {
          log.error("定时任务java反射执行异常,执行任务：{}", quartzJob.getJobClassName());
          throw new McloudJobException("定时任务java反射执行异常,执行任务：" + quartzJob.getJobClassName());
      }
    }
}
