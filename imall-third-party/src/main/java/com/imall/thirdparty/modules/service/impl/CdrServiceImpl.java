package com.imall.thirdparty.modules.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imall.thirdparty.common.DynamicTableNamePlugin;
import com.imall.thirdparty.constants.DsName;
import com.imall.thirdparty.modules.mapper.CdrMapper;
import com.imall.thirdparty.modules.pojo.domain.Cdr;
import com.imall.thirdparty.modules.pojo.dto.IncomingAndOutgoingCDRsDTO;
import com.imall.thirdparty.modules.pojo.dto.IncomingMissedCallMessageBillDTO;
import com.imall.thirdparty.modules.pojo.vo.IncomingAndOutgoingCDRsVO;
import com.imall.thirdparty.modules.pojo.vo.IncomingMissedCallMessageBillVO;
import com.imall.thirdparty.modules.service.CdrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangpengjun
 * @description 针对表【cdr】的数据库操作Service实现
 * @createDate 2022-07-11 11:52:14
 */
@DS(DsName.ASTERISK_CDRDB)
@Slf4j
@Service
public class CdrServiceImpl extends ServiceImpl<CdrMapper, Cdr>
        implements CdrService {

    @Override
    public List<IncomingAndOutgoingCDRsDTO> getInOutCdr(String companyid, IncomingAndOutgoingCDRsVO data) {
        String tableName = getTableNameFromDate(data.getCalldate());
        DynamicTableNamePlugin.setTable(tableName);
        LambdaQueryWrapper<Cdr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cdr::getProjectId, companyid);
        List<Cdr> list = list(queryWrapper);
        List<IncomingAndOutgoingCDRsDTO> collect = list.stream().map(o -> {
            IncomingAndOutgoingCDRsDTO dto = new IncomingAndOutgoingCDRsDTO();
            dto.setCalldate(o.getCalldate());
            dto.setCompanyid(o.getProjectId());
            dto.setPhone(o.getTelb());
            dto.setCalltype(o.getOption());
            dto.setHangup_type(o.getHangupsource());
            dto.setCalltime(o.getDuration() + "");
            dto.setWait((o.getDuration() - o.getBillsec()) + "");
            dto.setAgent(o.getAgentid());
            dto.setAnswered(o.getDisposition());
            dto.setCall_uuid(o.getLinkedid());
            dto.setRecID(o.getRecording());
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<IncomingMissedCallMessageBillDTO> getNoAnswerRecordCdr(String companyid, IncomingMissedCallMessageBillVO data) {
        String tableName = getTableNameFromDate(data.getCalldate());
        DynamicTableNamePlugin.setTable(tableName);
        LambdaQueryWrapper<Cdr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cdr::getProjectId, companyid);
        List<Cdr> list = list(queryWrapper);
        List<IncomingMissedCallMessageBillDTO> collect = list.stream().map(o -> {
            IncomingMissedCallMessageBillDTO dto = new IncomingMissedCallMessageBillDTO();
            dto.setCalldate(o.getCalldate());
            dto.setCompanyid(o.getProjectId());
            dto.setDuration(o.getDuration() + "");
            dto.setRecord_seconds(o.getDuration() + "");
            dto.setRecordfile(o.getRecording());
            dto.setSrc(o.getSrc());
            dto.setCall_uuid(o.getLinkedid());
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取表名
     *
     * @param date
     * @return
     */
    private String getTableNameFromDate(String date) {
        DateTime dateTime = DateUtil.parse(date);
        String yyyyMMdd = DateUtil.format(dateTime, new SimpleDateFormat("yyyyMMdd"));
        String tableName = "cdr_" + yyyyMMdd;
        return tableName;
    }
}




