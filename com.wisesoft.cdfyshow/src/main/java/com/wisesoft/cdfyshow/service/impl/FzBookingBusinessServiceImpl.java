package com.wisesoft.cdfyshow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisesoft.cdfyshow.service.FzBookingBusinessService;
import org.springframework.stereotype.Service;

/**
 * @author: wzl
 * @create: 2020/6/18
 * @description:
 */
@Service
public class FzBookingBusinessServiceImpl implements FzBookingBusinessService {

    /**
     *@description: 预定前验证价格库存
     *@author: wzl
     *@createTime: 2020/6/18
     */
    @Override
    public void validateRQ(JSONObject jsonObject) {

    }
}
