package com.wzl.chrome.task;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wzl
 * @create: 2020/5/21
 * @description:
 */
@Getter
@Setter
public class User {
    private String name;
    private int age;
    public User(String name,int age){
        this.name = name;
        this.age = age;
    }
}
