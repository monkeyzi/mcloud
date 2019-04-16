package com.monkeyzi.mcloud.quartz.protocal;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页查询基础参数
 */
@Data
public class BasePageReq implements Serializable {

    @NotNull(message = "页码不能为空")
    private Integer pageNum;

    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

}
