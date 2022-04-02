package com.imall.note.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.imall.common.exception.ApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * EasyExcel通用读入监听器
 *
 * @author zpj80231
 * @date 2022/4/2
 */
@Slf4j
public class CommonEasyExcelListener<T> extends AnalysisEventListener<T> {

    private Map<Integer, String> headMap = null;

    @Override
    public void invoke(T data, AnalysisContext context) {

    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        super.extra(extra, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            // 获取每个单元格的相关信息
            Integer columnIdex = ((ExcelDataConvertException) exception).getColumnIndex() + 1;
            Integer rowIndex = ((ExcelDataConvertException) exception).getRowIndex() + 1;
            String cellData = ((ExcelDataConvertException) exception).getCellData().getStringValue();
            // 获取sheet页和列头
            String sheetName = context.readSheetHolder().getSheetName();
            String head = headMap.get(columnIdex - 1);
            // 组装错误信息
            String message = "sheet:["+sheetName+"]第["+rowIndex+"]行，第["+columnIdex+"]列["
                    +head+"]数据：["+cellData+"]格式有误，请核实";
            throw new ApiException(message, exception);
        } else if (exception instanceof RuntimeException) {
            throw exception;
        } else {
            super.onException(exception, context);
        }
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        // 从Excel文件读取到的行数据
        Object data = context.readRowHolder().getCurrentRowAnalysisResult();
        // 如果有空行则停止读取
        if (checkObjFieldsIsNull(data)) {
            return false;
        }
        return super.hasNext(context);
    }

    @SneakyThrows
    private boolean checkObjFieldsIsNull(Object obj) {
        boolean flag = true;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
