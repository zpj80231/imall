package com.imall.thirdparty.modules.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangpengjun
 * @date 2022/7/15
 */
@Data
public class NoAnswerRecordCdrDTO {

    /**
     * 通话时间
     */
    private Date calldate;

    /**
     * 企业 ID
     * projectid
     */
    private String companyid;

    /**
     * 等待时间
     */
    private String duration;

    /**
     * 留言时长
     */
    private String record_seconds;

    /**
     * 留言文件名
     * recording
     */
    private String recordfile;

    /**
     * 主叫号码
     */
    private String src;

    /**
     * 唯一 ID
     * linkedid
     */
    private String call_uuid;

}
