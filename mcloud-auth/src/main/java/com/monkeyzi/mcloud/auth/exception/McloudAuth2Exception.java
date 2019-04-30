package com.monkeyzi.mcloud.auth.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义oauth异常
 */
public class McloudAuth2Exception extends OAuth2Exception {

    private String errorCode;
    public McloudAuth2Exception(String msg,String errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
    public McloudAuth2Exception(String msg) {
        super(msg);
    }
}
