package com.wzl.chrome.filterandinterceptor.config;

import cn.hutool.json.JSONUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wzl
 * @create: 2020/7/3
 * @description: 自定义拦截器
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    /**
     * @description: 是否拦截处理
     * @author: wzl
     * @createTime: 2020/7/3
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 模拟通过Ip拦截 实际中可以很多灵活应用 比如登陆拦截
        // 设置白名单
        List<String> list = Arrays.asList(new String[]{"127.0.0.1", "192.6.19.200"});
//        List<String> list = Arrays.asList(new String[]{"192.6.19.200"});
        String ipAddr = getIpAddr(request);
        if(list.contains(ipAddr)){
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("status","1");
        messageMap.put("message","该IP不允许通过");
        response.getWriter().write(JSONUtil.toJsonStr(messageMap));
        return false;
    }

    /**
     * @description: 相当于前置通知
     * @author: wzl
     * @createTime: 2020/7/3
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("前置通知:只有拦截通过 在执行对应请求之前 打印");
    }

    /**
     * @description: 相当于后置通知
     * @author: wzl
     * @createTime: 2020/7/3
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("后置通知:只有拦截通过 在执行对应请求结束 打印");
    }

    /**
     * @description: 基本上可以获取到真实IP
     * @author: wzl
     * @createTime: 2020/7/3
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
