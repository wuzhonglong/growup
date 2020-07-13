package com.wzl.chrome.executor.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

/**
 * @author: wzl
 * @create: 2020/6/15
 * @description: 实现线程的几种方式
 */
public class CreatThreadHandler {
    public static void main(String[] args) throws Exception {
//        threadDemo1();
//        threadDemo2();
//        threadDemo3();
        threadDemo4();
    }
    // 一、继承Thread类
    public static void threadDemo1() throws InterruptedException {
        System.out.println("主线程:"+Thread.currentThread().getName()+"开始");
        new Thread(new ThreadDemo1()).start();
        sleep(2000);
        System.out.println("主线程:"+Thread.currentThread().getName()+"结束");
    }

    // 二、实现Runable接口
    public static void threadDemo2() {
        System.out.println("主线程:"+Thread.currentThread().getName()+"开始");
        new Thread(() -> {
            System.out.println("通过实现Runable接口创建线程");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }).start();
        System.out.println("主线程:"+Thread.currentThread().getName()+"结束");
    }

    // 三、通过Callable和FutureTask创建
    public static void threadDemo3() throws Exception {
        System.out.println("主线程:"+Thread.currentThread().getName()+"开始");
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("通过实现Callable接口 重写call() 方法 创建接口");
            sleep(2000);
            return "可以返回值";
        });
        //这儿可以获取值不  还没启动的时候
        new Thread(futureTask).start();
        System.out.println("获取返回值:"+futureTask.get());
        System.out.println("主线程:"+Thread.currentThread().getName()+"结束");
    }

    // 四、通过线程池创建 注意 executorService 和 callable 都是属于 executor 框架 都能又返回值
    // executor怎么获取返回值 放到下章线程池详解中讲解
    public static void threadDemo4(){
        System.out.println("主线程:"+Thread.currentThread().getName()+"开始");
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10 ; i++) {
            // 创建线程并提交到线程池中执行
            executorService.execute(() ->{
                System.out.println("通过线程池创建线程池:"+Thread.currentThread().getName());
            });
        }
        // 关闭线程池
        executorService.shutdown();
        System.out.println("主线程:"+Thread.currentThread().getName()+"结束");
    }


    static class ThreadDemo1 extends Thread {
        @Override
        public void run() {
            System.out.println("通过继承Thread创建线程");
            System.out.println(Thread.currentThread().getName());
        }
    }
}
