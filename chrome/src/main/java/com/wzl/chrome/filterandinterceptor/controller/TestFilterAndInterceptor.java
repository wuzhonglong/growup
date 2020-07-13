package com.wzl.chrome.filterandinterceptor.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wzl
 * @create: 2020/7/3
 * @description: 测试过滤器和拦截器
 */
@RestController
@RequestMapping("/test/filterandinterceptor")
public class TestFilterAndInterceptor {
    @GetMapping("/filter")
    public void filter(){
        System.out.println("测试过滤");
    }
    @GetMapping("/interceptor")
    public void interceptor(){
        System.out.println("测试拦截");
    }
}
