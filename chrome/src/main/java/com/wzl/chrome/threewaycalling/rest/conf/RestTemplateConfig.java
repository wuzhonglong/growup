package com.wzl.chrome.threewaycalling.rest.conf;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @author: wzl
 * @create: 2020/6/9
 * @description: RestTemplate配置
 */
@Configuration
public class RestTemplateConfig {

    /**
     *@description: 创建rest
     *@author: wzl
     *@createTime: 2020/6/9
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        RestTemplate restTemplate = new RestTemplate(factory);
        //自定义异常处理
//        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
//            @Override
//            protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
//                super.handleError(response, statusCode);
//                //比如返回某个code 的时候 我们可以手动抛异常然后 统一抓取 处理
////                if(response.getRawStatusCode() == 404){
////                    throw new RuntimeException();
////                }
//            }
//        });
        return restTemplate;
    }

    /**
     *@description: 这个配置是忽略安全证书 使用的请求工厂是
     *  HttpComponentsClientHttpRequestFactory（这个好像可以配置连接池 对长连接而言可以选择这个template）
     *@author: wzl
     *@createTime: 2020/6/9
     */
    @Bean("httpsRestTemplate")
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }


    /**
     *@description: 配置请求工厂  rest需要请求工厂创建 工厂可以统一设置请求超时和代理设置等等
     *@author: wzl
     *@createTime: 2020/6/9
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //链接超时时间6秒
        factory.setConnectTimeout(6000);
        //数据传递超时时间1分钟
        factory.setReadTimeout(60000);
        //不设置输出流
        factory.setOutputStreaming(false);
        //是否用缓冲流来c存储请求体
        factory.setBufferRequestBody(true); //默认就是true
        //设置每次传输字节长度 必须结合setBufferRequestBody(false)使用
        //factory.setChunkSize(1024);
        //设置代理
        //factory.setProxy(new Proxy());
        //设置异步回调执行器
        //factory.setTaskExecutor();
        return factory;
    }
}
