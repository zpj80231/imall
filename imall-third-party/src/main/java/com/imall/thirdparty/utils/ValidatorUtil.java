package com.imall.thirdparty.utils;

import com.imall.thirdparty.exception.ApiCode;
import com.imall.thirdparty.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * 校验实体
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Slf4j
public class ValidatorUtil {

    private static Validator validatorFast = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
    private static Validator validatorAll = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

    /**
     * 校验遇到第一个不合法的字段直接返回不合法字段，后续字段不再校验
     */
    public static <T> Set<ConstraintViolation<T>> validateFast(T domain) throws ApiException {
        Set<ConstraintViolation<T>> validateResult = validatorFast.validate(domain);
        if (validateResult.size() > 0) {
            String message = validateResult.iterator().next().getPropertyPath() + "：" + validateResult.iterator().next().getMessage();
            throw new ApiException(ApiCode.REQUEST_PARAMETER_EXCEPTION.getCode(), message);
        }
        return validateResult;
    }

    /**
     * 校验所有字段并返回不合法字段
     */
    public static <T> Set<ConstraintViolation<T>> validateAll(T domain) throws ApiException {
        Set<ConstraintViolation<T>> validateResult = validatorAll.validate(domain);
        if (validateResult.size() > 0) {
            StringBuilder message = new StringBuilder();
            Iterator<ConstraintViolation<T>> it = validateResult.iterator();
            while (it.hasNext()) {
                ConstraintViolation<T> cv = it.next();
                message.append(cv.getPropertyPath() + ":" + cv.getMessage() + ";");
            }
            throw new ApiException(ApiCode.REQUEST_PARAMETER_EXCEPTION.getCode(), message.toString());
        }
        return validateResult;
    }

}
