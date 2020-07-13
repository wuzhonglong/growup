// com.wisesoft.bd.chart.service.impl.helper.StatisticsHelper  这是封装的工具类
// 错误日志可以去 98 www/wulong/tomcat8   tail -f logs/catalina.out 查看

// 车辆统计
function getTotal() {
    var sql = " select count(1) total,sum(state = 1) online,sum(state = 0) offline from wlztk_gps_czsssj ";
    var dataMap = $db.use("xodb_main_ModelManager").sql(sql).params({}).map();
    return dataMap;
}

function getDataList() {
    var sql = " select truck_num numberPlate,state statusCode,(case when state = 1 then '在线' else '离线' end) statusText " +
        " ,lng longitude,lat latitude from wlztk_gps_czsssj ";
    var dataList = $db.use("xodb_main_ModelManager").sql(sql).params({}).listMap();
    return dataList;
}

var $ret = {};
var $data = [];
var dataList = getDataList();
var dataMap = getTotal();
if (dataList.length > 0) {
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        $data.push({
            numberPlate: item.numberPlate,
            statusCode: item.statusCode,
            statusText: item.statusText,
            longitude: item.longitude,
            latitude: item.latitude
        })
    }
}
$ret.total = dataMap.total,
$ret.online = dataMap.online,
$ret.offline = dataMap.offline,
$ret.data = $data

// 车辆轨迹
function getDriverName(params) {
    var sql = " select driver_name driverName from wlztk_gps_czsssj where truck_num = :numberPlate ";
    var dataMap = $db.use("xodb_main_ModelManager").sql(sql).params(params).map();
    return dataMap;
}

function handleTimeRange(startTimeStr, endTimeStr) {
    var startTime = $helper.parseDate(startTimeStr, "yyyy-MM-dd HH:mm:ss");
    var endTime = $helper.parseDate(endTimeStr, "yyyy-MM-dd HH:mm:ss");

    var timeMillis = endTime.getTime() - startTime.getTime();
    if (timeMillis < 0) {
        throw new Error("结束时间必须大于开始时间");
    }
    if (timeMillis > 1000 * 60 * 60 * 24 * 7) {
        throw new Error("时间范围不能超过7天");
    }

    return [startTime, endTime];
}

function getDataList(params) {
    var sql = " select update_time time,lng longitude,lat latitude from wlztk_gps_czlssj " +
        " where truck_num = :numberPlate and update_time BETWEEN :startTime and :endTime ";
    var dataList = $db.use("xodb_main_ModelManager").sql(sql).params(params).listMap();
    return dataList;
}

var $ret = {};


var numberPlate = $req.numberPlate;
var dataMap = getDriverName({
    numberPlate: numberPlate
});

var startTimeStr = $req.startTime;
var endTimeStr = $req.endTime;
var dataList = [];
try {
    var timeRange = handleTimeRange(startTimeStr, endTimeStr);
    dataList = getDataList({
        startTime: timeRange[0],
        endTime: timeRange[1],
        numberPlate: numberPlate
    });
} catch (e) {
    $ret.msg = e.message;
}

var $path = [];
if (dataList.length > 0) {
    for (var i = 0; i < dataList.length; i++) {
        var data = dataList[i];
        $path.push({
            time: data.time,
            latitude: data.latitude,
            longitude: data.longitude
        });
    }
}
$ret.driverName = dataMap.driverName;
$ret.path = $path;


