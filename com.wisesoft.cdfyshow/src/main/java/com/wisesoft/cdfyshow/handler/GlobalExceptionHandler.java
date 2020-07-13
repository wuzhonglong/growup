package com.wisesoft.cdfyshow.handler;

import com.wisesoft.cdfyshow.api.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Auther: yi
 * @Date: 2020/6/3 11:06
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e){
        e.printStackTrace();
        return CommonResult.fail().msg(e.getMessage());
    }
}
