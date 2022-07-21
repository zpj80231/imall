package com.imall.thirdparty.modules.controller;

import com.imall.thirdparty.common.CommonRequest;
import com.imall.thirdparty.utils.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangpengjun
 * @date 2022/7/20
 */
@Slf4j
@RestController
@RequestMapping("/json/sign")
@Api(tags = "签名包装服务", description = "只为测试方便提供")
public class SignController {

    /**
     * 请求包装
     */
    @ApiOperation("请求包装")
    @PostMapping("/requestWrapping")
    public CommonRequest requestWrapping(@RequestBody @Validated CommonRequest commonRequest) {
        log.info("commonRequest: {}", commonRequest);
        String endodeData = SignUtil.encodeData(commonRequest.getData());
        CommonRequest requestWrapping = new CommonRequest(commonRequest.getToken(), endodeData, commonRequest.getTs(), null);
        String sign = SignUtil.createRequestSign(requestWrapping);
        requestWrapping.setSign(sign);
        return requestWrapping;
    }

}
