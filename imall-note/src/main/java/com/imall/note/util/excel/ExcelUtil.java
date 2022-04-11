package com.imall.note.util.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExcelUtil {


	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static String EXCE_VERSION_03 = "03";
	private final static String EXCE_VERSION_07 = "07";

	/**
	 *
	 * <P>(excel导出方法)</P>
	 * @param resp
	 * @param fileName
	 *            文件名，一级表头
	 * @param useFirstTitle
	 *            是否需要一级表头，true：是，false：否
	 * @param version
	 *            导出版本，可选值为：ExcelUtil.EXCE_VERSION_03或ExcelUtil.EXCE_VERSION_07
	 * @param titles
	 *            excel列名
	 * @param data
	 *            需要导出的数据集合
	 */
	public static void exportExcel(HttpServletResponse resp, String fileName,
			boolean useFirstTitle, String version, String[] titles,
			List<Object[]> data) {
		// 如果传入的文件名为空，则使用当前时间作为文件名
		String excelFileName = "";
		if ("".equals(fileName) || fileName == null) {
			excelFileName = sdf.format(new Date());
		}
		// (data.size() >= 0) 当列表中没有数据时，导出的excel加上标题等。刘玉梅 2015年8月24日
		if (titles.length >= 0) {
			try {
				if (EXCE_VERSION_03.equals(version)) {
					excelFileName = fileName + ".xls";
				} else if (EXCE_VERSION_07.equals(version)) {
					excelFileName = fileName + ".xlsx";
				}

				resp.setContentType("application/x-msdownload");
				excelFileName = new String(excelFileName.getBytes("gb2312"), "iso8859-1");
				resp.addHeader("Content-Disposition", "attachment;filename="
						+ excelFileName);
				OutputStream out = resp.getOutputStream();
				if (EXCE_VERSION_03.equals(version)) {
					exprotExcel_03(fileName, titles, useFirstTitle, data, out);
				} else if (EXCE_VERSION_07.equals(version)) {
//					exprotExcel_07(fileName, titles, useFirstTitle, data, out);
					exprotExcel_07_plus(excelFileName, titles, useFirstTitle, data, out);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	private static void exprotExcel_03(String fileName, String[] titles,
			boolean useFirstTitle, List<Object[]> data, OutputStream out) {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建一sheet页
		HSSFSheet sheet = wb.createSheet("sheet");
		// 设置格式 在单元格中右排放
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		int firstRow = 0;
		if (useFirstTitle) {
			HSSFRow fr = sheet.createRow(firstRow);
			HSSFFont font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 16);
			HSSFCellStyle cs = wb.createCellStyle();
			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cs.setFont(font);
			fr.setHeight((short) 700);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
					data.get(0).length-1));//excel中的大标题合并的单元格多一列
			for (int i = 0; i < titles.length; i++) {//excel 当列表中数据为空时，加上标题等的格式
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
							sheet.setColumnWidth(i, (short)50*(50+contentLength));  //初始列宽+自适应宽度
						}else{
							sheet.setColumnWidth(i, (short)100*(55+contentLength));  //初始列宽+自适应宽度
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
			HSSFCellStyle alignStyle = wb.createCellStyle();
			 alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			 alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			 alignStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			 alignStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			 alignStyle.setFont(font);
			fr.getCell(0).setCellValue(new HSSFRichTextString(fileName));
			fr.getCell(0).setCellStyle(alignStyle);


			firstRow += 1;
		}
		HSSFRow row = sheet.createRow(firstRow);

		for (int i = 0; i < titles.length; i++) {

			row.createCell(i).setCellValue(new HSSFRichTextString(titles[i]));

		}
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		try {
			for (int i = 0; i < data.size(); i++) {
				HSSFRow r = sheet.createRow(i + firstRow + 1);
				Object[] obj = data.get(i);
				for (int j = 0; j < obj.length; j++) {
					// 创建单元格
					HSSFCell cell = r.createCell(j);
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
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, i, (short) 6, i);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, wb.addPicture(bsValue,
								HSSFWorkbook.PICTURE_TYPE_JPEG));
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
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							cell.setCellValue(richString);
						}
					} else {// 若果是空值，单元格显示为空
						cell.setCellValue(new HSSFRichTextString(""));
					}

				}
			}
			wb.write(out);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			IOUtils.closeQuietly(out);
		}

	}

	private static void exprotExcel_07_plus(String fileName, String[] titles,
			boolean useFirstTitle, List<Object[]> data, OutputStream out) {
		XSSFWorkbook wb = new XSSFWorkbook();

		//把要下载的数据分为多个sheet下载
		List<List<Object[]>> dataList = createList(data, 50000);
		Long start = System.currentTimeMillis();
		System.out.println("下载开始时间：" + start);

		//使用多线程下载
		ExecutorService es = Executors.newFixedThreadPool(dataList.size());
		//使用计步器
		// final CountDownLatch doneSignal = new CountDownLatch(dataList.size());
		CountDownLatch doneSignal = new CountDownLatch(dataList.size());

		try {

		for(int x = 0;x<dataList.size();x++){
			PoiWriter poiWriter = new PoiWriter(x, wb, fileName, titles, data, dataList, useFirstTitle, doneSignal);
			Future future = es.submit(poiWriter);
			 /*try {
				 	//如果Future.get()返回null，任务完成
		            if(future.get()==null){
		                System.out.println("第"+(x+1)+"个sheet页，任务完成");
		            }
		        } catch (InterruptedException e) {
		        } catch (ExecutionException e) {
		            //失败
		            System.out.println("第"+(x+1)+"个sheet页，任务失败："+e.getCause().getMessage());
		        }*/
			//new PoiWriter(x, wb, fileName, titles, data, dataList, useFirstTitle, null).run();
		}
		doneSignal.await();//阻塞，直到计数器的值为0，才让主线程往下执行
	    es.shutdown();//关闭线程池
		Long end = System.currentTimeMillis();
		System.out.println("总计下载时间：" + ((end-start)/1000) + "s");
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			IOUtils.closeQuietly(out);
		}

	}


	 public static List<List<Object[]>>  createList(List<Object[]> targe,int size) {
		        List<List<Object[]>> listArr = new ArrayList<List<Object[]>>();
		        //获取被拆分的数组个数
		        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		        for(int i=0;i<arrSize;i++) {
		            List<Object[]>  sub = new ArrayList<Object[]>();
		            //把指定索引数据放入到list中
		            for(int j=i*size;j<=size*(i+1)-1;j++) {
		                if(j<=targe.size()-1) {
		                	//得到拆分后的集合
		                    sub.add(targe.get(j));
		                }
		            }
		            //拆分的集合可以做点什么
		            //sub.dosomething();
		            //将拆分后的集合综合为一个集合
		            listArr.add(sub);
		        }
		        return listArr;
	 }



}
