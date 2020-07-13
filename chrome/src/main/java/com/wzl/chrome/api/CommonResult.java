package com.wzl.chrome.api;

import java.io.Serializable;
import java.util.Objects;

public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = 4752616408398081819L;

    private int code;

    private String msg;

    private T data;

    protected CommonResult() {
    }

    public static <T> CommonResult<T> success() {
        return create(true, ResultCode.SUCCESS, null);
    }

    public static <T> CommonResult<T> success(T data) {
        return create(true, ResultCode.SUCCESS, data);
    }

    public static <T> CommonResult<T> fail() {
        return create(false, ResultCode.BIZ_FAIL, null);
    }

    public static <T> CommonResult<T> fail(ResultCode resultCode) {
        //校验 是否为null  如果为null  会抛出空指针  message:异常描述
        Objects.requireNonNull(resultCode, "The parameter 'resultCode' cannot be null.");
        return create(false, resultCode, null);
    }

    public static <T> CommonResult<T> create(boolean success, ResultCode resultCode, T data) {
        Objects.requireNonNull(resultCode, "The parameter 'resultCode' cannot be null.");
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.code = resultCode.code();
        commonResult.msg = resultCode.msg();
        commonResult.data = data;
        return commonResult;
    }

    public CommonResult msg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
