package com.wzl.chrome.wifi.controller;

import com.wzl.chrome.wifi.service.WifiSeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wzl
 * @create: 2020/6/30
 * @description: 测试驱动
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private WifiSeleniumService service;
    @GetMapping("test")
    public void test(){
        System.out.println("接口调用成功****************");
        service.open();
    }
}
