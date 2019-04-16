package com.monkeyzi.mcloud.quartz.invoke;

import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mcloud.quartz.entity.McloudQuartzJob;
import com.monkeyzi.mcloud.quartz.exception.McloudJobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 高艳国
 * @date 2019/4/16 17:14
 * @description  定时任务反射工具类
 **/
@Slf4j
@Component("mcloudJarTaskInvok")
public class McloudJarTaskInvok implements IMcloudTaskInvok {


    @Override
    public void invokeMethod(McloudQuartzJob quartzJob) throws McloudJobException {
        ProcessBuilder processBuilder=new ProcessBuilder();
        File jar=new File(quartzJob.getJobExecutePath());
        processBuilder.directory(jar.getParentFile());
        List<String> commands = new ArrayList<>();
        commands.add("java");
        commands.add("-jar");
        commands.add(quartzJob.getJobExecutePath());
        if (StrUtil.isNotEmpty(quartzJob.getJobMethodParam())) {
            commands.add(quartzJob.getJobMethodParam());
        }
        processBuilder.command(commands);
        try {
            Process process=processBuilder.start();
        }catch (IOException e){
            log.error("定时任务jar反射执行异常,执行任务：{}", quartzJob.getJobExecutePath());
            throw new McloudJobException("定时任务jar反射执行异常,执行任务：" + quartzJob.getJobExecutePath());
        }
    }
}
