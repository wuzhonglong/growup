package com.wzl.chrome.filterandinterceptor.config;

import cn.hutool.json.JSONUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author: wzl
 * @create: 2020/7/3
 * @description: 自定义过滤器
 */
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        StringBuffer requestURL = request.getRequestURL();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String[] split = servletPath.split("/");
        List<String> list = Arrays.asList(split);
        if (list.contains("test")) {
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().write("不允许通过");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("status","1");
            messageMap.put("message","测试不允许通过");
            response.getWriter().write(JSONUtil.toJsonStr(messageMap));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
