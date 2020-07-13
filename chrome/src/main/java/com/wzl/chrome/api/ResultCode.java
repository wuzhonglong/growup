package com.wzl.chrome.api;

public enum ResultCode {

    // 通用响应信息定义
    ERROR(-1, "内部错误"),
    SUCCESS(0, "请求成功"),
    AUTH_FAILED(1, "认证失败"),
    ILLEGAL_PARAM(2, "非法参数"),
    BIZ_FAIL(3, "业务错误");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
