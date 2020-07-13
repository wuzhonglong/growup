package com.wzl.chrome.threewaycalling.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author: wzl
 * @create: 2020/5/27
 * @description: 运用Apache的HttpClient进行三方调用工具类
 */
//public class ApacheHttpClientUtil {
//    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
//
//    private static PoolingHttpClientConnectionManager connManager = null;
//
//    private static CloseableHttpClient httpclient = null;
//
//    public final static int connectTimeout = 15000;
//
//    static {
//        try {
//            //安全认证
//            SSLContext sslContext = SSLContexts.custom().useTLS().build();//tls加密
//            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
//
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                    //采取绕过证书的策略： 像这样 不作处理 或者返回null 那么就是代表不需要认证
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//            } }, null);
//            //注册器   使用注册器可以保证既能发送http请求也能发送httpsclient请求
//            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                    .register("http", PlainConnectionSocketFactory.INSTANCE)
//                    .register("https", new SSLConnectionSocketFactory(sslContext)).build();
//
//            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//            //传输配置
//            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();//是否立即发送数据 true 立即发送关闭缓冲  默认false
//            connManager.setDefaultSocketConfig(socketConfig);
//            //消息约束
//            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
//                    .setMaxLineLength(2000).build();
//            //连接配置
//            ConnectionConfig connectionConfig = ConnectionConfig.custom()
//                    .setMalformedInputAction(CodingErrorAction.IGNORE)
//                    .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
//                    .setMessageConstraints(messageConstraints).build();
//            connManager.setDefaultConnectionConfig(connectionConfig);
//            connManager.setMaxTotal(1024);  //连接池最大数
//            connManager.setDefaultMaxPerRoute(200); //设置默认单个路由的最大连接数
//            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
//        } catch (KeyManagementException e) {
//            logger.error("KeyManagementException", e);
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("NoSuchAlgorithmException", e);
//        }
//    }
//
//    public static String executeHttpPostWithJSon(String requestUrl, String json, Map<String, String> header){
//        logger.info("httpclientmanager状态:" + connManager.getTotalStats().toString());
//        String response = null;
//        HttpPost post = new HttpPost(requestUrl);
//        try {
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
//                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
//                    .setExpectContinueEnabled(false).build();
//            post.setConfig(requestConfig);
//            StringEntity reqEntity = new StringEntity(json, Consts.UTF_8);
//            reqEntity.setContentType("application/json");
//            post.setEntity(reqEntity);
//            if (header != null && header.size() > 0) {
//                for (String key : header.keySet()) {
//                    post.setHeader(key, header.get(key));
//                }
//            }
//            CloseableHttpResponse httpResponse = httpclient.execute(post);
//            try {
//                HttpEntity entity = httpResponse.getEntity();
//                System.out.println(httpResponse.getStatusLine().toString());
//                try {
//                    if (entity != null) {
//                        response = EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8);
//                    }
//                } finally {
//                    if (entity != null) {
//                        entity.getContent().close();
//                    }
//                }
//            } finally {
//                if (httpResponse != null) {
//                    httpResponse.close();
//                }
//            }
//        } catch (UnsupportedEncodingException e) {
//            logger.error("UnsupportedEncodingException", e);
//        } catch (Exception e) {
//            logger.error("Exception", e);
//        } finally {
//            post.releaseConnection();
//        }
//        return response;
//    }
//}
