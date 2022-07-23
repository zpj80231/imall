package com.imall.thirdparty.modules.pojo.vo;

import com.imall.thirdparty.annotations.DateTimeStr;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangpengjun
 * @date 2022/7/15
 */
@Data
public class InOutCdrVO {

    @ApiModelProperty(value = "时间日期 yyyy-MM-dd", required = true)
    @NotBlank
    @DateTimeStr(format = "yyyy-MM-dd", message = "确认日期格式(yyyy-MM-dd)")
    private String calldate;

}
