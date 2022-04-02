package com.imall.note.util.excel;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class DateStringConverter implements Converter<Date> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                  GlobalConfiguration globalConfiguration) throws ParseException {
        // Excel日期得到的是1900年起的差值，且Excel日期差值比中国多2天8小时，Java日期是从1970年开始
        if (cellData.getType().equals(CellDataTypeEnum.NUMBER)) {
            long from = DateUtil.betweenMs(
                    DateUtil.parse("1900-1-1"), DateUtil.parse("1970-1-1"));
            long to = cellData.getNumberValue()
                    .multiply(new BigDecimal(24 * 60 * 60 * 1000))
                    .subtract(new BigDecimal((48 + 8) * 60 * 60 * 1000))
                    .longValue();
            // DateTime date = DateUtil.date(to - from);
            return new Date(to - from);
        }

        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return DateUtils.parseDate(cellData.getStringValue(), null);
        } else {
            return DateUtils.parseDate(cellData.getStringValue(),
                contentProperty.getDateTimeFormatProperty().getFormat());
        }
    }

    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new WriteCellData<>(DateUtils.format(value, null));
        } else {
            return new WriteCellData<>(DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }
}
