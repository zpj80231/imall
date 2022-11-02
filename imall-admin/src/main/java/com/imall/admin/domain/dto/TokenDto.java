package com.imall.admin.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "tokenHead")
    private String tokenHead;

}
