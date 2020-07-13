# 首先在项目中代开 gitBash here
lenovo@DESKTOP-6GTNQMT MINGW64 /e/wlyypt-back/wlyypt-index (master)
$ git init
Initialized empty Git repository in E:/wlyypt-back/wlyypt-index/.git/

lenovo@DESKTOP-6GTNQMT MINGW64 /e/wlyypt-back/wlyypt-index (master)
$ git remote add origin "http://git.wisesoft.net.cn/fuxi/wlyypt-index.git"

lenovo@DESKTOP-6GTNQMT MINGW64 /e/wlyypt-back/wlyypt-index (master)
$ git add .	

lenovo@DESKTOP-6GTNQMT MINGW64 /e/wlyypt-back/wlyypt-index (master)
$ git commit -m "第一次提交"
[master (root-commit) 67062cc] 第一次提交
...

lenovo@DESKTOP-6GTNQMT MINGW64 /e/wlyypt-back/wlyypt-index (master)
$ git push origin master
Enumerating objects: 781, done.
Counting objects: 100% (781/781), done.
Delta compression using up to 2 threads
Compressing objects: 100% (757/757), done.
Writing objects: 100% (781/781), 5.21 MiB | 2.16 MiB/s, done.
Total 781 (delta 172), reused 0 (delta 0)
remote: Resolving deltas: 100% (172/172), done.
To http://git.wisesoft.net.cn/fuxi/wlyypt-index.git
 * [new branch]      master -> master

lenovo@DESKTOP-6GTNQMT MINGW64 /e/wlyypt-back/wlyypt-index (master)
#推送完成后 记得忽略文件并提交