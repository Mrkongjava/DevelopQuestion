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

