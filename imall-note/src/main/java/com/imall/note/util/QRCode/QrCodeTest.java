package com.imall.note.util.QRCode;

import java.util.UUID;

public class QrCodeTest {

    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "https://zpj80231.gitee.io/znote/";
        //生成的文件名字
        String fileName = UUID.randomUUID().toString().replaceAll("-","");
        // 嵌入二维码的图片路径
        String imgPath = "D:/logo.png";
        // 生成的二维码的路径及名称
        String destPath = "D:/"+ fileName +".jpg";
        //生成二维码
        QRCodeUtil.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);

    }

}
