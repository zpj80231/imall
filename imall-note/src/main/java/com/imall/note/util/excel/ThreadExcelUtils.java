package com.imall.note.util.excel;

import com.imall.note.po.Student;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExcelUtils {

    /**
     * 定义没个 sheet 最多数量
     */
    public static final Integer EXCEL_MAX_CNT = 50000;

    private Workbook wb;

    private String fileName;

    private String filePath;

    private String[] hearders;

    private String[] fields;

    public Workbook getWb() {
        return wb;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String[] getHearders() {
        return hearders;
    }

    public String[] getFields() {
        return fields;
    }

    /**
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param hearders 文件头
     * @param fields   字段属性
     * @Author maochuang.li
     * @date 2017/12/28
     */
    public ThreadExcelUtils(String fileName, String filePath, String[] hearders, String[] fields) {
        this.wb = new SXSSFWorkbook(10000);
        this.fileName = fileName;
        this.filePath = filePath;
        this.hearders = hearders;
        this.fields = fields;
    }

    public static void main(String[] args) throws Exception {
        String[] header = {"姓名", "年龄"};
        String[] fileNames = {"name", "age"};
        ThreadExcelUtils utils = new ThreadExcelUtils("测试Excel1", "D:\\exceltext\\wer\\sd", header, fileNames);
        List list = new ArrayList<>();
        System.out.println("开始造数据.......");
        for (int i = 0; i < 900000; i++) {
            Student student = new Student();
            student.setName("name->" + i);
            student.setAge(i);
            list.add(student);
        }
        System.out.println(getDate(new Date()) + "开始写入文件.......");
        long startTime = System.currentTimeMillis();
        utils.exportExcelToFilePath(list);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
        //耗时：29230
        //耗时：25226

    }

    /**
     * @param list 数据
     * @Author maochuang.li
     * @date 2017/12/28
     */
    public void exportExcelToFilePath(List<Object> list) throws Exception {
        int excelSize = EXCEL_MAX_CNT;    //每个Excel文件条数
        int totalCount = list.size();    //查询结果总条数
        int pageCount = 0;//总sheet页个数
        int numPage = totalCount % excelSize;    //是否整页数
        if (numPage > 0)
            pageCount = totalCount / excelSize + 1;
        else
            pageCount = totalCount / excelSize;
        //创建线程池 多sheet多线程写入 线程数 为sheet页的 1/4
        Integer threadNumber = pageCount / 4;
        if (threadNumber == 0)
            threadNumber = 1;
//        ExecutorService threadPool = Executors.newFixedThreadPool(threadNumber);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadNumber, threadNumber, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50));
        //创建栅栏 等待任务完成
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);
        //循环遍历投递任务
        for (int i = 1; i <= pageCount; i++) {
            ThraedExcel thraedExcel = new ThraedExcel(list, i, pageCount, numPage, this);
            thraedExcel.setCountDownLatch(countDownLatch);
            threadPool.submit(thraedExcel);
        }
        countDownLatch.await(10L,TimeUnit.SECONDS);
        System.err.println("超时拉=====================================");
        Workbook wb = getWb();
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        FileOutputStream fout = new FileOutputStream(new File(file, fileName + ".xls"));
        try {
            wb.write(fout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件写入完成");
        //立即销毁线程池
        threadPool.shutdownNow();
    }


    private static String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * JavaBean转Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            int length = descriptors.length;
            for (int i = 0; i < length; i++) {
                String name = descriptors[i].getName();
                if (!StringUtils.equals(name, "class")) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            System.err.println("bean 转Map出错");
            e.printStackTrace();
        }
        return params;
    }

    /***
     * 线程写入sheet
     */
    private static class ThraedExcel implements Runnable {

        private List<Object> list;//数据

        private Integer sheetNumber;//当前sheet页

        private Integer totalSheetCount;//总数据

        private int numPage;    //是否整页数

        private Integer excelSize;

        private ThreadExcelUtils threadExcelUtils;
        //栅栏对象
        private CountDownLatch countDownLatch;

        /**
         * @param list            总数据
         * @param sheetNumber     当前sheet页
         * @param totalSheetCount 总sheet页
         * @param numPage         是否整数
         * @Author maochuang.li
         * @date 2017/12/28
         */
        public ThraedExcel(List<Object> list, Integer sheetNumber, Integer totalSheetCount, Integer numPage, ThreadExcelUtils threadExcelUtils) {
            this.list = list;//总数据
            this.sheetNumber = sheetNumber;//当前sheet页
            this.totalSheetCount = totalSheetCount;//总sheet页
            this.numPage = numPage;//是否整除
            this.excelSize = ThreadExcelUtils.EXCEL_MAX_CNT;//没个sheet最大数量
            this.threadExcelUtils = threadExcelUtils;//当前线程对象
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("第"+sheetNumber+"个sheet开始");
            List<Object> sheetList = null;
            if (totalSheetCount > 1) {
                if (numPage == 0) {
                    sheetList = list.subList((sheetNumber - 1) * excelSize, excelSize * sheetNumber);
                } else {
                    if (sheetNumber == totalSheetCount) {
                        sheetList = list.subList((sheetNumber - 1) * excelSize, list.size());
                    } else {
                        sheetList = list.subList((sheetNumber - 1) * excelSize, excelSize * (sheetNumber));
                    }
                }
            } else
                sheetList = list;
            //开始写入数据
            createWorkBook(sheetList);
            if (this.countDownLatch != null)
                this.countDownLatch.countDown();
            System.out.println("第"+sheetNumber+"个sheet  结束");
        }

        /***
         * 写出数据
         */
        private void createWorkBook(List<Object> sheetList) {
            Sheet sheet = null;
            Row row = null;
            synchronized (ThreadExcelUtils.class) {
                String fileName = threadExcelUtils.getFileName();
                Workbook wb = threadExcelUtils.getWb();
                sheet = wb.createSheet(fileName + "_" + this.sheetNumber);
                row = sheet.createRow(0);
            }
            String[] header = threadExcelUtils.getHearders();
            String[] fields = threadExcelUtils.getFields();
            //设置标题
            for (int i = 0; i < header.length; i++) {
                row.createCell(i).setCellValue(header[i]);
            }
            //开始写入数据
            if (sheetList != null && sheetList.size() > 0) {
                int dataLength = sheetList.size();
                for (int i = 0; i < dataLength; i++) {
                    Row row1 = sheet.createRow(i + 1);
                    Object obj = sheetList.get(i);
                    Map<String, Object> map = (obj instanceof Map) ? (Map<String, Object>) obj : beanToMap(obj);
                    int length = fields.length;
                    for (int j = 0; j < length; j++) {
                        String key = fields[j];
                        Object value = map.get(key);
//                        if ((StringUtil.isNotBlank(value))) {
                        if (!(org.springframework.util.StringUtils.isEmpty(value))) {
                            //不晓得 此处为啥有线程安全问题
                            synchronized (ThreadExcelUtils.class) {
                                if (value instanceof Date) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String format = sdf.format(value);
                                    row1.createCell(j).setCellValue(format);
                                } else {
                                    try {
                                        row1.createCell(j).setCellValue(value.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
