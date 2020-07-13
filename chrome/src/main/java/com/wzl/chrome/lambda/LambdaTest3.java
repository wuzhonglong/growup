package com.wzl.chrome.lambda;

import org.apache.commons.collections.MapUtils;

import java.awt.event.ItemEvent;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author: wzl
 * @create: 2020/6/8
 * @description:
 */
public class LambdaTest3 {
    private static List<String> list = Arrays.asList("hello","you","wzl","world","yangna","wzl");

    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();
//        demo4();
        demo5();
    }

    /**
     *@description: filter过滤
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo1(){
        list.stream().filter(str -> str.length() == 3).forEach(System.out::println);
    }
    /**
     *@description: distinct去重
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo2(){
        list.stream().distinct().forEach(System.out::println);
    }

    public static void demo3(){
        list.stream().mapToInt(str -> str.length()).forEach(System.out::println);
    }

    /**
     *@description: 注意key重复时需根据需要处理逻辑
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo4(){
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(String::length, s -> s,(key1,key2) -> key2));
        System.out.println("");
    }


    public static void demo5(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name","zhangsan");
        map1.put("age",18);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name","lisi");
        map2.put("age",28);
        list.add(map1);
        list.add(map2);
        Map<String, Integer> collect = list.stream().collect(Collectors.toMap(item -> MapUtils.getString(item, "name")
                , item -> MapUtils.getIntValue(item, "age", 0)));
        System.out.println("");
    }
}
