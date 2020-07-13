package com.wzl.chrome.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: wzl
 * @create: 2020/7/8
 * @description: apacheIO工具类使用
 */
public class ApacheIOUtils {
    /**
     * @description: 从流中读取数据
     * @author: wzl
     * @createTime: 2020/7/8
     */
    public static void read() {
        Resource resource = new ClassPathResource("utils/ApacheCommonsIO之IOUtils.md");
        FileInputStream fileInputStream = null;
        try {
            // 需要注意的是流的每一次读取都会清空流 所以下面的每一个演示都会注释另一个 因为同时存在后面的演示读取的流是同一个
            // 而这个流在上一个演示中已经被消费了 所以要么注释 要么重新new 一个流
            fileInputStream = new FileInputStream(resource.getFile());
            // 直接把流读取为字符串
//            String s = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
//            System.out.println(s);
            // 读取为List<String> 一行为一个元素
            List<String> list = IOUtils.readLines(fileInputStream, StandardCharsets.UTF_8);
            list.forEach(System.out::println);
            // 读取为byte[]
//            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            // 把字符串转换成流
            IOUtils.toInputStream("nihao",StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 这样关闭流可以解决null异常
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    /**
     * @description: 流中写入数据
     * @author: wzl
     * @createTime: 2020/7/8
     */
    public static void write() {
        Resource resource = new ClassPathResource("utils/ApacheCommonsIO之IOUtils.md");
        FileOutputStream fileOutputStream = null;
        try {
//            fileOutputStream = new FileOutputStream(resource.getFile());
            fileOutputStream = new FileOutputStream("E:\\cdzs-wzl-growup\\chrome\\src\\main\\resources\\utils\\ApacheCommons.md");
            IOUtils.write("测试写入", fileOutputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    /**
     *@description: 好像可以各种拷贝自己试下就行
     *@author: wzl
     *@createTime: 2020/7/8
     */
    public static void copy(){

    }
}
