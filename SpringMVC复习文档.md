# 2023-7-28               SpringMVC 开始学习



拦截器 、异常处理器，文件上传和下载

# 快速复习：

springMVC的作用的**在控制器 核心是 前端控制器DispatcherServlet ** -> 是一个servlet 类 但是 springmvc 已经帮你写好了DispatcherServlet类，需要你手动配置 servlet 的映射路径

**解决控制层的问题（表述层），对用户发来的请求进行解析，调用controller类，来返回视图**

```xml
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--配置IOC容器的初始化路径的信息-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springMVC.xml</param-value>
    </init-param>
    <!--让服务器启动就加载，让第一次访问时快一点-->
    <load-on-startup>1</load-on-startup>
</servlet>

<!--DispatcherServlet也是一个servlet 下面是对应的映射路径-->
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```



## springmvc 怎么是实现**控制层**   @Controller

```java
@Controller//必须要写 这个类表示servlet类 所以要被IOC容器管理
public class HelloController {
    
    @RequestMapping("/")//请求映射路径
    public String index(){
        return "index";
    }

}
```



## 访问路径的 ant风格： (如果和其他路径**冲突 优先匹配其他路径**)

-  ？表示单个字符
- \*  表示 0个或多个
- \** 表示 一层或多层目录  例如/**   则访问路径可以为 /a/b/c





## **常用注解：**

都是用来  **判断获取前端传递的数据，判断数据是否符合要求**

@RequestMapping、@GetMapping、@PathVariable、@RequestParam、@RequestHeader、@CookieValue

```java
//params = {"name!=123"} 参数的判定
@RequestMapping(value = {"hello6"},method = RequestMethod.GET,params = {"name!=123"})
@GetMapping("映射路径")
//@PathVariable("id") 获取路径变量上的值 和ant 风格一起使用
@RequestMapping("hello1/{id}/{name}")
public String hello1(@PathVariable("id") int id,@PathVariable("name")String name){
}

//@RequestParam  请求路径的参数和控制器方法的形参建立映射关系
@RequestMapping("/hello3")//defaultValue 如果value对不上前端传的属性参数名 ，则defaultValue的值会赋值给username
public String hello3(@RequestParam(value = "name",required = true,defaultValue = "风少") String username, String password){}//@RequestParam、@RequestHeader、@CookieValue 用法一样 可以获取前端传递的数据，判断数据是否符合要求

//Cookie 和session 是什么  session不会自己创建
//session 是依赖于Cookie 
//session首先由服务器创建 然后传给浏览器的Cookie的sessionId中
```



获取请求参数：

```java
1、原生的request对象
2、使用注解@RequstParam
```



向域中存放数据：

```java
//在request域对象共享数据
1、ModelAndView
  public ModelAndView domain2(){
        /**
         * ModelAndView有Model和View的功能
         * Model主要用于向请求域共享数据
         * View主要用于设置视图，实现页面跳转
         */
        ModelAndView modelAndView = new ModelAndView();
        //向请求域共享数据
        modelAndView.addObject("name","value,ModelAndView");
        //设置视图，实现页面跳转
        modelAndView.setViewName("domain");
        return modelAndView;
    }

2、Model
    public String domain3(Model model){
        model.addAttribute("name","value,model");
        return "domain";
    }
    
 //4、使用map向request域对象共享数据
    @RequestMapping("domain4")
    public String domain4(Map map){
        map.put("name","value,map");
        return "domain";
    }
    
```



**解决浏览器只能发送get post 请求的问题**

核心类是HiddenHttpMethodFilter 是一个过滤器 拦截前端发来的请求，再对请求进行修改封装

**过滤器想要生效需要配置在web.xml中** 配置过滤哪些请求

想要发送delete、put

前提一：post请求

前提二：前端请求路径传递一个参数 _method





## 怎么响应数据到浏览器

- 可以使用域

- 可以使用 将请求报文转换为Java对象 @RequestBody **将请求体转成字符串**，@RequestEntity **将http协议转成字符串**

- 将后台的数据响应给浏览器

  ```java
  //比如：可以通过原生的servlet 中的
      resposed.getWriter().print("响应")
  //  也可以通过@ResponseBody 注解
  @ResponseBody
  @RequestMapping("/converter5")
  public User converter5() {
      return new User("风少","男",19);
  }    
  ```

  

-  ResponseEntity 这个是一个类 不是注解  （用于文件上传）

  这个类中可以封装http协议 将数据封装成http协议 然后发送出去

  主要信息有：配置http响应报文 请求行、请求头、空白行（这个不用管）、请求体





## MultipartFile 文件上传

- public interface MultipartFile extends InputStreamSource 本质上就是一个IO流对象，进行文件转移啊，但是他不单单是文件，他还包括了前端的信息



## springmvc 怎么实现拦截器：

```xml
1、SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置

	<!-- 设置视图映射路径-->
    <mvc:view-controller path="/" view-name="index"/>
    <!--当使用上面那个的时候 注解就失效了，必须手动开启注解驱动-->
    <mvc:annotation-driven/>

    <!-- 将拦截器添加到容器中-->
    <bean id="towFilter" class="com.txs.day802.filter.TowFilter"/>
    <bean id="oneFilter" class="com.txs.day802.filter.OneFilter"/>

    <!--定义多个interceptors拦截器-->
    <mvc:interceptors>
    <!--interceptor一个拦截器-->      
      <mvc:interceptor>
            <!--下面的标签有顺序-->
            <!--需要拦截的请求路径 /**表示不限层任意路径  /*表示一层任意路径的-->
            <mvc:mapping path="/**"/>
            <!--排除哪些映射 -->
            <mvc:exclude-mapping path="/lmc/**"/>
            <!--所使用的拦截器-->
            <ref bean="towFilter"/>
        </mvc:interceptor>
        
    </mvc:interceptors>

2、实现HandlerInterceptor 处理程序拦截 
```



## 异常解析器 发生异常时会跳转到哪里

- 注解、配置二种方式

```java

- @ControllerAdvice 标记为异常通知类
- @ExceptionHandler(NullPointerException.class)  什么异常触发通知
@ControllerAdvice
public class OneException{

    @ExceptionHandler(NullPointerException.class)
    public String OneException(Exception ex, Model model){
        //输出异常信息到request域中
        model.addAttribute("ex", ex);
        return "error";
    }
}

<!--配置异常处理器 -->
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
            <prop key="java.lang.NullPointerException">error</prop>
        </props>
    </property>
    <!--value是Attribute获取数据的key -->
    <property name="exceptionAttribute" value="ex"/>
</bean>
```



# 1.springMVC项目的问题

###     1.spring6 、jdk17、tomcat10

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>6.0.11</version>
        </dependency>
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring6</artifactId>
            <version>3.1.1.RELEASE</version>
        </dependency>
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
    </dependencies>
```

###     2.spring5 、jdk1.8以上、tomcat 10

```xml
   <!-- SpringMVC -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.1</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
    </dependency>

    <!-- Spring5和Thymeleaf整合包 -->
    <dependency>
      <groupId>org.thymeleaf</groupId>
      <artifactId>thymeleaf-spring5</artifactId>
      <version>3.0.12.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.30</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.30</version>
    </dependency>

```



###     3.解决乱码问题 

```xml
  <!-- 自动扫描包 -->
    <context:component-scan base-package="com.txs.day729.controller"/>

    <!-- 配置Thymeleaf视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
        <property name="templateEngine" ref="templateEngine"/>
          <!--这里设置字符编码-->
        <property name="characterEncoding" value="UTF-8"/>
    </bean>

    <!--模板引擎 -->
    <bean id="templateEngine" class="org.thymeleaf.spring6.SpringTemplateEngine">
        <property name="templateResolver" ref="templateResolver"/>
    </bean>

    <!--Spring资源模板解析器 -->
    <bean id="templateResolver" class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".html"/>
        <property name="templateMode" value="HTML"/>
        <property name="characterEncoding" value="UTF-8"/>
    </bean>
```

- tomcat9 以上不存在。请求、响应乱码了。

  低版本可以配置以下内容

  ```xml
  //添加过滤器
  <filter>
      <filter-name>CharacterEncodingFilter</filter-name>
      <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
      <init-param>
          <param-name>encoding</param-name>
          <param-value>UTF-8</param-value>
      </init-param>
      <init-param>
          //这个名字不能乱写
          <param-name>forceResponseEncoding</param-name>
          <param-value>true</param-value>
      </init-param>
  </filter>
  <filter-mapping>
      <filter-name>CharacterEncodingFilter</filter-name>
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  ```

  > 注意：SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效。，放最上面进行，或者设置优先级



### 4.文件上传的问题

-  Spring 6 中，`org.springframework.web.multipart.commons.CommonsMultipartResolver` 已经被标记为过时（deprecated），不再推荐使用。

  在 Spring 6 中，推荐使用 `StandardServletMultipartResolver` 或 `CommonsFileUploadSupport` 进行文件上传的配置。

- https://docs.spring.io/spring-framework/docs/6.0.11/javadoc-api/org/springframework/web/multipart/support/StandardServletMultipartResolver.html

```xml
//为了使用基于 Servlet 容器的多部分解析， 您需要在 中使用 中的 “multipart-config” 部分标记受影响的 servlet，

 <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springMVC.xml</param-value>
        </init-param>
        <multipart-config>
            <max-file-size>1024000000</max-file-size>
            <max-request-size>1024000000</max-request-size>
        </multipart-config>
    </servlet>
```



### 5.页面错误

400：请求参数不对

405：请求方法不一样

404：



# 2.springMVC 用途 ？？？？

**1.介绍**

- Spring Web MVC是基于Servlet API构建的原始Web框架，已被包含 从一开始就在 Spring 框架中。正式名称“Spring Web MVC”。 来自其源模块的名称 （[`Spring-WebMVC`](https://github.com/spring-projects/spring-framework/tree/main/spring-webmvc)）， 但它通常被称为“Spring MVC”。
- Spring Framework 6.0 与 Tomcat 10.1 完全兼容
- **== SpringMVC是Spring的一个后续产品，是Spring的一个子项目，Spring MVC是Spring Framework的一部分，是基于Java实现MVC的轻量级Web框架。 ==**

**2.用来解决什么问题**

- 是围绕前端控制器设计的模式
- **解决控制层的问题（表述层），对用户发来的请求进行解析，调用controller类，来返回视图**



# 3.springMVC的流程图



​	**DispatcherServle是什么？？**

- **DispatcherServlet是一个实际的Servlet**

  ![image-20230729182712037](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230729182712037.png)

- 作用是将请求分发到不同的处理器类。**( 在处理器类用注解必须用@Controller)**
- **基于原生的Servlet**，通过了功能强大的**前端控制器DispatcherServlet**，对请求和响应进行统一处理

## 3.1、五个核心类

1. DispatcherServlet：**前端控制器**
2. HandlerMapping：**处理器映射器**
3. Handler：**处理器**，需要工程师开发
4. HandlerAdapter：**处理器适配器**
5. ViewResolver：**视图解析器**
   - View：**视图** 将模型数据通过页面展示给用户

![image-20230804110402821](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230804110402821.png)





# 4.springMVC中常用的注解



### 1、@RequestMapping

- 请求映射

- 类或方式上

- 其中的属性有 ：value（对映射路径的要求）、method（对请求方式的要求）、params（对请求参数的要求）、headers（对请求头的要求）

  ```JAVA
      // params属性 中写的是String
      // 1.属性名=属性值     表示必须是这个属性且值相同，才会访问这个方法
      // 2.属性名            表示必须有这个属性名
      // 3.属性名！          表示必须不能有这个属性名
      // 4.属性名！=属性值   表示必须是这个属性可以没用，或者有这个属性但值不能等于指定的值
  ```

  

### 2、@PathVariable

- 取路径对应的值

- 与ant风格：/deleteUser/1  使用

- ```java
  @RequestMapping("/testRest/{id}/{username}")
  public String testRest(@PathVariable("id") String id, @PathVariable("username") String username){
      System.out.println("id:"+id+",username:"+username);
      return "success";
  }
  ```



### 3.@RequestParam  

- 定义请求参数到方法的参数映射

- 有三个属性：value、required、defaultValue

- ```java
  //defaultValue 如果value对不上前端传的属性参数名 ，则defaultValue的值会赋值给username
  public String hello3(@RequestParam(value = "name",required = true,defaultValue = "风少") String username, String password){
      
  }
  ```



### 4.@RequestHeader 

- 获取请求头的信息
- @RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

- ```java
  @RequestMapping("/hello4")
  public String hello4(@RequestHeader("Host") String host){
      System.out.println("username:"+host);
      return "LmcHello";
  }
  ```



### 5.@CookieValue

- 获取 “cookie数据” cookie 数据包含了sessionid
- @CookieValue注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

```JAVA
@RequestMapping("/hello6")
public String hello6(HttpServletRequest request,@CookieValue("JSESSIONID") String sessionId){
    HttpSession session = request.getSession();
    System.out.println("sessionId"+sessionId);
    return "LmcHello";
}
//Cookie 和session 是什么
//session 是依赖于Cookie
//session不会自动创建
//session首先由服务器创建 然后传给浏览器的Cookie的sessionId中

//没有则创建 有就获取 不可能为空
HttpSession session = request.getSession();
//只获取 不会创建 可能为空
//HttpSession session = request.getSession(false);


```





# 5.SpringMVC获取请求参数



方法一：通过ServletAPI获取 （原始的方法）

- ```java
   //通过ServletAPI获取请求参数
  @RequestMapping("/hello2")
  public String hello2(HttpServletRequest request){
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      System.out.println("username:"+username+",password:"+password);
      return "LmcHello";
  }
  ```



方法二: 通过方法的形参获取请求参数

- ```java
  //通过方法的形参获取请求参数 如果前后端方法参数名不同，但还是想用当前的用 @RequestParam来指定前端的名字
  //@RequestParam  请求参数和控制器方法的形参创建映射关系
  @RequestMapping("/hello3")//defaultValue 如果value对不上前端传的属性参数名 ，则defaultValue的值会赋值给username
  public String hello3(@RequestParam(value = "name",required = true,defaultValue = "风少") String username, String password){
      System.out.println("username:"+username+",password:"+password);
      //重定义 用 redirect：/完整的请求路径 除了项目名
      return "redirect:/lmc/hello2";
  }
  ```



方法三：通过POJO获取请求参数 

- 将前端传的数据包装成pojo类

- pojo类的属性名，等于前端数据的名字

- **他是通过 set方法 或者 构造方法赋值** 必须要写 其中之一，不然无法赋值

  ```JAVA
  @PostMapping("/hello5")
      public String hello5(User user){//他是通过 set方法 或者 构造方法赋值
      System.out.println("user:"+user);
      return "LmcHello";
  }
  ```

  

# 6、域对象共享数据

- 三个域对象 request、HttpSession、ServletContext

  

## 6.1、request域的三种实现方式

- 1、使用ServletAPI向request域对象共享数据

  ```java
  //1、使用ServletAPI向request域对象共享数据
  @RequestMapping("domain1")
  public String domain1(HttpServletRequest request){
      request.setAttribute("name","value,ServletAPI,request");
      return "domain";
  }
  ```

- 2、使用ModelAndView向request域对象共享数据

  ```java
  @RequestMapping("domain2")
  public ModelAndView domain2(){
      /**
       * ModelAndView有Model和View的功能
       * Model主要用于向请求域共享数据
       * View主要用于设置视图，实现页面跳转
       */
      ModelAndView modelAndView = new ModelAndView();
      //向请求域共享数据
      modelAndView.addObject("name","value,ModelAndView");
      //设置视图，实现页面跳转
      modelAndView.setViewName("domain");
      return modelAndView;
  }
  ```

  

- 3、使用Model向request域对象共享数据

  ```java
  @RequestMapping("domain3")
  public String domain3(Model model){
      model.addAttribute("name","value,model");
      return "domain";
  }
  ```



- 4、使用map向request域对象共享数据

  ```java
  @RequestMapping("domain4")
  public String domain4(Map map){
      map.put("name","value,map");
      return "domain";
  }
  ```



- 5、使用ModelMap向request域对象共享数据

  ```java
  @RequestMapping("domain5")
  public String domain5(ModelMap map){
      /**
       * addAttribute() 和 put() 是一样的 ，addAttribute()又调用了put()
       * public ModelMap addAttribute(String attributeName, @Nullable Object attributeValue) {
       *         Assert.notNull(attributeName, "Model attribute name must not be null");
       *         this.put(attributeName, attributeValue);
       *         return this;
       * }
       */
      map.addAttribute("name","value,ModelMap-addAttribute");
      //map.put("name","value,ModelMap-put");
      return "domain";
  }
  ```

  

####          == Model、ModelMap、Map类型的参数都有一个共同的子类BindingAwareModelMap==

```java
public interface Model{}
public class ModelMap extends LinkedHashMap<String, Object> {}
public class ExtendedModelMap extends ModelMap implements Model {}
public class BindingAwareModelMap extends ExtendedModelMap {}
```



## 6.2、向session域共享数据

```xml
@RequestMapping("domain1")
public String domain1(HttpSession session){
    session.setAttribute("name","value,ServletAPI,session");
    return "domain";
}
```



## 6.3、向application域共享数据

```java
@RequestMapping("domain2")
public String domain2(HttpSession session){
    //ServletContext servletContext1 = request.getServletContext();
    ServletContext servletContext = session.getServletContext();
    servletContext.setAttribute("name","value,ServletAPI,servletContext");
    return "domain";
}
```



# 7.SpringMVC的视图

- SpringMVC视图的种类很多，默认有转发视图和重定向视图

## 7.1、配置文件中自定义的视图解析器

- 当控制器方法中所设置的视图名称**没有任何前缀时，**此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析

```xml
<!-- 配置Thymeleaf视图解析器 -->
<bean id="viewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
    <property name="templateEngine" ref="templateEngine"/>
    <property name="characterEncoding" value="UTF-8"/>
</bean>
```

- 前缀为："forward: 某个@RequestMapping上的映射路径 （不是视图名字）"  则视图解析器为：InternalResourceView

- 前缀为： "redirect: 请求路径(完整的) "  则视图解析器为：RedirectView

  

# 8.RESTful的案例浏览器的问题

- GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源。

- 由于浏览器只支持发送get和post方式的请求



## 8.1解决浏览器只能发送get和post方式的请求	

- SpringMVC 提供了 **HiddenHttpMethodFilter** 帮助我们**将 POST 请求转换为 DELETE 或 PUT 请求**

- 使用HiddenHttpMethodFilter 的前提

  -  必须是post请求 
  - 当前请求必须传输**请求参数的名字为 "_method"**

  ```java
  private String methodParam方法名字 = "_method";
  
  
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain 		filterChain) throws ServletException, IOException {
            HttpServletRequest requestToUse = request;
           //源码  request.getMethod()必须是post请求
            if ("POST".equals(request.getMethod())) {
                String paramValue = request.getParameter(this.methodParam方法名字);
      	         if (StringUtils.hasLength(paramValue)) {
                    String method = paramValue.toUpperCase(Locale.ENGLISH);
                    if (ALLOWED_METHODS.contains(method)) {
                        requestToUse = new HiddenHttpMethodFilter.HttpMethodRequestWrapper(request, method);
                    }
               }
            }
  ```



- 配置HiddenHttpMethodFilter 过滤器

  ```xml
  <filter>
      <filter-name>HiddenHttpMethodFilter</filter-name>
      <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
  </filter>
  <filter-mapping>
      <filter-name>HiddenHttpMethodFilter</filter-name>
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  ```

   

# 9.在springMVC中响应或请求数据信息到前端 

- HttpMessageConverter报文信息转换器
  - 将请求报文转换为Java对象  @RequestBody，RequestEntity
  - 将Java对象转换为响应报文  @ResponseBody，ResponseEntity

- HttpMessageConverter提供了两个注解和两个类型：
  - @RequestBody，@ResponseBody
  - RequestEntity，ResponseEntity



### @RequestBody

- 获取请求体中 （必须是post请求，当你传参的时候用post 请求体中 只有参数，requestBody:username=admin&password=123）

  ```java
  //1.将"请求体"报文转-->Java对象
  @RequestMapping("/converter1")
  public String converter1(@RequestBody String s){
      System.out.println(s);
      return "view";
  }
  ```



### RequestEntity 请求实体

- 将"http协议的所有信息"报文转-->Java对象RequestEntity请求实体

  ```java
  @RequestMapping("/converter2")
  public String converter2(RequestEntity s){
      System.out.println(s);
      return "view";
  }
  ```

- 输出结果

  ```
  requestHeader:[host:"localhost:8080", connection:"keep-alive", content-length:"27", cache-control:"max-age=0", sec-ch-ua:"" Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90"", sec-ch-ua-mobile:"?0", upgrade-insecure-requests:"1", origin:"http://localhost:8080", user-agent:"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"]
  requestBody:username=admin&password=123
  ```

  



###  @ResponseBody 响应体

- 将java对象转-->响应报文

  ```java
  @ResponseBody //该注解的使用类型：ElementType.TYPE, ElementType.METHOD
  //底层调用了将对象转成转成json（这件事服务器内部做的，看不见）怎么让他做这件事，需要导入依赖,
  //然后在SpringMVC的核心配置文件中开启mvc的注解驱动，
  //此时在HandlerAdaptor中会自动装配一个消息转换器：MappingJackson2HttpMessageConverter
  //它可以将响应到浏览器的Java对象转换为Json格式的字符串
      
  @RequestMapping("/converter5")
  public User converter5() {
      return new User("风少","男",19);
  }
  ```

- 依赖

  ```xml
  <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.15.2</version>
  </dependency>
  ```

- 配置

  ```xml
  <mvc:annotation-driven />
  
  注意：当你使用配置文件来确定映射关系的时候，注解就会失效，所以需要开启springmvc注解驱动  （有两个用处）
  <mvc:view-controller path="/" view-name="index"/>
  <!--当使用配置文件的时候 注解就失效了，必须手动开启注解驱动-->
  <mvc:annotation-driven/>
  ```



### @RestController 标记在类上

- 注解是springMVC提供的一个复合注解，标识在控制器的类上
- 相当于为类添加了@Controller注解，并且为其中的**每个方法**添加了@ResponseBody注解



### ResponseEntity（作用文件下载）

- 用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文

  ```java
  //本质就是响应给浏览器 配置http响应报文 请求行、请求头、空白行（这个不用管）、请求体（要用户下载的文件）
  @RequestMapping("/fileDown")
  public ResponseEntity<byte[]> fileDown(HttpSession session) throws IOException {
      //获取ServletContext对象
      ServletContext servletContext = session.getServletContext();
      //获取服务器中文件的真实路径
      String realPath = servletContext.getRealPath("/static/img/菲.jpg");
      //创建输入流
      InputStream is = new FileInputStream(realPath);
      //创建字节数组（响应体）
      byte[] bytes = new byte[is.available()];
      //将流读到字节数组中
      is.read(bytes);
      //创建HttpHeaders对象设置响应头信息
      MultiValueMap<String, String> headers = new HttpHeaders();
      //设置要下载方式以及下载文件的名字
      headers.add("Content-Disposition", "attachment;filename=菲.jpg");
      //设置响应状态码（请求行）
      HttpStatus statusCode = HttpStatus.OK;
      //创建ResponseEntity对象                                       请求体、 请求头、  请求行
      ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
      //关闭输入流
      is.close();
      return responseEntity;
  }
  ```

  

# 10.文件上传

- spring中有一个接口可以接受前端传的文件，然后转到服务器中

  -  public interface MultipartFile extends InputStreamSource 本质上就是一个IO流对象

- MultipartFile接口 是spring框架自带的

  - > import org.springframework.web.multipart.MultipartFile;

  - 在spring6 中只需要在 web.xml文件配置MultipartFile的基本参数就行

  - （官方文档）https://docs.spring.io/spring-framework/docs/6.0.11/javadoc-api/org/springframework/web/multipart/support/StandardServletMultipartResolver.html

```java
 //文件上传 就是IO流，进行文件转移啊，但是他不单单是文件，他还包括了前端的信息
@PostMapping("/fileUp")
public String fileUp(MultipartFile filename, HttpSession session) throws IOException {

    // filename.getName() 这个是获取前端name的名字；filename
    //System.out.println(filename.getName());
    // 获取上传的文件的文件名
    String fileName = filename.getOriginalFilename();

    // 处理文件重名问题
    String qName = fileName.substring(0, fileName.lastIndexOf("."));
    // 处理文件重名问题
    String hzName = fileName.substring(fileName.lastIndexOf("."));
    //生产一个随机的 UUID拼接在前缀和后缀直接
    fileName = String.valueOf(UUID.randomUUID());
    fileName =qName+fileName.substring(0,2)+hzName;

    // 获取服务器中photo目录的路径
    ServletContext servletContext = session.getServletContext();
    String photoPath = servletContext.getRealPath("photo");
    File file = new File(photoPath);
    if (!file.exists()) {
        file.mkdir();
    }
    String finalPath = photoPath + File.separator + fileName;
    // 实现上传功能
    filename.transferTo(new File(finalPath));
    return "success";
}
```

- web.xml中的配置

  ```xml
  <servlet>
      <servlet-name>springmvc</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:springMVC.xml</param-value>
      </init-param>
      
      <multipart-config>
          <max-file-size>1024000000</max-file-size>//文件的最大内存 100M
          <max-request-size>1024000000</max-request-size>//请求实体的最大内存100M
      </multipart-config>
  </servlet>
  ```

  

# 11.拦截器 过滤器(三个方法)

- SpringMVC中的拦截器需要实现HandlerInterceptor

- 想要拦截，还需要配置文件，配置拦截什么路径，指定那个拦截器

  

- 创建拦截器对象

  ```java
  /**
   * 拦截器（过滤器）
   * 1.SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置：
   * 2.拦截器的三个方法在哪里被调用了都在：DispatcherServlet中 doDispatch()被调用
   *   --1）处理前的方法被  mappedHandler.applyPreHandle(processedRequest, response)
   *   --2）处理后的方法被  mappedHandler.applyPostHandle(processedRequest, response, mv);
   *   --3）完成后的方法被
   *           首先调用this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
   *           然后在processDispatchResult()方法中，解析视图，并且解析视图结束后调用
   *           mappedHandler.triggerAfterCompletion(request, response, (Exception)null);
   *
   *
   * 执行顺序
   * OneFilter处理前
   * TowFilter处理前
   * TowFilter处理后
   * OneFilter处理后
   * TowFilter完成后
   * OneFilter完成后
   */
  public class OneFilter implements HandlerInterceptor {
  
      //处理前 调的是这个mappedHandler.applyPreHandle(processedRequest, response)
      //    for(int i = 0; i < this.interceptorList.size(); this.interceptorIndex = i++)
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          System.out.println("OneFilter处理前");
          return true;
      }
  
      //处理后 mappedHandler.applyPostHandle(processedRequest, response, mv);
      //  for(int i = this.interceptorList.size() - 1; i >= 0; --i) 多个控制器就逆序访问
      public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
          System.out.println("OneFilter处理后");
          HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
      }
  
      //完成后 triggerAfterCompletion
      // for(int i = this.interceptorIndex; i >= 0; --i)  多个控制器就逆序访问
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
          System.out.println("OneFilter完成后");
          HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
      }
  }
  ```

  

- 配置拦截器

  ```xml
  <!--创建拦截器-->
  <bean id="towFilter" class="com.txs.day802.filter.TowFilter"/>
  <bean id="oneFilter" class="com.txs.day802.filter.OneFilter"/>
  
  <!--定义多个interceptors拦截器-->
  <mvc:interceptors>
    
      <mvc:interceptor>
          <!--需要拦截的请求路径 /**表示不限层任意路径  /*表示一层任意路径的-->
          <mvc:mapping path="/**"/>
          <!--排除哪些映射 -->
          <mvc:exclude-mapping path="/lmc/**"/>
           <!--指定拦截器-->
          <ref bean="oneFilter"/>
      </mvc:interceptor>
  
      <mvc:interceptor>
          <!--需要拦截的请求路径 /**表示不限层任意路径  /*表示一层任意路径的-->
          <mvc:mapping path="/**"/>
          <!--排除哪些映射 -->
          <mvc:exclude-mapping path="/lmc/**"/>
          <!--指定拦截器-->
          <ref bean="towFilter"/>
      </mvc:interceptor>
  </mvc:interceptors>
  ```

  

  ## 11.1多个拦截器的执行顺序

  - 若每个拦截器的preHandle()都返回true，此时多个拦截器的执行顺序和拦截器在SpringMVC的配置文件的配置顺序有关

  - preHandle()返回false，则和它之前的拦截器的preHandle()都会执行，postHandle()都不执行，返回false的拦截器之前的拦截器的afterComplation()会执行（因为异常被抓住了afterComplation不影响它执行）





# 12.异常处理器（监听所有的请求，只要有异常发生，就触发）

- 当发生异常时处理异常后做什么（比如：跳到异常界面）
- SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver
- HandlerExceptionResolver接口的实现类有：DefaultHandlerExceptionResolver和SimpleMappingExceptionResolver

### 12.1、基于配置的异常处理

- 想要使用自定义异常需要用SimpleMappingExceptionResolver，使用方式： 只用配置即可

  ```xml
  <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
      <property name="exceptionMappings">
          <props>
          	<!--
          		properties的键表示处理器方法执行过程中出现的异常
          		properties的值表示若出现指定异常时，设置一个新的视图名称，跳转到指定页面
          	-->
              <prop key="java.lang.ArithmeticException">error</prop>
          </props>
      </property>
      <!--
      	exceptionAttribute打印异常信息，放到request域中  ex是Attribute获取数据的key
      -->
      <property name="exceptionAttribute" value="ex"></property>
  </bean>
  ```

  

### 12.2.基于注解的异常处理

- @ControllerAdvice 标记为异常通知类
- @ExceptionHandler(NullPointerException.class)  什么异常触发通知

```java
/**
 * 配置异常处理器HandlerExceptionResolver，发生异常会跳到哪里
 * 当出现异常的时候 拦截器，什么方法会执行，什么方法不会？为什么
 *     只有 postHandle()不执行
 *  
 */

//定义异常处理控制器
@ControllerAdvice
public class OneException{

    @ExceptionHandler(NullPointerException.class)
    public String OneException(Exception ex, Model model){
        //输出异常信息到request域中
        model.addAttribute("ex", ex);
        return "error";
    }
}
```



# 13.全注解开发SpringmvC

- **使用配置类和注解代替web.xml和SpringMVC配置文件的功能**

1.代替web.xml

```java
/**
 * 创建初始化类，代替web.xml
 * -只需要继承AbstractAnnotationConfigDispatcherServletInitializer：配置分派器Servlet初始化器
 * AbstractAnnotationConfigDispatcherServletInitializer是抽象类
 */

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    //获取根配置类 配置spring容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebSpring.class};
    }

    //获取Servlet配置类 配置springmvc的
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebSpringMVC.class};
    }

    //配置指定DispatcherServlet的映射规则
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //添加过滤器
    @Override
    protected Filter[] getServletFilters() {
        //字符编码过滤器
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        //设置请求数据的字符编码
        encodingFilter.setForceRequestEncoding(true);
        //设置请求数据的字符编码
        encodingFilter.setForceResponseEncoding(true);

        //解决浏览器不能发送delete、put请求
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{encodingFilter,hiddenHttpMethodFilter};
    }
}
```



2.代替springMVC.xml

```java
/**
 * 配置SpringMVC 需要 implements WebMvcConfigurer
 *
 * 1.扫描组件 （作用扫描@Configuration）
 * 2.视图解析器 
 * 3.开启servlet默认的控制器 configureDefaultServletHandling
 * 4.开启mvc注解驱动  @EnableWebMvc
 * 5.异常处理 方法有二种，第一种不用写在这个配置文件中（自定义异常类使用注解） 第二种extendHandlerExceptionResolvers()
 * 6.拦截器  addInterceptors() 
 */

@Configuration
//包扫描 解析 @Controller （注入的IOC容器的类）
@ComponentScan("com.txs.day804.controller")
//开启mvc注解驱动
@EnableWebMvc
public class WebSpringMVC implements WebMvcConfigurer {

    //解决静态资源问题
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //启用
        configurer.enable();
    }

    //配置视图解析器
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public ISpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OneFilter()).addPathPatterns("/**");
    }

    /**
     * HandlerExceptionResolver是一个接口
     * SimpleMappingExceptionResolver是HandlerExceptionResolver的一个自定义异常的实现类
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

        //创建自定义的异常类
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties=new Properties();
        //                     什么异常的时候发生         @RequestMapping("error") 相当于映射路径，需要解析
        properties.setProperty("java.lang.NullPointerException","error");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        //将异常信息方法request域中
        simpleMappingExceptionResolver.setExceptionAttribute("ex");
        //将异常解析器，放到异常解析器集合中去
        resolvers.add(simpleMappingExceptionResolver);
    }
}
```



# 14. SSM整合

- 第一步首先是一个web项目 

  配置web.xml文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
  
      <servlet>
          <servlet-name>springMVC</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:applicationContext.xml</param-value>
          </init-param>
          <!--可以不写 但是第一次访问会很慢-->
          <load-on-startup>1</load-on-startup>
          <multipart-config>
              <max-file-size>1024000000</max-file-size>
              <max-request-size>1024000000</max-request-size>
          </multipart-config>
      </servlet>
  
      <servlet-mapping>
          <servlet-name>springMVC</servlet-name>
          <url-pattern>/</url-pattern>
      </servlet-mapping>
  
      <session-config>
          <session-timeout>15</session-timeout>
      </session-config>
  
  </web-app>
  ```



- 配置核心的配置文件 applicationContext.xml ,在核心配置文件中整合ssm三大框架

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <import resource="classpath:spring-mapper.xml"/>
      <import resource="classpath:spring-service.xml"/>
      <import resource="classpath:spring-mvc.xml"/>
  
  </beans>
  ```



- 然后在配置三层架构的配置文件

  - Dao层 spring-mapper.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:http="http://www.springframework.org/schema/context"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd">
    
        <context:property-placeholder location="classpath:jdbc.properties"/>
    
        <context:component-scan base-package="com.txs.day805.mapper"/>
    
        <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource" >
            <property name="driverClassName" value="${jdbc.driver}"/>
            <property name="url" value="${jdbc.url}"/>
            <property name="username" value="${jdbc.username}"/>
            <property name="password" value="${jdbc.password}"/>
        </bean>
    
        <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
            <property name="dataSource" ref="druidDataSource"/>
            <property name="configLocation" value="classpath:mybatis-config.xml"/>
        </bean>
        <!--方法一使用SqlSessionTemplate 创建SqlSession对象-->
    
        <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate" >
            <constructor-arg ref="sqlSessionFactory"/>
        </bean>
    
        <!--
         方法二 继承 SqlSessionDaoSupport 调用getSqlSession()
        <bean class="com.txs.day805.mapper.StudentMapperImp" id="studentMapperImp">
            <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
        </bean>-->
        
        <!--Mapper扫描器-->
        <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
            <property name="basePackage" value="com/txs/day805/mapper"/>
        </bean>
    
    </beans>
    ```

  

  - 业务层  spring-service 配置 主要写spring的事务控制 AOP

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    
        <context:component-scan base-package="com.txs.day805.service"/>
    
        <!--配置事务-->
        <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
            <property name="dataSource" ref="druidDataSource"/>
        </bean>
    </beans>
    ```

     

  -  控制层 ：spring-mvc    写一些 拦截器，视图解析器 

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    
        <mvc:annotation-driven/>
        <mvc:default-servlet-handler/>
        <context:component-scan base-package="com.txs.day805.controller"/>
    
        <!-- 配置Thymeleaf视图解析器 -->
        <bean id="viewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
            <property name="templateEngine" ref="templateEngine"/>
            <property name="characterEncoding" value="UTF-8"/>
        </bean>
    
        <!--模板引擎 -->
        <bean id="templateEngine" class="org.thymeleaf.spring6.SpringTemplateEngine">
            <property name="templateResolver" ref="templateResolver"/>
        </bean>
    
        <!--Spring资源模板解析器 -->
        <bean id="templateResolver" class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
            <property name="prefix" value="/WEB-INF/view/"/>
            <property name="suffix" value=".html"/>
            <property name="templateMode" value="HTML"/>
            <property name="characterEncoding" value="UTF-8"/>
        </bean>
    
        <!--
        视图控制器view-controller
        path：设置处理的请求地址
    	view-name：设置请求地址所对应的视图名称
    	-->
        <mvc:view-controller path="/" view-name="index"/>
    
    </beans>
    ```

    
