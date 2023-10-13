

# 											1.spring 概述

- **从 Spring Framework 6.0 开始， Spring 需要 Java 17+。**

- Spring 框架分为多个模块。应用程序可以选择所需的模块。 **核心是核心容器的模块，包括配置模型和 依赖注入机制。**除此之外，Spring 框架提供了基础 支持不同的应用程序架构，包括消息传递、事务数据和 持久性和网络。它还包括基于 Servlet 的 Spring MVC Web 框架，并且 同时，Spring WebFlux 响应式 Web 框架。



## spring的使用：

1、获取容器中的bean对象

 ```java
 public void test(){
     //ClassPathXmlApplicationContext ->类路径Xml应用程序上下文
     ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
     StudentDao studentDao = applicationContext.getBean("studentDao", StudentDao.class);
     studentDao.selectStudent();
 }
 ```



### \<constructor-arg > 这里是构造注入 只要能构建对象的方法 里面的参数都可以赋值

spring.xml的配置：

```xml
1、bean对象
<bean id="studentDao" class="com.txs.java.mapper.imp.StudentDaoImp"/>
2、依赖注入的方法
<bean id="studentService" class="com.txs.java.service.StudentService">
        //构造方法注入  这里是构造注入 只要能构建对象的方法 里面的参数都可以赋值
       <constructor-arg ref="studentDao"/>
    	//setXXX()方法注入
   	   <property name="haihao">
           //数组
            <array>
                <value>抽烟</value>
                <value>喝酒</value>
            </array>
        </property>

        <property name="students">
            //集合
            <list>
                <ref bean="studentDao"/>
                <ref bean="studentDao"/>
            </list>
        </property>

        <property name="map">
            //map集合
            <map>
                <entry key="addr" value="广西"/>
                <!--如果是ref 则是对象、如果没有就是普通的字符串
                   <entry key="studentDao" value-ref="studentDao"/>
                -->
                <entry key="studentDao" value-ref="studentDao"/>
            </map>
        </property>
</bean>

3、命名空间 作用简化配置
p命名空间 setXX()方法的注入原理
c命名空间 构造方法的注入原理
 <bean id="studentService" class="com.txs.java.service.StudentService" 
       p:name-ref="" p:name="风少"
       c:_0="" c:_0-ref="studentDao">
     
     
util命名空间 作用 让配置复用
    <util:list id="list">
            <value>风少</value>
            <value>风少3</value>
    </util:list>
context命名空间 引入外部资源
<context:property-placeholder location="jdbc.properties"/>


4、自动配置 (先将所有的bean实例化 再去解析bean中的属性)
  <!--byType表示根据类型自动装配-->
   <bean id="accountService" class="com.powernode.spring6.service.AccountService" autowire="byType"/>
  	根据属性的名字自动装配  属性的名字要等于 bean的id
   <bean id="userService" class="com.powernode.spring6.service.UserService" autowire="byName"/>

5、bean对象的单例 
<bean id="studentDao" class="com.txs.java.mapper.imp.StudentDaoImp" scope="singleton"/>
  <!-- 范围scope ="prototype" 原型 每调用一次getBean()获得新的Bean对象-->
  <!-- 范围scope ="singleton" 单例 每调用一次getBean()都是同一个Bean对象-->
```



## **生成Bean对象的方法**

**工厂方法实例化**：三种方式

```java
1、在bean标签中使用 **factory-method**属性 factory-method="方法名 " 
该方法必须为 静态方法 否则报错 简单工厂模式 -->工厂对象是静态方法
<bean class="com.txs.java.设计模式.简单工厂设计模式.Vip" factory-method="方法名" id="vip"/>

2、使用工厂方法设计模式 方法必须是实例方法 工厂方法设计模式 --> 有抽象工厂 用的是实例方法
<bean class="com.txs.java.设计模式.工厂方法设计模式.Vip"  id="工厂"/>
<bean id="vip" factory-method="方法名" factory-bean="工厂" />

3、实现 FactoryBean<T> 接口 ，将该类作为 bean导入后容器，容器会自动调用 getObject() 实例化所需要的对象
public class PersonFactoryBean implements FactoryBean<T> {
    
    @Override
    public Person getObject() throws Exception {
        //返回实例化对象  写你自己需要的对象 比如时间对象 规定好格式 然后传过去
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        // true表示单例
        // false表示原型
        return true;
    }
}
```



**注解式声明bean：**

- @Component  依赖注入组件 ，组件扫描。以下三个是这个注解的别名
- @Controller 表现层
- @Service  业务层
- @Repository 持久层



**给bean的属性赋值：**

- @Value 给**简单类型**赋值 

- @Autowired  **给非简单类型赋值**

- @Qualifier("bean id") 这个必须和@Autowirted 注解 一起使用 这个value 指定通过 名字进行自动匹配  如果不写 则会报错

  **以上三个是来自 package org.springframework.beans.factory.annotation; 是spring 框架内置的** 

- @Resource(name = "id")//什么都不写会根据属性名进行依赖注入 ，如果名字对不上，则会通过类型自动匹配（jdk 8自带）

![image-20231006164350083](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231006164350083.png)

**配类：**

- @Configuration 声明这个类是个配置类 

- @ComponentScan({"com.powernode.spring6.dao", "com.powernode.spring6.service"})  组件扫描 扫描注解在那个类中 



## 代理模式：

动态代理：

- JDK动态代理技术：**只能代理接口。**
- CGLIB动态代理技术：CGLIB(Code Generation Library)是一个开源项目。是一个强大的，高性能，高质量的Code生成类库，它可以在运行期扩展Java类与实现Java接口。它既可以**代理接口，又可以代理类**，底层是通过继承的方式实现的。性能比JDK动态代理要好。（底层有一个小而快的字节码处理框架ASM。）
- Javassist动态代理技术：Javassist是一个开源的**分析、编辑和创建Java字节码的类库**。s是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。它已加入了开放源代码JBoss 应用服务器项目，通过使用Javassist对字节码操作为JBoss实现动态"AOP"框架。



>   mybatis内部已经实现了Javassist动态代理技术
>
>   Spring的AOP使用的动态代理是：JDK动态代理 + CGLIB动态代理技术。



## 动态代理实现AOP

- Spring默认使用JDK动态代理(代理接口)，如果要代理某个类，这个类没有实现接口，就会切换使用CGLIB。



- 切点表达式：**execution([访问控制权限修饰符] 返回值类型 [全限定类名]方法名(形式参数列表) [异常])**

```java
 @Before("execution( * com.java.day721.Controller.AaController.*())")
```

**spring怎么实现AOP** 

- 使用**导入切面依赖** aspects，写一个**配置类** 开启动态代理 @**EnableAspectJAutoProxy**(proxyTargetClass = true) （这个注解是开启动态代理，可以不写里面的内容默认是jdk动态代理 ）、扫描等等，然后定义一个切面类 可以使用注解 @Aspect +  @Component 或者使用配置文件 <bean ...> +  <aop:aspect ref="myAspectJ"> 将其定义为切面类，在切面类中编写切点方法 ，在方法上可以用注解 或者 配置文件 去指定将该方法插入到哪个类去（用切点表达式）



- 注解开发

  ```java
  //配置类
  @Configuration
  @ComponentScan("com.java.day721")
  //EnableAspectJAutoProxy 启用AspectJ自动代理   proxyTargetClass代理目标类 所以是 cglib动态代理
  @EnableAspectJAutoProxy //开启动态代理 必须要写
  public class Config {
  
  }
  
  @Component
  @Aspect//方面 切面=切点+ 通知
  public class MyAspectJ {
    //方法修饰符 没写，就是4个权限都包括。
  
      //整个注解+方法 就是一个切面
      @Around("execution(* com.java.day721.Controller..*(..))")//切点
      public void anQuan2(ProceedingJoinPoint joinPoint) throws Throwable {//通知
          System.out.println("第二道关卡开始");
          Object proceed = joinPoint.proceed();
          System.out.println("第二道关卡结束");
      }
  
      @After("execution( * com.java.day721.Controller.AaController.*())")
      public void anQuan3(){
          System.out.println("最终吗！");
      }
      @Before("execution( * com.java.day721.Controller.AaController.*())")
      public void anQuan1(){
          System.out.println("非常安全 放心吧！！");
      }
  
  }
  
  
  public class AopAspect {
  
      @Test
      public void test1(){
          //基于注解开发 自定义了配置类
          ApplicationContext c = new AnnotationConfigApplicationContext(Config.class);
          AaController aaController = c.getBean("aaController", AaController.class);
          aaController.a();
  
          //使用xml配置文件开发
          System.out.println("分界线=============----------------------");
          ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
          AaController aaController1 = context.getBean("aaController",AaController.class);
          aaController1.a();
  
      }
  
  ```



- xml配置

  ```xml
  设置并开启动态代理 并将动态代理设置为cglib 这个是代理类的
  <aop:aspectj-autoproxy  proxy-target-class="true"/> true 
  
  <!--纳入spring bean管理-->
      <bean id="aaController" class="com.java.day721.Controller.AaController"/>
      <bean id="myAspectJ" class="com.java.day721.MyAspectJ"/>
  
      <!--aop配置-->
      <aop:config>
          <!--切点表达式-->
          <aop:pointcut id="p" expression="execution(* com.java.day721.Controller..*(..))"/>
          <!--定义切面-->
          <aop:aspect ref="myAspectJ">
              <aop:after method="anQuan3" pointcut-ref="p"/>
              <aop:before method="anQuan1" pointcut-ref="p"/>
              <aop:around method="anQuan2" pointcut-ref="p"/>
              <aop:after method="anQuan3" pointcut-ref="p"/>
          </aop:aspect>
      </aop:config>
  ```



## spring对事物的实现

启动事务

```java
@EnableTransactionManagement
@Transactional //对该类或方法 启用事务
@Transactional(propagation=Propagation.NESTED)//事务的传播特性 （同一个对象中 不存在事务的传播）
```

一共有七种传播行为：propagation 传播

- REQUIRED：支持当前事务，如果不存在就新建一个(默认)**【没有就新建，有就加入】**
- SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行**【有就加入，没有就不管了】**
- MANDATORY：必须运行在一个事务中，如果当前没有事务正在发生，将抛出一个异常**【有就加入，没有就抛异常】**
- REQUIRES_NEW：开启一个新的事务，如果一个事务已经存在，则将这个存在的事务挂起**【不管有没有，直接开启一个新事务，开启的新事务和之前的事务不存在嵌套关系，之前事务被挂起】**
- NOT_SUPPORTED：以非事务方式运行，如果有事务存在，挂起当前事务**【不支持事务，存在就挂起】**
- NEVER：以非事务方式运行，如果有事务存在，抛出异常**【不支持事务，存在就抛异常】**
- NESTED：如果当前正有一个事务在进行中，则该方法应当运行在一个嵌套式事务中。被嵌套的事务可以独立于外层事务进行提交或回滚。如果外层事务不存在，行为就像REQUIRED一样。**【有事务的话，就在这个事务里再嵌套一个完全独立的事务，嵌套的事务可以独立的提交和回滚。没有事务就和****REQUIRED一样。****】**



前提都是 需要事务管理器

```xml
<!--事务管理器-->
<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
    <property name="dataSource" ref="druidDataSource"/>数据源
</bean>

<!-- 通过配置文件-- 》启动事务管理，然后选择对应的事务管理器-->
<tx:annotation-driven transaction-manager="transactionManager"/>
```







# ===================分割线=====================

# 2.spring 的介绍？？

- **spring的核心是IoC和AOP**



## 2.1、**软件开发的七大原则**

1. 开闭原则 OCP （**最核心的**）
   - 其核心思想是：要面向接口编程，不要面向实现编程。
2. 里氏替换原则 LSP  
   -   她提出：继承必须确保超类所拥有的性质在子类中仍然成立
3. 依赖倒置原则 DIP  
   -  **高层模块**不应该依赖**低层模块**，两者都应该依赖其抽象；
   -  抽象不应该依赖细节，细节应该依赖抽象
   -  其核心思想是：**要面向接口编程，不要面向实现编程。** 
4. 单一职责原则 SRP 
   - 规定一个类应该**有且仅有**一个引起它变化的原因，否则类应该被拆分
5. 接口隔离原则 ISP
   - 要求程序员尽量将臃肿庞大的接口拆分成更小的和更具体的接口 
   - 要为各个类建立它们需要的**专用接口**，而**不要试图去**建立一个**很庞大的接口供所有依赖**它的类去调用。
6. 迪米特法则 LoD
   - 如果两个软件实体**无须直接通信**，那么就不应当发生直接的相互调用，可以通过第三方转发该调用。
   - 其目的是降低类之间的耦合度，提高**模块的****相对独立性。**
7. 合成复用原则（Composite Reuse Principle，CRP）
   - 它要求在软件复用时，**要尽量先使用组合或者聚合等关联关系来实现**，
   - 其次才考虑使用**继承关系来实**现。



## 2.2、spring 是什么？？

### **spring 是一个轻量级的控制反转(IoC)和面向切面(AOP)的容器框架。**

- spring 中**结合实现了** **IoC** 和**AOP**（**这是思想 一个新型的设计模式 但不是软件开发七大原则**） 



### **spring 怎么实现的 控制反转(IoC)和面向切面(AOP)呢**

- IoC  是什么呢 （想一想xml 文件解析 bean标签）
  - 不在自己new 对象
  - 不再去管理对象和对象之前的关系
- 依赖注入DI  是实现控制反转的具体方式 （实现依赖注入有两个方式）
  - set 方法 注入
  - 构造方法 注入
- 依赖是什么意思 注入又是什么意思
  - 依赖是：对象之间的关系 
  - 注入是：一种手段 ，让对象和对象产生关系 （想想 xml 文件） set 注入 和 构造方法注入



# 3.spring 8大模块 

![image-20231010113129391](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231010113129391.png)



# 4.spring 的jar包

1. **spring-core-5.3.9.jar：字节码（这个是支撑程序运行的jar包）**
   - spring-core-5.3.9-javadoc.jar：代码中的注释
   
   - spring-core-5.3.9-sources.jar：源码
   
2. **spring-context-5.3.9.jar**     **实现了 IoC思想**
   - 这个jar 文件为Spring 核心提供了大量扩展。
   - 可以找到使用Spring ApplicationContext特性时所需的全部类

```
当加入spring context的依赖之后，会关联引入其他依赖：
spring aop：面向切面编程
spring beans：IoC核心
spring core：spring的核心工具包
spring jcl：spring的日志包
spring expression：spring表达式
```



# 5.spring 的核心接口  ApplicationContext  解析xml文件



**原生的创建容器的方法  ：**

![image-20231010220551154](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231010220551154.png)



**升级之后的：多了一些功能**

![image-20231010140852190](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231010140852190.png)

-  **ApplicationContext  接口 在 org.springframework.context.ApplicationContext包下**
- **他的父类 BeanFactory接口  在org.springframework.beans.factory包下**
  - **getBean("id")    返回值是 Object** 
  - **getBean("id",指定类型.class)    返回值是 指定类型**

>   创建springIOC容器都是 DefaultListableBeanFactory 这个类来实现的 默认是延迟加载（用到的时候才实例化） 但ApplicationContext 不是 ，他是启动就实例化了

创建spring容器的方式

![image-20231010141157768](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231010141157768.png)

# 6.bean对象属性依赖注入  “DI” ，这个是实现控制反转的具体方式

- **Spring通过依赖注入的方式来完成Bean管理的。**

- **Bean管理说的是：Bean对象的创建，以及Bean对象中属性的赋值（或者叫做Bean对象之间关系的维护）**

- **依赖注入的方法**
- 第一种：set方法注入
  
- 第二种：构造方法注入

## 6.1.set注入

- 实现原理

  > **首先肯定的加载配置文件 实例化对象 （除非设置了范围 scope = “prototype”  设置这个之后不用实例化对象）遍历所有的bean标签然后存在map<String, >当中， 然后再遍历bean标签下的property标签（当然bean不止这一个标签 。举一反三）  通过name属性 推出bean对象的set方法 然后看是value 还是ref，如果是value就之间传参就行了，如果是ref就读取map集合 通过ref获取对象 然后进行传参**

- 具体代码

  ```java
  //获取Class类对象
  Class<Student> aClass = (Class<Student>) Class.forName("com.java8.Student");
  //创建实例对象
  Student student = aClass.newInstance();
  //通过反射获取方法
  Method method = aClass.getDeclaredMethod("setAge",int.class);
  //通过反射调用该方法给属性赋值
  method.invoke(student,123);
  //测试
  System.out.println(student.getAge());//123
  ```

- **格式一 ：set 外部注入**  **（用它）**

```xml
<bean id="userDaoBean" class="com.powernode.spring6.dao.UserDao"/>
<bean id="userServiceBean" class="com.powernode.spring6.service.UserService">
        <property name="userDao" ref="userDaoBean"/>
</bean>
```

- 格式二 ：set 内部注入

```xml
<bean id="studentService1" class="com.txs.java.service.StudentService">
        <property name="studentDao">
            <bean class="com.txs.java.mapper.StudentDaoImp"/>
        </property>
 </bean>
```

- property 表示属性： 作用是 在set注入的的时候 会通过该标签 推出set方法 然后给属性赋值 

## 6.2. 构造方法注入

- 原理    

  > **通过反射机制调用构造方法给属性赋值 **
  
  ```java
      Object propertyValue1=1;
      Object propertyValue2="男";
      //获取字节码
      Class typeNameClass = Class.forName("com.java8.Student");
      //创建有参的构造函数
      Constructor constructor = typeNameClass.getDeclaredConstructor(Integer.class,String.class);
      //给构造函数赋值,创建实例化对象
      Object propertyValue = constructor.newInstance(propertyValue1, propertyValue2);
  ```
  
- spring 中 配置

```xml
<bean id="studentService" class="com.txs.java.service.StudentService">
        第一种 顺序不能变 
        <!--<constructor-arg index="0" ref="studentDao"/>
        <constructor-arg index="1" ref="clazzDao"/>-->
        第二种他会根据类型进行匹配
       <constructor-arg  ref="clazzDao"/>
       <constructor-arg  ref="studentDao"/>
 </bean>
```



## 6.3.注入简单类型 

- **什么是简单类型**
  1. 八种基本类型和对应的包装类
  2. 字符串类型  String是CharSequence子类
  3. Number子类
  4. Date子类
  5. Enum子类
  6. URI
  7. URL
  8. Temporal子类  时间
  9. Locale 当地时间 
  10. Class 
  11. 另外还包括以上简单值类型对应的数组类型。

- 怎么注入简单类型的数据 

  - 把ref 属性名 变成-->  value 属性名

  ```xml
   <bean id="dataSource" class="com.powernode.spring6.beans.MyDataSource">
          <property name="username" value="root"/>
          <property name="password" value="123456"/>
  </bean>
  ```

  1. 注入数组 简单类型 ( 用 value标签    非简单类型 用 ref标签 ) array 、 value

     ```xml
     <bean id="person" class="com.powernode.spring6.beans.Person">
         <property name="favariteFoods">
             <array>
                 <value>鸡排</value>
                 <value>汉堡</value>
                 <value>鹅肝</value>
             </array>
         </property>
     </bean>
     ```

  2. 注入List集合 (用 value标签    非简单类型 用 ref标签 ) list

     ```xml
     <bean id="peopleBean" class="com.powernode.spring6.beans.People">
         <property name="names">
             <list>
                 <value>铁锤</value>
                 <value>张三</value>
                 <value>狼</value>
             </list>
         </property>
     </bean>
     ```

  3. 注入Set集合  (非简单类型可以使用ref，简单类型使用value) set

     ```
     <bean id="peopleBean" class="com.powernode.spring6.beans.People">
         <property name="phones">
             <set> 
                 <!--非简单类型可以使用ref，简单类型使用value-->
                 <value>110</value>
                 <value>119</value>
                 <value>119</value>
             </set>
         </property>
     </bean>
     ```

  4. 注入Map集合  key(简单类型 ) --> key-ref（非简单类型 ） 标签 map、 entry ; 属性key, key-ref,value,value-ref 

     ```
       <map>
         <!--如果key不是简单类型，使用 key-ref 属性-->
         <!--如果value不是简单类型，使用 value-ref 属性-->
         <entry key="1" value="北京大兴区"/>
         <entry key="2" value="上海浦东区"/>
         <entry key="3" value="深圳宝安区"/>
     </map>
     ```

  5. **注入Properties  底层是map<String,String>  所以都是简单类型**

     - java.util.Properties继承java.util.Hashtable，所以Properties也是一个Map集合。标签 props 、prop ;属性 key  ,value 值写在标签里

     ```
     bean id="peopleBean" class="com.powernode.spring6.beans.People">
         <property name="properties">
             <props>
                 <prop key="driver">com.mysql.cj.jdbc.Driver</prop>
                 <prop key="url">jdbc:mysql://localhost:3306/spring</prop>
                 <prop key="username">root</prop>
                 <prop key="password">123456</prop>
             </props>
         </property>
     </bean>
     ```

  6. 注入null和空字符串

     - 注入空字符串使用：**<value/> ** 这个是空字符串，不是null、 或者 value=""

       ```xml
        <bean id="vipBean" class="com.powernode.spring6.beans.Vip">
               <!--空串的第一种方式-->
               <!--<property name="email" value=""/>-->
               <!--空串的第二种方式-->
               <property name="email">
                   <value/>
               </property>
           </bean>
       ```

       

     - 注入null使用：<null/> 或者 不为该属性赋值（不写）

  7. 注入的值中含有特殊符号

     - 特殊符号使用转义字符代替。 前端学过的**实体符合** 

       ```xml
       <bean id="mathBean" class="com.powernode.spring6.beans.Math">
           <property name="result" value="2 &lt; 3"/>
       </bean>
       ```

     - 将含有特殊符号的字符串放到：<![CDATA[ 放在这 ]]> 当中   CDATA翻译为”字符数据“ 

       ```xml
       <bean id="mathBean" class="com.powernode.spring6.beans.Math">
           <property name="result">
               <!--只能使用value标签-->
               <value><![CDATA[2 < 3]]></value>
           </property>
       </bean>
       ```

  8. ### **了解一下命名  目的：简化配置。**

     1. p命名空间注入  (setter方法注入) **简化配置**

        - 第一：在XML头部信息中添加p命名空间的配置信息：xmlns:p="http://www.springframework.org/schema/p"
          - 第二：p命名空间注入是基于setter方法的，所以需要对应的属性提供setter方法。
  
        - 语法 :      **p:属性名（setXXX() 后面的方法名** ） 
  
        ```xml
        <bean id="customerBean" class="com.powernode.spring6.beans.Customer" p:name="zhangsan" p:age="20"/>
        ```
  
     2.  c命名空间注入(简化构造方法注入的)  **简化配置**
  
        - 需要在xml配置文件头部添加信息：xmlns:c="http://www.springframework.org/schema/c"
  
        - 第二：需要提供构造方法。
  
        - 语法  c **: _下标（从0开始 ）="值 "**
  
          ```xml
          <bean id="myTimeBean" class="com.powernode.spring6.beans.MyTime" c:_0="2008" c:_1="8" c:_2="8"/>
          ```
  
     3. util命名空间 **配置复用**
  
        - 使用util命名空间可以让**配置复用**
        - 添加        xmlns:util="http://www.springframework.org/schema/util"
        - 添加         http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
  
        ```xml
        <util:properties id="prop">
            <prop key="driver">com.mysql.cj.jdbc.Driver</prop>
            <prop key="url">jdbc:mysql://localhost:3306/spring</prop>
            <prop key="username">root</prop>
            <prop key="password">123456</prop> 
        </util:properties>
        
        <bean id="dataSource1" class="com.powernode.spring6.beans.MyDataSource1">
            //复用
            <property name="properties" ref="prop"/>
        </bean>
        
        <bean id="dataSource2" class="com.powernode.spring6.beans.MyDataSource2">
               //复用
            <property name="properties" ref="prop"/>
        </bean>
        ```
  
        
  
  9. **基于XML的自动注入依赖装配**  **都是通过set方法注入** 匹配成功就赋值 ，不成功 就不赋值，就为null
  
     1. **根据名称自动装配**
  
     - Bean中需要添加**autowire="byName"**，表示通过名称进行装配。 固定写法  **autowire="byName"**
  
       ```xml
        <bean id="userService" class="com.powernode.spring6.service.UserService" autowire="byName"/>
       
        <bean id="dao" class="com.powernode.spring6.dao.UserDao"/>
       ```
  
     - **原理**
  
       **通过id名字去调用set方法 然后  还要判断里面的参数类型要一致，它是为了防止报错 类型不匹配异常** 
  
     ​    2.**根据类型自动装配**
  
     ```xml
       <!--byType表示根据类型自动装配-->
        <bean id="accountService" class="com.powernode.spring6.service.AccountService" autowire="byType"/>
     
        <bean class="com.powernode.spring6.dao.AccountDao"/>
     ```
  
     - 原理
  
       **通过类型匹配调用set方法 set方法的名字无所谓 看参数类型 匹配参数类型** 

# 7. Spring引入外部属性配置文件

- 第一步 在spring配置文件中引入**context命名空间。**引入外部属性配置文件

  ```xml
    xmlns:context="http://www.springframework.org/schema/context"
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
  ```

- 第二步 在spring中配置使用jdbc.properties文件。 属性占位符${}

  ```xml
  <context:property-placeholder location="jdbc.properties"/> location 根根路径下找
               属性 占位符
  <bean id="dataSource" class="com.powernode.spring6.beans.MyDataSource">这个是把这些配置信息封装在数据源里面了,和mybatis学的那个一样
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
  </bean>
  ```

  

# 8.Bean的作用域 （什么时候实例化bean 实例化后的bean的范围是多大）

> Spring的IoC容器中，默认情况下，Bean对象是单例的。 就是从jVM 启动到销毁都是同一个对象  因为 当执行那段代码和 生命周期于JVM 一样
>
> 专业一点就是 ：在初始化Spring上下文（ApplicationContext）的时候就完成的。

- bean标签中指定scope属性的值为

  - singleton 单例  默认的
  - prototype 多例 每一次执行getBean()方法的时候创建Bean对象

- 其它scope值  

  - **singleton：默认的，单例。**
  - **prototype：原型**。每调用一次getBean()方法则获取一个新的Bean对象。或每次注入的时候都是新对象。
  - **request：一**个请求对应一个Bean。**仅限于在WEB应用中使用**。
  - **session**：一个会话对应一个Bean。**仅限于在WEB应用中使用**。
  - global session：**portlet应用中专用的**。如果在Servlet的WEB应用中使用global session的话，和session一个效果。（portlet和servlet都是规范。servlet运行在servlet容器中，例如Tomcat。portlet运行在portlet容器中。）
  - **application：**一个应用对应一个Bean。**仅限于在WEB应用中使用。**
  - **websocket：**一个websocket生命周期对应一个Bean。**仅限于在WEB应用中使用。**
  - 自定义scope：很少使用。（了解过就行了）

  

# 9.GoF之工厂模式 （GoF 23种设计模式）

工厂模式通常有三种形态：

- 第一种：简单工厂模式（Simple Factory）：不属于23种设计模式之一。简单工厂模式又叫做：**静态 工厂方法模式**。简单工厂模式是工厂方法模式的一种特殊实现。(一个工厂)

  - 简单工厂模式的角色包括三个： 
    - 抽象产品 角色
    - 具体产品 角色
    - 工厂类 角色

- 第二种：工厂方法模式（Factory Method）：是23种设计模式之一。

  - 工厂方法模式的角色包括：
    - **抽象工厂角色**
    - **具体工厂角色**
    - 抽象产品角色
    - 具体产品角色

- 第三种：抽象工厂模式（Abstract Factory）：是23种设计模式之一。

  



# 10.Bean的实例化方式 都是为了获取bean 实例  所以只能是构造方法



bean 实例化的过程 

```txt
spring容器applocatopmContext在初始化的时候，会将xml配置的<bean> 信息封装成一个 BeanDefintion 对象，而BeanDefinition对象会存储到一个名为beanDefinitionMap的Map集合中去，spring框架在对该Map进行遍历，使用反射创建Bean实例对象，然后将创建好的Bean对象存储在一个名为singletonObjescts的Map集合中，当调用getBean（）方法的时候，最终会从singletonObjescts的Map集合中调用
```



![image-20231010220402620](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231010220402620.png)



## 10.1 通过无参构造方法实例化 .

- 默认情况下，会调用Bean的无参数构造方法。

## 10.2 通过简单工厂模式实例化 ，调用静态方法  必须是静态方法  ，就是通过"静态方法实例化对象"

> 在bean标签中使用 **factory-method**属性 factory-method="方法名 " 

```xml
可以返回实例化对象 但父类不实例化 因为什么 静态方法 “类名.方法名”
<bean id="vipBean" class="com.powernode.spring6.bean.VipFactory" factory-method="get"/>
```

## 10.3 通过factory-bean实例化  工厂方法模式进行实例化。 这个是 实例方法

```XML
<bean id="orderFactory" class="com.powernode.spring6.bean.OrderFactory"/>
<bean id="orderBean" factory-bean="orderFactory" factory-method="get"/>
```

## 10.4 通过FactoryBean接口实例化

- **当你编写的类直接实现FactoryBean接口之后，factory-bean不需要指定了，factory-method也不需要指定了。**

原理：将先工厂bean实例化（这个实例化对象放在singletonBojects ->HashMap） 然后调用工厂bean的方法返回实例化对象 返回的实例化对象存在 factoryBEanObjectCache (实际也是HashMap) 中

```java
public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        //返回实例化对象 
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        // true表示单例
        // false表示原型
        return true;
    }
}
```

```xml
<bean id="personBean" class="com.powernode.spring6.bean.PersonFactoryBean （实现了FactoryBean接口的类）"/>
```

# 11.BeanFactory和FactoryBean的区别

- ### BeanFactory

  - Spring IoC容器的顶级对象，BeanFactory被翻译为“Bean工厂”，在Spring的IoC容器中，“Bean工厂”负责创建Bean对象。

    BeanFactory是工厂。

- ###  **FactoryBean 辅助Spring实例化其它Bean对象**

  - FactoryBean：它是一个Bean，是一个能够**辅助Spring**实例化其它Bean对象的一个Bean。

    在Spring中，Bean可以分为两类：

    - 第一类：普通Bean
    - 第二类：工厂Bean（记住：工厂Bean也是一种Bean，只不过这种Bean比较特殊，它可以辅助Spring实例化其它Bean对象。）

# 12.Bean的生命周期

>  Spring Bean的生命周期是从Bean实例化之后，通过反射创建出对象后，到Bean成为一个完整的对象，最终存储到单例池中。

## 12.1.Bean的生命周期之5步

- Bean生命周期可以粗略的划分为五大步：

  - 第一步：实例化Bean (配置参数 执行构造方法)
  
  - 第二步：Bean属性赋值 （依赖注入）
  
  - 第三步：初始化Bean （配置bean xml文件中的init-method属性）//还可以使用**bean的初始化接口**

    ```java
    public interface InitializingBean {
        void afterPropertiesSet() throws Exception;
    }
    ```
  
    
  
  - 第四步：使用Bean 
  
  - 第五步：销毁Bean （配置bean xml文件中的destroy-method属性、再手动关闭）
  
  ![img](https://cdn.nlark.com/yuque/0/2022/png/21376908/1665388735200-444405f6-283d-4b3a-8cdf-8c3e01743618.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_24%2Ctext_5Yqo5Yqb6IqC54K5%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 第三步：初始化Bean

```xml
  <!--
init-method属性指定初始化方法。
destroy-method属性指定销毁方法。
-->
<bean id="userBean" class="com.powernode.spring6.bean.User" init-method="initBean" destroy-method="destroyBean">
    <property name="name" value="zhangsan"/>
</bean>
```

- 第四步：使用Bean

  调用（获取并调用）bean对象

  ```java
  User userBean = applicationContext.getBean("userBean", User.class);
  ```

- 第五步：销毁Bean

  ```java
   // 只有正常关闭spring容器才会执行销毁方法
          ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
          context.close();
  ```

  - 需要注意的：
    - 第一：只有正常关闭spring容器，bean的销毁方法才会被调用。
    - 第二：ClassPathXmlApplicationContext类才有close()方法。
    - 第三：配置文件中的init-method指定初始化方法。destroy-method指定销毁方法。

## 12.2. Bean生命周期之7步

spring提供了二种bean的后处理器 ：Bean工厂后处理器（可以在这期间，添加对象到IOC容器中）、Bean后处理器（每一个Bean实例化后都会调用）

![image-20231011104444091](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011104444091.png)



Bean工厂处理的时机：BeanDefinitionRegistryPostcessor（是BeanFactoryPostProcessor的子类）  bean定义的注册处理器先执行 再执行bean工厂后处理器

![image-20231011110924193](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011110924193.png)





- 加入“Bean后处理器”。

  BeanPostProcessor ：是bean对象实例化后，但是没还开始初始化的时候

  ![image-20231011114754611](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011114754611.png)

  - 第一步编写一个类实现BeanPostProcessor类，并且重写before和after方法：

    ```java
    //beanName --> id | bean -->对象
    
    public class MyBeanPostProcessorImp implements BeanPostProcessor{
    
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            //beanName --> id | bean -->对象
            System.out.println("3.初始化前的后期处理， 对象"+bean+"名字"+beanName);
            return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
        }
    
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("5.初始化后的后期处理， 对象"+bean+"名字"+beanName);
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
    }
    
    ```

    

  - 第二步在spring.xml文件中配置“Bean后处理器”：

    ```xml
    <!--配置Bean后处理器。这个后处理器将作用于当前配置文件中所有的bean。-->
    <bean class="com.powernode.spring6.bean.LogBeanPostProcessor"/>
    ```

    

##       12.3.Bean生命周期之10步

# 13.自己new的对象如何让Spring管理

```java
      //默认的可列表Bean工厂
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        //登记单 注册到单例池 
        defaultListableBeanFactory.registerSingleton("user1",new User());
        //获取对象
        User userBean = defaultListableBeanFactory.getBean("user1", User.class);
        //No bean named 'user1' available 所有容器不一样
        User user1 = ac.getBean("user1", User.class);
```

# 14.Bean的属性的循环依赖问题  （这个是循环 但是只有不循环调用进行）

**Bean属性的填充方式：**

![image-20231011172341999](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011172341999.png)

Spring提供了**三级缓存**存储完整Bean实例和半成品Bean实例，解决循环依赖的问题

先找一级-->二级--> 三级

```java
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
 
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);//存储完整bean 一级缓存
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap(16);//三 级缓存
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap(16);//半成品bean，二级缓存
}
```

![image-20231011180217392](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011180217392.png)



1. **singleton+setter的模式下产生的循环依赖**  （可以解决）

   - 这个是循环

2. **prototype+set注入的方式下** （无法解决）

   - 这个是无限的new 对象 因为有依赖 所有要一直创建对象 

3. 如果其中一个是singleton，另一个是prototype  可以解决

   - 这个是循环

   - ```xml
         <bean id="hBean" class="com.powernode.spring6.bean2.Husband" scope="singleton">
             <constructor-arg name="name" value="张三"/>
             <constructor-arg name="wife" ref="wBean"/>
         </bean>
     
         <bean id="wBean" class="com.powernode.spring6.bean2.Wife" scope="prototype">
             <constructor-arg name="name" value="小花"/>
             <constructor-arg name="husband" ref="hBean"/>//不用创建对象了 已经有了
         </bean>
     ```

4. singleton + 构造注入的方式下 （无法解决）

   - 因为 构造方法参数 只有当创建出来之后 才会调用 所以 这个**实例化Bean”和“给Bean属性赋值”这两个动作要同时完成。**

### Spring为什么可以解决set + singleton模式下循环依赖？ 实例化 和 赋值 分两步

- **这种方式可以做到将“实例化Bean”和“给Bean属性赋值”这两个动作分开去完成。**
- **实例化Bean的时候：调用无参数构造方法来完成。**
- **此时可以先不给属性赋值，可以提前将该Bean对象“曝光”给外界。**
- **两个步骤是完全可以分离开去完成的，并且这两步不要求在同一个时间点上完成。 **（实例化 和 赋值 分两步）



**Aware接口 ：**

![image-20231011211052102](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011211052102.png)



## 原理：总结 

![image-20231011211319645](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231011211319645.png)





# 15.Spring IoC注解式开发

## 15.1. 声明Bean的注解

- 负责声明Bean的注解，常见的包括四个：
  - @Component  依赖注入组件 ，组件扫描。以下三个是这个注解的别名
  - @Controller 表现层
  - @Service  业务层
  - @Repository 持久层

## 15.2.负责注入的注解



![image-20231012163416366](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231012163416366.png)



- Bean属性赋值需要用到这些注解：
  - @Value 给简单类型赋值  **可以写在 属性上 方法上 参数里  类上**
  
    ![image-20230720213641768](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230720213641768.png)
  
  - @Autowired  **给非简单类型赋值** 默认以‘’类型匹配‘’自动赋值  
  
  ![image-20230720214031520](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230720214031520.png)
  
  - @Qualifier 这个必须和@Autowirted 注解 一起使用 这个value 指定通过 名字进行自动匹配  如果不写 则会报错  "没有类型 为合格的bean "
    - ![](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230720214131004.png)
  
  
  
  **以上三个是来自 package org.springframework.beans.factory.annotation; 是spring 框架内置的** 
  
  - @Resource    该注解是JDK扩展包中的，也就是说属于JDK的一部分  是来自 javaEE包下的 
    -  **如果是JDK8的话不需要额外引入依赖。高于JDK11或低于JDK8需要引入以下依赖。**
  
  - @Resource   这个是先进行名字匹配  ，找不到 就用 类型匹配  
  
    - **默认根据名称装配byName，未指定name时，使用属性名作为name。通过name找不到的话会自动启动通过类型byType装配。**
  
      ![image-20230720215649798](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230720215649798.png)
  
      
  
    ```java
    @Service
    public class AaServiceImp implements AaService{
    
        @Resource(name = "aaDaoImp")//什么都不写会根据属性名进行依赖注入 如果名字对不上，则会通过类型自动匹配
        @Autowired//默认根据类型 自动连线
        @Qualifier("bbDaoImp")//指定特定名字进行依赖注入 限定符
        //@Qualifier("userDaoForOracle") // 这个是bean的名字。 Qualifier 要和AutoWirted一起使用
        private AaDao aaDao;
        @Override
        public void a() {
           aaDao.a();
        }
    }
    
    小细节 当这三个注解都写上的时候 Resource注解优先
    ```
  
    

## 15.3.配置类注解

- @Configuration 声明这个类是个配置类 

- @ComponentScan({"com.powernode.spring6.dao", "com.powernode.spring6.service"})  组件扫描 扫描注解在那个类中 

  ```java
  //使用注解 声明配置类 不能用之前那个对象进行使用了 得用 AnnotationConfigApplicationContext 
  ApplicationContext applicationContext = new AnnotationConfigApplicationContext(这里写的配置类Spring6Configuration.class);
  UserService userService = applicationContext.getBean("userService", UserService.class);
  
  ```

  

# 16.JdbcTemplate   四部曲 

- **JdbcTemplate是Spring提供的一个JDBC模板类，是对JDBC的封装，简化JDBC代码。**
  
  - 核心类 ：org.springframework.jdbc.core.**JdbcTemplate**
  - JdbcTemplate中有一个DataSource属性，这个属性是数据源，我们都知道连接数据库需要Connection对象，而生成Connection对象是数据源负责的。所以我们需要给JdbcTemplate设置数据源属性。
  
-  **第一步：添加spring jdbc内置依赖** 

  ```xml
    <!--新增的依赖：spring jdbc，这个依赖中有JdbcTemplate-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-jdbc</artifactId>
              <version>6.0.0-M2</version>
          </dependency>
  ```

- **第二步：写一个数据源对象  implements DataSource**  这**个数据源对象可以换  “”改一下spring 配置的bean数据源就行了“**

  ```java
  public class MyDataSource implements DataSource {
      // 添加4个属性
      private String driver;
      private String url;
      private String username;
      private String password;
  }
  ```

- **第三步 ：在spring.xml 中配置一下 给内置的spring jdbc对象赋值**

  ```
  <bean id="myDataSource" class="com.powernode.spring6.jdbc.MyDataSource">
          <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
          <property name="url" value="jdbc:mysql://localhost:3306/spring6"/>
          <property name="username" value="root"/>
          <property name="password" value="123456"/>
      </bean>
      <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
          <property name="dataSource" ref="myDataSource"/>
      </bean>
  ```

- **第四步：使用 spring中的 JdbcTemplate对象** 

  ```java
   //查询 queryXXX()  需要用到类映射器 new BeanPropertyRowMapper<>(Student.class)
      @Test
      public void test2(){
          JdbcTemplate springJdbc = ac.getBean("springJdbc", JdbcTemplate.class);
          //单个查询
          String sql="select * from student where id=?";
          //BeanPropertyRowMapper 查询出来是一个类 要指定类映射器
          Student student = springJdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class),1);
          System.out.println(student);//
          //查询多个 query
          String sql2="select * from student";//expected 1, actual 9 预期1，实际9
          List<Student> query = springJdbc.query(sql2, new BeanPropertyRowMapper<>(Student.class));
          System.out.println(query);
      }
  
      //插入 更新 删除  只要是数据修改的 都用 update
      @Test
      public void test1(){
          JdbcTemplate springJdbc = ac.getBean("springJdbc", JdbcTemplate.class);
         /* String sql="insert into student(id,name,age) values(719,'星期三',19)";
          int update = springJdbc.update(sql);*/
          String sql = "insert into student(id,name,age) values(?,?,?)";
          int count = springJdbc.update(sql, 720, "张三", 30);
          System.out.println(count);
      }
  ```

  

# 17.GoF二十三种 之代理模式 

- **代理分为**
  - **静态代理 ：手动写一个代理类** 
  - **动态代理：在内存中自动生成代理类** 

## 17.1.静态代理 手动写一个代理类 

```java
方法一： 实现目标对象所实现的接口 
public class OrderServiceProxy implements OrderService{ // 代理对象

    // 目标对象
    private OrderService orderService;

    // 通过构造方法将目标对象传递给代理对象
    public OrderServiceProxy(OrderService orderService) {
        //添加其他增强代码
        this.orderService = orderService;
        //添加其他增强代码
    }
}

方法二： 继承目标对象 
public class OrderServiceImplSub extends OrderServiceImpl{
    @Override
    public void generate() {
        long begin = System.currentTimeMillis();
        super.generate();
        long end = System.currentTimeMillis();
        System.out.println("耗时"+(end - begin)+"毫秒");
    }
}
```

## 17.2.动态代理 在内存中自动生成代理类

- 在内存当中动态生成类的技术常见的包括：
  - **JDK动态代理技术**：只能代理接口。
  - **CGLIB动态代理技术**：CGLIB(Code Generation Library)是一个开源项目。是一个强大的，高性能，高质量的Code生成类库，它可以在运行期扩展Java类与实现Java接口。它既可以代理接口，又可以代理类，**底层是通过继承的方式实现的**。性能比JDK动态代理要好。**（底层有一个小而快的字节码处理框架ASM。）**
  - **Javassist动态代理技术**：Javassist是一个开源的分析、编辑和创建Java字节码的类库。是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。它已加入了开放源代码JBoss 应用服务器项目，通过使用Javassist对字节码操作为JBoss实现动态"AOP"框架。

### 17.2.1.**JDK动态代理技术**：只能代理接口。

```java
/**
 * jdk动态代理
 *  Proxy 生成代理类
 *  InvocationHandler 处理应用程序 接口
 * */
public class Test01 {

    @Test
    public void test1(){
        //创建目标对象给父类 
        UserInterface user = new User();
        
        //子类--> 父类 向下转型 最后生成的代理是实现了那个接口的 因为返回值是Object类型 所以要强转
        UserInterface userProxy  = (UserInterface) Proxy.newProxyInstance(
                                 //1.获取类加载器，加载和处理字节码文件
                                 User.class.getClassLoader(),
                                 // 要实现的接口的名字
                                 User.class.getInterfaces(),
                                 //调用处理程序 编写增强代码
                                 new MyInvocationHandler(user));

        //使用代理生成后的对象
        System.out.println("代理后：增强后的对象"+userProxy.getName());

    }
}

//编写增强代码 使用 InvocationHandler 在invoke中写  
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override  // 代理对象（很少用） 目标对象的方法（ 不分你我）   目标对象的参数
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("实现了代理类，在前");
        // 目标方法的返回值             目标对象  参数
        Object retValue = method.invoke(target, args);

        System.out.println("实现了代理类，在后");
        //这只是一个方法对象而已 不分你我 看参数是谁的就是谁的 因为是把这个方法加载的那个对象中去
        System.out.println(method);//public abstract java.lang.String com.java.day719.bean.UserInterface.getName()
        //记得返回方法的返回值
        return retValue;
    }

}
```



```
面试题
JDK动态代理和CGLIB动态代理的区别？
 
1、JDK动态代理只能代理接口，CGLIB动态代理既可以代理接口，也可以代理类。
2、JDK动态代理的底层是采用实现接口的方式实现的，而CGLIB动态代理底层是使用继承实现的。
3、CGLIB动态代理的效率比JDK动态代理的高
```



### 17.2.2.CGLIB

- CGLIB既可以代理接口，又可以代理类。底层采用继承的方式实现。所以被代理的目标类不能使用final修饰。

- 第一步：引入它的依赖

  ```xml
     <!-- cglib 动态生成类 底层是继承的方式 可以动态生成接口，类-->
          <dependency>
              <groupId>cglib</groupId>
              <artifactId>cglib</artifactId>
              <version>3.3.0</version>
          </dependency>
  ```

- 第二步 创建代理对象  Enhancer通过它创建代理类 

  ```java
  /**
   * cglib 动态生成 底层是继承的方式
   * 核心类
   * 增强器Enhancer 动态生成类
   *
   * 高版本的jdk需要配置一下包的路径
   * ● --add-opens java.base/java.lang=ALL-UNNAMED
   * ● --add-opens java.base/sun.net.util=ALL-UNNAMED
   *
   * 不配就会出现 初始化错误异常 ExceptionInInitializerError 找不到  java.base 包
   *  module java.base does not "opens java.lang" to unnamed module @59f95c5d
   *  java模块。Base不会“打开java”。Lang”到未命名模块@59f95c5d
   */
  public class Test02 {
  
      @Test
      public void test1(){
  
          // 创建字节码增强器
          Enhancer enhancer = new Enhancer();
          // 告诉cglib要继承哪个类
          //设置它的父类是谁 接口和类都行
          enhancer.setSuperclass(User.class);
  
          //设置设置回调 编写方法呢  MethodInterceptor 方法拦截器
          //第一种方式 使用匿名内部类
          /*enhancer.setCallback(new MethodInterceptor() {
              @Override              //代理类                                                    代理方法 有invokeSuper
              public Object intercept(Object 代理类, Method 目标方法, Object[] args, MethodProxy methodProxy) throws Throwable {
  
                  System.out.println(methodProxy);
                  System.out.println(args.length);//0
                  System.out.println(代理类.getClass().getSimpleName());//User$$EnhancerByCGLIB$$7a786283 这个是给代理对象
  
                  //Object retValue = 目标方法.invoke(new User(), args);
                  Object retValue = methodProxy.invokeSuper(代理类, args);
                  return retValue;
              }
          });*/
  
          //第二种方式 实现方法拦截器
          enhancer.setCallback(new MyMethodInterceptor());
          User userInterface = (User) enhancer.create();
          System.out.println(userInterface.getName());
  
      }
  }
  
  ```

  

### 17.2.3.Javassist动态代理技术

- **介绍**

  - Javassist是一个开源的分析、编辑和创建Java字节码的类库。是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。它已加入了开放源代码JBoss 应用服务器项目，通过使用Javassist对字节码操作为JBoss实现动态"AOP"框架。

  - mybatis内部已经实现了Javassist动态代理技术

    - ```java
      获取sqlSession 
      AccountDao accountDao = (AccountDao)sqlSession.getMapper(AccountDao.class);
      ```

第一步 引入依赖

- ```xml
  <dependency>
    <groupId>org.javassist</groupId>
    <artifactId>javassist</artifactId>
    <version>3.29.1-GA</version>
  </dependency>
  ```

第二步 ：编写方法 

```java

public class JavassistTest {
    public static void main(String[] args) throws Exception {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        // 创建类
        CtClass ctClass = pool.makeClass("com.powernode.javassist.Test");
        // 创建方法
        // 1.返回值类型 2.方法名 3.形式参数列表 4.所属类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, ctClass);
        // 设置方法的修饰符列表
        ctMethod.setModifiers(Modifier.PUBLIC);
        // 设置方法体
        ctMethod.setBody("{System.out.println(\"hello world\");}");
        // 给类添加方法
        ctClass.addMethod(ctMethod);
        // 调用方法
        Class<?> aClass = ctClass.toClass();
        Object o = aClass.newInstance();
        Method method = aClass.getDeclaredMethod("execute");
        method.invoke(o);
    }
}
```



### 注意点

运行要注意：加两个参数，要不然会有异常。

- --add-opens java.base/java.lang=ALL-UNNAMED
- --add-opens java.base/sun.net.util=ALL-UNNAMED



# 18.面向切面编程AOP

- **AOP底层使用的就是动态代理来实现的。**
- **Spring的AOP使用的动态代理是：JDK动态代理 + CGLIB动态代理技术。**
- **Spring在这两种动态代理中灵活切换，如果是代理接口，会默认使用JDK动态代理，如果要代理某个类，这个类没有实现接口，就会切换使用CGLIB。**
- **也可以强制通过一些配置让Spring只使用CGLIB。**
  - <aop:aspectj-autoproxy  proxy-target-class="true"/> 开启自动代理之后，凡事带有@Aspect注解的bean都会生成代理对象。
    - proxy-target-class="true" 表示采用cglib动态代理。 **翻译为 代理目标类  因为jdk动态代理只能代理接口** 所以这里是指cglib动态代理。
    - proxy-target-class="false" 表示采用jdk动态代理。默认值是false。即使写成false，当没有接口的时候，也会自动选择cglib生成代理类。

**1.什么是交叉业务？**

- 日志、事务管理、安全等。将与核心业务**无关的代码独立的抽取**出来

## 18.1.AOP的七大术语

1. **连接点 Joinpoint**   **插入代码的位置**

2. **切点 Pointcut** **真正织入****切面的方法** 

3. **通知 Advice**
   - - 通知又叫增强，就是具体你要织入的代码。
     - 通知包括：
   
   - - - 前置通知 
       - 后置通知
       - 环绕通知
       - 异常通知
       - 最终通知
   
4. **切面 Aspect**
   - **切点 + 通知就是切面。**
   
5. 织入 Weaving

   - 把**通知应用**到目标对象上的过程 就是代码增加给目标对象

6. 代理对象 Proxy

7. 目标对象 Target

## 18.2. 切点表达式

- ### **execution([访问控制权限修饰符] 返回值类型 [全限定类名]方法名(形式参数列表) [异常])**

  访问控制权限修饰符:

  - 没写，就是4个权限都包括。

  返回值类型：

  - **\* 表示返回值类型任意。**
  
  **全限定类名：**
  
  - 两个点“..”代表当前包以及子包下的所有类。
  - 省略时表示所有的类。
  
  方法名：

  - 必填项。
  - *表示所有方法。
  - set*表示所有的set方法。
  
  形式参数列表：
  
  - () 表示没有参数的方法
  - (..) 参数类型和个数随意的方法
  - **(*) 只有一个参数的方法** **类型随意**
  - **(*, String) 第一个参数类型随意，第二个参数是String的。**
  
  异常：
  
  - 可选项。
  - 省略时表示任意异常类型。

## 18.3.使用Spring的AOP 、AspectJ切面、 方面

- **第一种方式：Spring框架结合AspectJ框架实现的AOP，基于注解方式**
- **第二种方式：Spring框架结合AspectJ框架实现的AOP，基于XML方式。**

![image-20231013110342695](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231013110342695.png)



> 实际开发中，都是Spring+AspectJ来实现AOP



什么是AspectJ？

- **（Eclipse组织的一个支持AOP的框架。AspectJ框架是独立于Spring框架之外的一个框架，Spring框架用了AspectJ）** 

### **第一种 基于注解的方式怎么实现** 

- 第一步：引入的依赖 AspectJ

  ```xml
  <!--spring aspects依赖-->
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>6.0.11</version>
  </dependency>
  ```

- 第二步 ：写一个配置类

  ```java
  /**
   * 配置类
   */
  @Configuration
  @ComponentScan("com.java.day721")
  //EnableAspectJAutoProxy 启用Aspect J自动代理   proxyTargetClass代理目标类 所以是 cglib动态代理
  @EnableAspectJAutoProxy(proxyTargetClass = false)  //这里用的是jdk动态代理
  public class Config {
  }
  ```

- 第三步：写一个切面类 在切面类中 写切点表达式（为了指定是那个）和通知

  ```java
  @Component
  @Aspect//定义这个是一个方面类 切面=切点+ 通知
  public class MyAspectJ {
    //方法修饰符 没写，就是4个权限都包括。
      @Before("execution( * com.java.day721.Controller.AaController.*())")
      public void anQuan1(){
          System.out.println("非常安全 放心吧！！");
      }
  
      @Around("execution(* com.java.day721.Controller..*(..))")
      public void anQuan2(ProceedingJoinPoint joinPoint) throws Throwable {
          System.out.println("第二道关卡开始");
          Object proceed = joinPoint.proceed();
          System.out.println("第二道关卡结束");
      }
  
      @After("execution( * com.java.day721.Controller.AaController.*())")
      public void anQuan3(){
          System.out.println("最终吗！");
      }
  
  }
  ```

- 第四步调用 ：

  ```java
      @Test
      public void test1(){
          ApplicationContext c = new AnnotationConfigApplicationContext(Config.class);
          AaController aaController = c.getBean("aaController", AaController.class);
          aaController.a();
      }
  ```

  

**通知类型包括：**

- **前置通知：@Before 目标方法执行之前的通知**
- **后置通知：@AfterReturning 目标方法执行之后的通知**
- **环绕通知：@Around 目标方法之前添加通知，同时目标方法执行之后添加通知。**
- **异常通知：@AfterThrowing 发生异常之后执行的通知**
- **最终通知：@After 放在finally语句块中的通知**



### **第二种、基于XML配置方式的AOP（了解） ** 存在一些问题 

前面都一样 把注解全去掉

- 在spring,xml配置文件中编写

  ```XML
   <!--纳入spring bean管理-->
          <bean id="aaController" class="com.java.day721.Controller.AaController"/>
          <bean id="myAspectJ" class="com.java.day721.MyAspectJ"/>
  
      <!--aop配置-->
      <aop:config>
          <!--切点表达式-->
          <aop:pointcut id="p" expression="execution(* com.java.day721.Controller..*(..))"/>
          <!--定义切面-->
          <aop:aspect ref="myAspectJ">
              <aop:after method="anQuan3" pointcut-ref="p"/>
              <aop:before method="anQuan1" pointcut-ref="p"/>
              <aop:around method="anQuan2" pointcut-ref="p"/>
          </aop:aspect>
      </aop:config>
  ```
  


![image-20230721161225887](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230721161225887.png)

在使用第二种方法的时候 切面类的切点 执行顺序会和之前不一样 

如果使用 配置吧编写 那它将按照配置的顺序执行 环绕依然环绕方法之间 

如果 after 在最上面 在环绕包裹 

**实际用到在测吧 ** 重点使用注解开发 



### 注意

- 如果多个切面的话，顺序如何控制：**可以使用@Order注解来标识切面类，为@Order注解的value指	小，优先级越高**。

  ```java
  @Aspect
  @Component
  @Order(1) //设置优先级
  ```



### 优化使用切点表达式：

- ```java
      定义任意的一个方法 ，在该方法上加上@Pointcut 注解 其他方法引入该方法名+()即可
      @Pointcut("execution(* com.powernode.spring6.service.OrderService.*(..))")
      public void pointcut(){}
  
      @Around("pointcut()")
      public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
   
      }
  ```

  

# 19.Spring对事务的支持 底层实现用的aop 面向切面原理 



## 19.1.事务概述

- 什么是事务

- - 在一个业务流程当中，通常需要多条DML（insert delete update）语句共同联合才能完成，这多条DML语句必须同时成功，或者同时失败，这样才能保证数据的安全。
  - 多条DML要么同时成功，要么同时失败，这叫做事务。
  - 事务：Transaction（tx）

- 事务的四个处理过程：

- - 第一步：开启事务 (start transaction)
  - 第二步：执行核心业务代码
  - 第三步：提交事务（如果核心业务处理过程中没有出现异常）(commit transaction)
  - 第四步：回滚事务（如果核心业务处理过程中出现异常）(rollback transaction)

- 事务的四个特性：**ACID**

- - A 原子性：事务是最小的工作单元，不可再分。
  - C 一致性：事务要求要么同时成功，要么同时失败。事务前和事务后的总量不变。
  - I 隔离性：事务和事务之间因为有隔离性，才可以保证互不干扰。
  - D 持久性：持久性是事务结束的标志。



- **事务的隔离级别**

  ![image-20231013165622822](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231013165622822.png)



![image-20231013165743710](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20231013165743710.png)



## 19.2.spring中如何实现事务管理？

- **核心接口PlatformTransactionManager接口：spring事务管理器的核心接口。在Spring6中它有两个实现：**
  - **DataSourceTransactionManager：支持JdbcTemplate、MyBatis、Hibernate等事务管理。**
  - **JtaTransactionManager：支持分布式事务管理。**

**（Spring内置写好了管理事务的代理了，可以直接用。）**



## **声明式事务：**

- ### 实现方法一 ：基于注解  ！@Transactional

  - 第一步：半注解 ：配置xml 文件

  ```xml
  <!--数据源 阿里巴巴的德鲁伊 支持连接池-->     
  <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?serverTimezone=UTC"/>
        <property name="username" value="root"/>
        <property name="password" value="369"/>
  </bean>
  
  <!--创建事务管理器-->
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="druidDataSource"/>
  </bean>
   
  <!--开始用注解管理的方法 ，然后选择对应的事务管理器-->
  <tx:annotation-driven transaction-manager="transactionManager"/>
  ```

  - ##### 如果是全注解开发呢？？？

    - 怎么用注解 “创建事务管理器“？？ 启动是启动了 启动之前肯定要创建 ，那怎么创建呢 ？？ 所以 在

    - 怎么开启注解管理 ？？  @EnableTransactionManagement  意思是 **以注解的形式启动事务管理**

    - 解决方法！！

      - 第一步  ：创建一个配置类来代理配置文件 

        ```java
        @Configuration
        @ComponentScan("com.powernode.bank")
        @EnableTransactionManagement
        public class Spring6Config {
            @Bean(name='bean的名字 就是配置文件中的id')
            
            //创建数据源对象 
            @Bean
            public DataSource getDataSource(){
                //手动的去创建对象 提供getXX方法 是为了给其他类从配置类中获取对象 
                DruidDataSource dataSource = new DruidDataSource();
                dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://localhost:3306/spring6");
                dataSource.setUsername("root");
                dataSource.setPassword("root");
                return dataSource;
            }
        
            //创建jdbc 对象 
            @Bean(name = "jdbcTemplate")
            public JdbcTemplate getJdbcTemplate(DataSource dataSource){
                JdbcTemplate jdbcTemplate = new JdbcTemplate();
                jdbcTemplate.setDataSource(dataSource);
                return jdbcTemplate;
            }
        
            //创建 DataSourceTransactionManager 事务管理对象 
            @Bean
            public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
                DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
                dataSourceTransactionManager.setDataSource(dataSource);
                return dataSourceTransactionManager;
            }
        
        }
        ```

        

  - 第二步：在类或者 方法上加上注解 @Transactional   

    

- ### 实现方法二 ：基于全xml文件开发

- ### 声明式事务之XML实现方式  （了解）

- 配置步骤：
  - 第一步：配置事务管理器
  - 第二步：配置通知 advice
  - 第三步：配置AOP 切点和通知 

```xml
<!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置通知 spring 内置提供的帮你实现好了的一个通知对象 -->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <tx:attributes>//属性就是切点
            <tx:method name="save*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="del*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="update*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="transfer*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
        </tx:attributes>
    </tx:advice>

    <!--配置切面-->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* com.powernode.bank.service..*(..))"/>
        通知 
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
```

## 19.3、事务属性 （重点记忆 和理解 ）

- 事务有六大属性 

  1. **事务的传播行为 ： 在spring事务管理中 ，调用同一个对象中的不同方法，不存在多个事务，依然是一个事务。自然也不存在事务的传播**

     ```
     为什么同一个对象不存在事务的传播呢 因为同一个对象 就没有必要再创一个代理类了 
     ```

     **如图所示  同一个类的调用，另一个方法设置了 Propagation.REQUIRES_NEW ，但是没用！！**
  
     ![image-20230723174136315](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230723174136315.png)
  
     
  
     **不同类的调用，另一个方法设置了 Propagation.REQUIRES_NEW**  
  
     - 不同类调用  如果事务传播 设置了 ，那么他会代理这两个类 ，当你调用方法 他会执行代理类的方法 代理类中方法会 暂停当前的事务 重写创建新的事务，获取新的连接对象
  
     ![image-20230723174459865](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230723174459865.png)
  
     - a()方法和b()方法，a()方法上有事务，b()方法上也有事务，当a()方法执行过程中调用了b()方法，事务是如何传递的？
  
     - 事务传播行为在spring框架中被定义为枚举类型：七种
  
       ```java
       package org.springframework.transaction.annotation;
       
       public enum Propagation {
         必须的  REQUIRED(0), 支持当前事务，如果不存在就新建一个(默认)【没有就新建，有就加入】
         支持  SUPPORTS(1), 支持当前事务，如果当前没有事务，就以非事务方式执行【有就加入，没有就不管了】
         强制性的  MANDATORY(2), 必须运行在一个事务中，如果当前没有事务正在发生，将抛出一个异常【有就加入，没有就抛异常】
         必须要新的  REQUIRES_NEW(3),【不管有没有，直接开启一个新事务，开启的新事务和之前的事务不存在嵌套关系，之前事务被挂起】
         不支持  NOT_SUPPORTED(4),【不支持事务，存在就挂起】                   
         从来没有  NEVER(5),【不支持事务，存在就抛异常】
         嵌套的  NESTED(6);【有事务的话，就在这个事务里再嵌套一个完全独立的事务，嵌套的事务可以独立的提交和回滚。没有事务就和REQUIRED一样。】
       
           private final int value;
       
           private Propagation(int value) {
               this.value = value;
           }
       
           public int value() {
               return this.value;
           }
       }
       ```
  
       
  
  2. **事务隔离级别包括四个级别：**
  
     数据库中读取数据存在的三大问题：（三大读问题）
  
     - **脏读：读取到没有提交到数据库的数据，叫做脏读。** 从缓存中读取的
     - **不可重复读：在同一个事务当中，第一次和第二次读取的数据不一样。**
     - **幻读：读到的数据是假的。** 虽然读的数据库的数据 ，但是读完之后，数据库的数据就改变了
  
     
  
     - 读未提交：READ_UNCOMMITTED （从缓存中读）
  - - 这种隔离级别，存在脏读问题，所谓的脏读(dirty read)表示能够读取到其它事务未提交的数据。
     - 读提交：READ_COMMITTED （不从缓存中读）
  - - 解决了脏读问题，其它事务提交之后才能读到，但存在不可重复读问题。
     - 可重复读：REPEATABLE_READ （）
  - - 解决了不可重复读，可以达到可重复读效果，只要当前事务不结束，读取到的数据一直都是一样的。但存在幻读问题。
     - 序列化：SERIALIZABLE
  - - 解决了幻读问题，事务排队执行。不支持并发。





# 20.集成mybatis-spring

方法一

- 第一步：需要在spring容器中配置SqlSessionFactoryBean 

  ```xml
  <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="druidDataSource"/>
  </bean>
  ```

  

- 第二步：SqlSessionFactoryBean对象 需要DataSource 数据源，再配置数据源

  ```xml
  <bean class="com.alibaba.druid.pool.DruidDataSource" id="druidDataSource">
      <property name="driverClassName" value="${jdbc.driver}"/>
      <property name="url" value="${jdbc.url}"/>
      <property name="username" value="${jdbc.username}"/>
      <property name="password" value="${jdbc.password}"/>
  </bean>
  ```



- 第三步：在spring配置文件中引入mybatis核心配置文件mybatis-config.xml、

  ```xml
  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="druidDataSource"/>
      第三步：在spring配置文件中引入mybatis核心配置文件mybatis-config.xml
      <property name="configLocation" value="classpath:mybatis-config.xml"/>
      配置mybatis映射文件的路径
      <property name="mapperLocations" value="classpath:com/txs/day805/mapper/*.xml"/>
  </bean>
  ```



- 第四步：在配置文件中配置 sqlSession 

  ```xml
  <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate" >
      <constructor-arg ref="sqlSessionFactory"/>
  </bean>
  ```



- 第五步：在Dao实现类中引入sqlSession 作为成员变量

  ```java
  @Repository
  public class StudentMapperImp implements StudentMapper{
  
      @Autowired
      SqlSessionTemplate sqlSession;
  
      @Override
      public Student selectById(int id) {
          return sqlSession.getMapper(StudentMapper.class).selectById(id);
      }
  }
  ```

  

方法二

- 继承 SqlSessionDaoSupport ，调用getSqlSession() 获取SqlSession对象

  ```java
  public class StudentMapperImp extends SqlSessionDaoSupport implements StudentMapper{
  
      @Override
      public Student selectById(int id) {
          return getSqlSession().getMapper(StudentMapper.class).selectById(id);
      }
  }
  ```



- 第二步在配置文件中配置“ StudentMapperImp” bean  、和 sessionFactory

  ```xml
  <bean class="com.alibaba.druid.pool.DruidDataSource" id="druidDataSource">
      <property name="driverClassName" value="${jdbc.driver}"/>
      <property name="url" value="${jdbc.url}"/>
      <property name="username" value="${jdbc.username}"/>
      <property name="password" value="${jdbc.password}"/>
  </bean>
  
  <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="druidDataSource"/>
      第三步：在spring配置文件中引入mybatis核心配置文件mybatis-config.xml
      <property name="configLocation" value="classpath:mybatis-config.xml"/>
      配置mybatis映射文件的路径
      <property name="mapperLocations" value="classpath:com/txs/day805/mapper/*.xml"/>
  </bean>
  
  <bean class="com.txs.day805.mapper.StudentMapperImp" id="studentMapperImp">
      <property name="sqlSessionFactory" ref="sessionFactory"/>
  </bean>
  ```

   