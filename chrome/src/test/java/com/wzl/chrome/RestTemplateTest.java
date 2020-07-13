package com.wzl.chrome;

import com.wzl.chrome.threewaycalling.rest.controller.RestTemplateController;
import com.wzl.chrome.threewaycalling.rest.handler.DirectSellPlatHandler;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author: wzl
 * @create: 2020/6/9
 * @description: 三方调用单元测试
 */
@SpringBootTest
public class RestTemplateTest {

    @Autowired
    RestTemplateController controller;

    @Autowired
    DirectSellPlatHandler handler;

    @Test
    public void test(){
        controller.sms();
//        handler.getHotelList();
//        handler.saveOrder();
//        handler.orderDetail();
//        handler.hotelOrderList(1);
//        handler.getHotelRooms(10,10
//                , DateUtils.addDays(new Date(),1)
//                ,DateUtils.addDays(new Date(),4));
    }
}
