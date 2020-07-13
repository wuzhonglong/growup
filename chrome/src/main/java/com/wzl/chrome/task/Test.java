package com.wzl.chrome.task;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * @author: wzl
 * @create: 2020/4/29
 * @description:
 */
@Slf4j
@Component
public class Test {

//    @Autowired
//    private BeansUtil beansUtil;

    @Value("${wifi.server}")
    String server;

    private WebDriver driver = null;

    //@PostConstruct
    public void open(){
        // TODO windows 下开启
        //System.setProperty("webdriver.chrome.driver","E:\\chromedriver\\chromedriver.exe");
        // TODO Linux 下开启
        ChromeOptions options=new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        try {
            // TODO Linux 下开启
            driver  = new RemoteWebDriver(new URL("http://localhost:9515"), options);
            // TODO windows 下开启
            //driver = new ChromeDriver();
            login();
        }catch (Exception e){
            e.printStackTrace();
            log.info("登陆获取cookie时发生异常，驱动关闭");
            driver.close();
        }
    }

    /**
     *@description: 打开WiFi并登陆
     *@author: wzl
     *@createTime: 2020/4/28
     */
    public void login() throws InterruptedException {

    }

    public void quit(){
        driver.quit();
        log.info("浏览器已关闭");
    }
}
