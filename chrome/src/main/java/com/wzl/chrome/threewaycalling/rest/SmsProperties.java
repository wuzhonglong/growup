package com.wzl.chrome.threewaycalling.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * @author: wzl
 * @create: 2020/7/10
 * @description: 短信属性配置
 */
@Getter
@Setter
//@Component
//@PropertySource("classpath:sms.properties")
//@ConfigurationProperties(prefix = "sms")
//@Validated
public class SmsProperties {
    private String url;
    private String commandPushTemplateId;
    private Map<String,String> gateway;

}
