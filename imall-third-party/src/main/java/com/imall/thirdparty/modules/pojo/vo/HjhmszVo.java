package com.imall.thirdparty.modules.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Data
public class HjhmszVo {
    /**
     * 坐席编号
     */
    @NotBlank
    @ApiModelProperty(value = "坐席编号", required = true)
    private String agent;
    /**
     * 真实主叫
     */
    @NotBlank
    @ApiModelProperty(value = "真实主叫", required = true)
    private String caller;
    /**
     * 中间号
     */
    @NotBlank
    @ApiModelProperty(value = "中间号", required = true)
    private String caller_show;
    /**
     * 真实被叫
     */
    @NotBlank
    @ApiModelProperty(value = "真实被叫", required = true)
    private String called;
}
