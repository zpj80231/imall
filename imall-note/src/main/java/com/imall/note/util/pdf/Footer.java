package com.imall.note.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by admin on 2020/3/5.
 */ //页脚事件
class Footer extends PdfPageEventHelper {
    public static PdfPTable footer;

    @SuppressWarnings("static-access")
    public Footer(PdfPTable footer) {
        this.footer = footer;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        //把页脚表格定位
        footer.writeSelectedRows(0, -1, 38, 50, writer.getDirectContent());
    }

    /**
     * 页脚是图片
     * @param writer
     * @throws MalformedURLException
     * @throws IOException
     * @throws DocumentException
     */
    public void setTableFooter(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
        String imageAddress = "E://TESTPDF/";
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(523);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(1);
        Image image01;
        image01 = Image.getInstance(imageAddress + "testfooter.png"); //图片自己传
        image01.scaleAbsoluteWidth(523);
        image01.scaleAbsoluteHeight(30f);
        image01.setWidthPercentage(100);
        cell.addElement(image01);
        table.addCell(cell);
        Footer event = new Footer(table);
        writer.setPageEvent(event);
    }

    /**
     * 页脚是文字
     * @param writer
     * @param songti09
     */
    public void setTableFooter(PdfWriter writer, Font songti09) {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(520f);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(1);
        String string = "地址:  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX         网址:  www.xxxxxxx.com       咨询热线:  400x-xxx-xxx";
        Paragraph p = new Paragraph(string, songti09);
        cell.setPaddingLeft(10f);
        cell.setPaddingTop(-2f);
        cell.addElement(p);
        table.addCell(cell);
        Footer event = new Footer(table);
        writer.setPageEvent(event);
    }
}
