package com.imall.thirdparty.modules.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    String tokentype;

    String token;

}
