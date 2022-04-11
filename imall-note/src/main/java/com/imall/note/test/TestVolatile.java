package com.imall.note.test;

import lombok.Data;

public class TestVolatile {
    public static void main(String[] args){ //这个线程是用来读取flag的值的
        ThreadDemo threadDemo = new ThreadDemo();
        Thread thread = new Thread(threadDemo);
        thread.start();
        while (true){
            // System.out.println("主线程读取到的threadDemo.isFlag() = " + threadDemo.isFlag());
            if (threadDemo.isFlag()){
                System.out.println("加volatile 主线程打印在前 读取到的flag = " + threadDemo.isFlag());
                break;
            }
        }
    }
}

@Data
class ThreadDemo implements Runnable{ //这个线程是用来修改flag的值的
   public boolean flag = false;

    //加volatile保证flag在主线程的可见性
    // public  volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("ThreadDemo线程修改后的flag = " + isFlag());
    }
}
