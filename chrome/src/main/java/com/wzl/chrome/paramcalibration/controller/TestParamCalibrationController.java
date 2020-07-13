package com.wzl.chrome.paramcalibration.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author: wzl
 * @create: 2020/6/5
 * @description: 参数校验测试
 */
@RestController
@RequestMapping(value = "param")
@Validated
public class TestParamCalibrationController {
    /**
     *@description: @Size 校验字符串和集合的长度
     *@author: wzl
     *@createTime: 2020/6/5
     */
    @GetMapping("/test")
    public void test(@Size(min = 2,max = 5,message = "长度必须2-5") String name,
                     @Size(min = 1,max = 3,message = "长度1-3") List<Integer> list){

    }
}
