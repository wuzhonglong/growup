package com.wzl.chrome.api;/**
 * @author wzl
 * @date 2020-06-19 9:10
 */

import lombok.Getter;

/**
 * @author: wzl
 * @create: 2020/6/19
 * @description: 返回给飞猪的编码详细 使用的时候可以参照看下
 */
@Getter
public enum FzResultCode {
    // 价格库存验证接口
    ValidateRQ_SUCCESS("0","处理成功","处理成功"),
    ValidateRQ_FAIL_FULL_ROOM("-1","处理失败-满房","请求日期段库存都为0，返回满房。系统会清空对应日历的库存报价"),
    ValidateRQ_FAIL_PRICE_PLAN("-2","处理失败-价格计划问题","失效对应的价格计划"),
    ValidateRQ_FAIL_NOT_ENOUGH_ROOM("-3","处理失败-房量不足","房量不足（请求日期端库存不全部为0，部分可售）"),
    ValidateRQ_FAIL_OTHER_EXCEPTION("-4","其他失败","例如发生异常等"),

    // 支付成功通知接口
    PaySuccessRQ_SUCCESS("0","处理成功","处理成功"),
    PaySuccessRQ_FAIL("-400","处理失败-内部错误","处理失败-内部错误"),

    //订单生成接口
    BookRQ_SUCCESS("0","订单生成成功","");
    // TODO 命名不规范 但是符合飞猪要求的xml节点 看修改那边
    private String ResultCode;
    private String Message;
    private String Remark;

    FzResultCode (String ResultCode,String Message,String Remark){
        this.ResultCode = ResultCode;
        this.Message = Message;
        this.Remark = Remark;
    }
}
