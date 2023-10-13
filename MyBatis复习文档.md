

# 1.MyBatis概述

发展历程

- MyBatis本是apache的一个开源项目iBatis，2010年这个项目由apache software foundation迁移到了google code，并且改名为MyBatis。2013年11月迁移到Github。

  

> - MyBatis本质上就是对JDBC的封装，通过MyBatis完成CRUD。
> - MyBatis在三层架构中负责持久层的，属于**持久层框架**



怎么简化：

```java
//1、获取SqlSessionFactoryBuilder  
SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//2、获取核心配置流文件       
InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
//3、获取SqlSessionFactory 
SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
//4、获取SqlSession
SqlSession sqlSession = sqlSessionFactory.openSession();
//5、通过接口动态生成类
StudentDao studentMapper = sqlSession.getMapper(StudentDao.class);
//6、调用接口中对应的方法	
List<Student> list = studentMapper.selectStudentByName("风少");
//SqlSession对事物的管理需要手动提交  
sqlSession.commit();
```



## mybatis-config.xml 核心配置文件

```xml
<configuration>

    <!--导入数据库配置信息-->
    <properties resource="mybatisDB.properties"/>

    <!--开启驼峰命名-->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    
    <!--起别名-->
    <typeAliases>
        <!--这个com.txs.day430.dao类的别名是 aaa 不区分大小写-->
        <typeAlias type="com.txs.day430.dao.UserDao" alias="aaa"/>
        <!--这个com.txs.day430.dao 包下的所有类的别名"简单类名"-->
        <package name="com.txs.day430.dao"/>
    </typeAliases>

    <!--配置分页插件-->
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
    </plugins>

    <!--环境配置-->
    <environments default="production">
        <!--环境1-->
        <environment id="production">
            <!--事务管理JDBC-->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>

    </environments>

    <mappers>
        <!-- 将包内的映射器接口实现全部注册为映射器  -->
        <package name="com.java.txs.mapper"></package>
    </mappers>

</configuration>
```





# 2.OMR 对象关系映射

对象关系映射就是 ：数据库数据表（记录） 对应 java对象

- MyBatis属于半自动化ORM框架。
- Hibernate属于全自动化的ORM框架。

![image-20230703134845334](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703134845334.png)





# 3.Mybatis的核心对象



**总结 ：**

S**qlSessionFactoryBuilder -**- 通过build( “流” )方法 创建-->>  **SqlSessionFactory**  -- 通过openSession()方法--> > SqlSession





## 3.1.SqlSessionFactoryBuilder对象的作用

> SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先配置的 Configuration 实例来构建出 SqlSessionFactory 实例

```java
 SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2. 创建SqlSessionFactory对象
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);

方法二 MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法（用它）
    
    String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```



## 3.2.SqlSessionFactory对象 看成数据库的连接池

```java
SqlSession session = sqlSessionFactory.openSession() 创建SqlSession对象
```



## 3.3.SqlSession

> 注意1：默认采用的事务管理器transactionManager是：JDBC。JDBC事务默认是不提交的，需要手动提交
>
> 但是在jdbc语句中事务是自动提交的
>
> conn.setAutoCommit(true); 开启事务自动提交 
>
> conn.setAutoCommit(false);关闭事务的自动提交（Mybatis默认是它）
>
> 注意区分配置文件和jdbc语句的事务 

没有开启事务，只有执行一条就提交一条（执行但不提交）

![image-20230703142456395](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703142456395.png)







# 4.工具插件



##    4.1.单元测试JUnit

- 用法 @Test注解 **注意加载方法上**



##    4.2.日志框架logback

标准日志也可以用，但是配置不够灵活，可以集成其他的日志组件，例如：log4j，logback等。

- 第一步:导入依赖 
- 第二步:配置日志格式的文件（文件名为 依赖名.xml）

```xml
日志输出的格式
<?xml version="1.0" encoding="UTF-8"?>

<configuration debug="false">
    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
    <!-- 按照每天生成日志文件 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${LOG_HOME}/TestWeb.log.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>100MB</MaxFileSize>
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

- 导入依赖

  ```XML
  <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
  <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.4.8</version>
      <scope>test</scope>
  </dependency>
  ```

  

## 4.3.解析XML文件的四种方式

> **XML 的解析方式分为四种：**
>
> **1、DOM 解析；**
>
> **2、SAX 解析；**
>
> **3、JDOM 解析；**
>
> **4、DOM4J 解析。**
>
> **其中前两种属于基础方法，是官方提供的平台无关的解析方式；**
>
> **后两种属于扩展方法，它们是在基础的方法上扩展出来的，只适用于 Java 平台。**

 

- ### 4.3.1.DOM 解析

> *Java JDK 里都自带了，无需再导包*
>
> 通过 DOM (**文档对象模型**)接口，应用程序可以在任何时候访问 XML 文档中的任何一部分数据，因此，这种利用 **DOM 接口的机制**也被称**作随机访问机制**。

```java
    //创建一个DocumentBuilderFactory的对象
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    //创建一个DocumentBuilder的对象
    DocumentBuilder db = dbf.newDocumentBuilder();

    //加载xml文件 获取DOM对象
    Document document = db.parse("books.xml");

    //获取所有book节点的集合
    NodeList bookList = document.getElementsByTagName("book");
```



### 4.3.2.**SAX 解析**  看不懂

>  **Simple APIs for XML，也即 XML 简单应用程序接口。**
>
> **与 DOM 不同，SAX 提供的访问模式是一种顺序模式，这是一种快速读写 XML 数据的方式。**

```
        //创建一个SAXParserFactory对象
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        //通过factory获取SAXParser实例
        SAXParser parser = factory.newSAXParser();
        //
        SAXParserHandler handler = new SAXParserHandler();
        
        parser.parse("books.xml", handler);
```

### 4.3.3.**JDOM 解析**

> - **仅使用具体类，而不使用接口。**
> - **API 大量使用了 Collections 类。**

### 4.3.4.dom4j使用方法

```xml
1.引入dom4j的依赖
    <groupId>org.dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>2.1.3</version>
2.导入jaxen依赖
    <groupId>jaxen</groupId>
    <artifactId>jaxen</artifactId>
    <version>1.2.0</version>
```

> 不引入jaxen依赖 会报错 缺少类

![image-20230705111421403](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230705111421403.png)

### 4.3.5.解析详情

```java
    * Document类有一个方法
    * selectSingleNode("xpath") 选择单节点
    *
    * Element类有四个方法
    * element()
    * elements()
    * attributeValue("property标签的name,value等等的属性名....")
    * getTextTrim()
    
        //xml解析对象
        SAXReader reader=new SAXReader();
        //获取xml文档对象
        Document document = reader.read(ClassLoader.getSystemResourceAsStream("mybatis-config.xml"));
        //获取根标签
        Element rootElement = document.getRootElement();
        // xpath路径 (标签路径)  单个/根路径下的xml路径  //当前标签的路径
        String xpath="/configuration/environments";
		//使用 document.selectSingleNode(xpath); 寻找特定的标签路径       
        Element environments = (Element) document.selectSingleNode(xpath);
        System.out.println(environments.attribute("default").getValue());
      
```

##  ***4.4.PageHelper分页插件

- 第一步 导入依赖

  ```xml
  <dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.3.1</version>
  </dependency>
  ```
  
- 第二步 mybatis-config.xml配置

  ```
  使用插件 插件所在的包
  <plugins>
    <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
  </plugins>
  ```

- 第三步      // 开启分页

  配置文件
  
  ```xml
  <!--使用分页插件-->
  <select id="selectByPage" resultType="Student">
      select * from student
  </select>
  
  <!--不需要写成-->
  <select id="selectAllByPage" resultType="Student">
      select * from student limit #{startIndex},#{pageSize}
  </select>
  
  ```
  
  
  
  ```java
      int startPage=2;//开始页数
      int pageSize=3;//每页数目
      //初始化页数
      PageHelper.startPage(startPage,pageSize);
  
      // 随便执行一个查询语句 会被插件检查到 然后生成分页信息
      List<Car> cars = mapper.selectAll();
  
      // 通过查询语句，获取分页信息对象
      PageInfo<Car> pageInfo = new PageInfo<>(cars, 5（底部导航栏数）);
  ```




##  ***4.5.Lombok 自动生成pojo类的基本方法

- 导入依赖

  ```xml
  <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
  <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.28</version>
      <scope>provided</scope>
  </dependency>
  ```

- 常用的注解

> @Data                           包括set ，get ，toString ，无参 ，hashCode ,equals
> @AllArgsConstructor    所有的参数的构造方法
> @NoArgsConstructor    无参构造

##  4.6.Log4j2日志框架

标准日志也可以用，但是配置不够灵活，可以集成其他的日志组件，例如：log4j，logback等。

- 导入依赖

  ```xml
  <!--log4j2的依赖-->
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>2.19.0</version>
  </dependency>
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j2-impl</artifactId>
      <version>2.19.0</version>
  </dependency>
  ```

- 加入日志配置文件 (**文件名固定为：log4j2.xml，文件必须放到类根路径下。**)

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <configuration>
      <loggers>
          <!--
              level指定日志级别，从低到高的优先级：
                  TRACE < DEBUG < INFO < WARN < ERROR < FATAL
                  trace：追踪，是最低的日志级别，相当于追踪程序的执行
                  debug：调试，一般在开发中，都将其设置为最低的日志级别
                  info：信息，输出重要的信息，使用较多
                  warn：警告，输出警告的信息
                  error：错误，输出错误信息
                  fatal：严重错误
          -->
          <root level="DEBUG">
              <appender-ref ref="spring6log"/>
              <appender-ref ref="RollingFile"/>
              <appender-ref ref="log"/>
          </root>
      </loggers>
  
      <appenders>
          <!--输出日志信息到控制台-->
          <console name="spring6log" target="SYSTEM_OUT">
              <!--控制日志输出的格式-->
              <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss SSS} [%t] %-3level %logger{1024} - %msg%n"/>
          </console>
  
          <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，适合临时测试用-->
          <File name="log" fileName="d:/spring6_log/test.log" append="false">
              <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
          </File>
  
          <!-- 这个会打印出所有的信息，
              每次大小超过size，
              则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，
              作为存档-->
          <RollingFile name="RollingFile" fileName="d:/spring6_log/app.log"
                       filePattern="log/$${date:yyyy-MM}/app-%d{MM-dd-yyyy}-%i.log.gz">
              <PatternLayout pattern="%d{yyyy-MM-dd 'at' HH:mm:ss z} %-5level %class{36} %L %M - %msg%xEx%n"/>
              <SizeBasedTriggeringPolicy size="50MB"/>
              <!-- DefaultRolloverStrategy属性如不设置，
              则默认为最多同一文件夹下7个文件，这里设置了20 -->
              <DefaultRolloverStrategy max="20"/>
          </RollingFile>
      </appenders>
  </configuration>
  ```






# 5.MyBatis核心配置文件详解

## 5.1.需求SQL语句中的值不应该写死（应用场景sql语句传参）

> 解决方法**在xml配置文件（SQL映射文件中）使用 #{} 来完成传值，#{} 等同于JDBC中的 ? ，#{}就是占位符**

```txt
如果采用map集合传参，#{} 里写的是map集合的key，如果key不存在不会报错，数据库表中会插入NULL。
如果采用POJO传参，#{} 里写的是get方法的方法名去掉get之后将剩下的单词首字母变小写（例如：getAge对应的是#{age}，getUserName对应的是#{userName}），如果这样的get方法不存在会报错。

原理就是通过反射，获取数据（值）然后 组成sql语句
```

> **注意：其实传参数的时候有一个属性parameterType，这个属性用来指定传参的数据类型，不过这个属性是可以省略的**
>
> **当占位符只有一个的时候，${} 里面的内容可以随便写。**

## 5.2.查询的小注意点 必须要写resultType="查询出的类名"

> 对于一个查询语句来说，你需要指定它的“结果类型 ”或者“结果映射”resultType

```xml
查询一条数据
<select id="selectCarById" resultType="com.powernode.mybatis.pojo.Car">
  select * from t_car where id = #{id}
</select>

查询多条数据
<!--虽然结果是List集合，但是resultType属性需要指定的是List集合中元素的类型。-->
<select id="selectCarAll" resultType="com.powernode.mybatis.pojo.Car">
</select>
```

## 5.3.插入map集合参数的注意点

> **map集合**的时候  可以允许key写错 ，因为他是掉方法的 名字不对就**返回 null** 
>
> **对象的属性**的时候  不能写错 **写错就程序错误**  因为这个是以这个名字去调用这个对象的getXXX()方法  如果没有那不就报错了吗



## 5.4.mybatis-config.xml 解析



### 1.configuration 根标签，表示布局,配置



### 2.properties，引入外部配置

```xml
<properties resource="org/mybatis/example/config.properties"></properties>
```



### 3.environments  环境（多个）**SqlSessionFactory 实例只能选择一种环境。**

```java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
```

- #### 	environment 环境变量  可以定义多个，有效是一个

  ```xml
  <!--环境配置-->
  <environments default="production">
      <!--环境1-->
      <environment id="production">
          <!--事务管理JDBC-->
          <transactionManager type="JDBC"/>
          <dataSource type="POOLED">
              <property name="driver" value="${jdbc.driver}"/>
              <property name="url" value="${jdbc.url}"/>
              <property name="username" value="${jdbc.username}"/>
              <property name="password" value="${jdbc.password}"/>
          </dataSource>
      </environment>
  
  </environments>
  ```

  

### 	4.transactionManager  事务管理器  

```java
transaction接口有二个实现类 ： 
    org.apache.ibatis.transaction.jdbc.JdbcTransaction
    org.apache.ibatis.transaction.managed.ManagedTransaction
```



### 5.dataSource 数据源

作用：

- 获取连接对象 ，然后是否使用连接池 type="[UNPOOLED|POOLED|JNDI]"）
-  JNDI第三方实现的连接池



数据源类型

- **UNPOOLED** 不使用连接池

  ```xml
  UNPOOLED 类型的数据源仅仅需要配置以下 5 种属性：
  
  driver – 这是 JDBC 驱动的 Java 类全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
  url – 这是数据库的 JDBC URL 地址。
  username – 登录数据库的用户名。
  password – 登录数据库的密码。
  defaultTransactionIsolationLevel – 默认的连接事务隔离级别。
  defaultNetworkTimeout – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。
  ```

  

- **POOLED** 实现利用“池” 

  ```xml
  除了上述提到 UNPOOLED 下的属性外
  1.poolMaximumActiveConnections 连接对象的数量，默认值：10
  2.poolMaximumIdleConnections 空闲连接数，如果超过，则会真的关闭Connction连接对象
  3.poolMaximumCheckoutTim  默认值：20000 毫秒（即 20 秒）意思就是当连接对象不够了 用20s来判断线程池是否有空闲 时间结束后线程池还没有空闲一个，则强制将某一个线程返回线程池
  4.poolTimeToWait 打印状态日志并重新尝试获取一个连接
  ```

  

### 6.mappers   SQL 映射

- 使用相对于类路径的资源引用

  ```xml
  <mappers>
    <mapper resource="org/mybatis/builder/PostMapper.xml"/>
  </mappers>
  ```

  

- 使用完全限定资源定位符（URL）

  ```xml
  <mappers>
    <mapper url="file:///var/mappers/PostMapper.xml"/>
  </mappers>
  ```



- 将**包内**的映射器接口全部注册为映射器

  ```xml
  <mappers>
    <package name="org.mybatis.builder"/>
  </mappers>
  ```



- 使用映射器接口实现类的完全限定类名

  ```xml
  <mappers>
    <mapper class="org.mybatis.builder.PostMapper"/>
  </mappers>
  ```

  

### 7.typeAliases 类型别名 别名不区分大小写。

- **namespace不能起别名**

````XML
○ type属性：指定给哪个类起别名
○ alias属性：别名。

方法一
<typeAliases>
  <typeAlias type="com.powernode.mybatis.pojo.Car" alias="Car"/>
</typeAliases>

方法二（主要用它）
<typeAliases>
 <package name="com.powernode.mybatis.pojo"/> 将pojo包下的所以类都自动起别名
</typeAliases>   

方法三
@Alias("author")
````

-  mybatis中内建了一些类型别名

  ```xml
  _byte	byte
  _short	short
  _int	int
  _integer	int
  _double	double
  _float	float
  _boolean	boolean
  string	String
  byte	Byte
  ```



###   8.settings（设置） 

- 这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为

比如：

1. logImpl ： STDOUT_LOGGING 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。
2. cacheEnabled：true    全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。
3. mapUnderscoreToCamelCase ：true  开启**驼峰命名**自动映射
4. lazyLoadingEnabled:true 开启**延迟加载**  （全局配置）



```xml
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
    <setting name="cacheEnabled" value="true"/>
     <setting name="mapUnderscoreToCamelCase" value="true"/>
    <setting name="lazyLoadingEnabled" value="true"/>
</settings>
```





# 6.MyBatis对象作用域以及事务问题



## SqlSessionFactoryBuilder

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。



## SqlSessionFactory

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。



## SqlSession (一个线程对应一个SqlSession)

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。 下面的示例就是一个确保 SqlSession 关闭的标准模式：





# 7.mybatis 基本实现原理 核心是  ‘’动态生成类‘’



## 7.1.使用javassist生成类 （过渡一下 让你了解动态代理）



```java
public class Test {
    public static void main(String[] args) throws Exception{

        //获取类池
        ClassPool classPool = ClassPool.getDefault();
        //找到类 放入类池
        CtClass ctClass = classPool.makeClass("com.txs.Test");
        //制造方法
        CtMethod method = CtMethod.make("public void beginTrans(){System.out.println(\"hello world\");}", ctClass);
        //给该类添加方法
        ctClass.addMethod(method);
        //将该类变成字节码文件 放在jVM中 （内存中）
        ctClass.toClass();
        //从JVM 中获取 如果没有才会创建
        Class<?> aClass = Class.forName("com.txs.Test");
        Object o = aClass.newInstance();
        Method beginTrans = aClass.getDeclaredMethod("beginTrans");
        //调用方法
        beginTrans.invoke(o);
        System.out.println(ctClass.getName());//com.txs.Test

    }
}
```







## 7.2.mybatis内部已经实现了接口代理机制

注意：使用mybatis内部的接口的动态机制时 需要遵循他的规则

- namespace的值必须是**接口的类名字**
- id为**接口的方法名**

```xml
<mapper namespace="接口的名字
    <insert id="方法名
        insert into student values(#{id},#{name},#{sex},#{age},#{school})
    </insert>
</mapper
```



原理：动态代理+反射机制

```java
//获取代理对象
AccountDao accountDao = (AccountDao)sqlSession.getMapper(AccountDao.class);
```





# 8.mybatis 的小技巧？ 小知识点	

## 8.1.#{}和${}区别

> **#{}  ‘值’** 
>
> **${}   值** 你传什么就是什么

![image-20230706042544755](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230706042544755.png)

## 8.2. 插入数据时获取自动生成的主键	

```xml
useGeneratedKeys="true" 使用生成的密钥 
keyProperty="对象的主键属性"  键属性 
<insert id="insert" keyProperty="nubId" useGeneratedKeys="true>
    insert into studensid VALUES(#{nubId},#{name},#{sex},#{schoolId})
</insert>
```

获取自动生成的主键

```java
mapper.insertUseGeneratedKeys(car);
System.out.println(car.getId());
```



# 9.MyBatis参数处理

## 9.1 单个简单类型参数 

> 直接传就行了

> 简单类型包括：
>
> - **byte short int long float double char**
> - **Byte Short Integer Long Float Double Character**
> - **String**
> - **java.util.Date**
> - **java.sql.Date**



## 9.2 Map参数 

> map用**key** 原理通过get('key')来获取value



## 9.3 实体类参数

> 用属性 通过getXXX()方法获取属性值



## 9.4 多参数实际上是个map 用key用arg0来表示 不友好 

> 可用的参数包括[arg1, arg0, param1, param2]

> 以此类推
>
> - arg0 是第一个参数
> - param1是第一个参数
> - arg1 是第二个参数
> - param2是第二个参数

原理 如下

```java
接口 List<Student> selectByNameAndSex(String name, Character sex);
  StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
类中 List<Student> students = mapper.selectByNameAndSex("张三", '女');

Map<String,Object> map = new HashMap<>();
map.put("arg0", name);
map.put("arg1", sex);
map.put("param1", name);
map.put("param2", sex);
```



## 9.5解决多个参数的变量名问题 使用注解 指定map集合的key

#### 语法

> **（命名参数）** value="变量名" 还记得不，注解如果是value的时候**可以省略**
>
>   **List<Student> selectByNameAndAge(@Param(value="name") String name, @Param("age") int age);**

> 设置注解之后key的取值变了 arg0 arg1 失效了

![image-20230707004628791](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230707004628791.png)

@Param注解的实现原理

> **底层判断了是否有param注解**

![image-20230707005455455](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230707005455455.png)



>  **接下来就是三个核心的对象了**
>
> ![image-20230707010445450](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230707010445450.png)
>
> **1.SoredMap  names**
>
> key         value
>
> 0              第一个属性的名字（我估计应该是解析注解了，才能获取到注解上的那个value="属性的名字"）
>
> 1              第二个属性的名字
>
> **2.Map            param**
>
> key                                                                                      value
>
> names.value的值 (第一个属性的名字)                    args[names.key]
>
> names.value的值 (第二个属性的名字)                    args[names.key]   





![image-20230707010017247](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230707010017247.png)

!(C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230707010328895.png)

> **总结**
>
> 先用一个Object[] args 数组来接受了传递的参数值
>
> 第二步  通过解析Param注解 将注解的value值存放在names 集合中的value 中， names 集合中的key 存放下标
>
> 第三步 最终的参数 param集合 结合上诉两个对象
>
> 它是将 names的value作为key， 然后将 names的key取出 作为下标去访问args 数组  进而获得值
>
> 有了这个**param集合**之后拼接sql语句就可以实现了



# 10.查询小知识



- 1.当查询的记录条数是多条的时候，必须使用集合接收。如果使用单个实体类接收会出现异常。

- 2.当返回Map 字段名做key，字段值做value 。规定的

- 3.当返回List<Map> 就是查询多条记录， 一个记录一个map

- 4.当返回Map<String,Map> 拿id（主键）做key，以后取出对应的Map集合时更方便

- 5.返回总记录条数  返回值resultType="long" 为long 或者int   sql语句写对 



# 11.属性名和数据库的映射怎么解决！！ 

> 查询结果的列名和java对象的属性名对应不上怎么办？
>
> - 第一种方式：as 给列起别名
> - 第二种方式：使用resultMap进行结果映射 

```xml
<!--
        resultMap:
            id：这个结果映射的标识，作为select标签的resultMap属性的值。
            type：结果集要映射的类。可以使用别名。
-->
<resultMap id="carResultMap" type="car">
  <!--对象的唯一标识，官方解释是：为了提高mybatis的性能。建议写上。-->
  <id property="id" column="id"/>
  <result property="carNum" column="car_num"/>
  <!--当属性名和数据库列名一致时，可以省略。但建议都写上。-->
  <!--javaType用来指定属性类型。jdbcType用来指定列类型。一般可以省略。-->
  <result property="brand" column="brand" javaType="string" jdbcType="VARCHAR"/>
  <result property="guidePrice" column="guide_price"/>
  <result property="produceTime" column="produce_time"/>
  <result property="carType" column="car_type"/>
</resultMap>

<!--resultMap属性的值必须和resultMap标签中id属性值一致。-->
<select id="selectAllByResultMap" resultMap="carResultMap">
  select * from t_car
</select>
```





# 12.动态SQL

1. if标签

   ```xml
   <if test="条件">
       条件成立会拼接,否则跳过
   </if>
   ```

2. where标签 ：解决了**前面**多余的and|or的问题

   ```xml
   有一个非常重要的作用  "自动去除某些条件前面多余的and或or
   <where>
       <if test="brand != null and brand != ''">
         and brand like #{brand}"%"
       </if>
   
       <if test="carType != null and carType != ''">
         and car_type = #{carType}
       </if>
   </where>
   ```

3. trim标签 ：解决都不成立时，多余的where

   ```xml
   trim标签的属性：
   ● prefix：在内容添加前缀
   ● suffix：在内容后添加后缀
   ● prefixOverrides：去掉前缀
   ● suffixOverrides：去掉后缀
   
   select * from t_car
     <trim prefix="where" suffixOverrides="and|or">
         下面内容
       <if test="brand != null and brand != ''">
         brand like #{brand}"%" and
       </if>
       <if test="guidePrice != null and guidePrice != ''">
         guide_price >= #{guidePrice} and
       </if>
       <if test="carType != null and carType != ''">
         car_type = #{carType}
       </if>
     </trim>
   如果所有条件为空，where不会被加上
   ```

4. set标签：用来生成set关键字，同时去掉最后多余的“,”

   ```xml
   update t_car
   <set>
   <if test="carNum != null and carNum != ''">car_num = #{carNum},</if>
   <if test="brand != null and brand != ''">brand = #{brand},</if>
   <if test="carType != null and carType != ''">car_type = #{carType},</if>
   </set>
   ```

5. foreach 完成批量删除

   ```xml
   <!--
   collection：集合或数组
   item：集合或数组中的元素
   separator：分隔符
   open：foreach标签中所有内容的开始
   close：foreach标签中所有内容的结束
   -->
   <delete id="deleteBatchByForeach">
     delete from t_car where id in
     <foreach collection="ids" item="id" separator="," open="(" close=")">
       #{id}
     </foreach>
   </delete>
   ```

   



# 13.MyBatis的高级映射及延迟加载 

​		高级映射针对**查询多张表** 

-  多对一：谁在前 谁是主表 

  - 方法一：级联属性映射

    ```xml
    <resultMap id="标识符" type="Student">
        <id property="id" column="id"/>
        <result property="clazz.cname" column="cname"/>
    </resultMap>-->
    
    <!--相互绑定 resultMap="标识符" -->
    <select id="selectBySid" resultMap="标识符">
        select s.*, c.* from t_student
    </select>
    ```

  - 方法二：association 有...联系 内部绑定

    ```xml
    <resultMap id="标识符" type="Student">
            <id property="id" column="id"/>
            <association property="clazz" javaType="Clazz">
                <id property="cid" column="cid"/>
                <result property="cname" column="cname"/>
            </association>
    </resultMap>
    ```

  - 方法三 ：分步查询 （执行二条sql语句）

    ```xml
    <resultMap id="标识符" type="Student">
        <id property="id" column="id"/>
        <!--
    		column属性作为这条子sql语句的条件。
    		select：填写需要执行那个接口的sql语句,填写sqlid==接口名+方法名
    		fetchType加载方式： lazy（延迟加载）
     		-->
        <association property="clazz"
                     select="com.java.txs.mapper.ClazzDao.selectByCid"
                     column="cid"
                     fetchType="lazy"/>
    </resultMap>
    ```



**延迟加载**

 fetchType= "lazy" 开启延迟加载  、"eager" 关闭延迟加载，（局部）只对当前sql语句开启延迟加载

- 延迟加载是？？
  - 将暂时访问不到的数据可以先不查询。提高程序的执行效率。
  - 没程序没调用到，不加载，就是不执行sql语句



### mybatis中如何开启全局的延迟加载呢？

| 设置名             | 描述                                                         | 有效值        | 默认值 |
| :----------------- | :----------------------------------------------------------- | :------------ | :----- |
| lazyLoadingEnabled | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 **特定关联关系**中可通过设置 `fetchType` 属性来覆盖该项的开关状态。 | true \| false | falas  |



**一对多：** 

- 通常是在一的一方中有List集合属性(怎么给这个list集合赋值)

- 有个注意点bug 用collection sql 语句得用select * from

两种实现方式：

- 第一种方式：collection

  ```xml
  <resultMap id="clazzResultMap" type="Clazz">
    <id property="cid" column="cid"/>
    <result property="cname" column="cname"/>
    <collection property="stus" ofType="Student">
      <id property="sid" column="sid"/>
      <result property="sname" column="sname"/>
    </collection>
  </resultMap>
  
  <select id="selectClazzAndStusByCid" resultMap="clazzResultMap">
    select * from t_clazz c join t_student s on c.cid = s.cid where c.cid = #{cid}
  </select>
  ```



- 第二种分步查询：

  ```xml
  <resultMap id="clazzResultMap" type="Clazz">
    <id property="cid" column="cid"/>
    <result property="cname" column="cname"/>
    <!--主要看这里-->
    <collection property="stus"
           <!--sqlId-->
           select="com.powernode.mybatis.mapper.StudentMapper.selectByCid"
       	 <!--要传的属性名-->
            column="cid"/>
  </resultMap>
  
  <!--sql语句也变化了-->
  <select id="selectClazzAndStusByCid" resultMap="clazzResultMap">
    select * from t_clazz c where c.cid = #{cid}
  </select>
  ```
  




# 14.缓存

认识缓存：

1. cache 在内存中  有数据修改就清空缓存了 所有缓存都一样
2. 缓存的作用：通过减少IO的方式，来提高程序的执行效率。
3. 查找缓存的顺序    二级缓存 --> 一级缓存  --> 数据库
4. 有**update、insert、delete**、sql语句就清空缓存了

mybatis缓存包括：

- 一级缓存：将查询到的数据存储到SqlSession中。

  - 同一个Connction连接对象

- 二级缓存：将查询到的数据存储到SqlSessionFactory中。

- 或者集成其它第三方的缓存：比如EhCache【Java语言开发的】、Memcache【C语言开发的】等。

  

### 一级缓存  

- 一级缓存默认是开启的。不需要做任何配置。
- 只要使用同一个SqlSession对象执行同一条SQL语句，就会走缓存。

```java
//清空一级缓存
sqlSession.clearCache();
```

思考为什么 作用域在SqlSession ?? 设计好了 



### 二级缓存 

- 二级缓存的范围是SqlSessionFactory
- SqlSessionFactory里面封装了很多关于配置文件的信息 程序启动就存在 程序结束就销毁

使用二级缓存需要具备以下几个条件：

```txt
1. <setting name="cacheEnabled" value="true">开启缓存、默认就是true，无需设置。
2. 在需要使用二级缓存的SqlMapper.xml文件中添加配置：<cache/>
3. 使用二级缓存的实体类对象必须是可序列化的，也就是必须实现java.io.Serializable接口
4. SqlSession对象关闭或提交之后，一级缓存中的数据才会被写入到二级缓存当中。此时二级缓存才可用。
```



#### MyBatis集成EhCache

- 第一步 导入依赖

  ```xml 
  <!--mybatis集成ehcache的组件-->
  <dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.2</version>
  </dependency>
  ```

- 第二步 在类的根路径下新建echcache.xml文件，并提供以下配置信息。

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
           updateCheck="false">
      <!--磁盘存储:将缓存中暂时不使用的对象,转移到硬盘,类似于Windows系统的虚拟内存-->
      <diskStore path="e:/ehcache"/>
    
      <!--defaultCache：默认的管理策略-->
      <!--eternal：设定缓存的elements是否永远不过期。如果为true，则缓存的数据始终有效，如果为false那么还要根据timeToIdleSeconds，timeToLiveSeconds判断-->
      <!--maxElementsInMemory：在内存中缓存的element的最大数目-->
      <!--overflowToDisk：如果内存中数据超过内存限制，是否要缓存到磁盘上-->
      <!--diskPersistent：是否在磁盘上持久化。指重启jvm后，数据是否有效。默认为false-->
      <!--timeToIdleSeconds：对象空闲时间(单位：秒)，指对象在多长时间没有被访问就会失效。只对eternal为false的有效。默认值0，表示一直可以访问-->
      <!--timeToLiveSeconds：对象存活时间(单位：秒)，指对象从创建到失效所需要的时间。只对eternal为false的有效。默认值0，表示一直可以访问-->
      <!--memoryStoreEvictionPolicy：缓存的3 种清空策略-->
      <!--FIFO：first in first out (先进先出)-->
      <!--LFU：Less Frequently Used (最少使用).意思是一直以来最少被使用的。缓存的元素有一个hit 属性，hit 值最小的将会被清出缓存-->
      <!--LRU：Least Recently Used(最近最少使用). (ehcache 默认值).缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存-->
      <defaultCache eternal="false" maxElementsInMemory="1000" overflowToDisk="false" diskPersistent="false"
                    timeToIdleSeconds="0" timeToLiveSeconds="600" memoryStoreEvictionPolicy="LRU"/>
  
  </ehcache>
  ```

- 第三步 修改SqlMapper.xml文件中的<cache/>标签，添加type属性

  > **<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>**



# 15.MyBatis的注解式开发

简单语句使用注解开发

复杂语句：动态sql类的使用xml开发

1. @insert

   ```java
   @Insert(value="insert into t_car values(null,#{carNum},#{brand}")
   int insert(Car car);
   ```

2. @Delete

   ```java
   @Delete("delete from t_car where id = #{id}")
   int deleteById(Long id);
   ```

3. @Update

   ```java
   @Update("update t_car set car_num=#{carNum},brand=#{brand} where id=#{id}")
   int update(Car car);
   ```

4. @Select

   ```java
   @Select("select * from t_car where id = #{id}")
   //映射关系，按照规范来，就可以不用写了
   @Results({
       @Result(column = "id", property = "id", id = true),
       @Result(column = "car_num", property = "carNum"),
       @Result(column = "brand", property = "brand"),
       @Result(column = "guide_price", property = "guidePrice"),
       @Result(column = "produce_time", property = "produceTime"),
       @Result(column = "car_type", property = "carType")
   })
   Car selectById(Long id);
   ```

   



# 16.JSR303

- @Validated 开启校验

```xml
<!--定义规则-->
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
</dependency>

<!--The Bean Validation API is on the classpath but no implementation could be found
    Bean验证API在类路径中，但找不到实现
	对上面规则的实现
-->
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
</dependency>
```



