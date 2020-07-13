package com.wzl.chrome.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @author: wzl
 * @create: 2020/5/22
 * @description: 定时异步线程池化
 */
@Component
public class TaskAsynPool {

    @Autowired
    private Executor asynTaskServiceExecutor;

    @Async(value = "asynTaskServiceExecutor")   //指定线程池
    @Scheduled(cron = "0/10 * * * * ?")
    public void task1(){
        System.out.println(Thread.currentThread().getName()+"定时异步任务");
    }

    //将任务交给线程池管理
    public void execute(){
        //() -> {} 这就是lambda 简化的匿名内部类线程创建
        asynTaskServiceExecutor.execute(() -> {
            //方法体 可以写原生业务 也可以通过service调用业务
        });
    }
}
