package com.imall.thirdparty.modules.pojo.dto;

import lombok.Data;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class AgentCDayCallReportDTO {
    /**
     * 平均通话时长
     */
    private Integer avgCalltime;
    /**
     * 空闲时长
     */
    private Integer idleTime;
    /**
     * 呼入数
     */
    private Integer callInCount;
    /**
     * 未接次数
     */
    private Integer callInCost;
    /**
     * 坐席编号
     */
    private String agent;
}
