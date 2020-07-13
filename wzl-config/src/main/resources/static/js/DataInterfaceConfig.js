


var sql = " SELECT n.scenic_name name,n.weather,d.longitude,d.latitude, " +
    " yw.light illumination,yw.temperature,yw.humidity,yw.windDirection,yw.windSpeed, " +
    " yw.pressure mpa,yw.rain rainfall FROM " +
    " (SELECT m.scenic_name,y.scenic_id,y.device_sn,w.weather FROM t_mid_scenic_mapping m " +
    " INNER JOIN t_mid_scenic_yingka_mapping y ON m.id = y.scenic_id " +
    " INNER JOIN wlztk_ywk_weather w ON y.scenic_id = w.`code` " +
    " WHERE w.day_time = (select date_format(now(),'%Y-%m-%d'))) AS n " +
    " LEFT JOIN wlztk_kstpw_yingka_device d ON n.device_sn = d.deviceSN " +
    " LEFT JOIN wlztk_kstpw_yingka_weather yw ON n.device_sn = yw.devicesn "
var dataList = $db.use("xodb_main_ModelManager").sql(sql).listMap();
var $ret = [];
//var $data = [];
var dataMap;
for (var i in dataList){
    dataMap = dataList[i];
    $ret.push({
        longitude: dataMap.longitude || 0,
        latitude: dataMap.latitude || 0,
        illumination: dataMap.illumination || 0,
        pm25: 0,
        name: dataMap.name || '',
        weather: dataMap.weather || '',
        temperature: dataMap.temperature || 0,
        ion: 0,
        humidity: dataMap.humidity || 0,
        windDirection: dataMap.windDirection || '',
        windSpeed: dataMap.windSpeed || 0,
        mpa: dataMap.mpa || 0,
        waterLevel: '',
        rainfall: dataMap.rainfall || 0,
        waterQuality: '',
        currentSpeed: ''
    })
}

