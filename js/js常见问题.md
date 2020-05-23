# 1、对象操作

## 		1.1 普通增删查改

```js
// 动态给对象添加属性的常见方式；直接 对象引用.属性值 ，然后该对象就会创建一个对应的属性并赋值
var obj = {};
obj.name = 'aaa';
obj.age = 22;

//打印结果
{name:"aaa",age:"22"}

//删除属性
delete obj.age;

//打印结果
{name:"aaa"}
```

​			

# 2、数组操作

## 	2.1 普通增删查改

```js
//创建数组
var array = []
//var array = new Array()

//往数组中添加元素
array.push(1);

//打印结果
[1]
```

## 	2.2 清空数组中的空值

```
/**
 * 过滤JS数组中的空值，返回新的数组
 * @param array 需要过滤的数组
 * @returns {Array} []
 */

function clear_arr_trim(array) {
    for(var i = 0 ;i<array.length;i++)
    {
        if(array[i] == "" || typeof(array[i]) == "undefined" || array[i] == null)
        {
            array.splice(i,1);
            i= i-1;
        }
    }
    return array;
}

/** 
 * es6语法
 * 过滤JS数组中的空值,假值等(es6语法)
 * @param array 需要过滤的数组 
 * @returns {Array} [] 
 */  
function filter_array(array) {  
  return array.filter(item=>item); 
} 
//调用
var arr = [undefined,undefined,1,'','false',false,true,null,'null'];   
```

## 	2.3 判断一个对象是否为数组、函数

```js
var array = []
1.Array.isArray(obj) //调用数组的isArray方法

2.obj instanceof Array  //判断对象是否是Array的实例

3.Object.prototype.toString.call(obj) ===‘[object Array]’  
  //Object.prototype.toString方法会取得对象的一个内部属性［［Class］］，然后依据这个属性，返回一个类似于［object Array］的字符串作为结果，call用来改变toString的this指向为待检测的对象
     
4.判断对象是否有push等数组的一些方法。（这个方法有兼容问题，但也是一个简单易用的方法）
    
5.obj.constructor===Array   //true

同理判断一个对象是否是函数：
console.log(Object.prototype.toString.call(obj)==='[object Function]')    //true或false 
```



# 3、字符串

## 	3.1 字符串和数组相互转换

```
// 一、数组转字符串
// 需要将数组元素用某个字符连接成字符串，示例代码如下：
var a, b,c;
a = new Array(1,2,3);[ 1, 2, 3 ]
b = a.join('-'); //1-2-3  使用-拼接数组元素
c = a.join(''); //123
console.log(a)
console.log(b)
console.log(c)

// 二、字符串转数组
// 实现方法为将字符串按某个字符切割成若干个字符串，并以数组形式返回，示例代码如下：
var str = 'ab+c+de';
var e = str.split('+'); // [ab, c, de]
var f = str.split(''); //[a, b, +, c, +, d, e]
console.log(e)
console.log(f)
```

## 	3.2 使用 += 进行字符串拼接

```
var tableHtml = "";
var nouse = [1,2,3]
for (var i=0;i<nouse.length;i++){

tableHtml += nouse[i]; //等于 tableHtml = tableHtml + nouse[i]
}
console.log(tableHtml)//结果为：123
```



# 4、保留小数点后面2位小数

```js
1.最笨的办法....... 
function get()
{
    var s = 22.127456 + "";
    var str = s.substring(0,s.indexOf(".") + 3);
    alert(str);
}

2. 正则表达式效果不错
<script type="text/javascript">
onload = function(){
    var a = "23.456322";
    var aNew;
    var re = /([0-9]+\.[0-9]{2})[0-9]*/;
    aNew = a.replace(re,"$1");
    alert(aNew);
}
</script>

3. 他就比较聪明了.....
<script>
var num=22.127456;
alert( Math.round(num*100)/100);
</script>

4.会用新鲜东西的朋友....... 但是需要 IE5.5+才支持。
 <script>
var num=22.127456;
alert( num.toFixed(2));
</script>
```





# 5、传递参数方式

```js
1、通过url传递参数和获取url中的参数：（注意：传递中文会乱码）

	传递参数：window.location.href = "payment.html?id="+id+"&discount="+discount;

	获取url中指定key（参数）的值	var couponid = getUrlParam("couponid");


2、通过cookie传递参数：

3、通过sessionStorage传递参数：（注意：若浏览器关闭就会失效，若重复为一个变量set，那么后者就会覆盖前者）

	a、页面跳转之前，在前一个页面存储参数，使用sessionStorage
               
	 sessionStorage.setItem("userId",userId);
	 sessionStorage.setItem("openid",wxUuid);

	b、页面跳转之后，使用sessionStorage

	 sessionStorage.getItem("userId");

```



# 6、判断字符串中是否包含某个字符串

```js
var str = "123";
console.log(str.indexOf("3") != -1 );  // true
//indexOf()方法返回指定的字符串值在字符串中首次出现的位置。若要检索的字符串值没有出现，则该方法返回 -1。
 

var str = "123";
console.log(str.search("3") != -1 );  // true
//search()方法用于检索字符串中指定的子字符串，或检索与正则表达式相匹配的子字符串。如果没有找到任何匹配的子串，则返回 -1。
 
//match() 方法可在字符串内检索指定的值，或找到一个或多个正则表达式的匹配。
var str = "123";
var reg = RegExp(/3/);
if(str.match(reg)){
    // 包含
    console.log("-------------------")
}

//RegExp 对象方法

//test() 方法用于检索字符串中指定的值。返回 true 或 false。
var str = "123";
var reg = RegExp(/3/);
console.log(reg.test(str)); // true

//exec()方法用于检索字符串中的正则表达式的匹配。返回一个数组，其中存放匹配的结果。如果未找到匹配，则返回值为 null。
var str = "123";
var reg = RegExp(/3/);
if(reg.exec(str)){
    // 包含
    console.log("=================")
}
```



# 7、json 字符串和json对象相互转换

```js
//json对象转json串
var arr = [1,2,3, { a : 1 } ];
var json = JSON.stringify( arr );
console.log(typeof json)

//json字符串转json对象
var jsonStr = '[1,2,3,{"a":1}]';
var jsonObject = JSON.parse( jsonStr );
console.log(typeof jsonObject)
```



# 8、

 



# 9、页面跳转方式

```js
//第一种：(跳转到b.html)
<script language="javascript" type="text/javascript">
window.location.href="b.html";
</script>

//第二种：（返回上一页面）
<script language="javascript">
window.history.back(-1);
</script>

//第三种：
<script language="javascript">
window.navigate("b.html");
</script>

//第四种：
<script language="JavaScript">
self.location=’b.html’;
</script>

//第五种：
<script language="javascript">
top.location=’b.html’;
</script>
```



# 10、页面自动加载的几种方法

```js
一、JS方法
1.最简单的调用方式，直接写到html的body标签里面：
<body onload="myfunction()">
<html> <body onload="func1();func2();func3();"></body> </html>

2.在JS语句调用：
<script type="text/javascript">
　　function myfun(){ 
    	alert("this window.onload"); 　　
	} 　　/*用window.onload调用myfun()*/　　
window.onload = myfun;//不要括号
</script>

3.<script type="text/javascript">
window.onload=function(){
func1();
func2();
func3(); }
</script>

二、JQ方法
1.整个页面的document全部加载完成以后执行。不幸的这种方式不仅要求页面的DOM tree全部加载完成，而且要求所有的外部图片和资源全部加载完成。更不幸的是，如果外部资源，例如图片需要很长时间来加载，那么这个js方法执行感觉就比较慢了。也就是说这是一种最严谨的页面加载完再执行方法的方法。
1	window.onload =function() { $("table tr:nth-child(even)").addClass("even"); //这个是jquery代码 };

2.仅只需要加载所有的DOM结构，在浏览器把所有的HTML放入DOM tree之前就执行方法。包括在加载外部图片和资源之前。
1	$(document).ready(function() { $("table tr:nth-child(even)").addClass("even"); //任何需要执行的js特效 });

还有一种简写方式
1	$(function() { $("table tr:nth-child(even)").addClass("even"); //任何需要执行的js特效 });
```



# 11、格林威治时间和普通时间互转

```
1.GMT转普通格式
GMTToStr(time){
    let date = new Date(time)
    let Str=date.getFullYear() + '-' +
    (date.getMonth() + 1) + '-' + 
    date.getDate() + ' ' + 
    date.getHours() + ':' + 
    date.getMinutes() + ':' + 
    date.getSeconds()
    return Str
}

2.普通格式转GMT
StrToGMT(time){
    let GMT = new Date(time)
    return GMT
}

测试：
Print(){
    let DateTime='Thu Jun 22 2017 19:07:30 GMT+0800'
    let a=this.GMTToStr(DateTime)
    console.log(a)
}
```



# 12、判断字段不为空串、null、undefined、0 

```
//若a为空串、null、undefined、数字0，!a都会返回true；但若a为"0"时，返回false，即"0"也会被当做一个值

var a = null

//判断
if(!a){
console.log("-----------------")
}
```

