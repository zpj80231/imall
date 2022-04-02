package com.imall.note.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.imall.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 封装的EasyExcel工具类
 *
 * @author zpj80231
 * @date 2022 /4/2
 */
@Slf4j
public class EasyExcelUtil {

    /**
     * Read list.
     * 从Excel文件获取数据转换为JavaBean
     *
     * @param <T>       the type parameter
     * @param file      the file
     * @param sheetName the sheet name
     * @param clazz     the head clazz
     * @return the JavaBean list
     * @throws IOException the io exception
     */
    public static <T> List<T> read(MultipartFile file,
                                   String sheetName,
                                   Class<T> clazz) throws IOException {
        // 校验文件格式
        if (!checkExcelSuffix(file.getOriginalFilename())) {
            throw new ApiException(file.getOriginalFilename() + " 不是一个Excel文件");
        }
        // 构建Reader对象，判断是否有指定sheet页
        if (!checkSheetName(file, sheetName)) {
            throw new ApiException("未找到sheet页：" + sheetName);
        }
        // 根据listener、头对象、sheet页，解析Excel
        List<Object> dataList = EasyExcel.read(file.getInputStream(), new CommonEasyExcelListener())
                .head(clazz)
                .sheet(sheetName)
                .doReadSync();
        return (List<T>) dataList;
    }

    /**
     * Excel文件下载，文件名默认为：sheetName+时间
     *
     * @param response
     * @param sheetName
     * @param list
     * @throws IOException
     */
    public static void write(HttpServletResponse response,
                             String sheetName,
                             List list) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String datetime = sdf.format(new Date());
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + datetime;

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // excel头策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteFont.setBold(false);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // excel内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short)11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 设置handler
        HorizontalCellStyleStrategy styleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        if (list == null || list.size() <= 0) {
            // throw new ApiException("下载的数据内容不能为空");
            EasyExcel.write(response.getOutputStream())
                    .sheet(sheetName)
                    .registerWriteHandler(styleStrategy)
                    .doWrite(list);
        } else {
            EasyExcel.write(response.getOutputStream())
                    .head(list.get(0).getClass())
                    .sheet(sheetName)
                    .registerWriteHandler(styleStrategy)
                    .doWrite(list);
        }
    }

    /**
     * @param file
     * @param sheetName
     * @return 存在指定sheet页返回true
     * @throws IOException
     */
    public static boolean checkSheetName(MultipartFile file, String sheetName) throws IOException {
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream());
        ExcelReader excelReader = excelReaderBuilder.build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();
        long count = readSheets.stream().filter(sheet -> sheetName.equals(sheet.getSheetName())).count();
        return count < 1 ? false : true;
    }

    /**
     * Check excel suffix boolean.
     *
     * @param fileName the file name
     * @return the boolean 是Excel文件则返回true
     */
    public static boolean checkExcelSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)
                || !fileName.endsWith(ExcelTypeEnum.XLSX.getValue())
                || !fileName.endsWith(ExcelTypeEnum.XLS.getValue())) {
            return false;
        }
        return true;
    }

}
