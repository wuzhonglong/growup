package com.wisesoft.wzl.config.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: wzl
 * @create: 2020/5/8
 * @description:
 */
@Getter
@Setter
@AllArgsConstructor
public class User {
    private String name;
    private String password;
    private String code;
}
