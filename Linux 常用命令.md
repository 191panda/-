# Linux 常用命令 

网络有问题，可能是服务关了

首先：了解Linux主要 以文件系统为主



**Linux的文件系统：**

/bin        二进制文件，**系统常规命令**
/boot       系统启动分区，系统启动时读取的文件
**/dev        设备文件**
**/etc        大多数配置文件**
**/home       普通用户的家目录**
/lib        32位函数库
/lib64      64位库
/media      手动临时挂载点
/mnt        手动临时挂载点
**/opt        第三方软件安装位置**
/proc       进程信息及硬件信息
/root       临时设备的默认挂载点
/sbin       系统管理命令
**/srv        数据**
/var        数据
/sys        内核相关信息
/tmp        临时文件
**/usr        用户相关设定**



**Linux的终端介绍：**

```
示例：root@app00:~# 
root    //用户名，root为超级用户
@       //分隔符
app00   //主机名称
~       //当前所在目录，默认用户目录为~，会随着目录切换而变化，例如：（root@app00:/bin# ，当前位置在bin目录下）
#       //表示当前用户是超级用户，普通用户为$，例如：（"yao@app00:/root$" ，表示使用用户"yao"访问/root文件夹）
```

![image-20231019192648278](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231019192648278.png)





常用命令：

1. 立刻关机 shutdown -h now

2. 两分钟后关机 shutdown -h 2

3. 取消关机 shutdown -c 

4. 切换目录 cd 

5. 查看目录 ls、ls -a(查看包括隐藏文件) 、ll 、dir

6. 查看当前目录（pwd）

7. 创建目录  `mkdir 名字`    在当前目录下创建目录  `mkdir  /bin/tools`   在指定目录下创建一个名为tools的目录

8. 创建文件 `touch a.txt `

9. 编辑文件 `vi 文件名`  、vim

   ```
     vi 文件名              //打开需要编辑的文件
     --进入后，操作界面有三种模式：命令模式（command mode）、插入模式（Insert mode）和底行模式（last line mode）
     命令模式
     -刚进入文件就是命令模式，通过方向键控制光标位置，
     -使用命令"dd"删除当前整行
     -使用命令"/字段"进行查找
     -按"i"在光标所在字符前开始插入
     -按"a"在光标所在字符后开始插入
     -按"o"在光标所在行的下面另起一新行插入
     -按"："进入底行模式
     插入模式
     -此时可以对文件内容进行编辑，左下角会显示 "-- 插入 --""
     -按"ESC"进入底行模式
     底行模式
     -退出编辑：      :q
     -强制退出：      :q!
     -保存并退出：    :wq
     ## 操作步骤示例 ##
     1.保存文件：按"ESC" -> 输入":" -> 输入"wq",回车     //保存并退出编辑
     2.取消操作：按"ESC" -> 输入":" -> 输入"q!",回车     //撤销本次修改并退出编辑
   ```

10. 查看文件  cat a.txt 

11. 删除**目录与文件**  rm  -rf  无论下面有没有目录

    ```
    rm -rf /*    '/' 表示根目录 ‘*’所有东西  r是删除此目录下的目录  di将根目录下的所有文件全部删除【慎用！相当于格式化系统】
    ```

12. 修改目录名  `mv 当前目录名 新目录名`、移动目录的位置 

13. 拷贝整个目录   `cp /usr/tmp/tool /opt`

14. 打包文件  `tar -zcvf 打包压缩后的文件名 要打包的文件`

15. 解压文件 ` tar -zxvf a.tar` 解压至当前目录

16. 查看网络 `ifconfig` windows 是 `ipconfig`

17. 修改IP 

    ```
      修改网络配置文件，文件地址：/etc/sysconfig/network-scripts/ifcfg-eth0
      ------------------------------------------------
      主要修改以下配置：  
      TYPE=Ethernet               //网络类型
      BOOTPROTO=static            //静态IP
      DEVICE=ens00                //网卡名
      IPADDR=192.168.1.100        //设置的IP
      NETMASK=255.255.255.0       //子网掩码
      GATEWAY=192.168.1.1         //网关
      DNS1=192.168.1.1            //DNS
      DNS2=8.8.8.8                //备用DNS
      ONBOOT=yes                  //系统启动时启动此设置
      -------------------------------------------------
      修改保存以后使用命令重启网卡：service network restart
    ```

    







在Linux中的知识点 ：

1. 打包与解压 ： .tar（打包文件扩展名）  、.gz（压缩包扩展名）



Linux下载：

yum:

```
  说明：安装插件命令
  yum install httpd      //使用yum安装apache 
  yum update httpd       //更新apache 
  yum remove httpd       //卸载/删除apache 
```



rpm :

```
  说明：插件安装命令
  rpm -ivh httpd-2.2.3-22.0.1.el5.i386.rpm      //使用rpm文件安装apache 
  rpm -uvh httpd-2.2.3-22.0.1.el5.i386.rpm      //使用rpm更新apache 
  rpm -ev httpd                                 //卸载/删除apache 
```



