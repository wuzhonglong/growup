package com.wzl.chrome.globleConfig;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: wzl
 * @create: 2020/7/13
 * @description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> exceptionHandler(Exception e) {
        // 这儿应该是一个统一返回类 比如commonResult
        CommonResult msg = CommonResult.fail().msg(e.getMessage());
        return msg;
    }
}
