package com.wisesoft.cdfyshow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author: wzl
 * @create: 2020/6/12
 * @description:
 */
@ApiModel(value = "用户类", description = "用户vo")
@Getter
@Setter
public class User {
//    @NotNull(message = "不能为null")
    // 这里设置required不生效 不知道为啥  接口层的可以生效
    @ApiModelProperty(name = "username",value = "姓名",  required = true, example = "张三")
    private String username;

    @ApiModelProperty(name = "age", value = "年龄", dataType = "Integer", example = "18")
    private Integer age;
}
