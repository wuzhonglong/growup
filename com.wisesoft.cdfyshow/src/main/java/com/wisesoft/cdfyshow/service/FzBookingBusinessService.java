package com.wisesoft.cdfyshow.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: wzl
 * @create: 2020/6/18
 * @description: 飞猪预定业务
 */
public interface FzBookingBusinessService {
    void validateRQ(JSONObject jsonObject);
}
