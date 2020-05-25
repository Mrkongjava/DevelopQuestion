生成uuid的多种方法

 1、java-uuid-generator依赖包，可以提供基于事件的uuid

(1) 引入依赖

```xml
<dependency>
   <groupId>com.fasterxml.uuid</groupId>
   <artifactId>java-uuid-generator</artifactId>
   <version>3.1.5</version>
</dependency>
```

(2) 代码实现

```
//引入指定事件类型的uuid生成器作为成员变量，这里是基于时间的uuid生成器（还有随机的uuid生成器、指定名字的uuid生成器等）
private TimeBasedGenerator uuid = Generators.timeBasedGenerator();

//获得一个uuid
uuid.generate().toString();
```



2、Jdk中也提供了一个随机的uuid生成器

 