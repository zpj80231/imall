package com.imall.thirdparty.modules.pojo.vo;

import com.imall.thirdparty.annotations.DateTimeStr;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class OutReportVO {
    @NotBlank
    @DateTimeStr(format = "yyyy-MM-dd", message = "确认日期格式(yyyy-MM-dd)")
    private String calldate;
}
