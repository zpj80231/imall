package com.imall.thirdparty.annotations;

import com.imall.thirdparty.support.Base64JsonCondictionVaildation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 校验是否为base64及解码后是否为json
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Base64JsonCondictionVaildation.class})
@ReportAsSingleViolation
public @interface Base64JSON {

    String message() default "输入的不是Base64编码字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
