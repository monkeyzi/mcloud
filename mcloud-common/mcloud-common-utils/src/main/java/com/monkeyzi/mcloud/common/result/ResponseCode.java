package com.monkeyzi.mcloud.common.result;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0,"成功"),
    ERROR(1,"操作失败");


    private final Integer code;
    private final String msg;


    ResponseCode(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
