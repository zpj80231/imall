package com.imall.thirdparty.modules.controller;

import com.imall.thirdparty.annotations.DkyThirdPartyParam;
import com.imall.thirdparty.common.TokenSignPlugin;
import com.imall.thirdparty.constants.TokenTypeEnum;
import com.imall.thirdparty.exception.ApiException;
import com.imall.thirdparty.modules.pojo.dto.TokenDTO;
import com.imall.thirdparty.modules.pojo.vo.TokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取token
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Slf4j
@RestController
@RequestMapping("/json/1/{companyid}")
@Api(tags = "token 接口服务", description = "token管理")
public class TokenController {

    @ApiOperation("获取token")
    @DkyThirdPartyParam(tokenType = TokenTypeEnum.Empty)
    @PostMapping("/token")
    public TokenDTO getToken(@PathVariable("companyid") String companyId,
                             @RequestBody @Validated TokenVo tokenVo) throws ApiException {
        log.info("companyId: {}, tokenVo: {}", companyId, tokenVo);
        String tokenType = tokenVo.getTokentype();
        String token = TokenSignPlugin.createToken(tokenType, companyId);
        return new TokenDTO(tokenType, token);
    }
}
