# git 命令大全

**常用命令**

- git init 初始化git  
- git status 查看当前文件状态 
- git add . 将当前文件的状态变成 committed 被提交的状态

- git commit -m "第二次提交"  提交到本地仓库中
- git push 提交到远程仓库



**与远程仓库建立连接**

- git remote -v 查看是否与远程仓库建立连接
- git remote add <remote_name> <remote_url>  与远程仓库建立连接



**分支**

- git branch 查看当前所在的分支
- git branch -r 查看所有远程跟踪分支。

- git branch -m 旧名字 新名字

- git checkout -b new_branch 创建新的分支 ，并切换、

- git checkout 分支名字

- git merge 分支名字 

开发的时候 ，先编写代码 到dev分支中，然后有了初始化模型之后，再去创建main分支，这个时候main分支是有之前分支的信息的，但之后的信息是没有的

![image-20231017190441053](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231017190441053.png)





克隆| 下载

- git clone -b <分支名> <仓库URL>





