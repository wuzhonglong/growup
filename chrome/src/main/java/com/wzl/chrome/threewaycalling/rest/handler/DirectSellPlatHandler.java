package com.wzl.chrome.threewaycalling.rest.handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: wzl
 * @create: 2020/5/28
 * @description: 使用Spirng的restemplate调用三方接口
 */
@Component
public class DirectSellPlatHandler {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @description: 保存客服预定订单
     * @author: wzl
     * @createTime: 2020/6/18
     */
    public void saveOrder() {
        // TODO 记得提出去到配置文件 只保留接口路径
        String url = "http://demo.maijinch.cn/webservice/webservice.asmx/save_order";
        List<Map<String, Object>> orderInfos = new ArrayList<>();
        Map<String, Object> order = new HashMap<>();
        order.put("account_id", 1);
        Date start = DateUtils.addDays(new Date(), 2);
        Date end = DateUtils.addDays(new Date(), 7);
        order.put("startTime", DateFormatUtils.format(start, "yyyy-MM-dd"));
        order.put("endTime", DateFormatUtils.format(end, "yyyy-MM-dd"));
        order.put("days", 5);
        order.put("roomNum", 1);
        order.put("roomPrice", new BigDecimal(999));
        order.put("roomType", "跳跳床");
        order.put("phone", "152781117111");
        order.put("checkinOne", "大吴");
        order.put("checkinTwo", "小杨");
//        order.put("email", "可以为空");
        order.put("totlePrice", new BigDecimal(10086.11));
        order.put("payment_id", 1);
        order.put("payment", "支付方式中文名称");
        order.put("trade_no", "NO.00000001");
        order.put("point", 100);
        order.put("order_source", 3);
        order.put("coupon_id", 0);
        orderInfos.add(order);
        Map<String, Object> params = new HashMap<>();
        params.put("_mdetail", JSONObject.toJSONString(orderInfos));
        params.put("key", null);
        String forObject = restTemplate.getForObject(url + "?_mdetail={_mdetail}&key={key}", String.class, params);
        // TODO 返回的是一个xml  如果需要落地保存订单号 就要解析xml
//<?xml version="1.0" encoding="utf-8"?>
//<string xmlns="http://tempuri.org/">{"status":1, "order_no":"H20061817483076", "msg":"提交成功"}</string>
        System.out.println(forObject);
    }

    /**
     * @description: 查看客房订单详细信息
     * @author: wzl
     * @createTime: 2020/6/18
     * @param: order_no String 订单号
     */
    public void orderDetail() {
        String order_no = "H20061817483076";
        String url = "http://demo.maijinch.cn/webservice/webservice.asmx/order_detail?order_no=" + order_no;
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println("");
    }

    /**
     * @description: 查询指定酒店所有客服预定订单
     * @author: wzl
     * @createTime: 2020/6/18
     * @param: 酒店门店账号 account_id
     */
    public void hotelOrderList(Integer accountId) {
        String url = "http://demo.maijinch.cn/webservice/webservice.asmx/hotel_order_list";
        String forObject = restTemplate.getForObject(url + "?account_id={accountId}", String.class, accountId);
        System.out.println(forObject);
    }

    /**
     * @description: 查看酒店列表信息
     * @author: wzl
     * @createTime: 2020/6/18
     */
    public void getHotelList() {
        String url = "http://demo.maijinch.cn/webservice/webservice.asmx/get_hotel_list";
        HttpHeaders headers = new HttpHeaders();
//        headers.add("_channel_id",10);
        Map<String, Integer> params = new HashMap<>();
        params.put("_channel_id", 10);
//        HttpEntity<Object> entity = new HttpEntity<>(null,headers);
        String result = restTemplate.getForObject(url + "?_channel_id={_channel_id}", String.class, params);

//        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("");
    }

    /**
     *@description: 查询酒店客服列表信息 可以用于获取可预售库存  目前没调通  提示参数有误
     *@author: wzl
     *@createTime: 2020/6/19
     *@param: channelId 频道ID（更像是一个分组ID 比如所有民宿是一个分组 所有五星级酒店一个分组） 测试频道10
     *@param: accountId 门店ID 就比如 维也纳国际酒店 有很多家 这个ID 就是指的某一家的ID
     *@param: startDate 预订开始时间
     *@param: endDate 预订结束时间
     */
    public void getHotelRooms(Integer channelId, Integer accountId, Date startDate, Date endDate) {
        String url = "http://demo.maijinch.cn/webservice/webservice.asmx/get_hotel_rooms";
        Map<String, Object> params = new HashMap<>();
        params.put("channel_id", channelId);
        params.put("account_id", accountId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        String s = restTemplate.getForObject(
                url + "?channel_id={channel_id}&account_id={account_id}&startDate={startDate}&endDate={endDate}"
                , String.class, params);
        System.out.println(s);
    }

}
