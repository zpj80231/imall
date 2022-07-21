package com.imall.thirdparty.support;

import com.alibaba.fastjson.JSONValidator;
import com.imall.thirdparty.annotations.Base64JSON;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author zhangpengjun
 * @date 2022/7/13
 */
public class Base64JsonCondictionVaildation implements ConstraintValidator<Base64JSON, String> {

    @Override
    public void initialize(Base64JSON base64JSON) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String decodedString;
        try {
            byte[] decode = Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8));
            decodedString = new String(decode);
        } catch (Exception e) {
            return false;
        }
        if (!JSONValidator.from(decodedString).validate()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Base64非合法Json")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
