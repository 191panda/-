# 1.HTML5.0前 简介

> 全称：HyperText Markup Language（超文本标记语言）。

> 超文本：暂且简单理解为 “超级的文本”，和普通文本比，内容更丰富。 
>
> 标 记：文本要变成超文本，就需要用到各种标记符号。
>
>  语 言：每一个标记的写法、读音、使用规则，组成了一个标记语言。

![image-20230701203353054](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701203353054.png)

## 1.1HTML 文档声明

```html
<!DOCTYPE html>
或
<!DOCTYPE HTML>
或
<!doctype html
```

## 1.2HTML 字符编码

> 1. **ASCII ：大写字母、小写字母、数字、一些符号，共计128个。 **
> 2. **2. ISO 8859-1 ：在 ASCII 基础上，扩充了一些希腊字符等，共计是256个。**
> 3. **GB2312 ：继续扩充，收录了 6763 个常用汉字、682个字符。** 
> 4. **GBK ：收录了的汉字和符号达到 20000+ ，支持繁体中文。 5. UTF-8 ：包含世界上所有语言的：所有文字与符号。—— 很常用。**

注意事项

```
原则1：存储时，务必采用合适的字符编码 。
否则：无法存储，数据会丢失！
原则2：存储时采用哪种方式编码 ，读取时就采用哪种方式解码。
否则：数据错乱（乱码）！
```

## 1.3label 标签 

>  label 标签可与表单控件相关联，关联之后点击文字，与之对应的表单控件就会获取焦点。
>
>  两种与 label 关联方式如下： 
>
> 1. 让 label 标签的 for 属性的值等于表单控件的 id 。
> 2. 把表单控件套在 label 标签的里面。

## 1.4.HTML全局属性

![image-20230701213736941](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701213736941.png)

## 1.5meta 元信息



```html
1. 配置字符编码 <meta charset="utf-8">
2. 针对 IE 浏览器的兼容性配置。<meta http-equiv="X-UA-Compatible" content="IE=edge">
4. 配置网页关键字 <meta name="keywords" content="8-12个以英文逗号隔开的单词/词语">
5. 配置网页描述信息 <meta name="description" content="80字以内的一段话，与网站内容相关">
6. 针对搜索引擎爬虫配置：<meta name="robots" content="此处可选值见下表">
7. 配置网页作者：<meta name="author" content="tony">
8. 配置网页自动刷新 <meta http-equiv="refresh" content="10;url=http://www.baidu.com">
```



> https://developer.mozilla.org/zh-CN/docs/Web/HTML/Element/meta 参考文档



> ### **注意区分属性和样式**

# 2.css2介绍

CSS 的全称为：层叠样式表 ( Cascading Style Sheets ) 。 

CSS 也是一种标记语言，用于给 HTML 结构设置样式，例如：文字大小、颜色、元素宽高等等。



> 简单理解： CSS 可以美化 HTML , 让 HTML 更漂亮。 
>
> *核心思想： HTML 搭建结构， CSS 添加样式，实现了：结构与样式的分离。*

### 2.1样式表的优先级 

> **优先级规则：行内样式 > 内部样式 = 外部样式**



### 2.2 CSS语法规范 

![image-20230701214730767](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701214730767.png)

### 2.3、CSS选择器



CSS复合选择器

1. 基本选择器： ①通配选择器 ②元素选择器 ③类选择器 ④ ID 选择器 二、复合选择器： ①交集选择器

2. 复合选择器： 

3. ①交集选择器 

   > **选择器1选择器2 {}**

4. ②并集选择器 

5. ③后代选择器 

   > ：选择器1 选择器2 {} 

6. ④子元素选择器

   > 选择器1, 选择器2{}

7. 兄弟选择器

   >  选择器1+选择器2 {} 。就是紧挨着他的下一个，简记：睡在我下铺的兄弟。
   >
   > 选择器1~选择器2 {} 。 选中指定元素后，符合条件的所有兄弟元素。（简记：睡在我下铺的所有兄弟

8. 属性选择器

9. 伪类选择器

   ```css
   一、动态伪类：
   1. :link 超链接未被访问的状态。
   2. :visited 超链接访问过的状态。
   3. :hover 鼠标悬停在元素上的状态。
   4. :active 元素激活的状态。
   5. :focus 获取焦点的元素。
   
   二、结构伪类
   常用的：
   1. :first-child 所有兄弟元素中的第一个。
   2. :last-child 所有兄弟元素中的最后一个。
   3. :nth-child(n) 所有兄弟元素中的第 n 个。
   4. :first-of-type 所有同类型兄弟元素中的第一个。
   5. :last-of-type 所有同类型兄弟元素中的最后一个。
   6. :nth-of-type(n) 所有同类型兄弟元素中的 第n个 。
   
   三、否定伪类：
   :not(选择器) 排除满足括号中条件的元素。
   
   四、UI伪类：
   1. :checked 被选中的复选框或单选按钮。
   2. :enable 可用的表单元素（没有 disabled 属性）。
   3. :disabled 不可用的表单元素（有 disabled 属性）。
   
   五、目标伪类（了解）
   :target 选中锚点指向的元素。
   
   六、语言伪类（了解）
   :lang() 根据指定的语言选择元素（本质是看 lang 属性的值）。
   ```

   

10. 伪元素选择器

```
作用：选中元素中的一些特殊位置。

常用伪元素：
::first-letter 选中元素中的第一个文字。
::first-line 选中元素中的第一行文字。
::selection 选中被鼠标选中的内容。
::placeholder 选中输入框的提示文字。
::before 在元素最开始的位置，创建一个子元素（必须用 content 属性指定内容）。
::after 在元素最后的位置，创建一个子元素（必须用 content 属性指定内容）。
```



### 2.4.选择器的优先级（权重）

> ### !important 的权重，大于行内样式，大于所有选择器，权重最高！

![image-20230701215954383](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701215954383.png)



### 2.5.CSS三大特性

1.层叠性 2.继承性

3.优先级

``` css
 !important > 行内样式 > ID选择器 > 类选择器 > 元素选择器 > * > 继承的样式。
```

### 2.6.CSS常用属性

#### 2.6.1 CSS列表属性

![image-20230701220559152](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701220559152.png)

#### 2.6.2. CSS表格属性



![image-20230701220622838](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701220622838.png)

![image-20230701220724475](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701220724475.png)



#### 2.6.3. CSS背景属性

![image-20230701220805410](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701220805410.png)

![image-20230701220823574](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701220823574.png)

### 2.7.margin 注意事项

```css
1. 子元素的 margin ，是参考父元素的 content 计算的。（因为是父亲的 content 中承装着
子元素）
2. 上 margin 、左 margin ：影响自己的位置；下 margin 、右 margin ：影响后面兄弟元素
的位置。
3. 块级元素、行内块元素，均可以完美地设置四个方向的 margin ；但行内元素，左右
margin 可以完美设置，上下 margin 设置无效。
4. margin 的值也可以是 auto ，如果给一个块级元素设置左右 margin 都为 auto ，该块级
元素会在父元素中水平居中。
5. margin 的值可以是负值。
```

#### 2.7.1 margin 塌陷问题

``` css
什么是 margin 塌陷？
第一个子元素的上 margin 会作用在父元素上，最后一个子元素的下 margin 会作用在父元素上。
如何解决 margin 塌陷？
方案一： 给父元素设置不为 0 的 padding 。
方案二： 给父元素设置宽度不为 0 的 border 。
方案三：给父元素设置 css 样式 overflow:hidden
```

#### 2.7.2 margin 合并问题 

什么是 margin 合并？ 上面兄弟元素的下外边距和下面兄弟元素的上外边距会合并，取一个最大的值，而不是相加。 如何解决 margin 塌陷？ 无需解决，布局的时候上下的兄弟元素，只给一个设置上下外边距就可以了





### 2.8.隐藏元素的方式

![image-20230701222735278](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230701222735278.png)

### 2.9.样式的继承

![image-20230702150841473](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702150841473.png)

### 2.10.元素之间的空白问题和行内块的幽灵空白问题

>**行内元素、行内块元素，彼此之间的换行会被浏览器解析为一个空白字符。**

> 行内块元素与文本的基线对齐，而文本的基线与文本最底端之间是有一定距离的



### 3.定位

> 注意： 
>
> 1. 发生固定定位、绝对定位后，元素都变成了定位元素，默认宽高被内容撑开，且依然可以设 置宽高。 
> 2. 发生相对定位后，元素依然是之前的显示模式。

####  3.1.相对定位  position:relative

![image-20230702161549701](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702161549701.png)

#### 3.2. 绝对定位  position: absolute

![image-20230702161653035](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702161653035.png)

#### 3.3. 固定定位  position: fixed 

![image-20230702161744499](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702161744499.png)

#### 3.4.粘性定位 position:sticky 

> 解释一下 
>
> 当滑动滚动条到一定位置的时候 固定住

![image-20230702161940874](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702161940874.png)



# 4.html 5

## 1.新增语义化标签

```
header 整个页面，或部分区域的头部 双
footer 整个页面，或部分区域的底部 双
nav 导航 双
article 文章、帖子、杂志、新闻、博客、评论等。 双
section  页面中的某段文字，或文章中的某段文字（里面文字通常里面会包含
标题）。 双 
aside 侧边栏  双
```

## 2.新增状态标签

### 2.1.meter 标签

> 语义：定义已知范围内的标量测量。也被称为 gauge （尺度），双标签，例如：电量、磁盘用量 等。
>
> 电池的电量

![image-20230702163534624](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702163534624.png)

### 2.2 progress 标签

![image-20230702163603405](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702163603405.png)

### 2.3新增列表标签 datalist

```html
 <input type="text" list="mydata">
    <datalist id="mydata">
        <option value="周冬雨">周冬雨</option>
        <option value="周杰伦">周杰伦</option>
        <option value="温兆伦">温兆伦</option>
        <option value="马冬梅">马冬梅</option>
    </datalist>
```

### 2.4 details 和summary

```html
<details>
<summary>如何走上人生巅峰？</summary>
<p>一步一步走呗</p>
</details>
```



## 3.新增表单功能

![image-20230702165056636](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702165056636.png)

### 3.1. input 新增属性值

![image-20230702190245100](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702190245100.png)



# 5.css3 简介

```
CSS3 在未来会按照模块化的方式去发展： https://www.w3.org/Style/CSS/current-work.html
CSS3 的新特性如下：
新增了更加实用的选择器，例如：动态伪类选择器、目标伪类选择器、伪元素选择器等等。
新增了更好的视觉效果，例如：圆角、阴影、渐变等。
新增了丰富的背景效果，例如：支持多个背景图片，同时新增了若干个背景相关的属性。
新增了全新的布局方案 —— 弹性盒子。
新增了 Web 字体，可以显示用户电脑上没有安装的字体。
增强了颜色，例如： HSL 、 HSLA 、 RGBA 几种新的颜色模式，新增 opacity 属性来控制
透明度。
增加了 2D 和 3D 变换，例如：旋转、扭曲、缩放、位移等。
增加动画与过渡效果，让效果的变换更具流线性、平滑性。
```



## 5.1.CSS3私有前缀

> W3C 标准所提出的某个 CSS 特性，在被浏览器**正式支持之前**，浏览器厂商会根据浏览器的内核， 使用私有前缀来测试该 CSS 特性，在**浏览器正式支持该 CSS 特性后**，就不需要私有前缀了。

```
Chrome 浏览器： -webkit
Safari 浏览器： -webkit
Firefox 浏览器： -moz
Edge 浏览器： -webkit
```

## 5.2、CSS3 基本语法

```
1. CSS3 新增长度单位
   rem 根元素字体大小的倍数，只与根元素字体大小有关。
   vw 视口宽度的百分之多少 10vw 就是视口宽度的 10% 。
   vh 视口高度的百分之多少 10vh 就是视口高度的 10% 。
```

### 5.3.CSS3 新增盒模型相关属性

#### 5.3.1. box-sizing 怪异盒模型

![image-20230702210054488](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702210054488.png)

#### 5.3.2.resize 调整盒子大小

![image-20230702210129927](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702210129927.png)

#### 5.3.3. box-shadow 盒子阴影

```css
语法：         水平位置、垂直位置、模糊值、外延值、颜色、内阴影
box-shadow: h-shadow v-shadow blur spread color inset;
```



> /* 写四个值，含义**：水平位置、垂直位置、模糊值、颜色** */ 
>
> box-shadow: 10px 10px 10px red;

#### 5.3.4.opacity **不透明度**

![image-20230702210402841](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702210402841.png)

### 5.4.CSS3 新增背景属性

#### 5.4.1 background-origin

```
语法
1.background-origin: padding-box 
2.background-origin: border-box 
3.background-origin: content-box
5.background-origin: background-clip
```

#### 5.4.2 background-clip

```
1. border-box ： 从 border 区域开始向外裁剪背景。 —— 默认值
2. padding-box ： 从 padding 区域开始向外裁剪背景。
3. content-box ： 从 content 区域开始向外裁剪背景。
4. text ：背景图只呈现在文字上。
注意：若值为 text ，那么 backgroun-clip 要加上 -webkit- 前缀。
```

#### 5.4.3.background-size 

作用：设置背景图的尺寸。

## 5.5.开启弹性布局display:flex

> 1.传统布局是指：基于传统盒状模型，主要靠： **display 属性 + position 属性 + float 属性。**
>
> 2.flex 布局目前在移动端应用比较广泛，因为传统布局不能很好的呈现在移动设备上

### 5.5.1.什么是伸缩容器、伸缩项目

> **伸缩容器：** 开启了 flex 的元素，就是：伸缩容器。
>
> **伸缩项目：**伸缩容器所有子元素自动成为了：伸缩项目。

### 5.5.2.什么是 主轴与侧轴

>主轴： 主轴默认是水平的，
>
>侧轴：默认方向是：从上到下

### 5.5.3主轴的属性

#### 5.5.3.1.主轴方向     **flex-direction**

```css
flex-direction:
1. row ：主轴方向水平从左到右 —— 默认值
2. row-reverse ：主轴方向水平从右到左。
3. column ：主轴方向垂直从上到下。
4. column-reverse ：主轴方向垂直从下到上。
```

![image-20230702225742011](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230702225742011.png)

### 5.5.3.2.主轴换行方式 flex-wrap

![image-20230703022323677](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703022323677.png)

#### 5.5.3.3.主轴对齐方式 justify-content

```
1. flex-start ：主轴起点对齐。—— 默认值
2. flex-end ：主轴终点对齐。
3. center ：居中对齐
4. space-between ：均匀分布，两端对齐（最常用）。
5. space-around ：均匀分布，两端距离是中间距离的一半。
6. space-evenly ：均匀分布，两端距离与中间距离一致。
```

![image-20230703022511999](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703022511999.png)

### 5.5.4. 侧轴

#### 5.5.4.1.侧轴对齐方式  一行的情况 align-items

```
1. flex-start ：侧轴的起点对齐。
2. flex-end ：侧轴的终点对齐。
3. center ：侧轴的中点对齐。
4. baseline : 伸缩项目的第一行文字的基线对齐。
5. stretch ：如果伸缩项目未设置高度，将占满整个容器的高度。—— （默认值）
```

![image-20230703023033319](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703023033319.png)

#### 5.5.4.2.多行的情况  align-content

```
1. flex-start ：与侧轴的起点对齐。
2. flex-end ：与侧轴的终点对齐。
3. center ：与侧轴的中点对齐。
4. space-between ：与侧轴两端对齐，中间平均分布。
5. space-around ：伸缩项目间的距离相等，比距边缘大一倍。
6. space-evenly : 在侧轴上完全平分。
7. stretch ：占满整个侧轴。—— 默认值
```

![image-20230703023150426](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703023150426.png)

![image-20230703023204507](C:\Users\Tang\AppData\Roaming\Typora\typora-user-images\image-20230703023204507.png)

### 5.5.5.flex 实现水平垂直居中

> 方法一：父容器开启 flex 布局，随后使用 justify-content 和 align-items 实现水平垂直居中

```css
.outer {
width: 400px;
height: 400px;
background-color: #888;
display: flex;
justify-content: center;
align-items: center;
}
.inner {
width: 100px;
height: 100px;
background-color: orange;
}

```

> 方法二：父容器开启 flex 布局，随后子元素 margin: auto

```css
.outer {
width: 400px;
height: 400px;
background-color: #888;
display: flex;
}
.inner {
width: 100px;
height: 100px;
background-color: orange;
margin: auto;
}
```

