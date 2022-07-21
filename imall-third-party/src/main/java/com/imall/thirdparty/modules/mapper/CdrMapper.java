package com.imall.thirdparty.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imall.thirdparty.modules.pojo.domain.Cdr;
import com.imall.thirdparty.modules.pojo.dto.AgentCDayCallReportDTO;
import com.imall.thirdparty.modules.pojo.dto.OutReportDTO;
import com.imall.thirdparty.modules.pojo.vo.AgentCDayCallReportVO;
import com.imall.thirdparty.modules.pojo.vo.OutReportVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangpengjun
 * @description 针对表【cdr】的数据库操作Mapper
 * @createDate 2022-07-11 11:52:14
 * @Entity com.dky.admin.modules.report.domain.Cdr
 */
public interface CdrMapper extends BaseMapper<Cdr> {

    List<AgentCDayCallReportDTO> agentCDayCallReport(@Param("companyid") String companyid, @Param("vo") AgentCDayCallReportVO vo, @Param("tableName") String tableName);

    List<OutReportDTO> getOutReport(@Param("companyid") String companyid, @Param("vo") OutReportVO vo, @Param("tableName") String tableName);
}




