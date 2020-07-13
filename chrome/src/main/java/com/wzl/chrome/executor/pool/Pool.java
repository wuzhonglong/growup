package com.wzl.chrome.executor.pool;

import java.util.concurrent.*;

/**
 * @author: wzl
 * @create: 2020/6/16
 * @description: 线程池
 */
public class Pool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService service = Executors.newFixedThreadPool(5);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 1000, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10000));


    }
}
