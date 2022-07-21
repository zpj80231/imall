package com.imall.thirdparty.modules.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangpengjun
 * @date 2022/7/18
 */
@Data
public class InboundIVRCdrDTO {

    /**
     * 通话时间
     */
    private Date calldate;

    /**
     * 主叫号码
     * projectid
     */
    private String src;

}
