package com.wisesoft.cdfyshow.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author: wzl
 * @create: 2020/6/12
 * @description:
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket fyDateApi(Environment environment){
        Profiles profile = Profiles.of("test");
        boolean b = environment.acceptsProfiles(profile);   //判断当前开发环境是否为 dev
        return new Docket(DocumentationType.SWAGGER_2)              //swagger版本
                .groupName("非遗数据接口")                               //分组名
                .apiInfo(fyDateApiInfo())                           //api描述
                .enable(b)                                          //是否开启swagger
                .select()                                           //开启扫描  接下来直到 biuld都是扫描规范
//                .apis(RequestHandlerSelectors.basePackage("com.wisesoft.cdfyshow.controller")) //绝对路径包扫描
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))     //扫描所有方法加了ApiOperation接口描述的接口
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))               //扫描所有类上加了接口类描述的类
                .paths(PathSelectors.any())   //过滤路径 --- 表示所有
//                .paths(PathSelectors.ant("/data/**"))   //过滤路径 --- 指定条件（）
                .build();
    }

    @Bean
    public Docket userApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户接口")
                .apiInfo(userApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/user/delete/**"))
                .build();
    }

    public ApiInfo fyDateApiInfo(){
        return new ApiInfoBuilder()
                .title("fydata")
                .description("非遗数据查询接口")
                .version("1.0")
                .contact(new Contact("wzl","http://www.baidu.com","1742182887@qq.com")) //设置作者
                .build();
    }
    public ApiInfo userApiInfo(){
        return new ApiInfoBuilder()
                .title("user")
                .description("用户接口")
                .version("1.0")
                .build();
    }

}
