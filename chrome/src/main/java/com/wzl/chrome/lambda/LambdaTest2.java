package com.wzl.chrome.lambda;


import java.util.*;
import java.util.function.Consumer;

/**
 * @author: wzl
 * @create: 2020/6/8
 * @description:
 */
public class LambdaTest2 {
    public static void main(String[] args) {
//        demo1();
        demo3();
    }
    /**
     *@description: foreach
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo1(){
        Set<String> set = new HashSet<>();
        set.add("hello");
        set.add("world");
        set.add("yangna");
        set.forEach(s -> {
            System.out.println(s);
        });
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("yangna");
        ((ArrayList) list).forEach(s -> System.out.println(s));
        Map<String, Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("age",18);
        ((HashMap) map).forEach((k,v) ->{
            System.out.println(k+":"+v);
        });
        List<Map<String,Object>> end = new ArrayList<>();
        end.forEach(m ->{
            m.forEach((k,v) ->{
                //...
            });
        });
        end.forEach(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) {

            }
        });
    }

    /**
     *@description: removeIf
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo2(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("yangna");
        list.removeIf(s -> s.length() == 5);
    }

    /**
     *@description: re
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo3(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("yangna");
        list.replaceAll(s -> {
            if (s.length() == 5){
                return s.toUpperCase();
            }
            return s;
        });
    }

    /**
     *@description: sort
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void demo4(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("yangna");
        list.sort((s1,s2) -> s1.length()-s2.length());
    }





    /**********************************************************   Map   **************************/

    public static void test1(){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.forEach((k,v) -> System.out.println("key:"+k+";value:"+v));
    }
    /**
     *@description: getOrDefault 对不存在的key设置一个默认值  存在就返回其value
     *@author: wzl
     *@createTime: 2020/6/8
     */
    public static void test2(){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        String defaultValue = map.getOrDefault(4, "defaultValue");
        System.out.println(defaultValue);
    }

}
