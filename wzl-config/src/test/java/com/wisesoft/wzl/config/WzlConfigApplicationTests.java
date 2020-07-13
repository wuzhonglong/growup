package com.wisesoft.wzl.config;

import com.wisesoft.wzl.config.entities.User;
import com.wisesoft.wzl.config.handler.WifiSeleniumHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class WzlConfigApplicationTests {

    @Autowired
    private WifiSeleniumHandler handler;

    @Test
    void seleniumLoginAndOut() throws InterruptedException {
        //handler.seleniumLoginAndOut();
    }

    /**
     *@description: 测试Java8新特性 Stream
     *@author: wzl
     *@createTime: 2020/5/8
     */
    @Test
    void testJava8ListStream(){
        User user1 = new User("张三","zhangsan","0001");
        User user2 = new User("李四","lisi","0002");
        User user3 = new User("王五","wangwu","0003");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        List<String> codes = users.stream().map(User::getCode).collect(Collectors.toList());
        System.out.println(codes);
    }

}
