# 1、Linux的介绍



## Linux的版本

- 目前存在**数百个**不同的 Linux 发行版
- CentOS：它专注于提供一个稳定且适用于企业环境的操作系统。
- Fedora：这是一个由社区驱动的发行版，旨在提供最新的开源软件和技术。Fedora 提供了一个稳定且有创新性的操作系统环境，并激励用户参与到开源社区中。
- Ubuntu：这是一个非常受欢迎的桌面和服务器发行版，注重易用性和用户友好性。它基于 Debian，提供了广泛的软件支持和强大的社区生态系统。







# 2、Linux 常用命令 



网络有问题，可能是服务关了

首先：了解Linux主要 以文件系统为主



## **Linux的文件系统目录：**

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





## 常用命令：

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

15. 解压文件 ` tar -zxvf a.tar` 解压至当前目录 （.tar（打包文件扩展名）  、.gz（压缩包扩展名））

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

18. make 会读取名为**Makefile**的文件 （其中包含了程序源代码的编译步骤、依赖关系和相关命令）Make会根据Makefile中的规则来自动化执行编译、链接和安装等操作。

    `make install`命令会执行如下操作：

    1. 将编译好的可执行文件、库文件、头文件、文档等文件复制到指定的目录下。这个目录通常是`/usr/local/bin`、`/usr/local/lib`、`/usr/local/include`等。

19. ps -ef | grep redis 查看指定的进程

20. `kill 进程号` 杀死进程



## Linux下载环境：

Yum 、rpm 都是 Red Hat 系列的 Linux 发行版中的**默认包管理工具**

RPM工具集提供了一组命令行工具，用于处理RPM软件包。常用的RPM工具包括：

1. rpm：**用于在系统上安装、升级、删除和查询软件包**。例如，可以使用`rpm -i package.rpm`命令来安装一个RPM软件包。
2. yum：是基于RPM的发行版中高层次的软件包管理工具，它可以自动解决软件包依赖关系并从软件源中下载和安装RPM软件包。
3. dnf：是Fedora和**最新版本的CentOS中取代yum**的软件包管理工具，它提供了与yum类似的功能，并具有改进的性能和用户体验。

> 在CentOS 8及之后的版本中，DNF已经取代了Yum作为默认的包管理工具



**yum常用命令：**

1. yum install <package_name>  命令安装指定的软件包。

2. yum update  命令升级系统上已安装的所有软件包到最新版本 `yum update <package_name>` 命令只升级指定的软件包。

3. yum search <key_word> 搜索软件包

4. yum list available 列出可用的软件包

5. yum list installed 列出已安装的软件包

6. yum remove <package_name> 删除软件包 ,会移除软件包及其相关的配置文件和依赖项。

7. yum clean all **删除所有下载的软件包**及其元数据

8. yum info <package_name> 显示软件包信息

   





