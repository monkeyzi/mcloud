package com.monkeyzi.mcloud.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2019/4/13 21:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@TableName(value = "quartz_test")
public class QuartzTest extends Model<QuartzTest> implements Serializable {
    private static final long serialVersionUID = 2049104316610649585L;

    @TableId
    private String id;

    @TableLogic
    private Integer name;
}
