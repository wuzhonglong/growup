package com.wisesoft.cdfyshow.enums;


import lombok.Getter;

/**
 * @author: wzl
 * @create: 2020/6/4
 * @description: 非遗类型枚举
 */
@Getter
public enum FyTypeEnum {

    CTJYXL("传统技艺"),
    CTYYXL("传统医药"),
    CTXJXL("传统戏剧"),
    MJWXXL("民间文学"),
    CTMSXL("传统美术"),
    CTYYXL_MIUSIC("传统音乐"),
    CTWDXL("传统舞蹈"),
    CTTYYYZJXL("传统体育、游艺、杂技"),
    MSXL("民俗"),
    QYXL("曲艺");
    private String note;

    FyTypeEnum(String note) {
        this.note = note;
    }
}
