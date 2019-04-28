package com.monkeyzi.mcloud.quartz.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: 高yg
 * @date: 2019/4/28 21:55
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class Item {

    @NotBlank(message = "Id不能为空")
    private String  id;
}
