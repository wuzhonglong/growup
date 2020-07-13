package com.wzl.chrome.lambda;

/**
 * @author: wzl
 * @create: 2020/6/8
 * @description: 自定义函数接口
 */
@FunctionalInterface    //这个注解可以不写 写了就是编译器会自动检查该接口是否符合函数接口规范
public interface MyFunctionIterface1 {
    //带参数 无返回值
    void accept(String s);
}
