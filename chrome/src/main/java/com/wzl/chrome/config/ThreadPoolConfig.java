package com.wzl.chrome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: wzl
 * @create: 2020/5/22
 * @description: 线程池配置
 * 什么情况下需要池化  任务需要异步执行  线程需要统一管理 然后降低资源消耗那些
 */
@EnableAsync    //异步线程池
@Configuration
public class ThreadPoolConfig {


    @Bean(name = "asynTaskServiceExecutor")
    public Executor asynTaskServiceExecutor(){


        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //当没有新任务时 超出核心线程数的线程存在时间
        executor.setKeepAliveSeconds(3);
        //线程前缀名
        executor.setThreadNamePrefix("async-task-service-");
        //队列长度  ThreadPoolTaskExecutor源码写死用的LinkedBlockingQueue无界阻塞队列 所以只需要传一个阻塞长度就行
        //当队列为空的时候 阻塞消费者  队列长度达到阻塞长度时 阻塞生产者
        executor.setQueueCapacity(1000);
        //拒绝策略 这个是默认的拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        /*executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor cc) {
                // list. 自定义的拒绝策略  一般很少用  基本都是作为日志输出
            }
        });*/
        //初始化加载
        executor.initialize();

        return executor;
    }
}
