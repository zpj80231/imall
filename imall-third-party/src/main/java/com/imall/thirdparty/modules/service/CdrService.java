package com.imall.thirdparty.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imall.thirdparty.modules.pojo.domain.Cdr;
import com.imall.thirdparty.modules.pojo.dto.IncomingAndOutgoingCDRsDTO;
import com.imall.thirdparty.modules.pojo.dto.IncomingMissedCallMessageBillDTO;
import com.imall.thirdparty.modules.pojo.vo.IncomingAndOutgoingCDRsVO;
import com.imall.thirdparty.modules.pojo.vo.IncomingMissedCallMessageBillVO;

import java.util.List;

/**
 * @author zhangpengjun
 * @description 针对表【cdr】的数据库操作Service
 * @createDate 2022-07-11 11:52:14
 */
public interface CdrService extends IService<Cdr> {
    List<IncomingAndOutgoingCDRsDTO> getInOutCdr(String companyid, IncomingAndOutgoingCDRsVO vo);

    List<IncomingMissedCallMessageBillDTO> getNoAnswerRecordCdr(String companyid, IncomingMissedCallMessageBillVO vo);
}
