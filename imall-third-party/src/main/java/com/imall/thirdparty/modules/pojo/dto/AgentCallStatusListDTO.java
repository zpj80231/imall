package com.imall.thirdparty.modules.pojo.dto;

import lombok.Data;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class AgentCallStatusListDTO {
    /**
     * 主叫号码
     */
    private String cid_num;
    /**
     * 被叫号码
     */
    private String callee_num;
    /**
     * 状态持续时长（HH:mm:ss）
     */
    private String duration;
    /**
     * 当前状态
     * callout（呼出）
     * ringing（响铃）
     * talking（通话）
     * idle（空闲）
     * paused（暂停）
     * offline（离线）
     */
    private String status;
    /**
     * 状态开始时间
     */
    private String time_st;
    /**
     * 坐席编号
     */
    private String agentid;
}
