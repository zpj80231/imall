package com.imall.thirdparty.annotations;

import com.imall.thirdparty.constants.TokenTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口入参自动解包，反参自动包装，方法级别优先级高<p>
 * <p>
 * 在 spring mvc 中获取请求实体方式为：<br/>
 * 1. @RequestBody CommonRequestParam<T> (解包后的带公共参数实体) <br/>
 * 2. @RequestBody T (解包的data实体) <br/>
 * <p>
 *
 * @author zhangpengjun
 * @date 2022/7/14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ThirdPartyPublicParam {

    /**
     * token类型，用于校验token
     */
    TokenTypeEnum tokenType() default TokenTypeEnum.Empty;

}
