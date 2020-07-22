#  解决返回的json格式的数据是乱码

 

在 controller 类中的 @Requestmapping 中添加如下代码可以解决返回的 json 数据是乱码：produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"



这段代码的作用是指定返回的数据的格式是json格式， 编码为utf-8这样就能解决乱码问题！

```
@RequestMapping(value="/itemcat/list", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
```

 

 

 

