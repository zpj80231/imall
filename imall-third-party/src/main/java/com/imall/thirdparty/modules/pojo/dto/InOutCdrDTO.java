package com.imall.thirdparty.modules.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangpengjun
 * @date 2022/7/15
 */
@Data
public class InOutCdrDTO {

    /**
     * 通话时间
     */
    @ApiModelProperty(value = "通话时间", required = true)
    private Date calldate;

    /**
     * 企业 ID
     * projectid
     */
    @ApiModelProperty(value = "企业 ID", required = true)
    private String companyid;

    /**
     * 客户号码
     * telb
     */
    @ApiModelProperty(value = "客户号码", required = true)
    private String phone;

    /**
     * 呼叫类型
     * option
     */
    @ApiModelProperty(value = "呼叫类型", required = true)
    private String calltype;

    /**
     * 是否主动挂机
     * hangupsource
     * PJSIP/6003-00000015: 截取 / 和 - 之间的数据，和坐席号匹配，如果能匹配就是坐席挂机，不能匹配为客户挂机
     * PJSIP/pjsiptrunk-00000015: 客户先挂机
     */
    @ApiModelProperty(value = "是否主动挂机", required = true)
    private String hangup_type;

    /**
     * 通话时长
     * duration
     */
    @ApiModelProperty(value = "通话时长", required = true)
    private String calltime;

    /**
     * 等待时长
     * 两个字段相减 duration - billsec
     */
    @ApiModelProperty(value = "等待时长", required = true)
    private String wait;

    /**
     * 坐席号码
     * agentid
     */
    @ApiModelProperty(value = "坐席号码", required = true)
    private String agent;

    /**
     * 是否接通
     * disposition = ANSWERED 则接通
     */
    @ApiModelProperty(value = "是否接通", required = true)
    private String answered;

    /**
     * 唯一 ID
     * linkedid
     */
    @ApiModelProperty(value = "唯一 ID", required = true)
    private String call_uuid;

    /**
     * 录音 ID
     * recording
     */
    @ApiModelProperty(value = "录音 ID", required = true)
    private String recID;


}
