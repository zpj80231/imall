package com.imall.thirdparty.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 公共请求参数
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonRequest<T> {

    /**
     * 令牌
     */
    @ApiModelProperty(value = "令牌", required = true)
    @NotNull
    private String token;

    /**
     * 数据信息
     */
    @ApiModelProperty(value = "数据信息", required = true)
    @NotNull
    @Valid
    private T data;

    /**
     * 时间戳，精度：秒 10位
     */
    @ApiModelProperty(value = "时间戳，精度：秒 10位", required = true)
    @NotBlank
    @Length(min = 10, max = 10, message = "请确认10位时间戳")
    @Pattern(regexp = "^[0-9]*$", message = "请确认纯数字")
    private String ts;

    /**
     * 签名
     */
    @ApiModelProperty(value = "签名", required = true)
    @NotBlank
    private String sign;

    // public <T> T getDataDecoded(Class<T> clazz) {
    //     byte[] decode = Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
    //     String decodeToString = new String(decode);
    //     T t = JSON.parseObject(decodeToString, clazz);
    //     return t;
    // }
}
