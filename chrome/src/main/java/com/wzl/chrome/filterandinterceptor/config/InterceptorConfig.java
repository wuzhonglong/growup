package com.wzl.chrome.filterandinterceptor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: wzl
 * @create: 2020/7/3
 * @description: 配置拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器 最好不用new 会创建太多的对象 通过注册Bean的方式好些
//        InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());
        InterceptorRegistration registration = registry.addInterceptor(getMyInterceptor());
        // 设置需要拦截的路径
        registration.addPathPatterns("/**");
        // 设置不需要被拦截的 多个可以放在一个list 或者用逗号隔开
//        registration.excludePathPatterns("","");
    }

    @Bean
    public MyInterceptor getMyInterceptor(){
        return new MyInterceptor();
    }
}
