package com.zpj80231.imall.common.exception;

import com.zpj80231.imall.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by zpj80231 on 2020/9/30.
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public CommonResult apiExceptionHandler(ApiException e){
        if(e.getErrorResult() != null) {
            log.error(e.getMessage());
            return CommonResult.failed(e.getErrorResult());
        }
        log.error(e.getMessage());
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 其他未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(Exception e){
        return CommonResult.failed(e.getMessage());
    }

}
