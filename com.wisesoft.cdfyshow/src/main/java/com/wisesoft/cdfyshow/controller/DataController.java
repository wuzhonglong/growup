package com.wisesoft.cdfyshow.controller;

import com.wisesoft.cdfyshow.api.CommonResult;
import com.wisesoft.cdfyshow.service.DataService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @Auther: yi
 * @Date: 2020/6/3 10:54
 * @Description:
 */
//@Api(value = "DataController", description = "非遗数据查询接口")
@RestController
@RequestMapping("/data")
@Validated          // 参数校验
public class DataController {

    @Autowired
    private DataService dataService;


    /**
     * @description: 左侧饼图 所有店铺的类型占比  总览页面不传参   市州页面传参  比如  cityName = "宜宾市"
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @ApiIgnore // 加了这个注解 不会生成swagger文档
    @GetMapping(value = "/shopTypeScale")
    public CommonResult<Map<String, Object>> shopTypeScale(@RequestParam(required = false) String cityName) {
        Map<String, Object> map = dataService.shopTypeScale(cityName);
        return CommonResult.success(map);
    }

    /**
     * @description: 左侧矩形方块 非遗等级、网销开设方式、贫困级别占比     总览页面不传参   市州页面传参  比如  cityName = "宜宾市"
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @ApiOperation(value = "查询非遗等级、店铺类型、贫困等级", notes = "总览、市州界面通用接口")
    @GetMapping(value = "/leftRectScale")
    public CommonResult<Map<String, Object>> leftRectScale(
            @ApiParam(name = "cityName", value = "城市名")
            @RequestParam(required = false) String cityName) {
        return CommonResult.success(dataService.leftRectScale(cityName));
    }

    /**
     * @description: 左侧线上占比柱状图     总览页面不传参   市州页面传参  比如  cityName = "宜宾市"
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @ApiOperation(value = "查询个平台商铺占比", notes = "总览、市州界面通用接口")
    @GetMapping(value = "/leftBarScale")
    public CommonResult<List<Map<String, Object>>> leftBarScale(
            @ApiParam(name = "cityName", value = "城市名")
            @RequestParam(required = false) String cityName) {
        List<Map<String, Object>> maps = dataService.leftBarScale(cityName);
        return CommonResult.success(maps);
    }

    /**
     * @description: 市州分布图
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @GetMapping(value = "/rightMapDistribution")
    public CommonResult<List<Map<String, Object>>> rightMapDistribution() {
        return CommonResult.success(dataService.rightMapDistribution());
    }

    /**
     * @description: 简介 - 平台 - 传承人
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @ApiOperation(value = "店铺简介",notes = "包括传承人信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "店铺ID",paramType = "path",dataType = "Integer",required = true,defaultValue = "10"),
            @ApiImplicitParam(name = "num",value = "需要返回的传承人数量",paramType = "query",dataType = "Integer",defaultValue = "2")
            // paramType = path(@PathVariable这种resul风格适用)、query(请求头中的参数,拼接再url的那种?name=zhangsan)、header(请求头参数 类是于cookie之类)
            //              、form(表单类型的参数)、body(请求体中的参数) 一般用的多的就是前两者
            // dataType = ""  默认是String
            // required = boolean   默认是false 和@RequestParam注解的默认值刚好相反  所以用的时候注意点
    })
    @GetMapping(value = "/shopDetail/{id}")
    public CommonResult<Map<String, Object>> shopDetail(@PathVariable Integer id, @RequestParam(required = false) Integer num) {
        Map<String, Object> shopInfo = dataService.shopDetail(id, num);
        return CommonResult.success(shopInfo);
    }

    /**
     * @description: 店铺信息列表 cityName = "宜宾市"  required = true    这儿需要返回店铺ID
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @ApiOperation(value = "店铺信息列表",notes = "店铺信息列表")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "cityName",value = "城市名",paramType = "query",required = true)})
    @GetMapping(value = "/shopInfos")
    public CommonResult<List<Map<String, Object>>> shopInfos(@RequestParam(required = false) String cityName) {
        return null;
//        return CommonResult.success(dataService.shopInfos(cityName));
    }


    /**
     * @description: 单个（所有）店铺 销量、营业额统计 以及非遗类型销量统计    单个 需要传店铺ID
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @GetMapping(value = "/saleStat")
    public CommonResult<Map<String, Object>> saleStat(
            @RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "cityName", required = false) String cityName) {
        return CommonResult.success(dataService.saleStat(shopId, cityName));
    }

    /**
     * @description: 平台销量、营业额统计      必须要传 店铺 ID
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @GetMapping(value = "/platSaleStat")
    public CommonResult<List<Map<String, Object>>> platSaleStat(Integer shopId) {
        List<Map<String, Object>> dataList = dataService.platSaleStat(shopId);
        return CommonResult.success(dataList);
    }

    /**
     * @description: 地区分布
     * @author: eshen
     * @createTime: 2020/6/4
     */
    @GetMapping(value = "/addrDistribution")
    public CommonResult<Map<String, Object>> addrDistribution() {
        return CommonResult.success(dataService.addrDistribution());
    }

}

