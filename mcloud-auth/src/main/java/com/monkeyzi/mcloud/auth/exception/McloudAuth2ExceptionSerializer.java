package com.monkeyzi.mcloud.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.monkeyzi.mcloud.common.result.ResponseCode;
import lombok.SneakyThrows;

/**
 * 异常格式化
 */
public class McloudAuth2ExceptionSerializer extends StdSerializer<McloudAuth2Exception> {


    protected McloudAuth2ExceptionSerializer(Class<McloudAuth2Exception> t) {
        super(t);
    }

    @Override
    @SneakyThrows
    public void serialize(McloudAuth2Exception e, JsonGenerator json, SerializerProvider
            serializerProvider) {
        json.writeStartObject();
        json.writeObjectField("code",ResponseCode.ERROR.getCode());
        json.writeObjectField("msg",e.getMessage());
        json.writeObjectField("data",e.getOAuth2ErrorCode());
        json.writeObjectField("success",false);
        json.writeEndObject();
    }
}
