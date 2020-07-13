package com.wzl.chrome.paramVerify.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: wzl
 * @create: 2020/7/9
 * @description: 参数校验
 */
@RestController
@RequestMapping("/param")
@Validated
public class ParamVerifyController {

    @GetMapping("/verify")
    public void demo(@NotBlank(message = "不能为空") String name, @NotBlank(message = "不能为空") String password) {

        System.out.println("sdad");
    }
}
