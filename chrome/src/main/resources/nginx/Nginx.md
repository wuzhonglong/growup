题外话：本地项目市可以同一个项目多环境同时启动的 修改下启动名称 改下profile

# 初识Nginx
    server {
        # 这个就表示监听的IP和端口 wlyypt.eimm.wisesoft.net.cn:8099
            listen       8099;
            server_name  wlyypt.eimm.wisesoft.net.cn;
            
        #  如果路径是wlyypt-index则根据以下规则处理
        #  wlyypt.eimm.wisesoft.net.cn:8099/wlyypt-index/*
            location /wlyypt-index{
                add_header Access-Control-Allow-Origin *;
                root /www/wulong/wlzy;  # 这个表示跳转到本地指定路径 wlyypt.eimm.wisesoft.net.cn:8099/www/wulong/wlzy/wlyypt-index/*
            }
            
        #  同理如果路径是    wlyypt
            location /wlyypt{
                proxy_pass http://172.16.9.98:7777/wlyypt;  // 进行代理 跳转到现在这个Ip+port
                proxy_set_header Host $host:8099;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            }
        }


 