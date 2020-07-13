package com.wisesoft.cdfyshow.controller;

import com.alibaba.fastjson.JSONObject;
import com.wisesoft.cdfyshow.api.CommonResult;
import com.wisesoft.cdfyshow.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.xml.XMLSerializer;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author: wzl
 * @create: 2020/6/12
 * @description: 用户接口
 */
@Api(value = "用户", description = "用户接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @ApiOperation(value = "测试接口", notes = "测试根据方法的ApiOperation注解扫描配置是否成功")
    @GetMapping("/login")
    public CommonResult<String> login() throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String reqDate = sb.toString();
        return CommonResult.success("测试成功");
    }

    @PostMapping("/test")
    public void doPost() throws ServletException, IOException, DocumentException {
        //Request get XML Data
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String postXMLData = sb.toString();
        System.out.println("请求XML参数:" + postXMLData);
        Document document = DocumentHelper.parseText(postXMLData);
        String rootName = document.getRootElement().getName();// 根节点名称
        System.out.println("根节点名称:" + rootName);
        XMLSerializer xmlSerializer = new XMLSerializer();
        String jsonDate = xmlSerializer.read(postXMLData).toString();
        //开始处理结果封装  至于做些什么
        //先转成 JSONObject(包含了根节点下所有节点信息) 放到具体业务进行处理 并返回
        JSONObject jsonObject = JSONObject.parseObject(jsonDate);
        switch (rootName) {
            case "ValidateRQ":
                System.out.println("验证价格库存");
                break;
            case "PaySuccessRQ":
                System.out.println("支付成功通知");
                break;
            case "BookRQ":
                System.out.println("创建订单");
                break;
            case "QueryStatusRQ":
                System.out.println("订单查询");
                break;
            case "OrderRefundRQ":
                System.out.println("退款申请通知");
                break;
            case "CancelRQ":
                System.out.println("取消订单");
                break;
        }
//        Element root = XmlUtils.loadXMLRootElementByXMLString(postXMLData, "UTF-8");
//        //TODO: 处理RQ数据，返回XML结果
//        String result = dealData(root);
//        //返回处理结果
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(result);
    }


    @ApiOperation(value = "测试接口", notes = "测试根据方法的ApiOperation注解扫描配置是否成功")
    @GetMapping("/upload")
    public CommonResult<String> upload() {
        return CommonResult.success("测试成功");
    }

    @ApiOperation(value = "测试接口", notes = "测试根据方法的ApiOperation注解扫描配置是否成功")
    @PostMapping("/delete/all")
    public CommonResult<String> pdelete(@Valid @RequestBody User user) {
        return CommonResult.success("测试成功");
    }
}
