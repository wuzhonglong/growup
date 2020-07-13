package com.wzl.chrome.wifi.service.impl;

import com.wzl.chrome.wifi.service.WifiSeleniumService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * @author: wzl
 * @create: 2020/4/25
 * @description:
 */
@Slf4j
@Service
public class WifiSeleniumServiceImpl implements WifiSeleniumService {

//    @Autowired
//    private BeansUtil beansUtil;

    @Value("${wifi.server}")
    String server;

    @Value("${wifi.username}")
    String name;

    @Value("${wifi.password}")
    String pass;

    @Value("${wifi.env}")
    String env;

    @Value("${wifi.windows.path}")
    String path;

    @Value("${wifi.linux.linuxDriverUrl}")
    String linuxDriverUrl;

    private WebDriver driver = null;

    @Override
    public void open(){
        if("linux".equals(env)){
            ChromeOptions options=new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("--disable-extensions");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            try {
                driver  = new RemoteWebDriver(new URL(linuxDriverUrl), options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if("windows".equals(env)){
            System.setProperty("webdriver.chrome.driver",path);
            driver = new ChromeDriver();
        }
        try {
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
        System.out.println("打开驱动了********************************");
        driver.get(server);
        Thread.sleep(3000);
        WebElement cancel = driver.findElement(By.className("margin-l-sm"));
        cancel.click();
        Thread.sleep(1000);
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(name);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(pass);
        WebElement loginIn = driver.findElement(By.id("login"));
        loginIn.click();
        Thread.sleep(20000);
        WebElement AP = driver.findElement(By.xpath("//*[@id=\"rui_menu\"]/ul/li[2]/a/span[1]"));
        AP.click();
        Thread.sleep(1000);
        WebElement ApList = driver.findElement(By.xpath("//*[@id=\"rui_menu\"]/ul/li[2]/ul/li[1]/a"));
        ApList.click();
        Thread.sleep(3000);
        Set<Cookie> cookies = driver.manage().getCookies();
        StringBuilder sb = new StringBuilder();
        for (Cookie coo : cookies) {
            String name = coo.getName();
            String value = coo.getValue();
            sb.append(name).append("=").append(value).append(";");
        }
        String cooStr = sb.toString();
        String token = cooStr.substring(0, cooStr.length() - 1);
//        ServletContext context = beansUtil.getBean(ServletContext.class);
//        context.setAttribute("token",token);
    }

    @Override
    public void quit(){
        driver.close();
    }
}
