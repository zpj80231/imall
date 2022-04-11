package com.imall.note.SourceCodeAnalysis;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;

/**
 * @className: com.zpj.electric.SourceCodeAnalysis.TestThreadPool
 * @descripe: 多线程的基本使用
 * @author: zpj
 * @date: 2019/7/8
 * @version: 1.0
 */
public class TestThreadPool {

    /*  打印结果：多个线程同时开始
        Thread:pool-1-thread-2 开始执行时间：2019-07-08 22:14:31
            ==  I'm a cat!  ==     <1>
        Thread:pool-1-thread-1 开始执行时间：2019-07-08 22:14:31
            ==  I'm a cat!  ==     <0>
        Thread:pool-1-thread-4 开始执行时间：2019-07-08 22:14:31
        Thread:pool-1-thread-3 开始执行时间：2019-07-08 22:14:31
            ==  I'm a cat!  ==     <2>
        Thread:pool-1-thread-5 开始执行时间：2019-07-08 22:14:31
            ==  I'm a cat!  ==     <4>
        Thread:pool-1-thread-4 开始执行时间：2019-07-08 22:14:31
            ==  I'm a cat!  ==     <5>
        Thread:pool-1-thread-4 开始执行时间：2019-07-08 22:14:32
            ==  I'm a cat!  ==     <6>
        Thread:pool-1-thread-5 开始执行时间：2019-07-08 22:14:32
            ==  I'm a cat!  ==     <7>
        Thread:pool-1-thread-3 开始执行时间：2019-07-08 22:14:32
        Thread:pool-1-thread-1 开始执行时间：2019-07-08 22:14:32
        Thread:pool-1-thread-3 开始执行时间：2019-07-08 22:14:32
            ==  I'm a cat!  ==     <9>
            ==  I'm a cat!  ==     <10>

     */
    public static void main(String[] args) throws InterruptedException {

        //要循环的次数
        int x = 10;

        //ExecutorService executorService = Executors.newFixedThreadPool(1);//单线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);//多线程

        //线程计数器
        CountDownLatch downLatch = new CountDownLatch(x);

        System.out.println("多线程执行开始！");
        for (int i = 0;i <= x;i++) {
            Future<?> future = executorService.submit(new Cat(i, downLatch));
            /*
            //加上它就变同步了，不知道为啥（百度咯，因为这是个阻塞的方法，future.isDone();是个非阻塞方法）
            try {
                if(future.get()==null){
                    System.out.println("第   "+i+"   个任务执行成功!\n");
                }
            } catch (ExecutionExceptionw e) {
                System.out.println("第   "+i+"   个任务执行失败!    " + e.getMessage() + "\n");
            }*/
        }
        System.out.println("这就证明：多线程是异步执行的！");
        downLatch.await();
        executorService.shutdown();
    }

   /* public static void main(String[] args) throws InterruptedException {

        //要循环的次数
        int x = 10;

        //ExecutorService executorService = Executors.newFixedThreadPool(1);//单线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);//多线程

        //线程计数器
        CountDownLatch downLatch = new CountDownLatch(x);

        for (int i = 0;i <= x;i++) {
            executorService.execute(new com.zpj.electric.SourceCodeAnalysis.Cat(i, downLatch));
        }
        *//*
         *  打印结果：可以看到多个线程基本同时开始执行
            Exception in thread "pool-1-thread-4" java.lang.ArithmeticException: / by zero
                at com.zpj.electric.SourceCodeAnalysis.Cat.run(com.zpj.electric.SourceCodeAnalysis.TestThreadPool.java:141)
                at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
                at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
                at java.lang.Thread.run(Thread.java:745)
            Thread:pool-1-thread-1 开始执行时间：2019-07-08 22:00:39
                ==  I'm a cat!  ==     <0>
            Thread:pool-1-thread-2 开始执行时间：2019-07-08 22:00:39
                ==  I'm a cat!  ==     <1>
            Thread:pool-1-thread-3 开始执行时间：2019-07-08 22:00:39
                ==  I'm a cat!  ==     <2>
            Thread:pool-1-thread-4 开始执行时间：2019-07-08 22:00:39
            Thread:pool-1-thread-5 开始执行时间：2019-07-08 22:00:39
                ==  I'm a cat!  ==     <4>
            Thread:pool-1-thread-6 开始执行时间：2019-07-08 22:00:39
                ==  I'm a cat!  ==     <5>
            Exception in thread "pool-1-thread-3" java.lang.ArithmeticException: / by zero
            Thread:pool-1-thread-1 开始执行时间：2019-07-08 22:00:40
                at com.zpj.electric.SourceCodeAnalysis.Cat.run(com.zpj.electric.SourceCodeAnalysis.TestThreadPool.java:141)
                ==  I'm a cat!  ==     <6>
                at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
                at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
            Thread:pool-1-thread-2 开始执行时间：2019-07-08 22:00:40
                at java.lang.Thread.run(Thread.java:745)
                ==  I'm a cat!  ==     <7>
            Thread:pool-1-thread-3 开始执行时间：2019-07-08 22:00:40
            Thread:pool-1-thread-6 开始执行时间：2019-07-08 22:00:40
                ==  I'm a cat!  ==     <10>
            Thread:pool-1-thread-5 开始执行时间：2019-07-08 22:00:40
                ==  I'm a cat!  ==     <9>
            *//*
        downLatch.await();
        executorService.shutdown();
    }*/

}

class Cat implements Runnable {

    int i;
    private CountDownLatch downLatch;

    public Cat(int i,CountDownLatch downLatch) {
        this.i = i;
        this.downLatch = downLatch;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void run() {
        System.out.println("Thread:"+Thread.currentThread().getName()+" 开始执行时间："+format.format(System.currentTimeMillis()));

        if(i == 8 || i == 3)
            i = i/0;
            System.out.println("    ==  I'm a cat!  ==     <" + i + ">");
        //每执行完一个线程 计数器就减1
        downLatch.countDown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
