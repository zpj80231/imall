package com.imall.thirdparty.modules.controller;

import com.imall.thirdparty.annotations.ThirdPartyPublicParam;
import com.imall.thirdparty.common.CommonRequest;
import com.imall.thirdparty.constants.TokenTypeEnum;
import com.imall.thirdparty.modules.pojo.dto.InOutCdrDTO;
import com.imall.thirdparty.modules.pojo.dto.NoAnswerRecordCdrDTO;
import com.imall.thirdparty.modules.pojo.vo.InOutCdrVO;
import com.imall.thirdparty.modules.pojo.vo.NoAnswerRecordCdrVO;
import com.imall.thirdparty.modules.service.CdrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CDR话单
 *
 * @author zhangpengjun
 * @date 2022/7/15
 */
@Slf4j
@RestController
@ThirdPartyPublicParam(tokenType = TokenTypeEnum.CDR)
@RequestMapping("/json/1/{companyid}")
@Api(tags = "CDR话单服务")
public class CdrController {

    @Autowired
    private CdrService cdrService;

    /**
     * 呼入呼出话单
     */
    @ApiOperation("呼入呼出话单")
    @PostMapping("/getInOutCdr")
    public List<InOutCdrDTO> getInOutCdr(@PathVariable("companyid") String companyid,
                                         @RequestBody @Validated CommonRequest<InOutCdrVO> commonRequest) {
        log.info("companyid: {}, commonRequest: {}", companyid, commonRequest);
        List<InOutCdrDTO> list = cdrService.getInOutCdr(companyid, commonRequest.getData());
        return list;
    }

    /**
     * 呼入漏电留言话单
     */
    @ApiOperation("呼入漏电留言话单")
    @PostMapping("/getNoAnswerRecordCdr")
    @ThirdPartyPublicParam(tokenType = TokenTypeEnum.SERVER)
    public List<NoAnswerRecordCdrDTO> getNoAnswerRecordCdr(@PathVariable("companyid") String companyid,
                                                           @RequestBody @Validated NoAnswerRecordCdrVO vo) {
        log.info("companyid: {}, vo: {}", companyid, vo);
        List<NoAnswerRecordCdrDTO> list = cdrService.getNoAnswerRecordCdr(companyid, vo);
        return list;
    }

}
