package com.wisesoft.cdfyshow.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yi
 * @Date: 2020/6/3 10:55
 * @Description:
 */
public interface DataService {

    Map<String, Object> shopTypeScale(String cityName);


    Map<String, Object> leftRectScale(String cityName);


    List<Map<String, Object>> leftBarScale(String cityName);


    List<Map<String, Object>> rightMapDistribution();


    Map<String, Object> shopDetail(Integer id, Integer num);

    List<Map<String, Object>> shopInfos(String cityName);

    Map<String, Object> saleStat(Integer shopId, String cityName);

    List<Map<String, Object>> platSaleStat(Integer shopId);

    Map<String, Object> addrDistribution();
}
