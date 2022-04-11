package com.imall.note.util.pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.Map;
import java.util.UUID;


public class BasePDFWrite {

    public static final String path = "D:\\T1";// 生成文件的位置
    public static final String fontPath = "D:\\T1\\fonts";
    Document document = null;// 建立一个Document对象
    PdfWriter writer = null;
    private static Font headFont ;
    private static Font keyFont ;
    private static Font textfont_H ;
    private static Font textfont_B ;
//    int maxWidth = 520;
    static{
        BaseFont bfChinese_H;
        try {
            /**
             * 新建一个字体,iText的方法 STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
             * UniGB-UCS2-H 是编码，在iTextAsian.jar 中以cmap为后缀 H 代表文字版式是 横版， 相应的 V 代表竖版
             */
//            bfChinese_H = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            bfChinese_H = BaseFont.createFont(fontPath+"\\FZFSK.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

            headFont = new Font(bfChinese_H, 20, Font.BOLD);
            keyFont = new Font(bfChinese_H, 14, Font.BOLD);
            textfont_H = new Font(bfChinese_H, 16, Font.NORMAL);
            textfont_B = new Font(bfChinese_H, 14, Font.BOLD);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置页面属性
     * @param file
     */
    public BasePDFWrite(File file) {

        //自定义纸张
        //Rectangle rectPageSize = new Rectangle(350, 620);

        // 定义A4页面大小
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        document = new Document(rectPageSize,20, 20, 20, 20);
        //rectPageSize = rectPageSize.rotate();// 加上这句可以实现页面的横置
        //document = new Document(rectPageSize);

        try {
            writer = PdfWriter.getInstance(document,new FileOutputStream(file));

            // 设置页面布局 页眉页脚用
            writer.setViewerPreferences(PdfWriter.PageLayoutOneColumn);
            // 为这篇文档设置页面事件(X/Y) 页眉页脚页码用
            writer.setPageEvent(new PageXofYTest());

            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建表格(以列的数量建)
     * @param colNumber
     * @return
     */
    public PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);
        try{
            //table.setTotalWidth(maxWidth);
            //table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 建表格(以列的宽度比建)
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths){
        PdfPTable table = new PdfPTable(widths);
        try{
            //table.setTotalWidth(maxWidth);
            //table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }


    /**
     * 表格中单元格
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    /**
     * 表格中单元格
     * @param value
     * @param font
     * @param align_v
     * @param colspan
     * @param rowspan
     * @return
     */
    public PdfPCell createCell(String value,Font font,int align_v,int align_h,int colspan,int rowspan){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(align_v);
        cell.setHorizontalAlignment(align_h);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    /**
     * 建短语
     * @param value
     * @param font
     * @return
     */
    public Phrase createPhrase(String value,Font font){
        Phrase phrase = new Phrase();
        phrase.add(value);
        phrase.setFont(font);
        return phrase;
    }

    /**
     * 建段落
     * @param value
     * @param font
     * @param align
     * @return
     */
    public Paragraph createParagraph(String value,Font font,int align){
        Paragraph paragraph = new Paragraph();
        Phrase elements = new Phrase(value, font);
        paragraph.setLeading(25F);//设置行间距
        paragraph.setAlignment(align);
        //paragraph.setFirstLineIndent(32.0F); //首行缩进
        //paragraph.setIndentationLeft(90.0F); //段落左边距
        paragraph.setSpacingBefore(5F); //段前间距
        paragraph.setSpacingAfter(5F); //段后间距
        paragraph.add(elements);
        return paragraph;
    }


    public void generatePDF_table() throws Exception{

        //页头信息
        document.add(createParagraph("【XXXX有限公司】",headFont,Element.ALIGN_LEFT));
        document.add(createParagraph("签  购  单",keyFont,Element.ALIGN_CENTER));
        document.add(createParagraph("编号：XD201602000003",headFont,Element.ALIGN_RIGHT));

        //表格信息
        float[] widths = {4f,10f,10f,20f,15f,8f,11f,12f,10f};
        PdfPTable table = createTable(widths);

        table.addCell(createCell("顾客信息", textfont_H, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,4));

        table.addCell(createCell("顾客名称", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("XXXX有限公司", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,6,1));

        table.addCell(createCell("编码/税号", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,6,1));

        table.addCell(createCell("联系地址", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,6,1));

        table.addCell(createCell("采购经办人", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("经办人电话", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("经办人手机", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));


        table.addCell(createCell("产品订购清单", textfont_H, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,8));

        table.addCell(createCell("序号", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("产品信息", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2,1));
        table.addCell(createCell("规格型号", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("单位", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("单价", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("数量", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));
        table.addCell(createCell("小计", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,1));

        table.addCell(createCell("1", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("塑料纸", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("小号", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("个", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("400", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));

        table.addCell(createCell("2", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("塑料纸", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("小号", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("个", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("400", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));

        table.addCell(createCell("3", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("塑料纸", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("小号", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("个", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("400", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));



        table.addCell(createCell("4", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("合计", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("20", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("60", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));
        table.addCell(createCell("1200", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,1,1));


        table.addCell(createCell("合计金额（元）", textfont_B,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3,1));
        table.addCell(createCell("壹仟俩佰元", textfont_B,Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT,6,1));


        table.addCell(createCell("顾客确认", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,1,3));

        table.addCell(createCell("注：本人已充分了解订购产品的功能用途，是根据实际需要自愿购买的。", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,8,1));

        table.addCell(createCell("采购经办人签字：", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,6,1));
        table.addCell(createCell("盖章：", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,2,1));

        table.addCell(createCell("签字日期", textfont_H,Element.ALIGN_MIDDLE, Element.ALIGN_LEFT,8,1));

        document.add(table);
        document.close();
    }

    public void addImage(String imagePath){
        // 读图片
        try {
            Image image = Image.getInstance(imagePath);
            image.setAlignment(Image.ALIGN_CENTER);
            image.scaleAbsoluteWidth(500);//设置图片宽度
            image.scaleAbsoluteHeight(300);//设置图片高度
            document.add(image);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteFile(String fileName){
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

//    private static String binPath = "D:\\software\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";// 插件引入地址
//    private static final String JSpath = path+"\\echarts-convert1.js";
    private static final String JSpath = "D:/phantomjs/echarts-convert/echarts-convert1.js";
    public String generateEChart(String options, Map<String,Object> resultMap) {
        String dataPath = writeFile(options);
        String fileName= "test-"+UUID.randomUUID().toString().substring(0, 8) + ".png";
        String path1 = path+"\\"+fileName;
        try {
            File file = new File(path1);     //文件路径（路径+文件名）
            if (!file.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            String cmd = "phantomjs" + " " + JSpath + " -infile " + dataPath + " -outfile " + path1 + " -width 850 -height 450";
            System.out.println(cmd);
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
//                logger.info(line);
            }
            input.close();
            deleteFile(dataPath);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return path1;
        }
    }

    public static String writeFile(String options) {
        String dataPath = path+"\\" + UUID.randomUUID().toString().substring(0, 8) + ".json";
        try {
            /* 写入Txt文件 */
            File writename = new File(dataPath); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writename.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(writename.getParent());
                dir.mkdirs();
                writename.createNewFile(); // 创建新文件
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(options); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPath;
    }

    public void generatePDF_text() throws DocumentException, IOException {
        //首页
        document.add(createParagraph("",headFont,Element.ALIGN_CENTER));
        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);
        document.add(createParagraph("BORROWLISM - iText示例",headFont,Element.ALIGN_CENTER));
        document.add(createParagraph("2019年7月",headFont,Element.ALIGN_CENTER));

        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);

        document.add(createParagraph("ZPJ 80231",textfont_B,Element.ALIGN_CENTER));
        document.add(createParagraph("2019年7月20日",textfont_B,Element.ALIGN_CENTER));

        //第二页
        document.newPage();

PdfPTable pdfPTable = new PdfPTable(1);
Footer footerTable = new Footer(pdfPTable);
footerTable.setTableFooter(writer, textfont_H);//添加页脚

        document.add(createParagraph("      As food is to the body, so is learning to the mind. Our bodies grow and muscles develop with the intake of adequate nutritious food. Likewise, we should keep learning day by day to maintain our keen mental power and expand our intellectual capacity. Constant learning supplies us with inexhaustible fuel for driving us to sharpen our power of reasoning, analysis, and judgment. Learning incessantly is the surest way to keep pace with the times in the information age, and an infallible warrant of success in times of uncertainty.",textfont_H,Element.ALIGN_LEFT));
        document.add(createParagraph("      Lack of learning will inevitably lead to the stagnation of the mind, or even worse, its fossilization, Therefore, to stay mentally young, we have to take learning as a lifelong career.",textfont_H,Element.ALIGN_LEFT));
        document.add(Chunk.NEWLINE);
        document.add(createParagraph("ONE",keyFont,Element.ALIGN_LEFT));
        document.add(createParagraph("（一）ZHONGWENLAILE",textfont_B,Element.ALIGN_LEFT));
        document.add(new Chunk("      学习之于",textfont_H));

        //上标 字体可以设置小一点
        document.add(new Chunk("12",
                new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED), 10, Font.NORMAL)).setTextRise(8f));

        document.add(new Chunk(" '心灵' ",keyFont));
        document.add(new Chunk("就像食物之于身体一样。",textfont_H));
        document.add(new Chunk("摄取了适量的营养食物，我们的身体得以生长而肌肉得以发达。同样地，我们应该日复一日不断地学习以保持我们敏锐的心智能力，并扩充我们的智力容量。不断的学习提供我们用不尽的燃料，来驱使我们磨利我们的推理、分析和判断的能力。持续的学习是在信息时代中跟时代并驾齐驱的最稳当的方法，也是在变动的世代中成功的可靠保证。",textfont_H));
        document.add(Chunk.NEWLINE);document.add(Chunk.NEWLINE);

        //根据echar的option添加一个echart图片，设置图片的位置，大小
        //        addImage("D:\\idea_workspace\\mall\\mall-ZDemo\\src\\main\\java\\com\\zpj\\electric\\img\\图片1.png");
        String echarOption1 = "{\"color\":[\"#2E89C3\"],\"title\":{\"text\":\"echar demo\",\"left\":\"center\",\"top\":\"5%\",\"textStyle\":{\"fontSize\":\"18\",\"color\":\"black\"}},\"tooltip\":{},\"legend\":{\"data\":[\"告警类型\"],\"show\":false},\"xAxis\":{\"axisLabel\":{\"interval\":0},\"data\":[\"汇总\",\"上衣\",\"下衣\",\"左衣\",\"右衣\",\"裤子\",\"鞋子\"]},\"yAxis\":{\"min\":0},\"series\":[{\"name\":\"告警类型\",\"type\":\"bar\",\"data\":[7647,1473,6183,2079,3157,877,678],\"itemStyle\":{\"normal\":{\"label\":{\"color\":\"black\",\"show\":true,\"position\":\"inside\"}}}}]}";
//        String echarOption2 = "{\"color\":[\"#BD15DC\",\"#F32E49\",\"#F98909\",\"#2E89C3\"],\"title\":{\"text\":\"告警级别分布统计\",\"left\":\"center\",\"top\":\"3%\",\"textStyle\":{\"fontSize\":\"18\",\"color\":\"black\"}},\"tooltip\":{\"trigger\":\"axis\",\"axisPointer\":{\"type\":\"shadow\"},\"formatter\":\"function(params){             var result='';             var sysName='';             params.forEach(function (item) {                 if(sysName===''){sysName=item.name;}                 result+=item.marker+item.seriesName+' : '+item.data+'%</br>';             });             return sysName+'</br>'+result;        }\"},\"legend\":{\"data\":[\"极高\",\"高危\",\"中危\",\"低危\"],\"bottom\":\"1%\"},\"grid\":{\"left\":\"3%\",\"right\":\"4%\",\"bottom\":\"8%\",\"containLabel\":true},\"xAxis\":{\"type\":\"value\",\"min\":\"0%\",\"max\":\"100%\"},\"yAxis\":[{\"name\":\"\",\"type\":\"category\",\"data\":[\"95598运营管理系统\", \"95598业务支持系统\", \"95598呼叫平台\", \"乡镇供电所一体化\", \"计量全寿命周期管理\", \"供电服务品质评价\", \"营销移动业务应用\", \"国家电力需求侧管理\", \"市场化售电业务应用\", \"95598基础支持平台\", \"计量生产调度平台\", \"分布式电源运营管理\", \"一体化交费平台\", \"营销档案管理系统\", \"营销基础数据平台\", \"营销地理信息系统\", \"营销业务管理平台\", \"电能服务管理平台\", \"省公司自建系统\", \"营销业务应用系统\", \"营销辅助决策系统\", \"营销远程费控系统\", \"95598智能互动网站\", \"多表合一抄收系统\", \"国网电子商城\", \"用电信息采集系统\", \"掌上电力\", \"第三方支付平台\", \"电动汽车车联网服务平台\"],\"axisLabel\":{\"interval\":0}},{\"name\":\"总数\",\"position\":\"right\",\"type\":\"category\",\"data\":[109, 520, 2168, 7147, 2950, 2123, 10465, 434, 1953, 184, 2677, 87, 1018, 1939, 6106, 4732, 7707, 10078, 25398, 43663, 4522, 4719, 413, 813, 15790, 51756, 9985, 9465, 12928],\"nameLocation\":\"end\",\"nameGap\":\"15\",\"axisLabel\":{\"interval\":0}}],\"series\":[{\"name\":\"极高\",\"type\":\"bar\",\"stack\":\"总量\",\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"position\":\"inside\",\"color\":\"black\",\"formatter\":\"function (params) {                            if (params.value > 5) {                                return params.value+'%';                            } else {                                return '';                            }                        }\"}}},\"data\":[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]},{\"name\":\"高危\",\"type\":\"bar\",\"stack\":\"总量\",\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"position\":\"inside\",\"color\":\"black\",\"formatter\":\"function (params) {                            if (params.value > 5) {                                return params.value+'%';                            } else {                                return '';                            }                        }\"}}},\"data\":[0.0, 0.2, 0.28, 0.69, 1.33, 1.7, 1.75, 2.08, 2.57, 2.72, 5.5, 5.75, 5.8, 6.04, 6.08, 6.94, 10.0, 11.44, 11.75, 15.89, 17.48, 21.58, 23.25, 27.8, 29.41, 43.32, 44.12, 62.83, 82.96]},{\"name\":\"中危\",\"type\":\"bar\",\"stack\":\"总量\",\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"position\":\"inside\",\"color\":\"black\",\"formatter\":\"function (params) {                            if (params.value > 5) {                                return params.value+'%';                            } else {                                return '';                            }                        }\"}}},\"data\":[0.92, 1.16, 11.72, 10.82, 10.24, 25.35, 57.91, 51.16, 20.18, 1.09, 7.48, 4.6, 49.22, 8.57, 20.0, 13.32, 23.72, 22.93, 19.83, 49.36, 39.35, 12.89, 38.02, 19.19, 26.97, 25.11, 19.48, 29.29, 6.92]},{\"name\":\"低危\",\"type\":\"bar\",\"stack\":\"总量\",\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"position\":\"inside\",\"color\":\"black\",\"formatter\":\"function (params) {                            if (params.value > 5) {                                return params.value+'%';                            } else {                                return '';                            }                        }\"}}},\"data\":[99.09, 98.66, 88.01, 88.5, 88.45, 72.97, 40.35, 46.78, 77.27, 96.2, 87.04, 89.66, 45.0, 85.41, 73.93, 79.76, 66.3, 65.65, 68.43, 34.77, 43.19, 65.55, 38.75, 53.02, 43.63, 31.59, 36.41, 7.9, 10.13]}]}";
        String eChart1 = generateEChart(echarOption1, null);
//        String eChart2 = generateEChart(echarOption2, null);
        addImage(eChart1);
//        addImage(eChart2);

        deleteFile(eChart1);
//        deleteFile(eChart2);

        /*
        * 在页面指定位置插入
        * */
        writer.setCompressionLevel(0);
        Phrase hello = new Phrase("zpj 80231",headFont);
        PdfContentByte canvas = writer.getDirectContentUnder();
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                hello, 520, 820,    -45);

        document.newPage();
        //document.close();
    }

    public static void main(String[] args) throws Exception {
        // File file = new File(path + "\\T1_font.pdf");
        File file = new File("imall-note/src/main/resources/static/T1_pdf_test.pdf");
        if (!file.exists()) {   //文件不存在则创建文件，先创建目录
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();
        }
        //file.createNewFile();
        BasePDFWrite basePDFWrite = new BasePDFWrite(file);
        basePDFWrite.generatePDF_text();
        basePDFWrite.generatePDF_table();
    }
}


