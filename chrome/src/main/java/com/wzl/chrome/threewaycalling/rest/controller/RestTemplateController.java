package com.wzl.chrome.threewaycalling.rest.controller;

import com.wzl.chrome.threewaycalling.rest.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wzl
 * @create: 2020/5/28
 * @description: 测试restrmplate调用
 */
@RestController
public class RestTemplateController {

    @Autowired
    private SmsProperties properties;

    @Value(value = "${sms.gateway.secretKey}")
    private String secretKey;
    @Value(value = "${sms.gateway.secretId}")
    private String secretId;
    @Value(value = "${sms.templateId}")
    private String templateId;
    @Value(value = "${sms.url}")
    private String url;

    //通过高德API 进行演示
//    amap.key=2ebbaa244d25966036d32a31e4acf1f1

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier(value = "httpsRestTemplate")
    private RestTemplate httpsRestTemplate; //如果遇到需要证书的 就用这个可以绕过证书

    private static final String amapKey = "2ebbaa244d25966036d32a31e4acf1f1";

    //private String url = "https://restapi.amap.com/v3/ip";
    private String key = "2ebbaa244d25966036d32a31e4acf1f1";


    /******************get请求的两种方式  建议第一种exchange******************/

    /**
     * @description: restTemplate.exchange(....)
     * @author: wzl
     * @createTime: 2020/6/11
     */
    public void demo1() {
        /** 通用部分不管是get 还是 post 等等  为了避免返回数据乱码 可以定义转换器 **/
        //获取消息转换器集合
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        //移除原有的转换器
        converters.remove(1);
        //创建新的转换器
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        //把新的转换器放在原来对应的位置
        converters.add(1, converter);
        //注入新的转换器集合
        restTemplate.setMessageConverters(converters);
        /**通用部分结束**/

        HttpHeaders headers = new HttpHeaders();
        //通过 headers 添加参数
        headers.add("key", key);
        //如果 参数值是一个集合对象 比如cookie 可以用 headers.put()
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("");
    }

    /**
     * @description: restTemplate.getForObject(....)
     * 这个需要注意: url 后面参数拼接   url+"?key={k}"  {k} 就是一个占位  后面跟值 或者map
     * 如果是map 需要注意  {name} 占位符中的名称 必须和 map中的键一致
     * @author: wzl
     * @createTime: 2020/6/11
     */
    public void demo2() {
//        String forObject = restTemplate.getForObject(url+"?key={k}", String.class, key); //方式一
        Map<String, Object> map = new HashMap<>();
        map.put("k", key);
        String forObject = restTemplate.getForObject(url + "?key={k}", String.class, map); //方式二
        System.out.println("");
    }


    /*********************post*****************************/
    /**
     * 目前用过的post ： 1、调用普通post接口 2、form表单方式的post 3、文件上传方式
     */

    public void postDemo1() {
        HttpHeaders headers = new HttpHeaders();
        // 普通的json post
        headers.setContentType(MediaType.APPLICATION_JSON);
        //form表单方式
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //xml文件上传
        headers.setContentType(MediaType.APPLICATION_XML);
        Object body = null;
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        //方式一
        String s = restTemplate.postForObject(url, entity, String.class);
        //方式二
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    }



    public void sms() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","hmac id=\"AKIDe8zqecmuu74w7coltp5rkitxd9a941txs96l\", algorithm=\"hmac-sha1\", headers=\"date\",  signature=\"clveqMQHPqdllATUt5dGuXS6GBI=\"");
        headers.add("Date","Fri, 09 Oct 2015 00:00:00 GMT");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("templateId","592396");
        String[] phones = {"18382375175", "17608036273"};
        map.put("phoneNumber",phones);
        String[] templateParams = {};
        map.put("templateParam",templateParams);
        list.add(map);
        HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<>(list, headers);
        String s = restTemplate.postForObject("https://service-m2l1f020-1257188045.gz.apigw.tencentcs.com/service/messages/msg-app/api/no/sms/create", entity, String.class);
        System.out.println(s);
    }



}
