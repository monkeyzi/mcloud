package com.monkeyzi.mcloud.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 任务管理
 */
@Data
public class McloudQuartzJob extends Model<McloudQuartzJob> implements Serializable {
    private static final long serialVersionUID = 2049104316610649585L;

    @TableId
    private Integer id;
    /**
     * 任务名
     */
    private String jobName;
    /**
     * 任务组名
     */
    private String jobGroup;
    /**
     * 组内执行顺利，值越大执行优先级越高，最大值9，最小值1
     */
    private String jobOrder;
    /**
     * 1、java类;2、spring bean名称;3、rest调用;4、jar调用;
     */
    private String jobType;

    /**
     * job_type=3时，rest调用地址，仅支持rest get协议,需要增加String返回值，0成功，1失败;job_type=4时，jar路径;其它值为空
     */
    private String jobExecutePath;
    /**
     * job_type=1时，类完整路径;job_type=2时，spring bean名称;其它值为空
     */
    private String jobClassName;
    /**
     * 任务方法
     */
    private String jobMethodName;
    /**
     * 参数值
     */
    private String jobMethodParam;
    /**
     * cron执行表达式
     */
    private String jobCronExpression;
    /**
     * 错失执行策略（1错失周期立即执行 2错失周期执行一次 3下周期执行）
     */
    private String jobMisfirePolicy;

    /**
     * 状态（0、未发布;1、已发布;2、运行中;3、暂停;4、删除;）
     */
    private String jobStatus;
    /**
     * 状态（0正常 1异常）
     */
    private String jobExecuteStatus;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 首次执行时间
     */
    private LocalDateTime jobStartTime;
    /**
     * 上次执行时间
     */
    private LocalDateTime jobPreviousTime;
    /**
     * 下次执行时间
     */
    private LocalDateTime jobNextTime;
    /**
     * 备注信息
     */
    private String jobRemark;




}
