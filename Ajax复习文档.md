# AJAX（Asynchronous Javascript And Xml）



ajax的介绍

- AJAX可以让浏览器发送一种特殊的请求，**异步请求实现页面局部刷新**
- **基于js开发** 
- AJAX可以做到在同一个网页中同时启动多个请求，类似于在同一个网页中启动“多线程”，一个“线程”一个“请求”。



## 1.传统请求及缺点

```js
<input type="button" onclick="oldrequest()" value="传统请求2">
<!--js代码-->

<script type="text/javascript">
  function oldrequest(){
    //任选其一 “oldRequestServlet” 映射路径
    window.open("oldRequestServlet")
    window.location.href="oldRequestServlet";
    document.location.href="oldRequestServlet";
  }
//注意: 没有写‘/’ 会自动添加项目名 写了‘/’表示当前路径就是根路径
```



# 2.Ajax的核心对象

### 1.XMLHttpRequest 文档

- XMLHttpRequest对象的方法

| 方法                                          | 描述                                                         |
| :-------------------------------------------- | :----------------------------------------------------------- |
| getAllResponseHeaders()                       | 返回头部信息                                                 |
| getResponseHeader()                           | 返回特定的头部信息                                           |
| open(*method*, *url*, *async*, *user*, *psw*) | 规定请求method：请求类型 GET 或 POSTurl：文件位置async：true（异步）或 false（同步）user：可选的用户名称psw：可选的密码 |
| send()                                        | 将请求发送到服务器，用于 GET 请求                            |
| send(*string*)                                | 将请求发送到服务器，用于 POST 请求                           |
| setRequestHeader()                            | 向要发送的报头添加标签/值对                                  |

- XMLHttpRequest对象的属性

| 属性               | 描述                                                         |
| :----------------- | :----------------------------------------------------------- |
| onreadystatechange | 定义当 readyState 属性发生变化时被调用的函数                 |
| readyState         | 保存 XMLHttpRequest 的状态。0：请求未初始化     1：服务器连接已建立     2：请求已收到    3：正在处理请求    4：请求已完成且响应已就绪 |
| responseText       | 以字符串返回响应数据                                         |
| responseXML        | 以 XML 数据返回响应数据                                      |
| status             | 返回请求的状态号200: "OK"403: "Forbidden"404: "Not Found"    |
| statusText         | 返回状态文本（比如 "OK" 或 "Not Found"）                     |



### 2.怎么实现ajax请求

```js
window.onload = function (){
        //获取触发ajax请求的Dom对象
        var bt = document.getElementById("bt");
        //使用点击事件
        bt.onclick = function (){
            //1.创建ajax核心对象
            var request = new XMLHttpRequest();
            request.onreadystatechange = function (){
                if (request.readyState==4){ 
                    if (request.status==200) {
                        //获取服务器响应的响应数据
                        document.getElementById("div").innerHTML=request.responseText;
                    }else {
                        alert("服务器错误！")
                    }
                }
            }
            //3.开启get请求异步请求通道，配置好参数， true表示异步（可以动态填写参数）
            request.open("get","Test01?user=风少&password=123456",true)
            
            //3.开启post请求
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded") // 设置请求头的内容类型。模拟form表单提交数据。
            
            //4.send函数中的参数就是发送的数据，这个数据在“请求体”当中发送。
            //get请求直接发送
            request.send();
            
            //4.post请求填写参数 （可以动态填写参数）
            xhr.send("username="+风少+"&password="+123456)
        }
    }
解决get缓存问题
- 可以采用时间戳："url?t=" + new Date().getTime()
- 或者可以通过随机数："url?t=" + Math.random()
```

- 什么是AJAX GET请求缓存问题呢？
  - 在HTTP协议中是这样规定get请求的：get请求会被缓存起来。
  - 发送AJAX GET请求时，在同一个浏览器上**，前后发送的AJAX请求路径一样的话**，对于低版本的IE来说，第二次的AJAX GET请求会走缓存，**不走服务器。**

> POST请求在HTTP协议中规定的是：POST请求不会被浏览器缓存。





# 3.发送ajax需要数据传输，用到一个技术“基于JSON的数据交换” and 基于XML的数据交换



### 1.在WEB前端中，如何将一个json格式的字符串转换成json对象

```js
var jsonStr = "{\"username\" : \"zhangsan\", \"password\" : \"1233344\"}"
var jsonObj = JSON.parse(jsonStr)
console.log(jsonObj.username)
console.log(jsonObj.password)

//注意：在js中 【】表示数组 {}表示json 对象（{}外面没有双引号“”）
```



### 2.在后端将java对象转成JSON格式的字符串，响应给前端的浏览器

```js
//使用之前需要使用对象转json的工具 ，也可以自己写
//可以使用阿里巴巴写的fastjson组件

List<Student> studentList = new ArrayList<>();
while (rs.next()) {
    // 取出数据
    String name = rs.getString("name");
    int age = rs.getInt("age");
    String addr = rs.getString("addr");
    // 将以上数据封装成Student对象
    Student s = new Student(name, age, addr);
    // 将Student对象放到List集合
    studentList.add(s);
}
// 将List集合转换成json字符串 重点在这 上面是准备数据
jsonStr = JSON.toJSONString(studentList);

```



### 3.基于XML的数据交换(了解)  

- 前端接收后端发送的xml字符串 （后端传的数据必须是xml类型的字符串）

```js
 window.onload = function(){
        document.getElementById("btn").onclick = function(){
            // 1.创建XMLHTTPRequest对象
            var xhr = new XMLHttpRequest();
            // 2.注册回调函数
            xhr.onreadystatechange = function () {
                if (this.readyState == 4) {
                    if (this.status == 200) {
                        // 服务器端响应了一个XML字符串，这里怎么接收呢？
                        // 使用XMLHTTPRequest对象的responseXML属性，接收返回之后，
                        //可以自动封装成document对象（文档对象）
                        var xmlDoc = this.responseXML
                        //转成dom对象之后就就像js一样了，Dom树   student标签的名字
                        var students = xmlDoc.getElementsByTagName("student")//返回的是数组
                   
                        //拼接html代码 标签 加 Dom树里的数据
                        var html = "";
                        for (var i = 0; i < students.length; i++) {
                            var student = students[i]
                            // 获取<student>元素下的所有子元素
                            html += "<tr>"
                            html += "<td>"+(i+1)+"</td>"
                                
                            /**
                                 <students>
                                    <student>
                                        <name>zhangsan</name>
                                        <age>20</age>
                                    </student>
                                    <student>
                                        <name>lisi</name>
                                        <age>22</age>
                                    </student>
                                </students>
                            */
                            //student标签下的所有子标签
                            var nameOrAge = student.childNodes
                            for (var j = 0; j < nameOrAge.length; j++) {
                                var node = nameOrAge[j]
                                if (node.nodeName == "name") {
                                    //console.log("name = " + node.textContent)
                                    html += "<td>"+node.textContent+"</td>"
                                }
                                if (node.nodeName == "age") {
                                    //console.log("age = " + node.textContent)
                                    html += "<td>"+node.textContent+"</td>"
                                }
                            }
                            html += "</tr>"
                        }
                        document.getElementById("stutbody").innerHTML = html
                    }else{
                        alert(this.status)
                    }
                }
            }
            // 3.开启通道
            xhr.open("GET", "/ajax/ajaxrequest6?t=" + new Date().getTime(), true)
            // 4.发送请求
            xhr.send()
        }
    }
```



# 4.AJAX代码封装

- jQuery只是一个前端的库，可以说是一个工具类，提高WEB前端的开发效率（减少代码的编写）



手写一个简易版的jquery工具库

```js
//在js中：函数和对象的区别
//1.函数的创建和对象的是一样的，但调用方式不同
//2.函数：函数名()   、 对象：new 对象名(可以传参)
//3.还是是可以赋值的，或者说函数名可以改

//定了一个方法 
function jQuery(selector){
    if (typeof selector == "string") {
        if (selector.charAt(0) == "#") {
            domObj = document.getElementById(selector.substring(1))
            return new jQuery()
        }
    }
    if (typeof selector == "function") {
        window.onload = selector
    }
    this.html = function(htmlStr){
        domObj.innerHTML = htmlStr
    }
    this.click = function(fun){
        domObj.onclick = fun
    }
    this.focus = function (fun){
        domObj.onfocus = fun
    }
    this.blur = function(fun) {
        domObj.onblur = fun
    }
    this.change = function (fun){
        domObj.onchange = fun
    }
    this.val = function(v){
        if (v == undefined) {
            return domObj.value
        }else{
            domObj.value = v
        }
    }

    // 静态的方法，发送ajax请求
    /**
     * 分析：使用ajax函数发送ajax请求的时候，需要程序员给我们传过来什么？
     *      请求的方式(type)：GET/POST
     *      请求的URL(url)：url
     *      请求时提交的数据(data)：data
     *      请求时发送异步请求还是同步请求(async)：true表示异步，false表示同步。
     */
    // jsonArgs 传的是json对象 在js中 {}定义的是json对象
    jQuery.ajax = function(jsonArgs){
        // 1.
        var xhr = new XMLHttpRequest();
        // 2.
        xhr.onreadystatechange = function(){
            if (this.readyState == 4) {
                if (this.status == 200) {
                    // 我们这个工具类在封装的时候，先不考虑那么多，假设服务器返回的都是json格式的字符串。
                    var jsonObj = JSON.parse(this.responseText)
                    //对象.属性 ，属性也可以接受函数  调用函数 参数是服务器返回的对象
                    jsonArgs.success(jsonObj)
                }
            }
        }

        if (jsonArgs.type.toUpperCase() == "POST") {
            // 3.
            xhr.open("POST", jsonArgs.url, jsonArgs.async)
            // 4.
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
            xhr.send(jsonArgs.data)
        }

        if (jsonArgs.type.toUpperCase() == "GET") {
            xhr.open("GET", jsonArgs.url + "?" + jsonArgs.data, jsonArgs.async)
            xhr.send()
        }

    }
}
//$变量名 将jQuery的值（函数）赋值给$
$ = jQuery

// 这里有个细节，执行这个目的是为了让静态方法ajax生效。
new jQuery()
```

- 使用

```js
<script type="text/javascript" src="/ajax/js/jQuery-1.0.0.js"></script>
<script type="text/javascript">
    
    $(function(){
        $("#btn1").click(function(){
            $.ajax({
                type : "POST",
                url : "/ajax/ajaxrequest11",
                data : "username=" + $("#username").val(),
                async : true,
                success : function(json){
                    $("#div1").html(json.uname)
                  
                }
            })
        })
    })
</script>

//$("#div1")获取的不是#div对应的对象
```

