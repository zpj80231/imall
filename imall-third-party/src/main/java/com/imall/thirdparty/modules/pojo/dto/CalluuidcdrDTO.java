package com.imall.thirdparty.modules.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class CalluuidcdrDTO {
    /**
     * 客户应答时间
     */
    private Date answered_time;
    /**
     * 通话时长
     */
    private String billsec;
    /**
     * 结果:坐席未接|客户未接|接通|呼叫失败
     */
    private String hangup_cause;
    /**
     * 坐席挂机为 agent,客户挂机为 phone
     */
    private String sip_hangup_disposition;
    /**
     * 坐席号码
     */
    private String agent;
    /**
     * 客户号码
     */
    private String phone;
    /**
     * 呼叫时长
     */
    private String duration;
    /**
     * out(外呼),in（呼入）
     */
    private String type;
    /**
     * 录音编号
     */
    private String fbocc_recID;
    /**
     * 呼叫开始时间
     */
    private Date call_time;
    /**
     * 坐席应答时间
     */
    private Date agent_answered_time;
    /**
     * 挂机时间
     */
    private Date hangup_time;
    /**
     * 通话唯一 ID
     */
    private String calluuid;
}
