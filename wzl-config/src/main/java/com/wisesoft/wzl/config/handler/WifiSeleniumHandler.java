package com.wisesoft.wzl.config.handler;

import com.wisesoft.wzl.config.utils.BeansUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: wzl
 * @create: 2020/4/26
 * @description:
 */
@Slf4j
@Component
public class WifiSeleniumHandler {

    @Autowired
    private BeansUtil beansUtil;

    @Value("${wifi.server}")
    String server;

    private WebDriver driver = null;

    //@PostConstruct
    public void open(){
        // TODO 需要研究下 Linux环境的驱动
        System.setProperty("webdriver.chrome.driver","E:\\chromedriver\\chromedriver.exe");
        //driver = new ChromeDriver();

        /*ChromeOptions options=new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");*/
        try {
            //driver  = new RemoteWebDriver(new URL("http://localhost:9515"), options);
            driver = new ChromeDriver();
            login();
            getCookie();
        }catch (Exception e){
            driver.quit();
        }
    }

    /**
     *@description: 打开WiFi并登陆
     *@author: wzl
     *@createTime: 2020/4/28
     */
    public void login(){
        driver.get(server);
        log.info("打开浏览器");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement cancel = driver.findElement(By.className("margin-l-sm"));
        cancel.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("admin");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("adminkst");
        WebElement loginIn = driver.findElement(By.id("login"));
        loginIn.click();
        log.info("登陆");
    }
    /**
     *@description: 获取存储cookie
     *@author: wzl
     *@createTime: 2020/4/28
     */
    public void getCookie(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement> elements = driver.findElements(By.className("rui-nav-text"));
        elements.get(3).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        elements.get(4).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);
        StringBuilder sb = new StringBuilder();
        for (Cookie coo : cookies) {
            String name = coo.getName();
            String value = coo.getValue();
            sb.append(name).append("=").append(value).append(";");
        }
        String cooStr = sb.toString();
        String token = cooStr.substring(0, cooStr.length() - 1);
        ServletContext context = beansUtil.getBean(ServletContext.class);
        context.setAttribute("token",token);
        log.info("获取到token:{}",token);
    }

    public void quit(){
        driver.quit();
        log.info("浏览器已关闭");
    }

    /*@Scheduled(cron = "0 0/3 * * * ?")
    public void seleniumLoginAndOut(){
        log.info("定时器开始");
        // TODO 需要研究下 Linux环境的驱动

        //Linux chromeDriver位置
        System.setProperty("webdriver.chrome.driver","/usr/sbin/chromedriver");
        //Linux chrome
        System.setProperty("webdriver.chrome.bin","/opt/google/chrome/chrome");
        log.info("设置property");
        ChromeOptions chromeOptions = new ChromeOptions();
        log.info("无界面参数");
        //无界面参数
        chromeOptions.addArguments("headless");
        log.info("禁用沙盒");
        //禁用沙盒
        chromeOptions.addArguments("no-sandbox");
        log.info("1");
        chromeOptions.addArguments("disable-extensions");
        log.info("2");
        chromeOptions.addArguments("disable-gpu");
        log.info("3");
        chromeOptions.addArguments("disable-dev-shm-usage");
        log.info("4");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("http://221.7.105.6:1002/");
        try {
            //取消https跳转
            Thread.sleep(1000);
            WebElement cancel = driver.findElement(By.className("margin-l-sm"));
            cancel.click();
            //登陆
            WebElement username = driver.findElement(By.id("username"));
            username.sendKeys("admin");
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("adminkst");
            WebElement login = driver.findElement(By.id("login"));
            login.click();
            Thread.sleep(2000);
            //获取cookie
            List<WebElement> elements = driver.findElements(By.className("rui-nav-text"));
            elements.get(3).click();
            Thread.sleep(1000);
            elements.get(4).click();
            Set<Cookie> cookies = driver.manage().getCookies();
            System.out.println(cookies);
            Iterator<Cookie> iterator = cookies.iterator();
            StringBuilder sb = new StringBuilder();
            while (iterator.hasNext()){
                Cookie cookie = iterator.next();
                String name = cookie.getName();
                String value = cookie.getValue();
                sb.append(name).append("=").append(value).append(";");
                System.out.println(name+value);
            }
            String coo = sb.toString();
            String token = coo.substring(0, coo.length() - 1).replace(";", "; ");
            ServletContext context = BeansUtil.getBean(ServletContext.class);
            context.setAttribute("token",token);
            log.info("成功模拟登陆获取cookie:{}",token);
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.id("cur_user"))).perform();
            Thread.sleep(2000);
            WebElement loginOut = driver.findElement(By.id("exit_a"));
            loginOut.click();
            log.info("成功模拟登出");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            driver.close();
        }
    }*/
}
