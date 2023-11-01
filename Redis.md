# `过渡`

# 1.Nosql介绍

- NOSQL(not only SQL 不仅仅是sql) 还有一种说法是 **非关系数据库**

- 方便扩展，数据之间没有关系

- 不需要事先设计数据库

- 本质上是一个内存的key-value缓存这个没什么好说的，这是nosql数据库的标准**特点之一**

  

> ## 为什么要用NOSQL ？？？

1. 数据库的发展过程： **优化数据结构和索引--> 文件缓存（IO）---> Memcached（高性能的、具有分布式内存对象的缓存系统）**

2. 早些年MyISAM数据库引擎：实现了表锁，当我查询一条数据的时候，所把整个表锁起来，不利于高并发

3. 然后转战为InnoDB数据库引擎: 实现了行锁

   

> ## NOSQL的分类：

1. KV键值对数据库  ：`redis`（c语言编写）**、memecache、tair** 

   应用于内容缓存、处理大数据量的高访问负载、日志等
   查找速度快但是数据无结构化 

2. 文档型数据库 ：**ConthDB、`MongoDB` （**基于分布式文件存储的数据库，C++编写，主要用于处理大量文档；它是一种介于关系型数据库和非关系型数据库的中间产品，是nosql中功能最丰富、**最像关系型数据库的非关系型数据库**）
   应用于web应用
   数据结构要求不严格、表结构可变、不需要预定义表结构但查询性能不高且缺少统一查询语言

3. 列存储数据库
   **`HBase`（大数据）、Cassandra**
   应用于分布式文件系统
   查找速度快、可扩展性强但功能相对局限

4. 图关系数据库（不是存图形，而是存关系，比如：朋友圈、社交网络、广告推荐）
   **`Neo4j`、InfoGrid**
   应用于社交网络、推荐系统
   可以利用图结构相关的算法但是计算时需要全部图，导致不太好做分布式集群

   


# `过渡`



# Redis 

**版本：redis-7.2.2**

**介绍：**

> Redis（Remote Dictionary Server )，即远程字典服务。
>
> 是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、**Key-Value数据库**，并提供多种语言的API。
>
> 为了保证效率，**数据都是缓存在内存中**
>
> redis会**周期性的把更新的数据写入磁盘**或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。
>
> 默认端口号为 `6379`



**有什么用？？？**

- 计时器、计数器(eg：浏览量)
- 地图信息分析



**`redis服务器` 怎么启动？？** 

- 服务器执行程序+**配置文件**文件路径 (需要修改配置文件)

  ```shell
  [root@txs bin]# redis-server ./redis.conf  #启动服务器
  ```

- 再启动客户端使用`set`存放数据，get 获取数据

  ```shell
  [root@txs bin]# redis-cli 
  127.0.0.1:6379> set name fengshao 
  OK
  127.0.0.1:6379> keys *    #查看所有的key
  1) "name"
  127.0.0.1:6379> get name
  "fengshao"
  
  #当设置密码后
  [root@txs bin]# redis-cli 
  127.0.0.1:6379> ping
  (error) NOAUTH Authentication required.
  127.0.0.1:6379> keys *
  127.0.0.1:6379> auth 密码
  OK
  127.0.0.1:6379> keys *
  1) "name"
  
  #可以直接设置密码 redis-cli -a 密码 2>/dev/null    (2>/dev/null 可以消除警告)
  #Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe. 就是说可能不安全
  
  127.0.0.1:6379> shutdown # 可以关闭服务器程序
  #远程关闭
  redis-cli -a 密码 shutdown
  ```

  



**redis的配置文件：**

1. `bind 0.0.0.0` 指定服务监听的网络地址 ，意思是对那个网络地址放行 0.0.0.0 表示任意

   `#bind 127.0.0.1 -::1`: 这是注释掉的代码，以"#"开头。它指示服务绑定到本地回环地址（127.0.0.1）和IPv6的回环地址（::1）。回环地址是指向本地计算机的地址，一般用于本地测试和回环通信。

2. `port 6379` 指定端口号

3. `daemonize yes` 是否开启后台启动

4. `protected-mode yes`  是否开启保护模式 关闭后 没有配置身份验证也能连接 （不知道意思是不是不要密码也能连接）

5. `databases 16` 默认数据库的数量

6. `requirepass 369` 设置redis 密码



> 当目录下没有makefile文件，会报错 make: *** 没有指明目标并且找不到 makefile。 停止。

**使用 make  && make install (Linux自带的系统命令) 后会`/usr/local/bin`生成以下文件** 

1. redis-benchmark  性能测试工具
2. redis-check-aof  修复有问题的AOF文件
3. redis-check-rdb  
4. redis-cli  客户端程序
5. redis-sentinel  redis集群使用
6. redis-server  服务器程序

