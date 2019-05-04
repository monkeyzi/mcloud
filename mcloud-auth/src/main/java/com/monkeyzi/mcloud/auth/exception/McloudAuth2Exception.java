package com.monkeyzi.mcloud.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义oauth异常
 */
@JsonSerialize(using = McloudAuth2ExceptionSerializer.class)
public class McloudAuth2Exception extends OAuth2Exception {

    @Getter
    private String errorCode;
    public McloudAuth2Exception(String msg,String errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
    public McloudAuth2Exception(String msg) {
        super(msg);
    }
}
