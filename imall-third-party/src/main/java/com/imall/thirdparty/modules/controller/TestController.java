package com.imall.thirdparty.modules.controller;

import com.imall.thirdparty.annotations.ThirdPartyPublicParam;
import com.imall.thirdparty.common.CommonRequest;
import com.imall.thirdparty.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangpengjun
 * @date 2023/2/27
 */
@Slf4j
@RestController
@ThirdPartyPublicParam
@RequestMapping("/version-1.0.0")
@Api(tags = "测试各种请求体")
public class TestController {

    @ApiOperation("测试Get请求")
    @GetMapping("/testGet")
    public String testGet(@RequestParam String projectId, @RequestBody CommonRequest<?> commonRequest) throws ApiException {
        log.info("projectId: {}, commonRequest: {}", projectId, commonRequest);
        return projectId;
    }

    @ApiOperation("测试Get请求,请求体为空")
    @GetMapping("/testGetEmptyBody")
    public String testGetEmptyBody(@RequestParam String projectId) throws ApiException {
        log.info("projectId: {}", projectId);
        return projectId;
    }

    @ApiOperation("测试Post请求")
    @PostMapping("/testPost")
    public CommonRequest<Object> testPost(@RequestParam String projectId, @RequestBody CommonRequest<Object> commonRequest) throws ApiException {
        log.info("projectId: {}, commonRequest: {}", projectId, commonRequest);
        return commonRequest;
    }


}



