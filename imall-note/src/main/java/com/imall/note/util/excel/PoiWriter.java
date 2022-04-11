package com.imall.note.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoiWriter implements Runnable {

	public int x;//第几个sheet页
	public XSSFWorkbook wb;//poi Workbook
	public String fileName;//要导出的文件名字(含后缀)
	public String[] titles;//标题
	public List<Object[]> data;//全部数据
	public List<List<Object[]>> dataList;//包含了各个sheet页的数据
	public boolean useFirstTitle;//是否需要表头
	public  CountDownLatch doneSignal;//多线程栅栏计步器


	public PoiWriter(int x, XSSFWorkbook wb, String fileName, String[] titles,
			List<Object[]> data, List<List<Object[]>> dataList,
			boolean useFirstTitle, CountDownLatch doneSignal) {
		super();
		this.x = x;
		this.wb = wb;
		this.fileName = fileName;
		this.titles = titles;
		this.data = data;
		this.dataList = dataList;
		this.useFirstTitle = useFirstTitle;
		this.doneSignal = doneSignal;
	}

	public PoiWriter() {

	}

	@Override
	public void run() {
		Long start1 = System.currentTimeMillis();
		System.out.println(fileName+"：第"+(x+1)+"个sheet开始时间：" + start1);
		// 创建一sheet页
		XSSFSheet sheet = wb.createSheet("sheet"+x);
		// 设置格式 在单元格中右排放
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

		int firstRow = 0;
		if (useFirstTitle) {
			XSSFRow fr = sheet.createRow(firstRow);
			XSSFFont font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 16);
			XSSFCellStyle cs = wb.createCellStyle();
			cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			cs.setFont(font);
			fr.setHeight((short) 700);
			fr.setHeight((short) 700);
			//excel大标题和并列多了一列，当列表中数据为空时，导出的excel中加上标题等信息 2015年8月24日
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
					titles.length-1));
			for (int i = 0; i < titles.length; i++) {
				fr.createCell(i);
			}
			/**
			 * 自适应宽度
			 */
			if(null!=data&&data.size()>0){
				Object[] obj=data.get(0);
				if(obj.length==titles.length){
					for(int i=0;i<titles.length;i++)
					{
						int contentLength=titles[i].length();
						if(null!=obj[i]&&obj[i].toString().length()>titles[i].length()){
							contentLength=obj[i].toString().length();
						}
						if(i==0)
						{
							//The maximum column width for an individual cell is 255 characters. 的解决方案
							if((short)50*(50+contentLength)/256>255){
								sheet.setColumnWidth(i, (short)254*256);  //初始列宽+自适应宽度
							}else{
								sheet.setColumnWidth(i, (short)50*(50+contentLength));  //初始列宽+自适应宽度
							}
						}else{
							//The maximum column width for an individual cell is 255 characters. 的解决方案
							if((short)100*(55+contentLength)/256>255){
								sheet.setColumnWidth(i, (short)254*256);  //初始列宽+自适应宽度
							}else{
								sheet.setColumnWidth(i, (short)100*(55+contentLength));  //初始列宽+自适应宽度
							}
						}
					}
				}else{
					for(int i=0;i<titles.length;i++)
					{
						int contentLength=titles[i].length();
						if(i==0)
						{
							sheet.setColumnWidth(i, (short)50*(50+contentLength));  //初始列宽+自适应宽度
						}else{
							sheet.setColumnWidth(i, (short)100*(55+contentLength));  //初始列宽+自适应宽度
						}
					}

				}

			}

			//创建新的样式给列表的大标题加上背景色
			 XSSFCellStyle alignStyle = (XSSFCellStyle)wb.createCellStyle();
			 alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			 alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			 alignStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			 alignStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			 alignStyle.setFont(font);
			fr.getCell(0).setCellValue(new XSSFRichTextString(fileName));
			fr.getCell(0).setCellStyle(alignStyle);
			firstRow += 1;
		}

		XSSFRow row = sheet.createRow(firstRow);
		for (int i = 0; i < titles.length; i++) {
			row.createCell(i).setCellValue(new XSSFRichTextString(titles[i]));
		}


			for (int i = 0; i < dataList.get(x).size(); i++) {
				XSSFRow r = sheet.createRow(i + firstRow + 1);
				Object[] obj = dataList.get(x).get(i);
				for (int j = 0; j < obj.length; j++) {
					// 创建单元格
					XSSFCell cell = r.createCell(j);
					Object value = obj[j];
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yy-MM-dd HH:mm:ss");
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;

					} else {
						if (value == null) {
							// 如果值为空，什么都不做
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							XSSFRichTextString richString = new XSSFRichTextString(
									textValue);
							cell.setCellValue(richString);
						}
					} else {// 若果是空值，单元格显示为空
						cell.setCellValue(new XSSFRichTextString(""));
					}
				}
			}

			Long end1 = System.currentTimeMillis();
			System.out.println("第"+(x+1)+"个sheet总计时间：" +((end1-start1)/1000) + "s");
			if(doneSignal!=null){
				doneSignal.countDown(); // 线程计数-1
				System.out.println("第" + (x+1) + "个sheet创建完成，" + "计数器减1");
			}
	}

}
