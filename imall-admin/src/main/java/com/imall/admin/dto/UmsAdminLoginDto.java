package com.imall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Data
public class UmsAdminLoginDto {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
