package com.monkeyzi.mcloud.quartz.protocal;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础参数
 */
@Data
public class BasePageReq implements Serializable {

    private Integer pageNum;

    private Integer pageSize;

}
