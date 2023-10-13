# maven配置文件

# 1.springframework 类

- spring-webmvc

  - ```xml
     <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-webmvc</artifactId>
                <version>6.0.11</version>
    </dependency>
    ```

- spring

  - ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.0.10</version>
    </dependency>
    
    ```




- 使用其他的数据源 需要引入 spring-jdbc

  ```xml
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>6.0.9</version>
  </dependency>
  ```

  

# 2.日志类

- logback

  - ```xml
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.4.8</version>
        <scope>test</scope>
    </dependency>
    ```

  -  logback.xml配置文件

  - ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    
    <configuration debug="false">
        <!-- 控制台输出 -->
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
                <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
                <pattern>[%thread] %-5level %logger{50} - %msg%n</pattern>
            </encoder>
        </appender>
        <!-- 按照每天生成日志文件 -->
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <!--日志文件输出的文件名-->
                <FileNamePattern>${LOG_HOME}/TestWeb.log.%d{yyyy-MM-dd}.log</FileNamePattern>
                <!--日志文件保留天数-->
                <MaxHistory>1</MaxHistory>
            </rollingPolicy>
            <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
                <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            </encoder>
            <!--日志文件最大的大小-->
            <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
                <MaxFileSize>10MB</MaxFileSize>
            </triggeringPolicy>
        </appender>
    
        <!--mybatis log configure-->
        <logger name="com.apache.ibatis" level="TRACE"/>
        <logger name="java.sql.Connection" level="DEBUG"/>
        <logger name="java.sql.Statement" level="DEBUG"/>
        <logger name="java.sql.PreparedStatement" level="DEBUG"/>
    
        <!-- 日志输出级别,logback日志级别包括五个：TRACE < DEBUG < INFO < WARN < ERROR -->
        <root level="DEBUG">
            <appender-ref ref="STDOUT"/>
            <appender-ref ref="FILE"/>
        </root>
    
    </configuration>
    ```



- 下面是springmvc中使用的、上面是spring中使用

- **log4j.properties**

  - ```xml
    log4j.rootLogger=DEBUG, stdout
    log4j.appender.stdout=org.apache.log4j.ConsoleAppender
    log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%c] - %m%n
    ```
    
  - slf4j依赖

  - ```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.7</version>
    </dependency>
    
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>2.0.7</version>
    </dependency>
    ```





# 3.javaEE           9 ---> jarkarta

- jarkarta

  ```xml
  <dependency>
      <groupId>jakarta.servlet</groupId>
      <artifactId>jakarta.servlet-api</artifactId>
      <version>6.0.0</version>
        <!--表示不参与打包-->
      <scope>provided</scope> 
  </dependency>
  ```

- javax    javaEE8 以前

  ```xml
  <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
        <!--表示不参与打包-->
      <scope>provided</scope>
  </dependency>
  ```

- jarkarta 注解包

  ```xml
   <dependency>
      <groupId>jakarta.annotation</groupId>
      <artifactId>jakarta.annotation-api</artifactId>
      <version>2.1.1</version>
  </dependency>
  ```

  

# 4.前端渲染 thymeleaf

```xml
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
    <version>3.1.1.RELEASE</version>
</dependency>
```

- thymeleaf-spring6 整合包

- ```xml
  <dependency>
      <groupId>org.thymeleaf</groupId>
      <artifactId>thymeleaf-spring6</artifactId>
      <version>3.1.1.RELEASE</version>
  </dependency>
  ```

- 需要依赖的包

![image-20230729160216479](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230729160216479.png)

```xml
<dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-api</artifactId>
          <version>2.0.7</version>
      </dependency>

      <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-log4j12</artifactId>
          <version>2.0.7</version>
</dependency>
```

- log4j.properties

  - ```xml
    log4j.rootLogger=DEBUG, stdout
    log4j.appender.stdout=org.apache.log4j.ConsoleAppender
    log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%c] - %m%n
    ```

  - 

# 5.自动转换为Json格式的字符串

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```





# 6.自动补充pojo对象代码

```xml
<!--Lombok自动生成pojo类-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.28</version>
    <scope>provided</scope>
</dependency>
```





# 7.单元测试

```xml
 <!--测试依赖-->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```



# 8.数据库信息

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/mybatis?serverTimezone=UTC
jdbc.username=root
jdbc.password=369
```





# 9.mybatis依赖

```xml
<!--mybatis依赖-->
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.5.10</version>
</dependency>
```



# 10.mysql驱动

```xml
<!--mysql驱动 -->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.15</version>
</dependency>
```



# 11.将对象转成json格式

```xml
<!--导入jackson的依赖。将对象转成json-->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```



# 12.德鲁伊连接池

````xml
<!--使用druid德鲁伊连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.18</version>
</dependency>
````



# 13.maven 镜像配置

```xml
<mirrors>
  <mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>central</mirrorOf>
    <name>Nexus aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public</url>
  </mirror>
</mirrors>
```





# 14.dev-tools 帮助快速实现"伪热部署"

- Ctrl+F9 重写在加载编译资源

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```





# 15.banner.txt设置 SpringBoot启动样式

```xml
                                   #        #           #                                  #         #    #########
                                   ###########          ##                             #   ##         ##   #     #
                                   ##      ##           ##                             ##  ##         ##   ########
                                   ##      ##        #  ##  #                         ##   ##  #           ##   ##
                                   ###   # ##        ## ##   ##                       ###########       #  ##   ##
                                   ## # #####       ##  ##    ##                     ##    ##        ##### #######
                                   ## #### ##       ##  ##    ###                    #     ##          ## ##    # #
                                   ##  ##  ##      ##   ##  #  #                    #      ##    #     ## ##########
                                   ##  ##  ##     #     ## ###        ##            ###############    ## ## ##  ##
                                   ## #### ##           #  ##        ####                  ##          ## #########
                                   ## #######             ##         ####                  ##          ## ## ##  ##
                                   ## #  ####            ##           ##                   ##          ## ## ##  ##
                                  ## #   # ## #         ##             #                   ##          ## #########
                                  ##       ## #        ##             #                    ##          ####      #
                                  #         ####     ##              #                     ##        ### ###########
                                 #           ##   ###                                      #          #    ########


                                        ////////////////////////////////////////////////////////////////////
                                        //                          _ooOoo_                               //
                                        //                         o8888888o                              //
                                        //                         88" . "88                              //
                                        //                         (| ^_^ |)                              //
                                        //                         O\  =  /O                              //
                                        //                      ____/`---'\____                           //
                                        //                    .'  \\|     |//  `.                         //
                                        //                   /  \\|||  :  |||//  \                        //
                                        //                  /  _||||| -:- |||||-  \                       //
                                        //                  |   | \\\  -  /// |   |                       //
                                        //                  | \_|  ''\---/''  |   |                       //
                                        //                  \  .-\__  `-`  ___/-. /                       //
                                        //                ___`. .'  /--.--\  `. . ___                     //
                                        //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
                                        //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
                                        //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
                                        //      ========`-.____`-.___\_____/___.-`____.-'========         //
                                        //                           `=---='                              //
                                        //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
                                        //            佛祖保佑       永不宕机     永无BUG                   //
                                        ////////////////////////////////////////////////////////////////////
```

