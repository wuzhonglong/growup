/*
 function getDataList(startTime, endTime) {
 var sql = " select t1.iregionalid,t1.szregionalname,count(*) as number from wlztk_kstpw_cpdjsj_tj t1 " +
 " where t1.bysalesvouchertype <> '02' " +
 " and t1.iregionalid in (select iregionalid from t_mid_kst_regional where ilevel = 2 and iparentid = 1 and byisuse = 1) " +
 " and t1.timemillis between unix_timestamp(:startTime) and unix_timestamp(:endTime) " +
 " group by t1.iregionalid order by count(*) desc ";
 var params = {"startTime": startTime, "endTime": endTime};
 return $db.use("xodb_main_ModelManager").sql(sql).params(params).listMap();
 }

 var currentTime = $helper.currentTime();
 var startTime = $helper.startTimeOfYear(currentTime);
 var endTime = $helper.endTimeOfDay();
 var dataList = getDataList(startTime, endTime);

 var $ret = [];
 for (var i = 0; i < dataList.length; i++) {
 var dataMap = dataList[i];
 var name = dataMap.SZREGIONALNAME;
 $ret.push({
 name: name,
 value: dataMap.NUMBER || 0
 });
 }*/


function getDataList() {
    return $helper.getService("tencentLbsServiceImpl").userprofileintervalOrderByProvince("5");
}
var dataList = getDataList();

var $ret = [];
for (var i = 0; i < dataList.length; i++) {
    var dataMap = dataList[i];
    var name = dataMap.province;
    $ret.push({
        name: name,
        value: dataMap.percent || 0
    });
};
