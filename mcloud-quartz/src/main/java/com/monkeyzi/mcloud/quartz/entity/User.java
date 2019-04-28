package com.monkeyzi.mcloud.quartz.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 参数嵌套校验
 */
@Data
public class User {

    @NotBlank(message = "姓名name不能为空")
    private String userName;


    @Valid
    @NotNull(message = "props不能为空")
    @Size(min = 1, message = "props至少要有一个自定义属性")
    private List<Item> items;
}
