# spring MVC 的MultipartFile转File读取

**第一种方法：**会在项目的根目录的临时文件夹下生成一个文件

```
 MultipartFile file = xxx;
 CommonsMultipartFile cf= (CommonsMultipartFile)file;    
 DiskFileItem fi = (DiskFileItem)cf.getFileItem();
 File f = fi.getStoreLocation();
```



**第二种方法：**transferTo(File dest)

**会在项目中生成一个新文件；**

 

**第三种方法：**File f = (File) xxx  强转即可。前提是要配置multipartResolver，要不然会报类型转换失败的异常。

```
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
   <property name="maxUploadSize" value="104857600"/>
   <property name="maxInMemorySize" value="4096"/>
</bean>
```

**没试过；**

 

**第四种方法：**转换为输入流，直接读取；

```
Workbook wb = Workbook.getWorkbook(xxx .getInputStream());
```



**第五种方法：**先转换为字节数组，没试过；

```
byte[] buffer = myfile.getBytes();
```



 