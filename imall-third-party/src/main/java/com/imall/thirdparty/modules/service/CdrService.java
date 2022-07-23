package com.imall.thirdparty.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imall.thirdparty.modules.pojo.domain.Cdr;
import com.imall.thirdparty.modules.pojo.dto.InOutCdrDTO;
import com.imall.thirdparty.modules.pojo.dto.NoAnswerRecordCdrDTO;
import com.imall.thirdparty.modules.pojo.vo.InOutCdrVO;
import com.imall.thirdparty.modules.pojo.vo.NoAnswerRecordCdrVO;

import java.util.List;

/**
 * @author zhangpengjun
 * @description 针对表【cdr】的数据库操作Service
 * @createDate 2022-07-11 11:52:14
 */
public interface CdrService extends IService<Cdr> {
    List<InOutCdrDTO> getInOutCdr(String companyid, InOutCdrVO vo);

    List<NoAnswerRecordCdrDTO> getNoAnswerRecordCdr(String companyid, NoAnswerRecordCdrVO vo);
}
