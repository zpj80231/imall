package com.imall.thirdparty.modules.pojo.vo;

import com.imall.thirdparty.annotations.DateTimeStr;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class CalluuidcdrVo {
    /**
     * 通话唯一 ID
     */
    @NotBlank
    private String calluuid;
    @NotBlank
    @DateTimeStr(format = "yyyy-MM-dd", message = "确认日期格式(yyyy-MM-dd)")
    private String calldate;
}
