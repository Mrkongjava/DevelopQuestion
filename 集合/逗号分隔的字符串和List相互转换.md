# [java中逗号分隔的字符串和List相互转换](https://www.cnblogs.com/itzyz/p/10844004.html)

1、将逗号分隔的字符串转换为List

```
String str = "a,b,c"; 
List<String> result = Arrays.asList(str.split(","));
```

2、将List转换为逗号分隔的字符串

（1） 利用Guava的Joiner

```
List<String> list = new ArrayList<String>(); 
list.add("a"); 
list.add("b"); 
list.add("c"); 
 
String str = Joiner.on(",").join(list); 
```

（2）利用Apache Commons的StringUtils

```
List<String> list = new ArrayList<String>(); 
list.add("a"); 
list.add("b"); 
list.add("c"); 

String str = StringUtils.join(list.toArray(), ","); 
```

