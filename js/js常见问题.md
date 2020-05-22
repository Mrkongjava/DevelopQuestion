# 1、往对象中添加属性

```
// 动态给对象添加属性的常见方式；直接 对象引用.属性值 ，然后该对象就会创建一个对应的属性并赋值
var obj = {};
obj.name = 'aaa';
obj.age = 22;

//打印结果
{name:"aaa",age:"22"}
```



# 2、往数组中添加元素(push)

```
var array = []
array.push(1);

//打印结果
[1]
```



# 3、JS 保留小数点后面2位小数

```
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



# 4、清空JS数组中的空值

```
/**
 * 过滤JS数组中的空值，返回新的数组
 * @param array 需要过滤的数组
 * @returns {Array} []
 */

function clear_arr_trim(array) {
    for(var i = 0 ;i<array.length;i++)
    {
        if(array[i] == "" || typeof(array[i]) == "undefined")
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



# 5、判断一个对象是不是Array

```
1.Array.isArray(obj) 调用数组的isArray方法

2.obj instanceof Array  判断对象是否是Array的实例

3.Object.prototype.toString.call(obj) ===‘[object Array]’  
  Object.prototype.toString方法会取得对象的一个内部属性［［Class］］，然后依据这个属性，返回一个类似于［object Array］的字符串作为结果，call用来改变toString的this指向为待检测的对象
     
4.判断对象是否有push等数组的一些方法。（这个方法有兼容问题，但也是一个简单易用的方法）
    
5.obj.constructor===Array   //true

同理判断一个对象是否是函数：
console.log(Object.prototype.toString.call(obj)==='[object Function]')    //true或false 
```



# 6、判断字符串中是否包含某个字符串

```
String对象的方法

方法一: indexOf()   (推荐)
var str = "123";
console.log(str.indexOf("3") != -1 );  // true
indexOf() 方法可返回某个指定的字符串值在字符串中首次出现的位置。如果要检索的字符串值没有出现，则该方法返回 -1。
 
方法二: search() 
var str = "123";
console.log(str.search("3") != -1 );  // true
search() 方法用于检索字符串中指定的子字符串，或检索与正则表达式相匹配的子字符串。如果没有找到任何匹配的子串，则返回 -1。
 
方法三:match()
var str = "123";var reg = RegExp(/3/);if(str.match(reg)){
    // 包含        
}
match() 方法可在字符串内检索指定的值，或找到一个或多个正则表达式的匹配。
 
 
 
RegExp 对象方法

方法四:test() 
var str = "123";var reg = RegExp(/3/);
console.log(reg.test(str)); // true
test() 方法用于检索字符串中指定的值。返回 true 或 false。
 
方法五:exec()
var str = "123";var reg = RegExp(/3/);if(reg.exec(str)){
    // 包含        
}
exec() 方法用于检索字符串中的正则表达式的匹配。返回一个数组，其中存放匹配的结果。如果未找到匹配，则返回值为 null。
```



# 7、json 字符串和数组相互转换

```
//数组转json串var 
arr = [1,2,3, { a : 1 } ];
JSON.stringify( arr );

//json字符串转数组var jsonStr = '[1,2,3,{"a":1}]';
JSON.parse( jsonStr );
```

