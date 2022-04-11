package com.imall.note.util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 2020/3/5.
 */ //页码事件
class PageXofYTest extends PdfPageEventHelper {
    public PdfTemplate total;

    public BaseFont bfChinese;

    /**
     * 重写PdfPageEventHelper中的onOpenDocument方法
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        // 得到文档的内容并为该内容新建一个模板
        total = writer.getDirectContent().createTemplate(500, 500);
        try {

            String prefixFont = "";
            String os = System.getProperties().getProperty("os.name");
            if(os.startsWith("win") || os.startsWith("Win")){
                prefixFont = "C:\\Windows\\Fonts" + File.separator;
            }else {
                prefixFont = "/usr/share/fonts/chinese" + File.separator;
            }

            // 设置字体对象为Windows系统默认的字体
            bfChinese = BaseFont.createFont(prefixFont + "simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /**
     * 重写PdfPageEventHelper中的onEndPage方法
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // 新建获得用户页面文本和图片内容位置的对象
        PdfContentByte pdfContentByte = writer.getDirectContent();
        // 保存图形状态
        pdfContentByte.saveState();
        String text = "";
        text = (writer.getPageNumber()) + "/";
//        if(writer.getPageNumber()>1){//不算封面
//            text = (writer.getPageNumber()-1) + "";
//        }

        // 获取点字符串的宽度
        float textSize = bfChinese.getWidthPoint(text, 9);
        pdfContentByte.beginText();
        // 设置随后的文本内容写作的字体和字号
        pdfContentByte.setFontAndSize(bfChinese, 9);

        // 定位'X/'
        float x = (document.right() + document.left()) / 2;
        float y = 56f;
        pdfContentByte.setTextMatrix(x, y);
        pdfContentByte.showText(text);
        pdfContentByte.endText();

        // 将模板加入到内容（content）中- // 定位'Y'
        pdfContentByte.addTemplate(total, x + textSize, y);

        pdfContentByte.restoreState();
    }

    /**
     * 重写PdfPageEventHelper中的onCloseDocument方法
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        total.beginText();
        try {
            String prefixFont = "";
            String os = System.getProperties().getProperty("os.name");
            if(os.startsWith("win") || os.startsWith("Win")){
                prefixFont = "C:\\Windows\\Fonts" + File.separator;
            }else {
                prefixFont = "/usr/share/fonts/chinese" + File.separator;
            }

            bfChinese = BaseFont.createFont(prefixFont + "simsun.ttc,0",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            total.setFontAndSize(bfChinese, 9);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        total.setTextMatrix(0, 0);
        // 设置总页数的值到模板上，并应用到每个界面
        total.showText(String.valueOf(writer.getPageNumber() - 1));
        total.endText();
    }

//    @Override
//    public void onCloseDocument(PdfWriter writer, Document document) {
//        total.beginText();
//        total.setFontAndSize(bfChinese, 9);
//        total.setTextMatrix(0, 0);
//        total.endText();
//    }
}
