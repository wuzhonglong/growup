package com.wzl.chrome.lambda;

/**
 * @author: wzl
 * @create: 2020/6/8
 * @description: 自定义函数接口
 */
@FunctionalInterface
public interface MyFunctionIterface2 {
    //带参数 并有返回
    int accept(int a,int b);
}
