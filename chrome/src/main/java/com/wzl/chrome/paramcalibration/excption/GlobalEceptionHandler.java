package com.wzl.chrome.paramcalibration.excption;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author: wzl
 * @create: 2020/6/5
 * @description: 全局异常处理器
 */
//JSON格式的全局异常处理返回类
@RestControllerAdvice
public class GlobalEceptionHandler {

    //指定处理的异常类
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException e){

    }

}
