package com.monkeyzi.mcloud.quartz.exception;

/**
 * @author: 高yg
 * @date: 2019/4/14 22:21
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class McloudJobException extends Exception {

    public McloudJobException() {
        super();
    }

    public McloudJobException(String msg) {
        super(msg);
    }

    public McloudJobException(Throwable cause){
        super(cause);
    }
}
