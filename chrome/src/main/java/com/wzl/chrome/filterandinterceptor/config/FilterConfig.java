package com.wzl.chrome.filterandinterceptor.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: wzl
 * @create: 2020/7/3
 * @description: 过滤器配置
 */
@Configuration
public class FilterConfig {

    /**
     * @description: 注册过滤器
     * @author: wzl
     * @createTime: 2020/7/3
     */
    //@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // 将自定义的过滤器注册到注册过滤器类
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>(new MyFilter());
        // 优先级 值越大 越优先
        registrationBean.setOrder(1);
        // 过滤器名称
        registrationBean.setName("自定义过滤器:MyFilter");
//        registrationBean.addUrlPatterns("/*");  // 所有
        registrationBean.addUrlPatterns("/test/filterandinterceptor/interceptor");  // 过滤处理指定请求 这儿并不是说加上就不执行请求了 具体执不执行看过滤规则放不放行
        return registrationBean;
    }
}
