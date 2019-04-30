package com.monkeyzi.mcloud.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * 重写未认证异常
 */
@JsonSerialize(using = McloudAuth2ExceptionSerializer.class)
public class UnAuthorizedException  extends McloudAuth2Exception{
    public UnAuthorizedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
