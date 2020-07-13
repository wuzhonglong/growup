package com.wzl.chrome.threewaycalling.rest.conf;

import com.wzl.chrome.threewaycalling.rest.SmsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wzl
 * @create: 2020/7/10
 * @description:
 */
@Configuration
public class SmsConfig {
    @Bean
    @ConfigurationProperties(prefix = "sms")
    public SmsProperties smsProperties(){
        return new SmsProperties();
    }
}
