package com.imall.thirdparty.modules.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangpengjun
 * @date 2022/7/14
 */
@Data
public class TokenVo {

    @ApiModelProperty(value = "token类型", required = true)
    @NotBlank
    private String tokentype;

}
