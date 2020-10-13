**1.第一种方法**

使用JSON-JAVA提供的方法，之前一直使用json-lib提供的方法转json，后来发现了这个开源项目，觉得用起来很不错，并且可以修改XML.java中的parse方法满足自己的转换需要。

1. 首先去git下载所需的java文件，并导入项目；Git：https://github.com/stleary/JSON-java

2. 使用XML.java中提供的XML.toJSONObject（xml）方法即可完成xml到json的转换，同时也可以对JSON进行格式

   ```java
   /* 第一种方法，使用JSON-JAVA提供的方法 */ 
   
   //将xml转为json 
   JSONObject xmlJSONObj = XML.toJSONObject(xml); 
   //设置缩进 
   String jsonPrettyPrintString = xmlJSONObj.toString(4); 
   //输出格式化后的json 
   System.out.println(jsonPrettyPrintString); 
   ```

 

**2.第二种方法**

使用json-lib的XMLSerializer对象

1. 创建XMLSerializer对象

2. 使用XMLSerializer的read(xml)方法即可

   ```java
   /* 第二种方法，使用json-lib提供的方法 */ 
   
   //创建 XMLSerializer对象 
   XMLSerializer xmlSerializer = *new* XMLSerializer(); 
   //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识） 
   String result = xmlSerializer.read(xml).toString(); 
   //输出json内容 
   System.out.println(result); 
   ```

   

**3.测试**

```java
public class Test { 
   public static void main(String[] args) { 
     String xml = "<class id="'1'"><student><name>aaaaaa</name><age>21</age></student><student><name>bbbbbb</name><age>22</age></student></class>";  

     /* 第一种方法，使用JSON-JAVA提供的方法 */ 
     //将xml转为json 
     JSONObject xmlJSONObj = XML.toJSONObject(xml); 
     //设置缩进 
     String jsonPrettyPrintString = xmlJSONObj.toString(4); 
     //输出格式化后的json 
     System.out.println(jsonPrettyPrintString); 
  
     /* 第二种方法，使用json-lib提供的方法 */ 
     //创建 XMLSerializer对象 
     XMLSerializer xmlSerializer = *new* XMLSerializer(); 
     //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识） 
     String result = xmlSerializer.read(xml).toString(); 
     //输出json内容 
     System.out.println(result); 
   } 
```

 

第一种方法输出：

```
 {"class": { 
   "id": 1, 
   "student": [ 
     { 
       "age": 21, 
       "name": "aaaaaa" 
     }, 
     { 
       "age": 22, 
      "name": "bbbbbb" 
     } 
   ] 
 }} 
```


第二种方法输出：

```
{"@id":"1","student":[{"name":"aaaaaa","age":"21"},{"name":"bbbbbb","age":"22"}]} 
```

 

源码下载：http://download.csdn.net/detail/lom9357bye/9690304

参考：http://hw1287789687.iteye.com/blog/2229267

http://heshans.blogspot.com/2014/01/java-library-to-convert-xml-to-json.html

 

 