package com.wzl.chrome.threewaycalling.gateway;

import com.wzl.chrome.threewaycalling.gateway.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SignAndSend {
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    public static String sign(String secret, String timeStr)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
        //获取签名字符串
//        String signStr = "date: "+timeStr+"\n"+"source: "+"source";
        String signStr = "date: "+timeStr;
        //获取接口签名
        String sig = null;
        Mac mac1 = Mac.getInstance(HMAC_ALGORITHM);
        byte[] hash;
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac1.getAlgorithm());
        mac1.init(secretKey);
        hash = mac1.doFinal(signStr.getBytes(CONTENT_CHARSET));
        sig = new String(Base64.encode(hash));
        System.out.println("signValue--->" + sig);
        return sig;
    }
    public static String sendGet(String url, String secretId, String secretKey) {
        String result = "";
        BufferedReader in = null;
        //获取当前 GMT 时间
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        String timeStr = sdf.format(cd.getTime());
        String timeStr = "Fri, 09 Oct 2015 00:00:00 GMT";
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和 URL 之间的连接
            URLConnection connection = realUrl.openConnection();
            HttpURLConnection httpUrlCon = (HttpURLConnection)connection;
            // 设置通用的请求属性
            httpUrlCon.setRequestProperty("Host", url);
            httpUrlCon.setRequestProperty("Accept", "text/html, */*; q=0.01");
            httpUrlCon.setRequestProperty("Source","source");
            httpUrlCon.setRequestProperty("Date",timeStr);
            String sig = sign(secretKey,timeStr);
            String authen = "hmac id=\""+secretId+"\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\""+sig+"\"";
            System.out.println("authen --->" + authen);
            httpUrlCon.setRequestProperty("Authorization",authen);
            httpUrlCon.setRequestProperty("X-Requested-With","XMLHttpRequest");
            httpUrlCon.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");

            // 如果是微服务 API，Header 中需要添加'X-NameSpace-Code'、'X-MicroService-Name'两个字段，通用 API 不需要添加。
//            httpUrlCon.setRequestProperty("X-NameSpace-Code","testmic");
//            httpUrlCon.setRequestProperty("X-MicroService-Name","provider-demo");

            // 建立实际的连接
            httpUrlCon.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = httpUrlCon.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader 输入流来读取 URL 的响应
            in = new BufferedReader(new InputStreamReader(
                    httpUrlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用 finally 块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://service-m2l1f020-1257188045.gz.apigw.tencentcs.com/service/messages/msg-app/api/no/sms/create"; // 用户 API 的访问路径
        String secretId = "AKIDe8zqecmuu74w7coltp5rkitxd9a941txs96l"; // 密钥对的 SecretId
        String secretKey = "Hb88juqkr65YU6k8Mc671P7vCGxEdw3w5M5FIv7f"; // 密钥对的 SecretKey
        String result = SignAndSend.sendGet(url, secretId, secretKey);
        System.out.println(result);
    }
}