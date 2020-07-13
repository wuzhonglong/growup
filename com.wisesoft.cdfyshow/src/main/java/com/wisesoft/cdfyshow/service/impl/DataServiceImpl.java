package com.wisesoft.cdfyshow.service.impl;

import com.wisesoft.cdfyshow.enums.FyTypeEnum;
import com.wisesoft.cdfyshow.service.DataService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: yi
 * @Date: 2020/6/3 10:56
 * @Description:
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${images.url}")
    private String url;
    @Value("${images.userPath}")
    private String userPath;
    @Value("${images.scenicPath}")
    private String scenicPath;

    @Override
    public Map<String, Object> shopTypeScale(String cityName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT FY_TYPE AS type,COUNT(*) AS num FROM t_shop ");
        if (cityName == null) {
            sql.append(" GROUP BY FY_TYPE ");
        } else {
            sql.append(" WHERE CITY = '").append(cityName).append("' GROUP BY FY_TYPE ");
        }
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        Integer sum = 0;
        for (Map<String, Object> map : mapList) {
            sum += MapUtils.getInteger(map, "num");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("sum", sum);
        data.put("scale", mapList);
        return data;
    }

    @Override
    public Map<String, Object> leftRectScale(String cityName) {
        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT %s AS name,COUNT(*) AS value FROM t_shop where 1=1");
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(cityName)) {
            params.add(cityName);
            sql.append(" and CITY = ?");
        }
        sql.append(" GROUP BY %s");
        List<Map<String, Object>> levelList = jdbcTemplate.queryForList(String.format(sql.toString(), "FY_LEVEL", "FY_LEVEL"), params.toArray());
        List<Map<String, Object>> areaList = jdbcTemplate.queryForList(String.format(sql.toString(), "AREA_LEVEL", "AREA_LEVEL"), params.toArray());
        List<Map<String, Object>> saleList = jdbcTemplate.queryForList(String.format(sql.toString(), "SALE_TYPE", "SALE_TYPE"), params.toArray());
        Map<String, Integer> levelMap = levelList.stream().collect(Collectors.toMap(item -> MapUtils.getString(item, "name")
                , item -> MapUtils.getIntValue(item, "value", 0)));
        Map<String, Integer> areaMap = areaList.stream().collect(Collectors.toMap(item -> MapUtils.getString(item, "name")
                , item -> MapUtils.getIntValue(item, "value", 0)));
        Map<String, Integer> saleMap = saleList.stream().collect(Collectors.toMap(item -> MapUtils.getString(item, "name")
                , item -> MapUtils.getIntValue(item, "value", 0)));
        Map<String, Integer> levelData = new LinkedHashMap<>();
        Map<String, Integer> areaData = new LinkedHashMap<>();
        Map<String, Integer> saleData = new LinkedHashMap<>();
        levelData.put("国家级", MapUtils.getIntValue(levelMap, "国家级"));
        levelData.put("省级", MapUtils.getIntValue(levelMap, "省级"));
        levelData.put("市（州）级", MapUtils.getIntValue(levelMap, "市（州）级"));
        levelData.put("区（县）级", MapUtils.getIntValue(levelMap, "区（县）级"));

        areaData.put("国定贫困县", MapUtils.getIntValue(areaMap, "国定贫困县"));
        areaData.put("省定贫困县", MapUtils.getIntValue(areaMap, "省定贫困县"));
        areaData.put("非贫困县", MapUtils.getIntValue(areaMap, "非贫困县"));

        saleData.put("独立开设店铺", MapUtils.getIntValue(saleMap, "独立开设店铺"));
        saleData.put("联合设立店铺", MapUtils.getIntValue(saleMap, "联合设立店铺"));
        saleData.put("委托代销产品", MapUtils.getIntValue(saleMap, "委托代销产品"));

        resultMap.put("fyLevel", levelData);
        resultMap.put("areaLevel", areaData);
        resultMap.put("saleLevel", saleData);

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> leftBarScale(String cityName) {
        StringBuilder sql = new StringBuilder();
        if (cityName == null) {
            sql.append(" SELECT TYPE AS plat,COUNT(*) AS num FROM t_online GROUP BY TYPE");
        } else {
            sql.append(" SELECT o.TYPE plat,COUNT(1) num FROM t_shop s, t_online o WHERE s.CITY = '")
                    .append(cityName).append("'  AND s.ID = o.SHOP_ID GROUP BY o.TYPE ");
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql.toString());
        List<Map<String, Object>> res = new ArrayList<>();
        Map<String, Object> otherMap = new HashMap<>();
        int otherTotal = 0;
        for (Map<String, Object> map : maps) {
            if (MapUtils.getString(map, "plat").startsWith("其他")) {
                Integer num = MapUtils.getInteger(map, "num");
                otherTotal += num;
            } else {
                res.add(map);
            }
        }
        if (otherTotal != 0) {
            otherMap.put("plat", "其他平台");
            otherMap.put("num", otherTotal);
            res.add(otherMap);
        }
        return (List<Map<String, Object>>) (res.stream().sorted(Comparator.comparing(
                item -> MapUtils.getInteger((Map) item, "num")
                , Comparator.nullsFirst(Integer::compareTo)).reversed()).collect(Collectors.toList()));
    }

    @Override
    public List<Map<String, Object>> rightMapDistribution() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CITY AS city,FY_LEVEL AS level,count(1) num FROM t_shop GROUP BY CITY,FY_LEVEL ");
        return jdbcTemplate.queryForList(sql.toString());
    }

    @Override
    public Map<String, Object> shopDetail(Integer id, Integer num) {
        StringBuilder shopSql = new StringBuilder();
        shopSql.append(" SELECT SHOP_NAME AS shopName,SALE_TYPE AS saleType,CITY AS city,AREA_LEVEL AS areaLevel, ")
                .append(" REMARK AS remark,(SELECT COUNT(1) FROM t_user WHERE SHOP_ID = ")
                .append(id).append(") AS inheritorNum FROM t_shop WHERE id = ").append(id);
        Map<String, Object> shopInfo = jdbcTemplate.queryForMap(shopSql.toString());
        // TODO 包含项目数量占位
        shopInfo.put("projectNum", null);
        StringBuilder platSql = new StringBuilder();
        platSql.append("SELECT TYPE AS platName,`NAME` AS shopNameOnPlat,URL AS shopLink FROM t_online WHERE SHOP_ID = ").append(id);
        List<Map<String, Object>> plats = jdbcTemplate.queryForList(platSql.toString());
        StringBuilder inheritorSql = new StringBuilder();
        inheritorSql.append(" SELECT IMG AS imagesName, USER_NAME AS name,TEL AS tel,USER_LEVEL AS level FROM t_user WHERE SHOP_ID = ").append(id);
        if (num != null && num != 0) {
            inheritorSql.append(" limit ").append(num);
        }
        List<Map<String, Object>> inheritorInfos = jdbcTemplate.queryForList(inheritorSql.toString());
        //图片封装
        for (Map<String, Object> map : inheritorInfos) {
            String imagesName = MapUtils.getString(map, "imagesName");
            String[] split = imagesName.split(",");
            List<String> names = new ArrayList<>();
            for (String s : split) {
                names.add(url + userPath + "/" + s.trim());
            }
            map.put("imagesName", names);
        }
        shopInfo.put("plats", plats);
        shopInfo.put("inheritorInfos", inheritorInfos);
        return shopInfo;
    }

    @Override
    public List<Map<String, Object>> shopInfos(String cityName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ss.ID AS shopId,ss.SHOP_NAME AS shopName,ss.FY_NAME AS fyName,ss.SALE_TYPE AS saleType,oo.plats,oo.accountMoney,oo.accountNum ")
                .append(" FROM (SELECT s.ID,s.SHOP_NAME,s.FY_NAME,s.SALE_TYPE FROM t_shop s WHERE s.CITY = ?) AS ss,")
                .append(" (SELECT o.SHOP_ID,REPLACE(GROUP_CONCAT(o.TYPE),',','、') AS plats,SUM(o.TOTAL_MONEY) AS accountMoney, ")
                .append(" SUM((o.CTJYXL+o.CTYYXL+o.CTXJXL+o.MJWXXL+o.CTMSXL+o.CTYYXL_MIUSIC+o.CTWDXL+o.CTTYYYZJXL+o.MSXL+o.QYXL)) AS accountNum ")
                .append(" FROM t_online o GROUP BY o.SHOP_ID) AS oo WHERE ss.ID = oo.SHOP_ID order by accountMoney desc");
        List<Object> params = new ArrayList<>();
        params.add(cityName);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), params.toArray());
        return list;
    }

    @Override
    public Map<String, Object> saleStat(Integer shopId, String cityName) {
        StringBuilder sql = new StringBuilder();
        //总览 查询所有销量 营业额 每个类型的总销量
        sql.append(" SELECT SUM(o.TOTAL_MONEY) accountMoney,SUM((CTJYXL+CTYYXL+CTXJXL+MJWXXL+CTMSXL+CTYYXL_MIUSIC+CTWDXL+CTTYYYZJXL+MSXL+QYXL)) AS accountNum, ")
                .append(" SUM(o.CTJYXL) CTJYXL,SUM(o.CTYYXL) CTYYXL,SUM(o.CTXJXL) CTXJXL,SUM(o.MJWXXL) MJWXXL,SUM(o.CTMSXL) CTMSXL, ")
                .append(" SUM(o.CTYYXL_MIUSIC) CTYYXL_MIUSIC,SUM(o.CTWDXL) CTWDXL,SUM(o.CTTYYYZJXL) CTTYYYZJXL,SUM(o.MSXL) MSXL, ")
                .append(" SUM(o.QYXL) QYXL FROM t_online o where 1=1");
        //单个店铺 查询所有销量 营业额 每个类型的总销量
        List<Object> params = new ArrayList<>();
        if (shopId != null) {
            params.add(shopId);
            sql.append(" and o.SHOP_ID = ? GROUP BY o.SHOP_ID ");
        }
        //市州 查询当前市州 所有销量 营业额 每个类型的总销量
        if (cityName != null) {
            params.add(cityName);
            sql.append(" and o.SHOP_ID IN (SELECT ID FROM t_shop WHERE CITY = ?)");
        }
        Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString(), params.toArray());
        Map<String, Integer> resMap = new HashMap<>();
        map.forEach((k, v) -> {
            if (StringUtils.equals("accountMoney", k)) {
                resMap.put("accountMoney", MapUtils.getIntValue(map, "accountMoney"));
            } else if (StringUtils.equals("accountNum", k)) {
                resMap.put("accountNum", MapUtils.getIntValue(map, "accountNum"));
            } else {
                resMap.put(FyTypeEnum.valueOf(k).getNote(), MapUtils.getIntValue(map, k));
            }
        });
        Map<String, Object> result = new LinkedHashMap<>();
        resMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        return result;
    }

    @Override
    public List<Map<String, Object>> platSaleStat(Integer shopId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT TYPE plat,TOTAL_MONEY saleMoney,(CTJYXL+CTYYXL+CTXJXL+MJWXXL+CTMSXL+CTYYXL_MIUSIC+CTWDXL+CTTYYYZJXL+MSXL+QYXL) ")
                .append(" AS saleNum FROM t_online WHERE SHOP_ID = ").append(shopId);
        return jdbcTemplate.queryForList(sql.toString());

    }

    @Override
    public Map<String, Object> addrDistribution() {
        Map<String, Object> resultMap = new HashMap<>();

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_addr where type = 2", new Object[]{});
        List<Map<String, Object>> textList = jdbcTemplate.queryForList("select * from t_addr_text", new Object[]{});
        List<Map<String, Object>> type1List = jdbcTemplate.queryForList("SELECT COUNT(1) account,city,`level` FROM t_addr WHERE type = 1 GROUP BY `level`,city", new Object[]{});
        list.stream().forEach(map -> {
            String id = MapUtils.getString(map, "addr_id");
            String addr = MapUtils.getString(map, "addr");
            String lng = "";
            String lat = "";
            if (StringUtils.isNotBlank(addr) && addr.split(",").length == 2) {
                lng = addr.split(",")[0];
                lat = addr.split(",")[1];
            }
            map.put("lng", lng);
            map.put("lat", lat);
            List<Map<String, Object>> texts = textList.stream().filter(textMap -> StringUtils.equals(id, MapUtils.getString(textMap, "id")))
                    .peek(item -> {
                        String img = MapUtils.getString(item, "img");
                        if (StringUtils.isNotBlank(img)) {
                            item.put("img", url + scenicPath + "/" + img.trim());
                        }
                    }).sorted(Comparator.comparingInt(item -> MapUtils.getIntValue(item, "sort")))
                    .collect(Collectors.toList());
            map.put("texts", texts);
        });

        resultMap.put("type2", list);
        resultMap.put("type1", type1List);
        Map<String, Object> total = new LinkedHashMap<>();
        List<Map<String, Object>> accountLevel1 = type1List.stream().filter(map -> MapUtils.getInteger(map, "level") == 1).collect(Collectors.toList());
        List<Map<String, Object>> accountLevel2 = type1List.stream().filter(map -> MapUtils.getInteger(map, "level") == 2).collect(Collectors.toList());
        List<Map<String, Object>> accountLevel3 = type1List.stream().filter(map -> MapUtils.getInteger(map, "level") == 3).collect(Collectors.toList());
        List<Map<String, Object>> accountLevel4 = type1List.stream().filter(map -> MapUtils.getInteger(map, "level") == 4).collect(Collectors.toList());
        total.put("accountLevel1", accountLevel1.size());
        total.put("accountLevel2", accountLevel2.size());
        total.put("accountLevel3", accountLevel3.size());
        total.put("accountLevel4", accountLevel4.size());
        total.put("accountType2", list.size());
        resultMap.put("total", total);
        return resultMap;
    }
}
