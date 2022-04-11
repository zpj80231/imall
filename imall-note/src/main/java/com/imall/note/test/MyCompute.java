package com.imall.note.test;

import javax.swing.*;
import java.awt.*;

public class MyCompute {
    public static void main(String[] args) {
        JFrame jf = new JFrame("计算器"); //创建一个窗口
        JTextField jtf = new JTextField("0",20); //初始的文本为0，长度是20
        jf.add(jtf,BorderLayout.NORTH);
        String[] lab = {"CE","C","+/-","BackS","7","8","9","+","4","5","6",
            "-","1","2","3","*","0",".","=","/"}; //按键上的文本
        JPanel jp = new JPanel(); //创建面板
        GridLayout gl = new GridLayout(5,4); //创建网格布局
        jp.setLayout(gl); //将面板的布局方式改为网格布局
        for(int i=0;i<lab.length;i++){
            JButton jb = new JButton(lab[i]); //创建按钮
            jp.add(jb);//将创建出来的按钮都放入面板
        }
        jf.add(jp);//将面板放入窗口
        jf.pack(); //自动调节大小
        jf.setResizable(false);//不能改变窗口的大小
        jf.setLocation(300,200); // 设置初始的位置
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭
        jf.setVisible(true); //解决了初始不可见的问题
    }
}
