package com.imall.thirdparty.modules.pojo.dto;

import lombok.Data;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class OutReportDTO {

    /**
     * 坐席号码
     */
    private String agent;
    /**
     * 呼出数
     */
    private String outcount;
    /**
     * 呼出应答数
     */
    private String outydcount;
    /**
     * 呼出平均时长
     */
    private String outavgsec;
    /**
     * 呼出平均通话时长
     */
    private String avgsec;
    /**
     * 呼出通话时长
     */
    private String totalsec;
    /**
     * 呼出时长
     */
    private String outtotalsec;
    /**
     * 呼出通话大于 60 秒数
     */
    private String jfEvery2Minute;
    /**
     * 呼出最大通话时长
     */
    private String outmaxtimecount;
    /**
     * 内部错误
     */
    private String innererrcount;
    /**
     * 呼叫放弃数
     */
    private String cancelcount;
    /**
     * 呼叫拒接数
     */
    private String refusecount;
    /**
     * 其他未通数
     */
    private String othercount;
    /**
     * 企业 ID
     */
    private String companyid;

}
