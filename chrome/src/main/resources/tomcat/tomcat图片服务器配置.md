# tomcat-conf-server.xml配置
<Context path="/images" docBase="/www/wulong/uploads/images" reloadable="false"></Context>		
<Context path="/files" docBase="/www/wulong/uploads/files" reloadable="false"></Context>

yml配置
attachment:
  image:
    dir: /www/wulong/uploads/images         //这个就是上面配置的图片目录
    url: http://172.16.9.85:5050/images     //这个是地址路径
  file:
    dir: /www/wulong/uploads/files
    url: http://124.161.16.163:8099/files