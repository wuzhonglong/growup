package com.wzl.chrome.lambda;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wzl
 * @create: 2020/6/8
 * @description: 测试lambda各种优势
 */
@RestController
public class LambdaTest1 {
    //创建一个线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {

    }

    /**
     *@description: 测试取代匿名内部类
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public void test1(){
        //现在需要开辟一个线程 以前的写法
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("这是以前常用的写法");
            }
        });
        executorService.submit(() -> System.out.println("这是lambda写法，直接省略了接口名称和方法名"));
        executorService.submit(() ->{
            System.out.println("这儿可以调用其它的方法");
            System.out.println("因为这儿有多条语句 所以需要{}将代码块包起来");
        });

        List<String> list = Arrays.asList("hello","you","world");
        //这是之前常用的写法
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null){
                    return -1;
                }
                if (o2 == null){
                    return 1;
                }
                return o1.length() - o2.length();
            }
        });
        //这是lambda写法  省略了接口名和方法名 同时可以自动上下文判断类型
        list.sort((o1,o2) ->{
            if (o1 == null){
                return -1;
            }
            if (o2 == null){
                return 1;
            }
            return o1.length() - o2.length();
        });
    }
    /**
     *@description: 自定义函数接口  注意 函数接口有且仅有一个抽象方法
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public void test2(){
        MyFunctionIterface1 myFunctionIterface1 = (s) -> System.out.println("s:"+s);
        myFunctionIterface1.accept("你好");

        MyFunctionIterface2 myFunctionIterface2 = (a,b) -> {
            System.out.println("sum:"+a+b);
            return a+b;
        };
        int res = myFunctionIterface2.accept(10, 20);
        System.out.println("获取到返回值:"+res);
    }


    /**
     *@description:
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public void test3(){

    }
}
