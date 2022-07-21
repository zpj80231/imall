package com.imall.thirdparty.modules.pojo.vo;

import com.imall.thirdparty.annotations.DateTimeStr;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class AgentCDayCallReportVO {
    @NotBlank
    @DateTimeStr(format = "yyyy-MM-dd", message = "确认日期格式(yyyy-MM-dd)")
    private String calldate;
    /**
     * 坐席编号，如果查询全部坐席，请置空
     */
    @NotNull
    private String agent;
}
