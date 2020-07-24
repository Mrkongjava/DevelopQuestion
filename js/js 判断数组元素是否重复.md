**第一种方法：**但是下面的这种方法数字字符串类似相同，返回的还是真，有点不靠谱，如果是其它的字符是可以的

```
var ary11 = new Array("1", "ff", "11", "aa", "2222");
// 验证重复元素，有重复返回true；否则返回false
function mm(a) {
  return /(\x0f[^\x0f]+)\x0f[\s\S]*\1/.test("\x0f" + a.join("\x0f\x0f") + "\x0f");
}
mm(ary11)
alert(mm(ary11))
```



**第二种方法：**但是下面的这种方法数字字符串类似相同，返回的也还是真，有点不靠谱，如果是其它的字符是可以的

```
var ary = ["1", "ff", "11", "aa", "2222"]
var s = ary.join(",") + ",";
for(var i = 0; i < ary.length; i++) {
  if(s.replace(ary[i] + ",", "").indexOf(ary[i] + ",") > -1) {
    alert("数组中有重复元素：" + ary[i]);
    break;
  }
}
```



**第三种方法：**这种方法好像不会有什么问题

```
var ary = new Array("11", "222", "33", "111", "22");
var nary = ary.sort();
for(var i = 0; i < nary.length - 1; i++) {
  if(nary[i] == nary[i + 1]) {
    alert("重复内容：" + nary[i]);
  }
}
```



**第四种方法：**这种方法好像不会有什么问题（亲测可用，字符串数组）

```
var ary = new Array("1111", "222", "33", "111", "22");
alert(isRepeat(ary));
// 验证重复元素，有重复返回true；否则返回false
function isRepeat(arr) {
  var hash = {};
  for(var i in arr) {
    if(hash[arr[i]]) {
      return true;
    }
    // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
    hash[arr[i]] = true;
  }
  return false;
}
```

