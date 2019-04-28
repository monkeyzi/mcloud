package com.monkeyzi.mcloud.quartz.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class User {

    @NotBlank(message = "姓名name")
    private String userName;
}
