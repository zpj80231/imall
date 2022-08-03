package com.imall.note.rabbitMq;

import java.io.IOException;

/**
 * @author zhangpengjun
 */
public class TestConsumer {
    public static void main(String[] args) throws IOException {
        //测试公平分发
//        Consumer recv1 = new Consumer("A",500);
//        recv1.recv_2();
//
//        Consumer recv2_2 = new Consumer("B",1000);
//        recv2_2.recv_2();

        for (int i = 0; i < 10; i++) {
            Consumer consumer = new Consumer("【"+i+"】",0);
            consumer.recv_2();
        }
    }
}

