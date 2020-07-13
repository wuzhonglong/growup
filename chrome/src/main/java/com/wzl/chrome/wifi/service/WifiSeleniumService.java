package com.wzl.chrome.wifi.service;

/**
 * @author: wzl
 * @create: 2020/4/25
 * @description: 测试selenium
 */
public interface WifiSeleniumService {
    /**
     *@description: 登陆WIFI 获取cookie
     *@author: wzl
     *@createTime: 2020/4/30
     */
    void open();
    /**
     *@description: 关闭chrome
     *@author: wzl
     *@createTime: 2020/4/28
     */
    void quit();
}
